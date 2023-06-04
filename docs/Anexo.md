# Anexo

- [Anexo](#anexo)
  - [Pruebas Correctas](#pruebas-correctas)
    - [Prueba 1](#prueba-1)
    - [Prueba 2](#prueba-2)
    - [Prueba 3](#prueba-3)
    - [Prueba 4](#prueba-4)
  - [Pruebas con Errores](#pruebas-con-errores)
    - [Prueba 6](#prueba-6)
    - [Prueba 7](#prueba-7)
    - [Prueba 8](#prueba-8)
    - [Prueba 9](#prueba-9)
    - [Prueba 10](#prueba-10)

## Pruebas Correctas

### Prueba 1

```js
let sexto string;
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
 (texto);
```

---

- **TOKENS**

```
<ResLet,>  
<ID,0>  
<TypeString,>  
<SemCol,>  
<FunID,>  
<ID,1>  
<ParOpen,>  
<TypeString,>  
<ID,2>  
<ParClose,>  
<KeyOpen,>  
<ResPrint,>  
<ID,2>  
<SemCol,>  
<KeyClose,>  
<FunID,>  
<ID,3>  
<ParOpen,>  
<ParClose,>  
<KeyOpen,>  
<ResPrint,>  
<Cad,"Introduce un texto">  
<SemCol,>  
<ResIn,>  
<ID,4>  
<SemCol,>  
<KeyClose,>  
<ID,3>  
<ParOpen,>  
<ParClose,>  
<SemCol,>  
<ID,1>  
<ParOpen,>  
<ID,4>  
<ParClose,>  
<SemCol,>  
<Teof,>
```

---

- **TABLA DE SIMBOLOS**

```text
TABLA PRINCIPAL #1:  
--------- ----------  
* Lexema:'sexto'  
   Atributos:  
   + type:'TypeString'  
   + offset:0  
  
--------- ----------  
* Lexema:'alert'  
   Atributos:  
   + type:'Function'  
   + NumParams:1  
   + TypesParams:'[TypeString]'  
   + ReturnType:'Void'  
   + offset:64  
  
--------- ----------  
* Lexema:'pideTexto'  
   Atributos:  
   + type:'Function'  
   + NumParams:0  
   + TypesParams:'[]'  
   + ReturnType:'Void'  
   + offset:128  
  
--------- ----------  
* Lexema:'texto'  
   Atributos:  
   + type:'TypeInt'  
   + offset:129  
  
--------- ----------  
  
TABLA DE LA FUNCION alert #2:  
--------- ----------  
* Lexema:'msg_'  
   Atributos:  
   + type:'TypeString'  
   + offset:0  
  
--------- ----------    
  
TABLA DE LA FUNCION pideTexto #3:  
--------- ----------
```

---

- **TRAZA PARSER**

```txt
D    1 32 21 25 23 2 53 28 54 25 57 40 35 37 29 9 15 19 14 41 2 53 28 55 40 35 37 29 9 16 4 14 40 35 37 30 41 1 35 36 52 46 1 35 36 52 45 9 15 19 14 48 3
```

---

- **ARBOL DEL A.S**

![[p1.png]]

---

### Prueba 2

```js
let a int;  
let   b  int  ;  
let bbb boolean ;  
a = 3;  
b=a                                                                                                           ;  
let c boolean;  
  
c = a > b;  
  
if (c) b = 3333;  
a = a % b;  
print a ;  
print b;
```

---

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

---

- **TABLA DE SIMBOLOS**

```text
TABLA PRINCIPAL #1:  
--------- ----------  
* Lexema:'a'  
   Atributos:  
   + type:'TypeInt'  
   + offset:0  
  
--------- ----------  
* Lexema:'b'  
   Atributos:  
   + type:'TypeInt'  
   + offset:1  
  
--------- ----------  
* Lexema:'bbb'  
   Atributos:  
   + type:'TypeBool'  
   + offset:2  
  
--------- ----------  
* Lexema:'c'  
   Atributos:  
   + type:'TypeBool'  
   + offset:3
```

---

- **TRAZA PARSER**

```text
D    1 32 21 24 23 1 32 21 24 23 1 32 21 26 23 1 35 36 51 20 9 16 5 14 1 35 36 51 20 9 15 19 14 1 32 21 26 23 1 35 36 51 20 9 15 19 11 9 15 19 14 1 31 42 9 15 19 14 44 36 51 20 9 16 5 14 1 35 36 51 20 9 15 19 13 9 15 19 14 1 35 37 29 9 15 19 14 1 35 37 29 9 15 19 14 3
```

---

- **ARBOL DEL A.S**

   ![[p2.png]]

---

### Prueba 3

```js
let n1  int     ;  
let l1 boolean ;  
let cad    string  ;  
let n2 int     ;  
let l2 boolean ;  
input n1;  
l1 = l2;  
if (l1&& l2) cad = "hello";  
n2 = n1 % 378;  
  
print  33  
%  
n1  
%  
n2;  
  
function ff boolean(boolean ss)  
{  
    varglobal = 8;  
    if (l1) l2 = ff (ss);  
    return ss;  
}  
  
  
  
if (ff(l2))  
    print varglobal;
```

---

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
<ID,6>  
<ParClose,>  
<KeyOpen,>  
<ID,7>  
<AsValue,>  
<CteInt,8>  
<SemCol,>  
<CondIf,>  
<ParOpen,>  
<ID,1>  
<ParClose,>  
<ID,4>  
<AsValue,>  
<ID,5>  
<ParOpen,>  
<ID,6>  
<ParClose,>  
<SemCol,>  
<Return,>  
<ID,6>  
<SemCol,>  
<KeyClose,>  
<CondIf,>  
<ParOpen,>  
<ID,5>  
<ParOpen,>  
<ID,4>  
<ParClose,>  
<ParClose,>  
<ResPrint,>  
<ID,7>  
<SemCol,>  
<Teof,>
```

---

- **TABLA DE SIMBOLOS**

```text
TABLA PRINCIPAL #1:  
--------- ----------  
* Lexema:'sexto'  
   Atributos:  
   + type:'TypeString'  
   + offset:0  
  
--------- ----------  
* Lexema:'alert'  
   Atributos:  
   + type:'Function'  
   + NumParams:1  
   + TypesParams:'[TypeString]'  
   + ReturnType:'Void'  
   + offset:64  
  
--------- ----------  
* Lexema:'pideTexto'  
   Atributos:  
   + type:'Function'  
   + NumParams:0  
   + TypesParams:'[]'  
   + ReturnType:'Void'  
   + offset:128  
  
--------- ----------  
* Lexema:'texto'  
   Atributos:  
   + type:'TypeInt'  
   + offset:129  
  
--------- ----------  
  
TABLA DE LA FUNCION alert #2:  
--------- ----------  
* Lexema:'msg_'  
   Atributos:  
   + type:'TypeString'  
   + offset:0  
  
--------- ----------    
  
TABLA DE LA FUNCION pideTexto #3:  
--------- ----------
```

---

- **TRAZA PARSER**

```text
D    1 32 21 25 23 2 53 28 54 25 57 40 35 37 29 9 15 19 14 41 2 53 28 55 40 35 37 29 9 16 4 14 40 35 37 30 41 1 35 36 52 46 1 35 36 52 45 9 15 19 14 48 3
```

---

- **ARBOL DEL A.S**

---

### Prueba 4

```js
let sexto string;
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
 (texto);
```

---

- **TOKENS**

```text
<ResLet,>  
<ID,0>  
<TypeString,>  
<SemCol,>  
<FunID,>  
<ID,1>  
<ParOpen,>  
<TypeString,>  
<ID,2>  
<ParClose,>  
<KeyOpen,>  
<ResPrint,>  
<ID,2>  
<SemCol,>  
<KeyClose,>  
<FunID,>  
<ID,3>  
<ParOpen,>  
<ParClose,>  
<KeyOpen,>  
<ResPrint,>  
<Cad,"Introduce un texto">  
<SemCol,>  
<ResIn,>  
<ID,4>  
<SemCol,>  
<KeyClose,>  
<ID,3>  
<ParOpen,>  
<ParClose,>  
<SemCol,>  
<ID,1>  
<ParOpen,>  
<ID,4>  
<ParClose,>  
<SemCol,>  
<Teof,>
```

---

- **TABLA DE SIMBOLOS**

```text
TABLA PRINCIPAL #1:  
--------- ----------  
* Lexema:'sexto'  
   Atributos:  
   + type:'TypeString'  
   + offset:0  
  
--------- ----------  
* Lexema:'alert'  
   Atributos:  
   + type:'Function'  
   + NumParams:1  
   + TypesParams:'[TypeString]'  
   + ReturnType:'Void'  
   + offset:64  
  
--------- ----------  
* Lexema:'pideTexto'  
   Atributos:  
   + type:'Function'  
   + NumParams:0  
   + TypesParams:'[]'  
   + ReturnType:'Void'  
   + offset:128  
  
--------- ----------  
* Lexema:'texto'  
   Atributos:  
   + type:'TypeInt'  
   + offset:129  
  
--------- ----------  
  
TABLA DE LA FUNCION alert #2:  
--------- ----------  
* Lexema:'msg_'  
   Atributos:  
   + type:'TypeString'  
   + offset:0  
  
--------- ----------    
  
TABLA DE LA FUNCION pideTexto #3:  
--------- ----------
```

---

- **TRAZA PARSER**

```text
D    1 32 21 25 23 2 53 28 54 25 57 40 35 37 29 9 15 19 14 41 2 53 28 55 40 35 37 29 9 16 4 14 40 35 37 30 41 1 35 36 52 46 1 35 36 52 45 9 15 19 14 48 3
```

---

- **ARBOL DEL A.S**

---

## Pruebas con Errores

### Prueba 6

- **SOURCE**

```Js
/* String e Int muy largos, faltan algunos puntos y comas */
let sexto string;
let a int;
function alert (string msg_)
{
 print "mnzimswrj0szl1i46uoxz1geg87h5xp4bnga70pktl9tgodjakcur4lpawhoghkfb"
 print msg_;
 
}
function pideTexto ()
{
 print "Introduce un texto";
 a = 99999;
 input texto;
}
pideTexto();
  alert
```

- **Tokens**

```text
<ResLet,>
<ID,0>
<TypeString,>
<SemCol,>
<ResLet,>
<ID,1>
<TypeInt,>
<SemCol,>
<FunID,>
<ID,2>
<ParOpen,>
<TypeString,>
<ID,3>
<ParClose,>
<KeyOpen,>
<ResPrint,>
<Cad,"mnzimswrj0szl1i46uoxz1geg87h5xp4bnga70pktl9tgodjakcur4lpawhoghkfb">
<ResPrint,>
<ID,3>
<SemCol,>
<KeyClose,>
<FunID,>
<ID,4>
<ParOpen,>
<ParClose,>
<KeyOpen,>
<ResPrint,>
<Cad,"Introduce un texto">
<SemCol,>
<ID,1>
<AsValue,>
<CteInt,99999>
<SemCol,>
<ResIn,>
<ID,5>
<SemCol,>
<KeyClose,>
<ID,4>
<ParOpen,>
<ParClose,>
<SemCol,>
<ID,2>
<Teof,>
<Teof,>

```

- **TABLA DE SIMBOLOS**

```text
TABLA PRINCIPAL #1:
--------- ----------
* Lexema:'sexto'
   Atributos:
   + type:'TypeString'
   + offset:0

--------- ----------
* Lexema:'a'
   Atributos:
   + type:'TypeInt'
   + offset:64

--------- ----------
* Lexema:'alert'
   Atributos:
   + type:'Function'
   + NumParams:1
   + TypesParams:'[TypeString]'
   + ReturnType:'Void'
   + offset:65

--------- ----------
* Lexema:'pideTexto'
   Atributos:
   + type:'Function'
   + NumParams:0
   + TypesParams:'[]'
   + ReturnType:'Void'
   + offset:129

--------- ----------
* Lexema:'texto'
   Atributos:
   + type:'TypeInt'
   + offset:130

--------- ----------

TABLA DE LA FUNCION alert #2:
--------- ----------
* Lexema:'msg_'
   Atributos:
   + type:'TypeString'
   + offset:0

--------- ----------
--------- ----------

TABLA DE LA FUNCION pideTexto #3:
--------- ----------

```

- **TRAZA PARSER**

```text
D  1 32 21 25 23 1 32 21 24 23 2 53 28 54 25 57 40 35 37 29 9 16 4 14 40 35 36 41 2 53 28 55 40 35 37 29 9 16 4 14 40 35 36 51 20 9 16 5 14 40 35 37 30 41 1 35 36 52 46 1 35 36 3 
```

- **ERRORES**

  ```text
  ERROR FOUND USING THE LEXICAL ANALYZER

   ###################
   Error #12 @ line 6: String demasiado largo

   ERROR FOUND USING THE LEXICAL ANALYZER
   ###################
   Error #12 @ line 6: String demasiado largo

   ERROR FOUND USING THE SINTACTIC ANALYZER
   ###################
   Error #107 @ line 7: Se esperaba ';'

   - TRAZA -> D  1 32 21 25 23 1 32 21 24 23 2 53 28 54 25 57 40 35 37 29 9 16 4 14
   - ÚLTIMO TK LEIDO -> <ResPrint,>

   ERROR FOUND USING THE SINTACTIC ANALYZER
   ###################
   Error #103 @ line 7: Se esperaba el inicio de la expresión: '(

   - TRAZA -> D  1 32 21 25 23 1 32 21 24 23 2 53 28 54 25 57 40 35 37 29 9 16 4 14 40 35 36
   - ÚLTIMO TK LEIDO -> <SemCol,>

   ERROR FOUND USING THE LEXICAL ANALYZER
   ###################
   Error #11 @ line 13: SE HA SUPERADO EL MÁXIMO INT

   ERROR FOUND USING THE SINTACTIC ANALYZER
   ###################
   Error #103 @ line 17: Se esperaba el inicio de la expresión: '(

   - TRAZA -> D  1 32 21 25 23 1 32 21 24 23 2 53 28 54 25 57 40 35 37 29 9 16 4 14 40 35 36 41 2 53 28 55 40 35 37 29 9 16 4 14 40 35 36 51 20 9 16 5 14 40 35 37 30 41 1 35 36 52 46 1 35 36
   - ÚLTIMO TK LEIDO -> <Teof,>
   ```

### Prueba 7

- **SOURCE**

```JS
/* Tests INOUT mal */
function badPrint(){
    print true
    print ;
}

function badInput(){
    input (something);
}

badPrint();
badInput();

```

- **Tokens**

```text
<FunID,>
<ID,0>
<ParOpen,>
<ParClose,>
<KeyOpen,>
<ResPrint,>
<TokT,>
<ResPrint,>
<SemCol,>
<KeyClose,>
<FunID,>
<ID,1>
<ParOpen,>
<ParClose,>
<KeyOpen,>
<ResIn,>
<ParOpen,>
<ID,2>
<ParClose,>
<SemCol,>
<KeyClose,>
<ID,0>
<ParOpen,>
<ParClose,>
<SemCol,>
<ID,1>
<ParOpen,>
<ParClose,>
<SemCol,>
<Teof,>

```

- **TABLA DE SIMBOLOS**

```text
TABLA PRINCIPAL #1:
--------- ----------
* Lexema:'badPrint'
   Atributos:
   + type:'Function'
   + NumParams:0
   + TypesParams:'[]'
   + ReturnType:'Void'
   + offset:0

--------- ----------
* Lexema:'badInput'
   Atributos:
   + type:'Function'
   + NumParams:0
   + TypesParams:'[]'
   + ReturnType:'Void'
   + offset:0

--------- ----------
* Lexema:'something'
   Atributos:
   + type:'TypeInt'
   + offset:1

--------- ----------

TABLA DE LA FUNCION badPrint #2:
--------- ----------
--------- ----------

TABLA DE LA FUNCION badInput #3:
--------- ----------

```

- **TRAZA PARSER**

```text
D  2 53 28 55 40 35 37 29 9 16 6 14 40 41 2 53 28 55 40 35 37 30 40 35 36 40 41 1 35 36 52 46 1 35 36 52 46 3 
```

- **ERRORES**

```text
ERROR FOUND USING THE SINTACTIC ANALYZER
###################
Error #107 @ line 4: Se esperaba ';'
- TRAZA -> D  2 53 28 55 40 35 37 29 9 16 6 14 
- ÚLTIMO TK LEIDO -> <ResPrint,>


ERROR FOUND USING THE SINTACTIC ANALYZER
###################
Error #102 @ line 4: Se esperaba una declaración o un condicional
- TRAZA -> D  2 53 28 55 40 35 37 29 9 16 6 14 40 
- ÚLTIMO TK LEIDO -> <SemCol,>


ERROR FOUND USING THE SINTACTIC ANALYZER
###################
Error #105 @ line 8: Se esperaba un identificador
- TRAZA -> D  2 53 28 55 40 35 37 29 9 16 6 14 40 41 2 53 28 55 40 35 37 30 
- ÚLTIMO TK LEIDO -> <ParOpen,>


ERROR FOUND USING THE SINTACTIC ANALYZER
###################
Error #103 @ line 8: Se esperaba el inicio de la expresión: '(
- TRAZA -> D  2 53 28 55 40 35 37 29 9 16 6 14 40 41 2 53 28 55 40 35 37 30 40 35 36 
- ÚLTIMO TK LEIDO -> <ParClose,>


ERROR FOUND USING THE SINTACTIC ANALYZER
###################
Error #102 @ line 8: Se esperaba una declaración o un condicional
- TRAZA -> D  2 53 28 55 40 35 37 29 9 16 6 14 40 41 2 53 28 55 40 35 37 30 40 35 36 40 
- ÚLTIMO TK LEIDO -> <SemCol,>



```

### Prueba 8

- **SOURCE**

```JS
/* Mal formados function y bucle while */
let texto string;

print x__x;
function alert (string msg_)
{
 print msg_;
}
pideTexto ()
{
 while(5>n);
}
pideTexto();
  alert
 (texto);

```

- **Tokens**

```text
<ResLet,>
<ID,0>
<TypeString,>
<SemCol,>
<ResPrint,>
<ID,1>
<SemCol,>
<FunID,>
<ID,2>
<ParOpen,>
<TypeString,>
<ID,3>
<ParClose,>
<KeyOpen,>
<ResPrint,>
<ID,3>
<SemCol,>
<KeyClose,>
<ID,4>
<ParOpen,>
<ParClose,>
<KeyOpen,>
<LoopWhile,>

```

- **TABLA DE SIMBOLOS**

```text
TABLA PRINCIPAL #1:
--------- ----------
* Lexema:'texto'
   Atributos:
   + type:'TypeString'
   + offset:0

--------- ----------
* Lexema:'x__x'
   Atributos:
   + type:'TypeInt'
   + offset:65

--------- ----------
* Lexema:'alert'
   Atributos:
   + type:'Function'
   + NumParams:1
   + TypesParams:'[TypeString]'
   + ReturnType:'Void'
   + offset:65

--------- ----------
* Lexema:'pideTexto'
   Atributos:
   + type:'TypeInt'
   + offset:130

--------- ----------

TABLA DE LA FUNCION alert #2:
--------- ----------
* Lexema:'msg_'
   Atributos:
   + type:'TypeString'
   + offset:0

--------- ----------

```

- **TRAZA PARSER**

```text
D  1 32 21 25 23 1 35 37 29 9 15 19 14 2 53 28 54 25 57 40 35 37 29 9 15 19 14 41 1 35 36 52 46 
```

- **ERRORES**

```text
ERROR FOUND USING THE SINTACTIC ANALYZER
###################
Error #107 @ line 10: Se esperaba ';'
- TRAZA -> D  1 32 21 25 23 1 35 37 29 9 15 19 14 2 53 28 54 25 57 40 35 37 29 9 15 19 14 41 1 35 36 52 46 
- ÚLTIMO TK LEIDO -> <KeyOpen,>


ERROR FOUND USING THE SINTACTIC ANALYZER
###################
Error #100 @ line 11: Error sintáctico genérico
- TRAZA -> D  1 32 21 25 23 1 35 37 29 9 15 19 14 2 53 28 54 25 57 40 35 37 29 9 15 19 14 41 1 35 36 52 46 
- ÚLTIMO TK LEIDO -> <LoopWhile,>

```

### Prueba 9

- **SOURCE**

```JS
/* Bad formatted expressions */
let n1 int;
let mentira boolean = false;
function jodete(int n2){
    do{
        if(mentira > (7>n1))
            print n1 && 5;
        ++n2;
    }while(n2>9);
    return n1;
}

```

- **Tokens**

```text
<ResLet,>
<ID,0>
<TypeInt,>
<SemCol,>
<ResLet,>
<ID,1>
<TypeBool,>
<AsValue,>
<TokF,>
<SemCol,>
<FunID,>
<ID,2>
<ParOpen,>
<TypeInt,>
<ID,3>
<ParClose,>
<KeyOpen,>
<LoopDo,>
<KeyOpen,>
<CondIf,>
<ParOpen,>
<ID,1>
<GT,>
<ParOpen,>
<CteInt,7>
<GT,>
<ID,0>
<ParClose,>
<ParClose,>
<ResPrint,>
<ID,0>
<AND,>
<CteInt,5>
<SemCol,>
<ResAutoSum,>
<ID,3>
<SemCol,>
<KeyClose,>
<LoopWhile,>
<ParOpen,>
<ID,4>
<GT,>
<CteInt,9>
<ParClose,>
<SemCol,>
<Return,>
<ID,0>
<SemCol,>
<KeyClose,>
<Teof,>

```

- **TABLA DE SIMBOLOS**

```text
TABLA PRINCIPAL #1:
--------- ----------
* Lexema:'n1'
   Atributos:
   + type:'TypeInt'
   + offset:0

--------- ----------
* Lexema:'mentira'
   Atributos:
   + type:'TypeBool'
   + offset:1

--------- ----------
* Lexema:'jodete'
   Atributos:
   + type:'Function'
   + NumParams:1
   + TypesParams:'[TypeInt]'
   + ReturnType:'Void'
   + offset:2

--------- ----------
* Lexema:'n2'
   Atributos:
   + type:'TypeInt'
   + offset:3

--------- ----------

TABLA DE LA FUNCION jodete #2:
--------- ----------
* Lexema:'n2'
   Atributos:
   + type:'TypeInt'
   + offset:0

--------- ----------

```

- **TRAZA PARSER**

```text
D  1 32 21 24 23 1 32 21 26 22 20 9 16 7 14 2 53 28 54 24 57 40 33 40 31 42 9 15 19 11 9 17 9 16 5 11 9 15 19 14 14 44 37 29 9 15 19 12 9 16 5 14 40 35 39 8 41 34 9 15 19 11 9 16 5 14 40 35 38 49 9 15 19 14 41 3 
```

- **ERRORES**

```text
ERROR FOUND USING THE SEMANTIC ANALYZER
###################
Error #202 @ line 10: Error de unicidad; se ha declarado de nuevo el identificador: n1

```

### Prueba 10

- **SOURCE**

```JS
/*Varibales declaradas */
let texto string;

print ;
input esto_es_un_nombre_de_variable_global_de_tipo_entero_que_tiene_que_aparecer_en_la_TS_global;
print x__x;
function alert (string msg_)
{
    let texto string ="hola";
 print msg_;
}
function pideTexto ()
{
 print "Introduce un texto";
    alert(texto)
 input texto;
}
pideTexto();
  alert
 (nuevaVar);

```

- **Tokens**

```text
<ResLet,>
<ID,0>
<TypeString,>
<SemCol,>
<ResPrint,>
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
<ID,4>
<ParClose,>
<KeyOpen,>
<ResLet,>
<ID,5>
<TypeString,>
<AsValue,>
<Cad,"hola">
<SemCol,>
<ResPrint,>
<ID,4>
<SemCol,>
<KeyClose,>
<FunID,>
<ID,6>
<ParOpen,>
<ParClose,>
<KeyOpen,>
<ResPrint,>
<Cad,"Introduce un texto">
<SemCol,>
<ID,3>
<ParOpen,>
<ID,0>
<ParClose,>
<ResIn,>
<ID,0>
<SemCol,>
<KeyClose,>
<ID,6>
<ParOpen,>
<ParClose,>
<SemCol,>
<ID,3>
<ParOpen,>
<ID,7>
<ParClose,>
<SemCol,>
<Teof,>

```

- **TABLA DE SIMBOLOS**

```text
TABLA PRINCIPAL #1:
--------- ----------
* Lexema:'texto'
   Atributos:
   + type:'TypeString'
   + offset:0

--------- ----------
* Lexema:'esto_es_un_nombre_de_variable_global_de_tipo_entero_que_tiene_que_aparecer_en_la_TS_global'
   Atributos:
   + type:'TypeInt'
   + offset:65

--------- ----------
* Lexema:'x__x'
   Atributos:
   + type:'TypeInt'
   + offset:66

--------- ----------
* Lexema:'alert'
   Atributos:
   + type:'Function'
   + NumParams:1
   + TypesParams:'[TypeString]'
   + ReturnType:'Void'
   + offset:66

--------- ----------
* Lexema:'pideTexto'
   Atributos:
   + type:'Function'
   + NumParams:0
   + TypesParams:'[]'
   + ReturnType:'Void'
   + offset:194

--------- ----------
* Lexema:'nuevaVar'
   Atributos:
   + type:'TypeInt'
   + offset:195

--------- ----------

TABLA DE LA FUNCION alert #2:
--------- ----------
* Lexema:'msg_'
   Atributos:
   + type:'TypeString'
   + offset:0

--------- ----------
* Lexema:'texto'
   Atributos:
   + type:'TypeString'
   + offset:64

--------- ----------
--------- ----------

TABLA DE LA FUNCION pideTexto #3:
--------- ----------

```

- **TRAZA PARSER**

```text
D  1 32 21 25 23 1 35 37 29 1 35 37 30 1 35 37 29 9 15 19 14 2 53 28 54 25 57 40 32 21 25 22 20 9 16 4 14 40 35 37 29 9 15 19 14 41 2 53 28 55 40 35 37 29 9 16 4 14 40 35 36 52 45 9 15 19 14 40 35 36 41 1 35 36 52 46 1 35 36 52 45 9 15 19 14 3 
```

- **ERRORES**

```text
ERROR FOUND USING THE SEMANTIC ANALYZER
###################
Error #221 @ line 4: Intentando declarar una expresión vacía/inválida
- TRAZA -> D  1 32 21 25 23 1 35 37 29 
- ÚLTIMO TK LEIDO -> <SemCol,>


ERROR FOUND USING THE SINTACTIC ANALYZER
###################
Error #113 @ line 16: Se esperaba el cierre de la expresión: ')'
- TRAZA -> D  1 32 21 25 23 1 35 37 29 1 35 37 30 1 35 37 29 9 15 19 14 2 53 28 54 25 57 40 32 21 25 22 20 9 16 4 14 40 35 37 29 9 15 19 14 41 2 53 28 55 40 35 37 29 9 16 4 14 40 35 36 52 45 9 15 19 14 
- ÚLTIMO TK LEIDO -> <ResIn,>


ERROR FOUND USING THE SINTACTIC ANALYZER
###################
Error #107 @ line 16: Se esperaba ';'
- TRAZA -> D  1 32 21 25 23 1 35 37 29 1 35 37 30 1 35 37 29 9 15 19 14 2 53 28 54 25 57 40 32 21 25 22 20 9 16 4 14 40 35 37 29 9 15 19 14 41 2 53 28 55 40 35 37 29 9 16 4 14 40 35 36 52 45 9 15 19 14 
- ÚLTIMO TK LEIDO -> <ResIn,>


ERROR FOUND USING THE SINTACTIC ANALYZER
###################
Error #103 @ line 16: Se esperaba el inicio de la expresión: '(
- TRAZA -> D  1 32 21 25 23 1 35 37 29 1 35 37 30 1 35 37 29 9 15 19 14 2 53 28 54 25 57 40 32 21 25 22 20 9 16 4 14 40 35 37 29 9 15 19 14 41 2 53 28 55 40 35 37 29 9 16 4 14 40 35 36 52 45 9 15 19 14 40 35 36 
- ÚLTIMO TK LEIDO -> <SemCol,>


ERROR FOUND USING THE SINTACTIC ANALYZER
###################
Error #113 @ line 20: Se esperaba el cierre de la expresión: ')'
- TRAZA -> D  1 32 21 25 23 1 35 37 29 1 35 37 30 1 35 37 29 9 15 19 14 2 53 28 54 25 57 40 32 21 25 22 20 9 16 4 14 40 35 37 29 9 15 19 14 41 2 53 28 55 40 35 37 29 9 16 4 14 40 35 36 52 45 9 15 19 14 40 35 36 41 1 35 36 52 46 1 35 36 52 45 9 15 19 14 
- ÚLTIMO TK LEIDO -> <SemCol,>
```
