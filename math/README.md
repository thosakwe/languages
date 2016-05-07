# Math
Another one of those math programming languages. Could be cool, a lot
of possibilities. Functional, at least for the most part.

The language is strongly typed, despite not require type annotations.
This is solely because it deals almost exclusively with numbers.

It could be transpiled to produce very efficient code, as it would be
significantly less difficult to create an intelligent (com/trans)piler
for this.

I think the design is pretty solid.

# Types
- `num`
    - `int`
    - `lng`
    - `dbl`
    - can be declared as chars
        - i.e. `' '` will become `20` 
- `vector<T>`
    - More like an array in JS than anything
    - Can be accessed like `[n]`
    - `.x` is syntactic sugar, will return 0th value
        - allows for something like ordered pairs
        - `.y` follows same idea^
    - have `.len` property
- `fn([x...])`
    - have an `evaluate([args...])` method attached natively
    - can be derived (if inline) or integrated
- `integral`
    - `.definite` returns either `0` or `1`.
    - Value accessed via `.eval()`
        - If integral is indefinite, lower and upper limits must
          be passed
          - Solved via Riemann sum
- `var`
    - Raw variable type
    - `let var <id>;`
    - cannot be reassigned
        - must use `.set(value)`
- `any`
    - Refers to any data
    - Should never be used
    - Implict default type for any variable

# Syntax

## Literals
- Supports hexadecimal, scientific notation

```
let hex:int = 0x25;
let const avogadro:lng = 6.022e23;
```

- Integrals
    - Use `S<id|closureexpr>` to denote an integral of the
      given function. Using a closure might be messy

```
fn f(x) => x + 2;

let indefinite:integral = S{f};
let definite:integral = S(0, 2){f};

let zero = indefinite.eval(0, 2) - definite.eval();
```

## Variables
- Declared using `let const? <id> = <expr>`
- Reassigned via `<id> = <expr>`
- `var` can only be declared via `let var <id>` statement
- `const` variables throw an error if changed

### Type-checking
- Variables can be type-checked, woohoo

```
let my_int:int = 32;

// Will throw an error
let not_an_int:int = [];

let vector_any:vector = [1, [2, 3]];
let vector_dbl:vector<dbl> = [1, 2.5, 3.14];
```

## Functions
- Declared using `fn <id> <closure_expr>`
- Inline functions look like `fn <id> <arg_spec> => <expr>`
  - can be derived via `diff()`

```
// Inline
fn add(x:int):int => x + 2;

// Full
fn add(x) {
    // Do stuff...
    ret x + 2;
}
```
- Full functions have to have a `.diff` defined to be differentiable

```
fn add(x) {
    ret x + 2;
}

// Manually define derivative
fn add.diff(x) => 2 * x;
```

### Built-in Functions
- `get` - read a char from stdin (ascii)
- `put` - write a char to stdout (ascii)
- `diff` - derives a function, returns a function in turn
- All the functions built into CalcLang
![Built-in functions](https://github.com/calclang/calclang/raw/master/screenshots/stdfuncs.png?raw=true)

## Vectors
- Declared via `[<expr...>]`
- can use `+=` or `-=` operators to add or remove items

# Imports/Exports

## Exports
Files can `export` expressions to be referenced in other files.
- `export <id>`
- `export <expr> as <id>`
- `export <expr>`

If an export is not named, then it will be the file's *default export*. Each
file can only have one.

Exported expressions can be referenced in other files.

## Imports
`import` statements must be located at the top of a file.

```
import "<file>"
```
Imports the given file. All exports will be copied into the current file's
symbol table.

```
import {[<id>,] <id>}` from "<file>"
```
Imports the given exports from the file only.

```
import <id> from "<file>"
```

Imports the file's default export, and assigns it to the given variable.

# Keywords
- `const`
- `if`, `else`
- `import`, `export`
- `for`, `loop`, `while`
- `num`, `int`, `dbl`, `lng`, `vector`, `fn`
- `let`
- `ret`
- `var`


