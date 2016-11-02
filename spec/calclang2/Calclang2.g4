grammar Calclang2;

WS: (' ' | '\n' | '\r' | '\r\n') -> skip;
SL_CMT: '//' ~('\n')* -> channel(HIDDEN);

BRACKETS: '[]';

LET: 'let';
MUT: 'mut';

INT: '-'? [0-9]+;
STRING: '\'' ('\\\'' | ~('\n' | '\''))* '\'';
ID: [a-z] [A-Za-z0-9_]*;

compilationUnit: topLevelDef*;

topLevelDef:
    'fn' funcSignature funcBody #TopLevelFuncDef
    | 'const' (variableDeclaration ',')* variableDeclaration #ConstDef
    | 'import' importSource #ImportDecl
;

importSource:
    STRING #StringSource
    | '<' name=ID '>' #GlobalSource
;

funcSignature: name=ID '(' ((params+=paramSpec ',')* params+=paramSpec)? ')';

funcBody:
    block #BlockBody
    | expr #ExprBody
;

paramSpec:
    ID #SimpleParamSpec
    | 'fn' funcSignature #FunctionParamSpec
    | ID BRACKETS+ #TupleParamSpec
;

expr:
    ID #IdentifierExpr
    | INT #IntegerLiteralExpr
    | '{' lower=expr? '.' '.' exclusive='.'? upper=expr '}' #RangeLiteralExpr
    | '{' (expr ',')+ expr '}' #TupleLiteralExpr
    | callee=ID '(' ((args+=expr ',')* args+=expr)? ')' #InvocationExpr
    | left=expr right=expr #AdjacentExprs
    | left=expr assignmentOp right=expr #AssignmentExpr
    | left=expr binaryOp right=expr #BinaryExpr
    | '(' expr ')' #ParenthesizedExpr
;

assignmentOp: '^=' | '*=' | '/=' | '+=' | '-=' | '%=';
binaryOp: '^' | '*' | '/' | '+' | '-' | '%' | '+-' | '==' || '!=' || '&&' || '||';

block: (stmt ';'*) | ('{' (stmt ';'*)* '}');

stmt:
    left=expr assignmentOp right=expr #AssignmentStmt
    | 'for' '(' name=ID ':' in=expr ')' block #ForEachStmt
    | 'for' '(' LET name=ID '=' init=expr ';' cond=expr ';' incrementer=stmt ')' block #ForStmt
    | ifBlock ('else' ifBlock)* elseBlock? #IfStmt
    | 'do' block 'while' '(' cond=expr ')' #DoWhileStmt
    | 'while' '(' cond=expr ')' block #WhileStmt
    | (LET|MUT) (variableDeclaration ',')* variableDeclaration #VarDeclStmt
    | LET funcSignature '=' expr #InlineFuncDeclStmt
;

ifBlock: 'if' '(' cond=expr ')' block;
elseBlock: 'else' block;

variableDeclaration: name=ID '=' expr;