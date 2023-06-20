# Memoria de Procesador de Lenguajes de JS-PDL (G136)

## Table of Contents

- [Memoria de Procesador de Lenguajes de JS-PDL (G136)](#memoria-de-procesador-de-lenguajes-de-js-pdl-g136)
  - [Table of Contents](#table-of-contents)
  - [Autores](#autores)
    - [Opciones del Grupo](#opciones-del-grupo)
  - [Analizador léxico\`](#analizador-léxico)
    - [Tokens](#tokens)
    - [Gramática Regular](#gramática-regular)
    - [AFD](#afd)
    - [Acciones semánticas](#acciones-semánticas)
      - [VARIABLES GLOBALES](#variables-globales)
      - [FUNCIONES AUXILIARES](#funciones-auxiliares)
      - [Pseudocódigo que genera tokens sin valor](#pseudocódigo-que-genera-tokens-sin-valor)
      - [Secuencia de generación de tokes con valor](#secuencia-de-generación-de-tokes-con-valor)
  - [Analizador Sintáctico](#analizador-sintáctico)
    - [Gramática](#gramática)
      - [Demostración gramática](#demostración-gramática)
  - [Análisis semántico](#análisis-semántico)
    - [Gramática-Semántico](#gramática-semántico)
      - [TRADUCCIÓN DIRIGIDA POR LA SINTAXIS](#traducción-dirigida-por-la-sintaxis)
    - [Algoritmo de control de expresiones](#algoritmo-de-control-de-expresiones)
      - [1. Creación del árbol](#1-creación-del-árbol)
      - [2. Comprobación del árbol](#2-comprobación-del-árbol)
  - [Tabla de Simbolos](#tabla-de-simbolos)
  - [Control de errores](#control-de-errores)
    - [Formateo de errores detectados](#formateo-de-errores-detectados)

## Autores

| Nombre            | Matricula | Correo                     |
| ----------------- | --------- | -------------------------- |
| Alvaro Cabo       | c200172   | <alvaro.cabo@alumnos.upm.es> |
| Oussama El Hatifi | c200359   | <o.elhatifi@alumnos.upm.es>  |
| Jessica Zhu       | c200132   | <jessica.zhu@alumnos.upm.es> |

### Opciones del Grupo

Las opciones obligatorias para el grupo 136 son:

- Sentencia repetitiva (do-while)
- Pre-auto-incremento (++ como prefijo)
- Comentario de bloque (/\*\*/)
- Con comillas dobles (" ")
- Descendente Recursivo

Los elementos opcionales seleccionados por el grupo son\*:

- Operador Aritmético %
- Operador Lógico &&
- Operador Relacional >

## Analizador léxico`

### Tokens

<TypeBool,- >  
<LoopDo,- >  
<FunID,- >  
<CondIf,- >  
<ResIn,- >  
<TypeInt,- >  
<ResLet,- >  
<ResPrint,- >  
<Return,- >  
<TypeString,- >  
<LoopWhile,- >

<CteInt,num>  
<Cad,-”c\*”>  
<TokT,>
<TokF,>
<ID,num >

<AsValue,- >  
<Com,- >  
<SemCol,- >  
<ParOpen,- >  
<ParClose,- >  
<KeyOpen,- >  
<KeyClose,- >

<MOD,- >  
<AND,- >  
<GT,- >

<Teof,>

### Gramática Regular

  ![Gramatica Regular](Gram.svg)

### AFD

  ![Autómata Finito Determinista](AFD.svg)

### Acciones semánticas

Esta sección la planteamos con una presentación distinta a la vista en clase, ya que creemos que utilizando una sintaxis estilo pseudocódigo (con varibles globales, funciones y todo lo que incluye la estructura de un programa) podemos plasmar la idea a implementar de una forma más gráfica

#### VARIABLES GLOBALES

- STR_MAX_SIZE= 64 (car)

- MAX_POSSIBLE_INT = 32767

#### FUNCIONES AUXILIARES

- **NEXT():** Lee el siguiente carácter del fichero fuente. car := leer()
  - **Transiciones:** Todas las que no implican una λ-transición (Representadas por o.c)
  - **Implementación:** Inicializa car <- next() con el caracter recién leido del texto fuente
- **CONCAT(car c):** Concatena el car con el lexema.

  - Se realiza después de Next() para utilizar el car recién leido
    - **Transiciones:** Lo vamos a utilizar en 3 contextos en nuestro autómata:
      - Comentarios: {0:7,7:6,6:4}
      - Identificadores: {0:2, 2:2}
      - Cadenas: {3:3}

- **VALOR(car c):** Similar a la función _atoi(char c)_ de C, obtiene el valor numérico de un carácter leido

  - **Transiciones:** {0:1, 1:1}

- **ERROR (int code)**: Devuelve error si se da cualquier caso no contemplado por el programa. Cuenta con algunos tipos de errores definidos:

  - STRING_OVER_MAX_LEGTH: Si se intenta contatenar car con un lexema con size == STR_MAX_SIZE
  - INT_OUT_OF_BOUNDS: Si se intenta generar un token cte_entera con un valor > 32767

- **GENTOKEN(type, @nullable(value)):** Se encarga de generar el token

  - **Funciones auxiliares:**

    - BuscaTS(lex): Comprueba si el lexema es un identificador ya insertado en la tabla de símbolos, devolviendo su posición.
      En caso contrario: NULL
    - AddTS(lex, scope): Inserta un identificador en la tabla de símbolos en la tabla indicada por scope (Global o local)

  - **Variables auxiliares**
    - Bool Scope -> indica si se trata de una declaracion, global = true y local = false
    - DirToken [][]:=diccionario de cod_token para aquellos que no necesiten un tratamiento especial (cadenas, nums e IDs)
    - ResWords [][]:= diccionario de cod_token para **palabras reservadas**

<center>

<table style="border:1px solid black;">

<tr>
  <th>DirToken</th>
  <th></th
  ><th>ResWords</th>
</tr>
<tr>
  <td>

| Lex | Cod_token |
| --- | --------- |
| {   | KeyOpen   |
| }   | KeyClose  |
| (   | ParOpen   |
| )   | ParClose  |
| =   | AsValue   |
| ,   | Com       |
| ;   | SemiCol   |
| ++  | AutoSum   |
| %   | MOD       |
| >   | GT        |
| &&  | AND       |

  </td>
  <td>
  </td>
  <td>

| Lexema   | Cod_token  |
| -------- | ---------- |
| do       | LoopDo     |
| while    | LoopWhile  |
| boolean  | TypeBool   |
| int      | TypeInt    |
| string   | TypeString |
| function | FunID      |
| let      | ResLet     |
| input    | ResInput   |
| print    | ResPrint   |
| return   | ResReturn  |
| if       | CondIf     |

  </td>
</tr>  
</table>
</center>

#### Pseudocódigo que genera tokens sin valor

Esta es la casuística para tokens que no solo están compuestos de un
key sin un valor variable (Todos menos cad, ID y num)

```c
    //CASE: palabra reservada
    if ( lex in ResWords[][] )
      genToken( lex , - )
    else { //CASE: identificador
        p <- buscaTS(lex)
        if ( p != null )
          genToken(id, p)
        else {
          p <- addTS(lex, scope)
          genToken(id, p) //<ID, puntero en la tabla>
        }
    }
```

#### Secuencia de generación de tokes con valor

Este tipo de tokens no se recogen en el DirToken ya que necesitan utilizar la funciones auxiliares anteriormente documentadas

| Tipo       | Transiciones | Acción                                                  |
| ---------- | ------------ | ------------------------------------------------------- |
| **CteInt** | 0->1         | num = valor(car)                                        |
|            | 1->1         | num = num\*10+valor(car)                                |
|            | 1->108       | if (num < 32768) -> genToken(cteEnt, num) else error(2) |
| **Cad**    | 0->3         | lex_init                                                |
|            | 3->3         | lex=lex+car                                             |
|            | 3->110       | if (len(lex) < ) -> genToken(cad, lex) else error(2)    |

## Analizador Sintáctico

La opción del grupo es **Análisis recursivo descendente**

### Gramática

```text
Terminales = { eof  let  id  ;  if  (  )  {   }  while % do else
            function return input print int boolean string and >  =
            cad num  ++  , &&  true false  }
NoTerminales = {  START  SENA  CTE  INC  EXP    EXPX  VALUE  XPX  ASIGN  DECL   DECLX  TD  TDX  INOUT
                 WILE  SENB  BODY  IFX  FCALL  FCALLX  RX  IDX	IFAX   FUN  PARM  PARMX EXPA }

Axioma = START

Producciones = {

START -> SENA START ////1 > Sentencias A
START -> FUN START ////2 > Dec funciones
START -> eof ////3

CTE -> cad ////4 
CTE -> num ////5
CTE -> true ////6
CTE -> false ////7

INC -> ++ id //// 8

EXP -> VALUE EXPX ////9 > 
EXP -> INC EXPX ////10 > 

EXPX -> > EXP  ////11
EXPX -> &&  EXP ////12
EXPX -> % EXP ////13
EXPX -> lambda ////14

VALUE -> id XPX ////15
VALUE -> CTE    ////16
VALUE -> ( EXP ) ////17


XPX -> ( FCALL ) ////18
XPX -> lambda   ////19

ASIGN ->  = EXP ; ////20

DECL -> let id TD DECLX ; //// 21

DECLX -> ASIGN //// 22
DECLX -> lambda //// 23

TD -> int //// 24 tipos de datos
TD -> string //// 25
TD -> boolean   //// 26

TDX -> TD   //// 27
TDX -> lambda   //// 28

INOUT -> print EXP ;     //// 29
INOUT -> input id ;     //// 30

SENA -> IFX     //// 31 > Condicionales
SENA -> DECL    //// 32 > Declaraciones
SENA -> do { BODY } WILE    //// 33 > do wile
WILE -> while ( EXP ) ;     //// 34
SENA -> SENB                //// 35

SENB -> id IDX              //// 36
SENB -> INOUT               //// 37
SENB -> return RX ;         //// 38
SENB -> INC ;               //// 39

BODY -> SENA BODY           //// 40
BODY -> lambda              //// 41

IFX -> if ( EXP ) IFAX      //// 42

IFAX -> { BODY } //// 43
IFAX -> SENB    //// 44


FCALL -> EXP FCALLX     //// 45
FCALL -> lambda         //// 46
FCALLX -> , EXP FCALLX  //// 47
FCALLX -> lambda         //// 48

RX -> EXP               //// 49
RX -> lambda            //// 50

IDX -> ASIGN          //// 51
IDX -> ( FCALL ) ;      //// 52

FUN -> function id TDX ( PARM ) { BODY }    //// 53

PARM -> TD id PARMX     //// 54
PARM -> lambda      //// 55
PARMX -> , TD id PARMX  //// 56
PARMX -> lambda  //// 57


}

```

#### Demostración gramática

Para realizar este parser recursivo descendente, es necesario que la gramática se encuentre en forma LL1 para poder operar con 1 token por iteración.

Vamos a proceder a demostrar que efectivamente nuestra gramática está en forma LL1

1. Tablas First & Follow  
   Para depurar nuestra gramática, hemos utilizado [esta herramienta online](https://mikedevice.github.io/first-follow/) que devuelve ambos sets formateados en una tabla comparativa

|        | eof | cad | num | true | false | ++  | id  | \>  | &&  | %   | lambda | (   | )   | \=  | ;   | let | int | string | boolean | print | input | do  | {   | }   | while | return | if  | ,   | function | ε   |
| ------ | --- | --- | --- | ---- | ----- | --- | --- | --- | --- | --- | ------ | --- | --- | --- | --- | --- | --- | ------ | ------- | ----- | ----- | --- | --- | --- | ----- | ------ | --- | --- | -------- | --- |
| START  | +   | −   | −   | −    | −     | +   | +   | −   | −   | −   | −      | −   | −   | −   | −   | +   | −   | −      | −       | +     | +     | +   | −   | −   | −     | +      | +   | −   | +        | −   |
| CTE    | −   | +   | +   | +    | +     | −   | −   | −   | −   | −   | −      | −   | −   | −   | −   | −   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | −   | −   | −        | −   |
| INC    | −   | −   | −   | −    | −     | +   | −   | −   | −   | −   | −      | −   | −   | −   | −   | −   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | −   | −   | −        | −   |
| EXP    | −   | +   | +   | +    | +     | +   | +   | −   | −   | −   | −      | +   | −   | −   | −   | −   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | −   | −   | −        | −   |
| EXPX   | −   | −   | −   | −    | −     | −   | −   | +   | +   | +   | +      | −   | −   | −   | −   | −   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | −   | −   | −        | −   |
| VALUE  | −   | +   | +   | +    | +     | −   | +   | −   | −   | −   | −      | +   | −   | −   | −   | −   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | −   | −   | −        | −   |
| XPX    | −   | −   | −   | −    | −     | −   | −   | −   | −   | −   | +      | +   | −   | −   | −   | −   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | −   | −   | −        | −   |
| ASIGN  | −   | −   | −   | −    | −     | −   | −   | −   | −   | −   | −      | −   | −   | +   | −   | −   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | −   | −   | −        | −   |
| DECL   | −   | −   | −   | −    | −     | −   | −   | −   | −   | −   | −      | −   | −   | −   | −   | +   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | −   | −   | −        | −   |
| DECLX  | −   | −   | −   | −    | −     | −   | −   | −   | −   | −   | +      | −   | −   | +   | −   | −   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | −   | −   | −        | −   |
| TD     | −   | −   | −   | −    | −     | −   | −   | −   | −   | −   | −      | −   | −   | −   | −   | −   | +   | +      | +       | −     | −     | −   | −   | −   | −     | −      | −   | −   | −        | −   |
| TDX    | −   | −   | −   | −    | −     | −   | −   | −   | −   | −   | +      | −   | −   | −   | −   | −   | +   | +      | +       | −     | −     | −   | −   | −   | −     | −      | −   | −   | −        | −   |
| INOUT  | −   | −   | −   | −    | −     | −   | −   | −   | −   | −   | −      | −   | −   | −   | −   | −   | −   | −      | −       | +     | +     | −   | −   | −   | −     | −      | −   | −   | −        | −   |
| SENA   | −   | −   | −   | −    | −     | +   | +   | −   | −   | −   | −      | −   | −   | −   | −   | +   | −   | −      | −       | +     | +     | +   | −   | −   | −     | +      | +   | −   | −        | −   |
| WILE   | −   | −   | −   | −    | −     | −   | −   | −   | −   | −   | −      | −   | −   | −   | −   | −   | −   | −      | −       | −     | −     | −   | −   | −   | +     | −      | −   | −   | −        | −   |
| SENB   | −   | −   | −   | −    | −     | +   | +   | −   | −   | −   | −      | −   | −   | −   | −   | −   | −   | −      | −       | +     | +     | −   | −   | −   | −     | +      | −   | −   | −        | −   |
| BODY   | −   | −   | −   | −    | −     | +   | +   | −   | −   | −   | +      | −   | −   | −   | −   | +   | −   | −      | −       | +     | +     | +   | −   | −   | −     | +      | +   | −   | −        | −   |
| IFX    | −   | −   | −   | −    | −     | −   | −   | −   | −   | −   | −      | −   | −   | −   | −   | −   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | +   | −   | −        | −   |
| IFAX   | −   | −   | −   | −    | −     | +   | +   | −   | −   | −   | −      | −   | −   | −   | −   | −   | −   | −      | −       | +     | +     | −   | +   | −   | −     | +      | −   | −   | −        | −   |
| FCALL  | −   | +   | +   | +    | +     | +   | +   | −   | −   | −   | +      | +   | −   | −   | −   | −   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | −   | −   | −        | −   |
| FCALLX | −   | −   | −   | −    | −     | −   | −   | −   | −   | −   | +      | −   | −   | −   | −   | −   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | −   | +   | −        | −   |
| RX     | −   | +   | +   | +    | +     | +   | +   | −   | −   | −   | +      | +   | −   | −   | −   | −   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | −   | −   | −        | −   |
| IDX    | −   | −   | −   | −    | −     | −   | −   | −   | −   | −   | −      | +   | −   | +   | −   | −   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | −   | −   | −        | −   |
| FUN    | −   | −   | −   | −    | −     | −   | −   | −   | −   | −   | −      | −   | −   | −   | −   | −   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | −   | −   | +        | −   |
| PARM   | −   | −   | −   | −    | −     | −   | −   | −   | −   | −   | +      | −   | −   | −   | −   | −   | +   | +      | +       | −     | −     | −   | −   | −   | −     | −      | −   | −   | −        | −   |
| PARMX  | −   | −   | −   | −    | −     | −   | −   | −   | −   | −   | +      | −   | −   | −   | −   | −   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | −   | +   | −        | −   |

|        | eof | cad | num | true | false | ++  | id  | \>  | &&  | %   | lambda | (   | )   | \=  | ;   | let | int | string | boolean | print | input | do  | {   | }   | while | return | if  | ,   | function | ┤   |
| ------ | --- | --- | --- | ---- | ----- | --- | --- | --- | --- | --- | ------ | --- | --- | --- | --- | --- | --- | ------ | ------- | ----- | ----- | --- | --- | --- | ----- | ------ | --- | --- | -------- | --- |
| START  | −   | −   | −   | −    | −     | −   | −   | −   | −   | −   | −      | −   | −   | −   | −   | −   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | −   | −   | −        | +   |
| CTE    | −   | −   | −   | −    | −     | −   | −   | +   | +   | +   | +      | −   | −   | −   | −   | −   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | −   | −   | −        | −   |
| INC    | −   | −   | −   | −    | −     | −   | −   | +   | +   | +   | +      | −   | −   | −   | +   | −   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | −   | −   | −        | −   |
| EXP    | −   | −   | −   | −    | −     | −   | −   | −   | −   | −   | +      | −   | +   | −   | +   | −   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | −   | +   | −        | −   |
| EXPX   | −   | −   | −   | −    | −     | −   | −   | −   | −   | −   | +      | −   | +   | −   | +   | −   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | −   | +   | −        | −   |
| VALUE  | −   | −   | −   | −    | −     | −   | −   | +   | +   | +   | +      | −   | −   | −   | −   | −   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | −   | −   | −        | −   |
| XPX    | −   | −   | −   | −    | −     | −   | −   | +   | +   | +   | +      | −   | −   | −   | −   | −   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | −   | −   | −        | −   |
| ASIGN  | +   | −   | −   | −    | −     | +   | +   | −   | −   | −   | +      | −   | −   | −   | +   | +   | −   | −      | −       | +     | +     | +   | −   | +   | −     | +      | +   | −   | +        | −   |
| DECL   | +   | −   | −   | −    | −     | +   | +   | −   | −   | −   | +      | −   | −   | −   | −   | +   | −   | −      | −       | +     | +     | +   | −   | −   | −     | +      | +   | −   | +        | −   |
| DECLX  | −   | −   | −   | −    | −     | −   | −   | −   | −   | −   | −      | −   | −   | −   | +   | −   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | −   | −   | −        | −   |
| TD     | −   | −   | −   | −    | −     | −   | +   | −   | −   | −   | +      | +   | −   | +   | −   | −   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | −   | −   | −        | −   |
| TDX    | −   | −   | −   | −    | −     | −   | −   | −   | −   | −   | −      | +   | −   | −   | −   | −   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | −   | −   | −        | −   |
| INOUT  | +   | −   | −   | −    | −     | +   | +   | −   | −   | −   | +      | −   | −   | −   | +   | +   | −   | −      | −       | +     | +     | +   | −   | +   | −     | +      | +   | −   | +        | −   |
| SENA   | +   | −   | −   | −    | −     | +   | +   | −   | −   | −   | +      | −   | −   | −   | −   | +   | −   | −      | −       | +     | +     | +   | −   | −   | −     | +      | +   | −   | +        | −   |
| WILE   | +   | −   | −   | −    | −     | +   | +   | −   | −   | −   | +      | −   | −   | −   | −   | +   | −   | −      | −       | +     | +     | +   | −   | −   | −     | +      | +   | −   | +        | −   |
| SENB   | +   | −   | −   | −    | −     | +   | +   | −   | −   | −   | +      | −   | −   | −   | +   | +   | −   | −      | −       | +     | +     | +   | −   | +   | −     | +      | +   | −   | +        | −   |
| BODY   | −   | −   | −   | −    | −     | −   | −   | −   | −   | −   | −      | −   | −   | −   | −   | −   | −   | −      | −       | −     | −     | −   | −   | +   | −     | −      | −   | −   | −        | −   |
| IFX    | +   | −   | −   | −    | −     | +   | +   | −   | −   | −   | +      | −   | −   | −   | −   | +   | −   | −      | −       | +     | +     | +   | −   | −   | −     | +      | +   | −   | +        | −   |
| IFAX   | −   | −   | −   | −    | −     | −   | −   | −   | −   | −   | −      | −   | −   | −   | +   | −   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | −   | −   | −        | −   |
| FCALL  | −   | −   | −   | −    | −     | −   | −   | −   | −   | −   | −      | −   | +   | −   | −   | −   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | −   | −   | −        | −   |
| FCALLX | −   | −   | −   | −    | −     | −   | −   | −   | −   | −   | −      | −   | +   | −   | −   | −   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | −   | −   | −        | −   |
| RX     | −   | −   | −   | −    | −     | −   | −   | −   | −   | −   | −      | −   | −   | −   | +   | −   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | −   | −   | −        | −   |
| IDX    | +   | −   | −   | −    | −     | +   | +   | −   | −   | −   | +      | −   | −   | −   | +   | +   | −   | −      | −       | +     | +     | +   | −   | +   | −     | +      | +   | −   | +        | −   |
| FUN    | +   | −   | −   | −    | −     | +   | +   | −   | −   | −   | −      | −   | −   | −   | −   | +   | −   | −      | −       | +     | +     | +   | −   | −   | −     | +      | +   | −   | +        | −   |
| PARM   | −   | −   | −   | −    | −     | −   | −   | −   | −   | −   | −      | −   | +   | −   | −   | −   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | −   | −   | −        | −   |
| PARMX  | −   | −   | −   | −    | −     | −   | −   | −   | −   | −   | −      | −   | +   | −   | −   | −   | −   | −      | −       | −     | −     | −   | −   | −   | −     | −      | −   | −   | −        | −   |

Como se puede observar en la tabla no existen conflicots first/first ni first/follow.

2. Finalemnte, entre los No terminales que derivan en No terminales, no existe recursividad por la izquierda en ningún caso.

> Por lo tanto, estamos ante una gramática en forma LL1

## Análisis semántico

Se encarga de controlar los tipados, operaciones de flujo y administra los símbolos introducido por el léxico a la TS

### Gramática-Semántico

Utilizamos reglas estilo DDS para formalizar las reglas que introduciremos en nuestro analizador semántico:

#### TRADUCCIÓN DIRIGIDA POR LA SINTAXIS

```t


START -> SENA START 
START -> {inFunc := true} FUN START 
START -> eof 

CTE -> cad { ParseLib.insertOperand() }
CTE -> num { ParseLib.insertOperand() }
CTE -> true { ParseLib.insertOperand() }
CTE -> false { ParseLib.insertOperand() }

INC -> ++ id { ParseLib.setID(), ParseLib.CheckExplicitness(), ParseLib.insertOperand() }

EXP -> VALUE EXPX 
EXP -> INC EXPX 

EXPX -> {if (!tk.isOperator()) {  
            if (e.getFree() != Insertion.LEFT || e.isEmpty())  
                then e.clear()  
            else {  
                cursor.addChild(new ExpNode(e.clear()))  
            }  
            if (tk.getType().equals("ParClose")) {  
                if (cursor.isRoot()) {   
                    for (List<ExpNode> nodesList : expresions.findValidPath()){  
                        int i := 1,  
                        for (ExpNode node : nodesList) {     
       i++,
                        }  
                        i :=0,  
                    }  
                    ParseLib.endTree(),  
                } else {  
                    cursor := cursor.getParent(),  
                }  
            }  
            return,  
        }  
        e.insert(tk);  }
EXPX -> > {e.setTipo(Token.Tipado.BOOL) } EXP  
EXPX -> &&  {e.setTipo(Token.Tipado.BOOL)} EXP 
EXPX -> % { e.setTipo(Token.Tipado.INT) } EXP 
EXPX -> lambda 

VALUE -> id { ParseLib.setID(), ParseLib.insertOperand()} XPX 
VALUE -> CTE   
VALUE -> ( {cursor := cursor.addChild(new ExpNode(e.clear()))} EXP ) 

XPX -> ( { if (id.getType() != "Function")  then ParseLib.ezError(117) else{tmp := id; inFCall := true;  
            nArgs := 0;  }}
                
                FCALL { if(nArgs != tmp.getNumParams()) ParseLib.ezError(204, tmp.getLexema()),  
            ,inFCall := false } ) 
XPX -> lambda   {ParseLib.CheckExplicitness() }

ASIGN ->  = EXP ; 

DECL -> let id TD DECLX ; 

DECLX -> {inAss := true;} ASIGN {inAss := false; }
DECLX -> lambda 

TD -> int
TD -> string 
TD -> boolean   

TDX -> TD   {   funcID.setOffset(OffsetG)  
            ParseLib.IncOffset(tk.getType()),  
            funcID.setReturnType(tk.getType()),  }
TDX -> lambda   {  funcID.setReturnType("Void")  }

INOUT -> print EXP ;    {if (!expresions.isEmpty())  then ParseLib.endTree()  }
INOUT -> input id ;     {if (ParseLib.setID())  then ParseLib.CheckExplicitness()  
                if (id.getType() != "TypeInt" && id.getType() != "TypeString")  then ParseLib.ezError(214)}

SENA -> IFX     {}
SENA -> {inVarDec := true  } DECL    
SENA -> do { BODY } WILE   
WILE -> while ( EXP ) ;     
SENA -> SENB               

SENB -> id IDX            
SENB -> INOUT               
SENB -> return RX ;         
SENB -> INC ;       {e.clear()}

BODY -> SENA BODY         
BODY -> lambda            

IFX -> if ( EXP ) IFAX ;   

IFAX -> SENB    
IFAX -> { SENB } 


FCALL -> EXP {nArgs++} FCALLX 
FCALL -> lambda         
FCALLX -> , EXP {nArgs++} FCALLX  
FCALLX -> lambda        

RX -> EXP               
RX -> lambda            

IDX -> {    ParseLib.setID(),  ParseLib.CheckExplicitness() }
IDX -> ASIGN          
IDX -> ( { inFCall := true } FCALL ) ;      

FUN -> function {        funcID := Compiler.ts.lookAtIndex((int) tk.getInfo()),  
        if (funcID == null)  
            then return;  
        TabLex := funcID.getLexema(), 
        funcID.setType("Function"), funcID.setOffset(OffsetG)  } id TDX 
         {inFunc := true} ( {ParseLib.toLowerSc(), inParms := true} PARM {inParms := false, funcID.setNumParams(nParams) } ) { { Compiler.ts.changeScope(true)} BODY }  {OffsetG += OffsetL,
        OffsetL = 0,  
        Compiler.ts.changeScope(true),
        inFunc := false,  
        nParams := 0  }

PARM -> TD { funcID.addTypesParams(tk.getType()),  
            LastType := tk.getType(),  
         
                ParseLib.setID(),
                id.setOffset(OffsetL),  
                ParseLib.IncOffset(LastType),  
                id.setType(LastType),  
            
                nParams++ } 
                 } 
            id PARMX    
PARM -> lambda      
PARMX -> , TD {funcID.addTypesParams(tk.getType()),  
                LastType := tk.getType(),
                nParams++}
                id {if (ParseLib.setID()) {  
                    id.setOffset(OffsetL),  
                    ParseLib.IncOffset(LastType),
                    id.setType(LastType),  
                     }
                      } PARMX  
PARMX -> lambda     

```

### Algoritmo de control de expresiones

Sin duda alguna, uno de los mayores desafíos a los que nos hemos enfrentado
para llevar a cabo el analizador semántico ha sido la comprobación del tipado de las expresiones.  

Para ello hemos creado un algoritmo de ordenación basado en un árbol de expresiones, permitiendo al parser recrear al final de la expresión el camino a seguir en función del orden de operadores y de la asociatividad de izquierda a derecha.

> Ejemplo: s3.js

```js
let buleano boolean;
do{
    ++n1;
}
while  (buleano && (5>(n1%10)) && true);
```

#### 1. Creación del árbol

1. Se crea una instancia de la API de `Tree` de java que modela la expresión completa -> `ExptTree`
2. Crea la expresión de izquierda a derecha

   - Si encuentra un OpenPar '(' -> Coloca el cursor en el nodo actual y sus hijos pasan a ser los nodos de creación

3. Si encuentra una expresión formada completa (ningún miembro == null):

   - Sube de nivel recursivamente según vaya encontrando ')'

4. Si intenta subir al nivel de root -> termina el árbol

```text
ROOT
│ └ ID AND
│   └ CteInt GT
│     └ EXP_INT
  └ AND TokT
```

#### 2. Comprobación del árbol

Se ha implementado el método getValidPath() en la clase ExpTree para intentar construir un camino válido basado en la precedencia de operadores.

```txt
1) {left=<ID,1>
, right=<CteInt,10>
, op=<MOD,>
, tipo=INT}

2) {left=<CteInt,5>
, right=, op=<GT,>
, tipo=BOOL}

3) {left=<ID,0>
, right=, op=<AND,>
, tipo=BOOL}

1) {left=, right=<TokT,>
, op=<AND,>
, tipo=BOOL}
```

## Tabla de Simbolos

Hemos utilizado el siguiente diseño para la creación de las tablas de simbolos:

| Lexema             | ID                   | Type          | NumParams |TypeParams| ReturnType | Offset
| -------------- | -------------------- | --------------------- |----|---|---|----
| Nombre del Identificador  | Posición en la tabla | Tipado asignado |Número de parámetros de la función | List[Tipo del elemento] | Tipo de retorno | Número de bytes desde la creación de la tabla ( Desplazamiento)
 _El atributo ID es privado, no aparece en el fichero resultante: TS.txt_

Esta tabla se va rellenando siguiendo el formato pedido mediante el uso de un objeto de tipo SymbolAt que va guardando los atributos, las tablas se van modificando a lo largo de la ejecucion de los analizadores, creando, destruyendo o modificando el objeto definido por el lexema.

## Control de errores

### Formateo de errores detectados

Se ha implementado una clase: `ErrorAt` para gestionar los errores encontrados por los distintos analizadores, que genera la siguiente plantilla:

```text
ERROR FOUND USING THE {$Analizer} Analyzer
###################
Error #{$ErrorCode} @ line {$LineAt}: {$ExtraInfo}
```

## Anexo

### Pruebas correctas

#### Prueba 1

```text
let a int;
let   b  int  ;
let bbb boolean ;

a = 3;
b=a;
let c boolean;

c = a > b;

if (c) b = 3333;
a = a % b;
print a ;
print b;
```
- **TOKENS** 

```text
<ResLet,>
<ID,0>
<TypeInt,>
<SemCol,>
<ResLet,>
<ID,1>
<TypeInt,>
<SemCol,>
<ResLet,>
<ID,2>
<TypeBool,>
<SemCol,>
<ID,0>
<AsValue,>
<CteInt,3>
<SemCol,>
<ID,1>
<AsValue,>
<ID,0>
<SemCol,>
<ResLet,>
<ID,3>
<TypeBool,>
<SemCol,>
<ID,3>
<AsValue,>
<ID,0>
<GT,>
<ID,1>
<SemCol,>
<CondIf,>
<ParOpen,>
<ID,3>
<ParClose,>
<ID,1>
<AsValue,>
<CteInt,3333>
<SemCol,>
<ID,0>
<AsValue,>
<ID,0>
<MOD,>
<ID,1>
<SemCol,>
<ResPrint,>
<ID,0>
<SemCol,>
<ResPrint,>
<ID,1>
<SemCol,>
<Teof,>
```

- **TABLA DE SIMBOLOS** 

```text
```

- **TRAZA PARSER** 

```text
```

- **ARBOL DE A.S** 

#### Prueba 2

```text
let x int;
let b boolean;
let z int;
input x;
print x;
input z;
print x%z;
b=x>z;if (b)
x =
  x % 6
    % z
    % 1
    % (2
    % y
    % 7)
    > 5;
```
- **TOKENS** 

```text
<ResLet,>
<ID,0>
<TypeInt,>
<SemCol,>
<ResLet,>
<ID,1>
<TypeBool,>
<SemCol,>
<ResLet,>
<ID,2>
<TypeInt,>
<SemCol,>
<ResIn,>
<ID,0>
<SemCol,>
<ResPrint,>
<ID,0>
<SemCol,>
<ResIn,>
<ID,2>
<SemCol,>
<ResPrint,>
<ID,0>
<MOD,>
<ID,2>
<SemCol,>
<ID,1>
<AsValue,>
<ID,0>
<GT,>
<ID,2>
<SemCol,>
<CondIf,>
<ParOpen,>
<ID,1>
<ParClose,>
<ID,0>
<AsValue,>
<ID,0>
<MOD,>
<CteInt,6>
<MOD,>
<ID,2>
<MOD,>
<CteInt,1>
<MOD,>
<ParOpen,>
<CteInt,2>
<MOD,>
<ID,3>
<MOD,>
<CteInt,7>
<ParClose,>
<GT,>
<CteInt,5>
<SemCol,>
<Teof,>
```

- **TABLA DE SIMBOLOS** 

```text
```

- **TRAZA PARSER** 

```text
D	 1 32 21 24 23 1 32 21 26 23 1 32 21 24 23 1 35 37 30 1 35 37 29 9 15 19 14 1 35 37 30 1 35 37 29 9 15 19 13 9 15 19 14 1 35 36 51 20 9 15 19 11 9 15 19 14 1 31 42 9 15 19 14 44 36 51 20 9 15 19 13 9 16 5 13 9 15 19 13 9 16 5 13 9 17 9 16 5 13 9 15 19 13 9 16 5 14 11 9 16 5 14 3 
```

- **ARBOL DE A.S** 

#### Prueba 3

```text
let texto string;

print "hola";
input esto_es_un_nombre_de_variable_global_de_tipo_entero_que_tiene_que_aparecer_en_la_TS_global;
print x__x;
function alert (string msg_)
{
	print msg_;
}
function pideTexto ()
{
	print "Introduce un texto";
	input texto;
}
pideTexto();
  alert
	(texto, a);
```
- **TOKENS** 

```text
<ResLet,>
<ID,0>
<TypeString,>
<SemCol,>
<ResPrint,>
<Cad,"hola">
<SemCol,>
<ResIn,>
<ID,1>
<SemCol,>
<ResPrint,>
<ID,2>
<SemCol,>
<FunID,>
<ID,3>
<ParOpen,>
<TypeString,>
<ID,0>
<ParClose,>
<KeyOpen,>
<ResPrint,>
<ID,0>
<SemCol,>
<KeyClose,>
<FunID,>
<ID,1>
<ParOpen,>
<ParClose,>
<KeyOpen,>
<ResPrint,>
<Cad,"Introduce un texto">
<SemCol,>
<ResIn,>
<ID,2>
<SemCol,>
<KeyClose,>
<ID,1>
<ParOpen,>
<ParClose,>
<SemCol,>
<ID,2>
<ParOpen,>
<ID,3>
<Com,>
<ID,4>
<ParClose,>
<SemCol,>
<Teof,>
```

- **TABLA DE SIMBOLOS** 

```text
```

- **TRAZA PARSER** 

```text
D	 1 32 21 25 23 1 35 37 29 9 16 4 14 1 35 37 30 1 35 37 29 9 15 19 14 2 53 28 54 25 57 40 35 37 29 9 15 19 14 41 2 53 28 55 40 35 37 29 9 16 4 14 40 35 37 30 41 1 35 36 52 46 1 35 36 52 45 9 15 19 14 47 9 15 19 14 48 3 
```

- **ARBOL DE A.S** 

#### Prueba 4

```text
let n1	int     ;
let l1	boolean ;
let cad	string  ;
let n2	int     ;
let l2	boolean ;
input n1;
l1 = l2;
if (l1&& l2) cad = "hello";
n2 = n1 % 378;

print	33
%
n1
%
n2;

function ff boolean(boolean ss)
{
    varglobal = 8;
    if (l1)
        l2 = ff (ss, a);
    return ss;
}



if (ff(l2))
    print varglobal;
```
- **TOKENS** 

```text
<ResLet,>
<ID,0>
<TypeInt,>
<SemCol,>
<ResLet,>
<ID,1>
<TypeBool,>
<SemCol,>
<ResLet,>
<ID,2>
<TypeString,>
<SemCol,>
<ResLet,>
<ID,3>
<TypeInt,>
<SemCol,>
<ResLet,>
<ID,4>
<TypeBool,>
<SemCol,>
<ResIn,>
<ID,0>
<SemCol,>
<ID,1>
<AsValue,>
<ID,4>
<SemCol,>
<CondIf,>
<ParOpen,>
<ID,1>
<AND,>
<ID,4>
<ParClose,>
<ID,2>
<AsValue,>
<Cad,"hello">
<SemCol,>
<ID,3>
<AsValue,>
<ID,0>
<MOD,>
<CteInt,378>
<SemCol,>
<ResPrint,>
<CteInt,33>
<MOD,>
<ID,0>
<MOD,>
<ID,3>
<SemCol,>
<FunID,>
<ID,5>
<TypeBool,>
<ParOpen,>
<TypeBool,>
<ID,0>
<ParClose,>
<KeyOpen,>
<ID,1>
<AsValue,>
<CteInt,8>
<SemCol,>
<CondIf,>
<ParOpen,>
<ID,1>
<ParClose,>
<ID,2>
<AsValue,>
<ID,2>
<ParOpen,>
<ID,0>
<Com,>
<ID,3>
<ParClose,>
<SemCol,>
<Return,>
<ID,0>
<SemCol,>
<KeyClose,>
<CondIf,>
<ParOpen,>
<ID,2>
<ParOpen,>
<ID,4>
<ParClose,>
<ParClose,>
<ResPrint,>
<ID,5>
<SemCol,>
<Teof,>
```

- **TABLA DE SIMBOLOS** 

```text
```

- **TRAZA PARSER** 

```text
D	 1 32 21 24 23 1 32 21 26 23 1 32 21 25 23 1 32 21 24 23 1 32 21 26 23 1 35 37 30 1 35 36 51 20 9 15 19 14 1 31 42 9 15 19 12 9 15 19 14 44 36 51 20 9 16 4 14 1 35 36 51 20 9 15 19 13 9 16 5 14 1 35 37 29 9 16 5 13 9 15 19 13 9 15 19 14 2 53 27 26 54 26 57 40 35 36 51 20 9 16 5 14 40 31 42 9 15 19 14 44 36 51 20 9 15 18 45 9 15 19 14 47 9 15 19 14 48 14 40 35 38 49 9 15 19 14 41 1 31 42 9 15 18 45 9 15 19 14 48 14 44 37 29 9 15 19 14 3 
```

- **ARBOL DE A.S** 

#### Prueba 5

```text
let s_s string ;
let a int;
let bb boolean;

function  cadena string (string kk)
{
    do{
        print ("{9}"); /* Aqui mal ->  RR(s, bb)*/
		let	xx	int;
		xx=s_s;
		if (bb)  return kk; /* No se puede  if(bb)-> nada */
    }
	while (a > 9 && bb); /*|| -> && */

	s= RR (a, bb, kk); /* quito $ */
	kk = "print (kk)"; /* void prin -> print */
	return s_s;
} /* Faltaba llave */
```
- **TOKENS** 

```text
<ResLet,>
<ID,0>
<TypeString,>
<SemCol,>
<ResLet,>
<ID,1>
<TypeInt,>
<SemCol,>
<ResLet,>
<ID,2>
<TypeBool,>
<SemCol,>
<FunID,>
<ID,3>
<TypeString,>
<ParOpen,>
<TypeString,>
<ID,0>
<ParClose,>
<KeyOpen,>
<LoopDo,>
<KeyOpen,>
<ResPrint,>
<ParOpen,>
<Cad,"{9}">
<ParClose,>
<SemCol,>
<ResLet,>
<ID,1>
<TypeInt,>
<SemCol,>
<ID,1>
<AsValue,>
<ID,2>
<SemCol,>
<CondIf,>
<ParOpen,>
<ID,3>
<ParClose,>
<Return,>
<ID,0>
<SemCol,>
<KeyClose,>
<LoopWhile,>
<ParOpen,>
<ID,4>
<GT,>
<CteInt,9>
<AND,>
<ID,3>
<ParClose,>
<SemCol,>
<ID,5>
<AsValue,>
<ID,5>
<ParOpen,>
<ID,4>
<Com,>
<ID,3>
<Com,>
<ID,0>
<ParClose,>
<SemCol,>
<ID,0>
<AsValue,>
<Cad,"print (kk)">
<SemCol,>
<Return,>
<ID,2>
<SemCol,>
<KeyClose,>
<Teof,>
```

- **TABLA DE SIMBOLOS** 

```text
```

- **TRAZA PARSER** 

```text
D	 1 32 21 25 23 1 32 21 24 23 1 32 21 26 23 2 53 27 25 54 25 57 40 33 40 35 37 29 9 17 9 16 4 14 14 40 32 21 24 23 40 35 36 51 20 9 15 19 14 40 31 42 9 15 19 14 44 38 49 9 15 19 14 41 34 9 15 19 11 9 16 5 12 9 15 19 14 40 35 36 51 20 9 15 18 45 9 15 19 14 47 9 15 19 14 47 9 15 19 14 48 14 40 35 36 51 20 9 16 4 14 40 35 38 49 9 15 19 14 41 3 
```

- **ARBOL DE A.S** 

### Pruebas con errores

#### Prueba 6

```text
```
- **TOKENS** 

```text
```

- **TABLA DE SIMBOLOS** 

```text
```

- **TRAZA PARSER** 

```text
```

- **ARBOL DE A.S** 

#### Prueba 7

```text
```
- **TOKENS** 

```text
```

- **TABLA DE SIMBOLOS** 

```text
```

- **TRAZA PARSER** 

```text
```

- **ARBOL DE A.S** 

#### Prueba 8

```text
```
- **TOKENS** 

```text
```

- **TABLA DE SIMBOLOS** 

```text
```

- **TRAZA PARSER** 

```text
```

- **ARBOL DE A.S** 

#### Prueba 9

```text
```
- **TOKENS** 

```text
```

- **TABLA DE SIMBOLOS** 

```text
```

- **TRAZA PARSER** 

```text
```

- **ARBOL DE A.S** 

#### Prueba 10

```text
```
- **TOKENS** 

```text
```

- **TABLA DE SIMBOLOS** 

```text
```

- **TRAZA PARSER** 

```text
```

- **ARBOL DE A.S** 