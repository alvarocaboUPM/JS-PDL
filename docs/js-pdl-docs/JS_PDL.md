# Introducción a JavaScript-PdL para la Práctica de "Procesadores de Lenguajes"

## Table of Contents

- [Generalidades](http://dlsiisv.fi.upm.es/procesadores/IntroJavaScript.html#Generalidades "Generalidades")
- [Estructura de un Programa](http://dlsiisv.fi.upm.es/procesadores/IntroJavaScript.html#Estructura "Estructura de un Programa")
- [Comentarios](http://dlsiisv.fi.upm.es/procesadores/IntroJavaScript.html#Comentarios "Comentarios")
- [Constantes](http://dlsiisv.fi.upm.es/procesadores/IntroJavaScript.html#Constantes "Constantes")
- [Operadores](http://dlsiisv.fi.upm.es/procesadores/IntroJavaScript.html#Operadores "Operadores")
- [Identificadores](http://dlsiisv.fi.upm.es/procesadores/IntroJavaScript.html#Identificadores "Identificadores")
- [Declaraciones](http://dlsiisv.fi.upm.es/procesadores/IntroJavaScript.html#Declaraciones "Declaraciones")
- [Tipos de Datos](http://dlsiisv.fi.upm.es/procesadores/IntroJavaScript.html#Tipos "Tipos de Datos")
- [Instrucciones de entrada/salida](http://dlsiisv.fi.upm.es/procesadores/IntroJavaScript.html#entradasalida "Entrada/Salida")
- [Sentencias](http://dlsiisv.fi.upm.es/procesadores/IntroJavaScript.html#Sentencias "Sentencias")
- [Funciones](http://dlsiisv.fi.upm.es/procesadores/IntroJavaScript.html#Funciones "Subprogramas")
- [Más información sobre el lenguaje](http://dlsiisv.fi.upm.es/procesadores/IntroJavaScript.html#MasInfo)

___

## Generalidades

JavaScript es un lenguaje de programación ideado en 1995 en Netscape a partir de los lenguajes C, C++ y Java.

Este resumen presenta las principales características de la variante de JavaScript denominada JavaScript-PdL que _es la que hay que utilizar para la práctica_ de la asignatura. No hay que considerar los elementos de JavaScript no mencionados en este resumen y se deben considerar los elementos y características tal como aparecen aquí descritos. Entre corchetes se dan indicaciones sobre la obligatoriedad u opcionalidad de algunas partes del lenguaje en cuanto a su implementación, aunque no se recomienda que el grupo de prácticas intente abarcar muchos elementos opcionales. Con el fin de facilitar la implementación de la Práctica, las características mostradas en esta página pueden no coincidir al 100% con el estándar del lenguaje JavaScript, por lo que, en caso de duda, se deberá implementar siempre el comportamiento aquí descrito.

### Some basics about the languaje

- JavaScript-PdL es un lenguaje en el que se diferencian las minúsculas y las mayúsculas (es **case sensitive**).

- JavaScript-PdL es un lenguaje de formato libre, es decir, que se admiten espacios, tabuladores, saltos de línea y comentarios en cualquier parte del código.
  - Las sentencias finalizan en punto y coma.

- Las palabras clave que tiene el lenguaje son reservadas, aunque cada grupo de prácticas sólo ha de tener en cuenta las palabras asignadas a su grupo.

JavaScript-PdL es un lenguaje con estructura de bloques que se definen mediante la utilización de las llaves `{ }` y, por tanto, maneja los conceptos de:

- Identificadores globales y locales: Los identificadores declarados fuera de cualquier función son globales y pueden ser utilizados desde cualquier función. Los declarados en el interior de una función son locales a dicha función.

- En JavaScript-PdL no es obligatorio declarar todos los identificadores antes de que se utilicen; en este caso, un uso de un identificador no declarado se considera una variable global entera.

- Además, hay que realizar la implementación considerando que es un lenguaje con **recursividad**, por lo que cualquier función puede ser recursiva. El lenguaje no permite la definición de funciones anidadas.

## Estructura de un Programa

Debe considerarse que un programa en JavaScript-PdL estará compuesto por un único fichero que puede tener declaraciones de variables globales, sentencias y declaración de funciones, en cualquier orden.

En este enlace se muestra un [ejemplo de un fichero](http://dlsiisv.fi.upm.es/procesadores/Ejem.javascript) válido según el lenguaje descrito en esta asignatura.

### Programa Principal

El programa principal (lo que se ejecutaría al arrancar el programa) estará formado por todas las sentencias ubicadas fuera de las funciones.

Por tanto, la ejecución comenzaría por la primera sentencia que se encuentre en el fuente (fuera de una función) y proseguiría secuencialmente hasta el final del fichero ejecutando todas las sentencias situadas fuera de las funciones. Hay que tener en cuenta que una función se ejecuta únicamente cuando es invocada.

## Comentarios

En JavaScript-PdL hay dos tipos de comentarios \[cada grupo deberá implementar obligatoriamente solo el que le corresponda\]:

1. Comentario de bloque: Se utilizan los caracteres `/*` para abrir el comentario, y `*/` para cerrarlo. No se permiten comentarios anidados. Los comentarios pueden ocupar más de una línea y pueden ir colocados en cualquier parte del código:  

    ```txt
    /* Comentario con apertura y cierre */
    ```

2. Comentario de línea: Los comentarios comienzan por los caracteres `//` y finalizan al acabar la línea. Este tipo de comentario sólo ocupa una línea y puede ir colocado en cualquier parte del código:  

    ```txt
    // Comentario de línea
    ```

## Constantes

El lenguaje dispone de varios tipos de constantes \[implementación obligatoria de las constantes enteras y cadenas\]:

### Enteras

Para representar las constantes enteras se utilizan los dígitos decimales. Por ejemplo: `378`.

Los números enteros se tienen que poder representar con una palabra (16 bits, incluido el signo), por lo que el máximo entero válido será el 32767.

### Cadenas de Caracteres

Las constantes cadena van encerradas entre comillas dobles (`"Hola, mundo"`) o entre comillas simples (`'Hola, mundo'`) \[cada grupo deberá implementar obligatoriamente solo la que le corresponda\]. Se utiliza internamente el carácter nulo (cuyo código ASCII es 0) como carácter de fin de cadena. Puede aparecer cualquier carácter imprimible en la cadena (por tanto, no es válido un salto de línea como tal; para usar el salto de línea, se pueden emplear las secuencias de escape).

Para representar caracteres especiales dentro de una cadena se utiliza una secuencia de escape. Una secuencia de escape se representa mediante el carácter barra inversa seguido de un determinado carácter. Algunos de estos caracteres son: el salto de línea (`\n`) o el carácter de barra inversa (`\\`) \[la implementación de estos caracteres especiales es opcional\].

Una cadena no puede contener más de 64 caracteres.

### Lógicas

En JavaScript-PdL existen dos constantes lógicas para representar verdadero y falso: `true` y `false` \[es opcional implementar las constantes lógicas, que son las palabras reservadas `true` y `false`\].

## Operadores

Este lenguaje presenta un conjunto de operadores con los que escribir distintas expresiones. Además, se pueden utilizar los paréntesis para agrupar subexpresiones \[es obligatorio implementar los paréntesis\]. Las _expresiones_ pueden tener varios operadores, varios operandos, paréntesis... Las expresiones se pueden utilizar en multitud de construcciones del lenguaje: asignaciones, condiciones de un `if`, condiciones de un bucle, parámetros de una función, instrucciones de salida, instrucción de retorno...

### Operadores Aritméticos

Son los operadores que permiten realizar la suma, resta, producto, división y módulo: `+`, `-`, `*`, `/` y `%` \[obligatorio implementar al menos uno y dos como máximo\]. Se aplican sobre datos enteros, proporcionando un resultado entero (en el caso de la división, redondeando el valor si es necesario).

También existen los operadores más y menos unarios: `+`, `-` \[implementación opcional\]. Estos operadores se pueden utilizar delante de una constante entera, una variable o una expresión.

### Operadores de Relación

Son los operadores que permiten realizar las comparaciones de igual, distinto, menor, mayor, menor o igual, mayor o igual: `==`, `!=`, `<`, `>`, `<=` y `>=` \[obligatorio implementar al menos uno de los operadores y dos como máximo\]. Se aplican sobre datos enteros y proporcionan un resultado lógico.

### Operadores Lógicos

Representan las operaciones de conjunción, disyunción y negación: `&&`, `||` y `!` \[obligatorio implementar al menos uno y dos como máximo\]. Se aplican sobre datos lógicos y devuelven un resultado lógico.

### Operadores de Incremento y Decremento

Permiten auto-incrementar o auto-decrementar el valor de una variable entera: `++` y `--` (pueden actuar como prefijos o como sufijos) \[algunos grupos tienen que implementar uno de estos operadores\]. Se aplican sobre variables enteras y devuelven un resultado entero modificando también el valor de la variable. Ejemplo:

```txt
a = j++; /* si j valía 5, ahora a == 5 y j == 6 */
a = ++j; /* si j valía 5, ahora a == 6 y j == 6 */
```txt

### Operadores de asignación

Permiten realizar asignaciones simples o realizando simultáneamente una operación: `=` (asignación), `+=` (asignación con suma), `-=` (asignación con resta), `*=` (asignación con producto), `/=` (asignación con división), `%=` (asignación con módulo), `&=` (asignación con y lógico) y `|=` (asignación con o lógico) \[todos los grupos tienen que implementar la asignación simple (`=`) y algunos grupos deberán implementar uno de los operadores de asignación con operación\]. Ejemplo:

```txt
n += m;    /* es equivalente a n = n + m */
b1 &= b2;  /* es equivalente a b1 = b1 && b2 */
```txt

### Precedencia de Operadores

En la tabla siguiente se muestra la precedencia de los operadores con el siguiente significado: los operadores del mismo grupo tienen la misma precedencia y, conforme se desciende por la tabla, la precedencia aumenta. En cualquier caso, el uso de paréntesis permite alterar el orden de evaluación de las expresiones \[es obligatorio para todos los grupos tener en cuenta la precedencia y asociatividad de los operadores utilizados\].

Precedencias de los Operadores Más información
Tabla con la relación de todos los operadores de JavaScript-PdL con su significado, orden de prioridad y relación de asociatividad. La primera columna muestra los operadores, la segunda su significado y la tercera la asociatividad.

| Operadores | Significado | Asociatividad |
| --- | --- | --- |
| `||` | O lógico | Izquierda a derecha |
| `&&` | Y lógico | Izquierda a derecha |
| `==   !=` | Igual  
Distinto | Izquierda a derecha |
| `>   >=   <   <=` | Mayor  
Mayor o igual  
Menor  
Menor o igual | Izquierda a derecha |
| `+   -` | Suma  
Resta | Izquierda a derecha |
| `*   /   %` | Producto  
División  
Módulo | Izquierda a derecha |
| `!   ++   --   +   -` | Negación lógica  
Autoincremento  
Autodecremento  
Más unario  
Menos unario | Derecha a izquierda |

## Identificadores

Los nombres de identificadores están formados por cualquier cantidad de letras, dígitos y subrayados (`_`), siendo el primero siempre una letra o un subrayado. Ejemplos: `a`, `a3`, `A3`, `Sueldo_de_Trabajador`, `_z__9_9__`...

Como ya se ha dicho, el lenguaje es dependiente de minúsculas o mayúsculas, por lo que los nombres `a3` y `A3` son identificadores distintos.

## Declaraciones

El lenguaje JavaScript-PdL no exige declaración explícita de las variables que se utilicen. En el caso de que se use un nombre de _variable que no ha sido declarado previamente_, se considera que dicha variable es global y entera (declaración implícita).

Para realizar una declaración explícita de una variable, se coloca la palabra `let` seguida del nombre de la variable y del tipo.

```txt
let var0 Tipo;
```txt

Pueden realizarse declaraciones explícitas en cualquier lugar dentro de una función; en este caso, la variable será local y visible desde ese punto hasta el final de la función. También pueden realizarse declaraciones explícitas fuera de las funciones en cualquier parte del código (variables globales), siendo solo visibles desde ese punto hasta el final del fichero.

Opcionalmente, puede inicializarse una variable en la misma instrucción de la declaración, colocando el operador de asignación (`=`) seguido de una expresión \[es opcional implementar la inicialización de variables\].

```txt
let var4 Tipo = expresión4;
```txt

Si una variable no se inicializa cuando se declara se realiza una inicialización por omisión basándose en su tipo: `0` si es entera, falso si es lógica y la cadena vacía (`""`) si es cadena.

Por tanto, el ámbito de una variable será global si no se ha declarado explícitamente o si se declara fuera de cualquier función, y será local si se declara dentro del cuerpo de una función. Cualquier otro bloque (por ejemplo, los usados por las sentencias de control) no definen un ámbito nuevo. No se admite la redeclaración del mismo identificador en un mismo ámbito.

## Tipos de Datos

El lenguaje dispone de distintos tipos de datos básicos.

Se deben considerar sólo los siguientes tipos de datos básicos: entero, lógico y cadena.

El tipo **entero** se refiere a un número entero que debe representarse con un tamaño de 1 palabra (16 bits). Se representa con la palabra `int`.

El tipo **lógico** permite representar valores lógicos. Se representa también con un tamaño de 1 palabra (16 bits). Las expresiones relacionales y lógicas devuelven un valor lógico. Se representa con la palabra `boolean`.

El tipo **cadena** permite representar secuencias de caracteres. Se representa con la palabra `string` y una variable de tipo cadena ocupa 64 palabras (128 bytes).

El lenguaje no tiene conversiones automáticas entre tipos.

Ejemplos:

```txt
let i int = 11;        // variable entera
let st string;         // variable cadena 
let b boolean;         // variable lógica
let c int = 66+i;      // variable entera
b = i != c + 1;        // i y c+1 son enteros; b valdrá verdadero
c = c + i;             // i y c son enteras; c valdrá 88
i = b + i;             // Error: no se puede sumar un lógico con un entero
b = ! i;               // Error: el operador de negación solo puede aplicarse a lógicos
```txt

## Instrucciones de Entrada/Salida

Las instrucciones de entrada/salida disponibles en el lenguaje son dos. Su uso tiene la sintaxis de una sentencia.

La instrucción `print expresión` evalúa la `expresión` e imprime el resultado por pantalla. La `expresión` puede ser de tipo cadena o entera. Por ejemplo:

```txt

c= 50; print c * 2 + 16 ; /* imprime: 116 */
a= 'Adiós';
print 'Hola'; print a;    // imprime HolaAdiós
```txt

La instrucción `input variable` lee un número o una cadena del teclado y lo almacena en la variable `variable`, que tiene que ser, respectivamente, de tipo entero o cadena. Por ejemplo:

```txt
let a int;
let c string;
input a;     /* lee un número */
print a * a; // imprime el cuadrado del número leído 
print"Pon tu nombre";
input  c;    /* lee una cadena */
print ("Hola, ");
print(c);    // imprime las cadenas
```txt

## Sentencias

De todo el grupo de sentencias del lenguaje JavaScript, se han seleccionado para ser implementadas las que aparecen a continuación \[opcional u obligatoriamente, según se indique en cada caso\]. Además de las sentencias aquí indicadas, también se consideran sentencias en este lenguaje las instrucciones de entrada/salida, así como las declaraciones de variables.

### Sentencias de Asignación

Existe una sentencia de asignación en JavaScript-PdL, que se construye mediante el símbolo de asignación `=` \[es obligatorio implementar la sentencia de asignación por todos los grupos; los grupos que tengan el operador de asignación con operación deberán implementar también la sentencia de asignación con operación con el operador asignado\]. Su sintaxis general es la siguiente: identificador, igual y expresión. Esta sentencia asigna al identificador el resultado de evaluar la expresión:

```txt
i= 8 + 6;
```txt

Como ya se ha indicado, no hay conversiones entre tipos, por lo que tanto el identificador como la expresión han de ser del mismo tipo.

```txt

let i int = 123; // i es una variable entera
let cad string;
print i;         // imprime el valor entero 123
cad= 'hola';
print cad;       // imprime el valor cadena "hola"
i = i > 88;   // Error: no se puede asignar un lógico a un entero
```txt

### Sentencia de Llamada a una Función

Esta sentencia permite invocar la ejecución de una función que debe estar previamente definida \[implementación obligatoria\].

La llamada a una función se realiza mediante el nombre de la función seguido de los parámetros actuales (separados por comas) entre paréntesis (si no tiene parámetros, hay que poner los paréntesis vacíos). Los parámetros pueden ser cualquier expresión válida en el lenguaje:

```txt
p1 (5);         /* llamada a una función con un argumento entero */ 
p2 ();          /* llamada sin parámetros a una función */ 
p3 ("", i - 8); /* llamada con dos argumentos a una función */
```txt

Los parámetros actuales en la llamada tienen que coincidir en número y tipo con los parámetros formales de la declaración de la función.

Si una función devuelve un valor, podrá incluirse una llamada a dicha función dentro de cualquier expresión. Si la llamada se realiza como una sentencia (no se realiza en una expresión), se invocará a la función pero el valor devuelto se perderá:

```txt

if (fun1 (9))    /* llamada a una función con un argumento entero */ 
c= b + fun2 (b, fun3() != 8); /* llamada con dos argumentos a una función, 
                 siendo fun3, una llamada a otra función sin argumentos */
fun2 (c, true);  /* el valor devuelto por fun2 se pierde */
```txt

### Sentencia de Retorno de una Función

JavaScript-PdL dispone de la sentencia `return` para finalizar la ejecución de una función y volver al punto desde el que fue llamada \[implementación obligatoria\]. Si no se desea que una función devuelva un valor, ésta terminará cuando se ejecute la instrucción `return` (sin expresión) o al llegar al final del cuerpo de la función. Si se desea que la función devuelva algún dato, deberá incluirse una expresión en la sentencia `return`. Si se indica, el tipo de la expresión retornada deberá coincidir con el tipo de la función. Si no se incluye una expresión, la función debe haber sido declarada sin tipo. No es necesario que todas las funciones tengan instrucción de retorno ni tampoco hay ninguna restricción a la cantidad o ubicación de instrucciones de retorno.

```txt
function SumaAlCuadrado int (int a, int b)
{
  j= a + b;
  return j * j;
  /* La función finaliza y devuelve el valor entero de la expresión */
}
function pro (int x)
{
  x= SumaAlCuadrado (x - 1, x);
   /* x contendrá el valor devuelto por la función: (x+x-1)^2 */
  if (x > (194/2)) return; /* finaliza la ejecución si se ejecuta */
  print SumaAlCuadrado (x, x);
} /* finaliza la ejecución si antes no se ejecutó el return */
```txt

### Sentencia Condicional simple

Selecciona la ejecución de una sentencia, dependiendo del valor correspondiente de una condición de tipo lógico \[implementación obligatoria para todos los grupos\]:

```txt
if (condición) sentencia
```txt

Si la `condición` lógica se evalúa como cierta, se ejecuta la `sentencia` que puede ser cualquier sentencia simple del lenguaje, es decir, asignación, instrucción de entrada/salida, llamada a función o retorno (también `break` o sentencias de auto-incremento o auto-decremento para los grupos que tengan dichas opciones); en caso contrario, se finaliza su ejecución:

```txt
if (a > b) c= b;
if (fin) print("adiós");
```txt

### Sentencia Condicional compuesta

Selecciona la ejecución de una de las secuencias de sentencias que encierra, dependiendo del valor correspondiente de una condición de tipo lógico. Tiene dos formatos \[implementación obligatoria de ambos para los grupos que les corresponda\]:

- ```txt
    if (condición)
    {
       cuerpo1
    }
    ```txt

- ```txt
    if (condición) 
    {
       cuerpo1
    }
    else 
    {
       cuerpo2
    }
    ```txt

Si la `condición` lógica se evalúa como cierta, se ejecuta el `cuerpo1`; en caso contrario, se ejecuta el `cuerpo2` (si el programador lo ha escrito). Cada uno de estos cuerpos contendrán cero, una o más sentencias o declaraciones, siempre entre llaves.

```txt
if (a > b)
{
  c= b;
}
else 
{
  c= a;
  if (fin)
  {
    print"adiós";
  }
}
```txt

### Sentencia Repetitiva `while`

Esta sentencia permite repetir la ejecución de unas sentencias basándose en el resultado de una expresión lógica \[implementación obligatoria para algunos grupos\]. La sintaxis es:

```txt
while (condición) 
{
   cuerpo
}
```txt

Se evalúa la `condición` lógica y, si resulta ser cierta, se ejecuta el `cuerpo` (que será un bloque de sentencias o declaraciones de variables entre llaves). Este proceso se repite hasta que la `condición` sea falsa:

```txt
while (n <= 10) 
{
    n= n + 1;
    print n;
} // mientras que n sea menor o igual que 10...
```txt

### Sentencia Repetitiva `do while`

Esta sentencia permite repetir la ejecución de las sentencias del bucle mientras se cumpla una condición \[implementación obligatoria para algunos grupos\]. La sintaxis es:

```txt
do {
   cuerpo
} while (condición);
```txt

En esta instrucción se ha de colocar un bloque de sentencias o declaraciones de variables encerradas entre llaves. Primero, se ejecuta el `cuerpo`; seguidamente, se evalúa la `condición` lógica y, si resulta ser cierta, se ejecuta de nuevo el `cuerpo`. Este proceso se repite hasta que la `condición` sea falsa:

```txt
do {
  c= a++;
  c*= b;
} while (a < b);
```txt

### Sentencia Repetitiva `for`

Esta sentencia `for` permite ejecutar un bucle según una condición compleja \[implementación obligatoria para algunos grupos\]. La sintaxis es:

```txt
for (inicialización; condición; actualización)
{
   cuerpo
}
```txt

La `inicialización` debe ser una sentencia de asignación o nada; la `condición` debe ser una expresión lógica; y la `actualización` puede ser una asignación (sencilla o con operación \[para los grupos que tengan esta opción\]), un autoincremento o autodecremento \[para los grupos que tengan esta opción\] o estar vacía. El `cuerpo` (sentencias o declaraciones de variables) está en un bloque delimitado por llaves.

El funcionamiento de este bucle es como sigue:

1. Se ejecuta la `inicialización`
2. Se evalúa la `condición`
3. Si la `condición` lógica es falsa, se abandona la ejecución del bucle
4. Se ejecuta el `cuerpo`
5. Se ejecuta la `actualización`
6. Se vuelve al paso 2.

```txt
for (i = 1; i < 10; i++)
{
 f *= i;
}
```txt

### Sentencia de Selección Múltiple

Esta sentencia selecciona y ejecuta unas sentencias basándose en el resultado de una expresión \[implementación obligatoria para algunos grupos\]. La sintaxis de la sentencia de selección múltiple es (el cuerpo de una etiqueta `case` o `default` puede omitirse):

```txt
switch (expresión)
{
  case valor1: cuerpo1
  case valor2: cuerpo2
  /* . . . */
  default: cuerpon
}
```txt

Su funcionamiento es como sigue: Se evalúa la `expresión` (que debe ser de tipo entero), se busca el valor que coincida con el resultado (los valores tienen que ser constantes enteras) y se ejecuta su cuerpo asociado (cada cuerpo puede estar formado por sentencias o declaraciones de variables). Una vez ejecutado, se continúa la ejecución de todos los cuerpos asociados a todos los valores que se encuentren a continuación hasta el final del `switch`. Si no se encuentra el valor, se ejecutan las sentencias asociadas a `default`, si el programador la ha incluido \[el `default` es opcional para la implementación\]:

```txt
switch (dia)
{
   case 1: print 'lunes';
   case 2: print 'martes';
   case 3: print 'miércoles';
   case 4: print 'jueves';
   case 5: 
           print 'viernes';
   default: print'fiesta';
} /* si dia == 4, se imprimirá: jueves viernes fiesta */
```txt

### Sentencia `break`

Esta sentencia aborta la ejecución de un `switch` \[implementación obligatoria para los grupos que tengan `switch`\]:

```txt
switch (dia)
{
   case 0: break;
   case 1: print "lunes"; break;
   case 2: print "martes";
   case 3: print "miércoles";
           break;
   case 4: print "jueves"; break;
   case 5: print "viernes"; break;
   case 6: if (error) break; print("sábado");
   default: if (dia < 8) print  "fiesta";
} /* si dia == 4, se imprimirá: jueves */
```txt

## Funciones

Es necesario definir cada función antes de poder utilizarla. La definición de una función se realiza indicando la palabra `function`, el nombre, el tipo de retorno (si la función devuelve algo) y, entre paréntesis, los parámetros (si existen) con sus tipos. Tras esta cabecera va un bloque (delimitado por llaves) con el cuerpo de la función (que puede tener cualquier cantidad de sentencias o declaraciones de variables):

```txt
function nombre [Tipo] (lista de argumentos)
{
   sentencias | declaración de variables
}
```txt

La lista de argumentos (que puede estar vacía y, en este caso, se ponen los paréntesis vacíos) consta del tipo y del nombre de cada parámetro formal. Si hay más de un argumento, éstos se separan por comas. Los parámetros se pasan siempre por valor.

Las funciones pueden recibir como parámetros cualquiera de los tipos básicos del lenguaje (entero, lógico o cadena).

Las funciones pueden devolver un valor de uno de los tipos básicos del lenguaje (entero, lógico o cadena). El tipo de retorno de la función se determina según el tipo que aparezca en su declaración. Si se omite el tipo en la declaración, se entiende que la función no devolverá ningún valor. En caso de que las instrucciones `return` de una función tengan expresiones de un tipo distinto al declarado, será un error.

JavaScript-PdL admite **recursividad**. Todos los grupos de prácticas han de considerarla en su implementación. Cualquier función puede ser _recursiva_, es decir, puede llamarse a sí misma.

El lenguaje JavaScript-PdL no permite la definición de funciones anidadas. Esto implica que dentro de una función no se puede declarar ni definir otra función.

Dentro de una función se tiene acceso a las variables locales, a sus argumentos y a las variables globales. Si en una función se declara una variable local o un argumento con el mismo nombre que un identificador global, éste último no es accesible desde dicha función.

```txt
let x int;  // global
function factorial int (int x) 
   /* se define la función recursiva con un parámetro, 
      que oculta a la variable global de igual nombre */
{
  if (x > 1) 
    return x * factorial (x - 1);
  return 1;
}// la función devuelve un entero
function Suma boolean (int aux, int fin, boolean b)
  /* se define la función Suma que recibe 
     dos enteros y un lógico */
  /* usa la variable global x */ 
{
    for (x= 1; (x < fin) && b; x= x + 2)
    {
      aux += factorial (aux-1);
    }
    return aux > 10000;
}// la función devuelve un lógico
function Imprime (int a)
{
    print (a);
    return;// esta instrucción se podría omitir
}// la función no devuelve nada
input x;
Imprime (factorial (Suma (x, 3, true)));  // se llama a las tres funciones
```txt

## Más Información sobre el Lenguaje

Hay que tener en cuenta que para este trabajo no es necesario tener un conocimiento total del lenguaje JavaScript. Tan solo hay que incorporar el funcionamiento del pequeño subconjunto del lenguaje JavaScript-PdL necesario para implementar las partes comunes a todos los grupos que se han explicado aquí, así como las partes obligatorias de cada grupo. Por tanto no se deben incluir todas las características, variantes y elementos que tiene el lenguaje completo JavaScript y, en caso de incosistencia, deberá elegirse el comportamiento aquí descrito.

Aquí se tiene un [ejemplo de un fichero](http://dlsiisv.fi.upm.es/procesadores/Ejem.javascript) que incorpora la mayoría de las funcionalidades descritas en esta página, a modo de ejemplo adicional para facilitar su compresión.

En cualquier caso, se puede encontrar más información sobre JavaScript en la página de [referencias](http://dlsiisv.fi.upm.es/procesadores/BibliografiaJavaScript.html "Bibliografía de JavaScript"), en los libros que hay en la [Biblioteca](http://www.fi.upm.es/index.php?pagina=24 "Biblioteca") de la Escuela (se puede usar el [buscador de la UPM](http://marte.biblioteca.upm.es/cgi-bin/webcat/ "Buscador en la Biblioteca")), así como en cualquier libro, manual o documentación del lenguaje (buscando en librerías como [Amazon](https://www.amazon.es/gp/search?ie=UTF8&tag=fi03b-21&linkCode=ur2&linkId=f92cb1c72586265cf781dc81fd3d3e00&camp=3638&creative=24630&index=books&keywords=JavaScript)). También se pueden encontrar otras fuentes de información realizando [búsquedas en la Web](https://www.google.es/search?q=javascript) o en los siguientes enlaces:

- [JavaScript FAQ](http://www.javascripter.net/faq/startpag.htm) \[HTML\]
- [The Modern JavaScript Tutorial](https://javascript.info/) \[HTML\]
