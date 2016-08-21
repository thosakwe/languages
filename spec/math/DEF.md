# Math

```antlr4
WS: (' ' | '\n' | '\r' | '\r\n') -> skip;

PAREN_L: '(';
PAREN_R: ')';
CARET: '^';
TIMES: '*';
SLASH: '/';
PLUS: '+';
MINUS: '-';

NUMBER: [0-9]+;

binaryOperator: CARET | TIMES | SLASH | PLUS | MINUS;
expr:
    NUMBER #NumberExpr
    | expr binaryOperator #BinaryExpr
    | PAREN_L expr PAREN_R #NestedExpr
;
```