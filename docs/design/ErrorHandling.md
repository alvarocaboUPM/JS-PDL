# Notes on Error Handling

## Lexer

### Panic Mode recovery

Skips character until it finds a "safe" one:

- ;
- }
- "

### Statement Mode for known errors

- Missing double '&' or '+'
- Confused '\' with '/'
- Confused "'" with double quotes

## Parser

### Panic Mode recovery for Parser

Performs the exact same operation as the lexer, skips tokens until
one of the "save" tokens is found

### Statement Mode recovery

If any of these error cases takes places:

- Forgoten ';'
- Forgoten '(' or '{'
- Forgoten ')' or '}'

The compiler while notify the error and introduce it in order to continue with the compilation

## Semantic

- If the error “Undeclared Identifier” is encountered then, to recover from this a symbol table entry for the corresponding identifier is made.

- If data types of two operands are incompatible then, automatic type conversion is done by the compiler
