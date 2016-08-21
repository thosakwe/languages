# Languages
Several hypothetical (programming) languages.

# Why
I really like compiler theory. Since I was younger, a dream of mine
has been to create the next big programming language. This repo will
contain many drafts of languages, as I seek out the perfect one.

# Interpreted or Compiled?
This really depends on the language, and its eventual purpose.
Interpreters leveraging ANTLR are the easiest to create. As for compilers,
I never know whether to target native output, write a VM, or transpile
to something like C.

For most languages, it is a good idea to compile to efficient Javascript.
At least, in 2016. So turning wild concepts into Javascript will be goal
for some time.

# TobeVM
It would be cool to write a custom VM. The thing about VM's, however,
is that users have to install them. And then you have to maintain them
yourself. As an individual coder, I might as well transpile.

# Without ANTLR
Lexing is easy. For parsing, I find this algorithm to be good:
1. Loop through tokens
2. Find all potential parse nodes and store into a queue
3. Select the longest, consume its tokens and then continue
parsing
4. Profit from generated AST