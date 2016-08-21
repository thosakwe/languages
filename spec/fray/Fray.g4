// Fray Language Definition
grammar Fray;

block locals [
    BlockContext parent;
    java.util.HashMap<String, FullFnDeclStmtContext> fullFunctions = new java.util.HashMap();
    java.util.HashMap<String, InlineFnDeclStmtContext> inlineFunctions = new java.util.HashMap();
    java.util.HashMap<String, ExprContext> symbols = new java.util.HashMap();
]: compilationUnit | scopedStmt;

compilationUnit: (importStmt SEMI?)* stmt*;

importStmt:
    IMPORT target=STRING #FullImportStmt
    | IMPORT CURLY_L (ID COMMA)* ID CURLY_R FROM target=STRING #PartialImportStmt
    | IMPORT ID FROM target=STRING #DefaultImportStmt;

stmt:
    (conditionalStmt
    | exportStmt
    | incrementStmt
    | invocationStmt
    | rawVarDeclStmt
    | reassignStmt
    | returnStmt
    | scopedStmt
    | varDeclStmt) SEMI?;

scopedStmt:
    doWhileStmt
    | elIfStmt
    | elseStmt
    | ifStmt
    | forStmt
    | fullFnDeclStmt
    | inlineFnDeclStmt
    | loopStmt
    | whileStmt;

conditionalStmt: ifStmt elIfStmt* elseStmt;
ifStmt: (IF PAREN_L expr PAREN_R stmt) | (IF PAREN_L expr PAREN_R stmt*);
elIfStmt: (ELIF PAREN_L expr PAREN_R stmt) | (ELIF PAREN_L expr PAREN_R stmt*);
elseStmt: (ELSE stmt) | (ELSE CURLY_L stmt* CURLY_R);

doWhileStmt: DO CURLY_L stmt* CURLY_R WHILE PAREN_L expr PAREN_R;
whileStmt: WHILE PAREN_L expr PAREN_R CURLY_L stmt* CURLY_R;

forStmt: FOR PAREN_L (varDeclStmt SEMI expr SEMI stmt) PAREN_R CURLY_L stmt* CURLY_R;
loopStmt: LOOP PAREN_L argumentSpec COLON expr PAREN_R CURLY_L stmt* CURLY_R;

fullFnDeclStmt: FN name=ID PAREN_L argumentsSpec PAREN_R typeSpec? CURLY_L stmt* CURLY_R;
inlineFnDeclStmt: FN name=ID PAREN_L argumentsSpec PAREN_R typeSpec? ARROW expr;

exportStmt:
    EXPORT expr #DefaultExportStmt
    | EXPORT expr AS ID #NamedExportStmt;

incrementStmt:
    postIncrementStmt
    | preIncrementStmt;

preIncrementStmt:
    expr PLUS PLUS #PrePlusPlus
    | expr MINUS MINUS #PreMinusMinus;
postIncrementStmt:
    PLUS PLUS expr #PostPlusPlus
    | MINUS MINUS expr #PostMinusMinus;

invocationStmt: ID PAREN_L ((expr COMMA)* expr)? PAREN_R;
rawVarDeclStmt: LET TYPE_VAR ID;
reassignStmt: ID EQUALS expr;
returnStmt: RET expr;
varDeclStmt: LET ID typeSpec? EQUALS expr;

argumentSpec: ID typeSpec?;
argumentsSpec: ((argumentSpec COMMA)* argumentSpec?);
typeSpec: COLON type;

type:
    TYPE_ANY
    | TYPE_DBL
    | TYPE_INT
    | TYPE_LNG
    | TYPE_NUM
    | TYPE_VAR
    | TYPE_VECTOR
    | TYPE_VECTOR ANGLE_L type ANGLE_R;

expr:
    NULL #NullExpr
    | DBL #DoubleExpr
    | HEX #HexExpr
    | ID #IdentifierExpr
    | INT #IntegerExpr
    | expr CARET expr #ExponentialExpr
    | expr TIMES expr #MultiplicationExpr
    | expr DIVIDE expr #DivisionExpr
    | expr PLUS expr #AdditionExpr
    | expr MINUS expr #SubtractionExpr
    | expr MODULO expr #ModuloExpr
    | MINUS MINUS expr #PreMinusMinusExpr
    | PLUS PLUS expr #PrePlusPlusExpr
    | expr MINUS MINUS #PostMinusMinusExpr
    | expr PLUS PLUS #PostPlusPlusExpr
    | SUMMA PAREN_L expr COMMA expr PAREN_R CURLY_L ID CURLY_R #DefiniteIntegralExpr
    | SUMMA CURLY_L ID CURLY_R #IndefiniteIntegralExpr
    | ID PAREN_L ((expr COMMA)* expr)? PAREN_R #InvocationExpr
    | SQUARE_L ((expr COMMA)* expr)? SQUARE_R #VectorExpr
    | target=expr SQUARE_L expr SQUARE_R #VectorIndexExpr
    | expr (IS | NOT | LT | LTE | GT | GTE | OR | AND) expr #BoolExpr
    | expr (BITWISE_OR | BITWISE_AND | BITWISE_NOT | BITWISE_SHL | BITWISE_SHR | BITWISE_XOR) expr #BitwiseExpr
    | expr QUESTION expr COLON expr #TernaryExpr
    | PAREN_L expr PAREN_R #NestedExpr;

WS: (' ' | '\n' | '\r' | '\r\n') -> skip;

ANGLE_L: '<';
ANGLE_R: '>';
ARROW: '=>';
COLON: ':';
COMMA: ',';
CURLY_L: '{';
CURLY_R: '}';
DOT: '.';
EQUALS: '=';
PAREN_L: '(';
PAREN_R: ')';
QUESTION: '?';
SEMI: ';';
SQUARE_L: '[';
SQUARE_R: ']';

CARET: '^';
PLUS: '+';
MINUS: '-';
MODULO: '%';
TIMES: '*';
DIVIDE: '/';
SUMMA: '$';

BITWISE_AND: '&';
BITWISE_OR: '|';
BITWISE_NOT: '!';
BITWISE_SHL: '<<';
BITWISE_SHR: '>>';
BITWISE_XOR:  'xor';

IF: 'if';
ELIF: 'elif';
ELSE: 'else';
IS: '==' | 'is' | 'equ';
NOT: '!=' | 'not' | 'nequ';
AND: '&&' | 'and';
OR: '||' | 'or';
LT: '<' | 'lt';
LTE: LT EQUALS | 'lte';
GT: '>' | 'gt';
GTE: GT EQUALS | 'gte';

TYPE_ANY: 'any';
TYPE_DBL: 'dbl';
TYPE_INT: 'int';
TYPE_LNG: 'lng';
TYPE_NUM: 'num';
TYPE_VAR: 'var';
TYPE_VECTOR: 'vector';

AS: 'as';
CONST: 'const';
DO: 'do';
EXPORT: 'export';
FN: 'fn';
FOR: 'for';
FROM: 'from';
IMPORT: 'import';
LET: 'let';
LOOP: 'loop';
NULL: 'null';
RET: 'ret';
WHILE: 'while';

DBL: MINUS? [0-9]+ DOT [0-9]+;
HEX: ('0x' [a-zA-Z0-9]+) | ([a-zA-Z0-9]+ 'h');
INT: MINUS? [0-9]+;
fragment ESCAPED: '\\"' | '\\r' | '\\n';
STRING: '"' (ESCAPED | ~('\n'|'\r'))*? '"';
ID: ('a' .. 'z' | 'A' .. 'Z' | '_') [a-zA-Z0-9_]* (DOT ID)*;