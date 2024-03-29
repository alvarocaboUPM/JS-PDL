# JS-PDL Compiler

This is a projecto for the Languages Processors subject at Universidad Politécnica de Madrid

## Table of Contents

- [JS-PDL Compiler](#js-pdl-compiler)
  - [Table of Contents](#table-of-contents)
  - [Author](#author)
  - [Program usage](#program-usage)
  - [Documentation](#documentation)
    - [Useful links](#useful-links)

## Author

[@alvarocabo](https://github.com/alvarocabo)
[@ouhat](https://github.com/ouhat)

## Program usage

```bash
mvn exec:java
```

## Documentation

In the `docs` folder you can find useful documentation about the project

```text
├── ast-generator-docs: example programms and docs
├── js-pdl-docs: All about JS-PDL language
├── grammars
│   ├── lexer: AFD and grammar
│   ├── parser
│   │   ├── DecisionTrees
│   │   ├── Grammar
│   │   └── First and Follow tables
│   └── semantic: DDS parser grammar
├── Memoria.md: Everything i can tell you about the project :>
└── testing: some testing cases
```

### Useful links

[Write interpreters in Rust](https://rust-hosted-langs.github.io/book/introduction.html)
