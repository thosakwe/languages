# Calclang 2.0
I'm going to use actions in this grammar, but I don't
care at all ;)

- Very strongly typed
    - Everything is either a number or tuple (linked list)
- `let` declares constant, `mut` is mutable
- `print`, `printf`, `dbg`, `dbgf` are helper functions
- Maybe strings eventually, will be transformed to byte tuples :)
  
# Quirks
- All ID's must start with lowercase
    - Lets us multiply implicitly, without op
    
# GC
GC is easy, just call for GC of declared symbols when
exiting a scope.
Everything is static as well, so we can always reliably
know how many bytes to free.
    
# Functions
- MUST return a value
    - This is so return type can be inferenced
- Can be curried
- If you call with the first arg as a tuple:
    - If the first param is declared a tuple (`fn myFunc(a[], b) {}`), the tuple will be injected
    - Otherwise `fn myFunc(a, b) {}`, you will receive a tuple containing the result of calling
    the function with each tuple member as the first argument
- "Inline" functions declare a function that returns just an expression

```
// Inline func
let f(x) = 2x -1;

// Declare tuple
let domain = {1 .. 5};

// Call normal func with tuple
let range = f(domain); // {1, 3, 5, 7, 9}

// Declare param as tuple

// The `x in domain` syntax returns a tuple, the result of running the
// expression on each member

let pairBuilder(domain[]) = x in domain => {x, f(x)};
```

# Syntactic Sugars
Using a series of code transformers prior to compilation,
it is possible to add new features to the language without
actually rewriting the compiler's logic.

- Factorials expand to tuples
- `anti <func>` => becomes antiderivative
- Maybe derivative parser in the future `diff`
    - Or use Riemann sum :)