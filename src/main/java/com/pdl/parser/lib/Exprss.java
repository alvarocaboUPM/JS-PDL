package com.pdl.parser.lib;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import com.pdl.common.ErrorAt;

public class Exprss {
    /**
     * Método para evaluar las expresiones utilizando el algoritmo Shunting Yard
     * 
     * @param tokens una lista de tokens de la expresión a evaluar
     * @return el tipo de la expresión evaluada, o NULL si la expresión es inválida
     */
    public static String evaluate(List<String> tokens) {
        // Verificamos si la lista de tokens está vacía
        if (tokens.isEmpty()) {
            ErrorAt.ezError(221, "Token list is empty");
        }

        // Verificamos casos especiales
        if (tokens.contains("Void"))
            return "Void";
        if (tokens.size() == 1 && tokens.get(0).equals("TypeString"))
            return "TypeString";
        if (tokens.contains("TypeString") && tokens.size() == 3)
            return "TypeString";

        // Contamos los paréntesis para verificar que estén equilibrados
        int openParens = 0;
        int closeParens = 0;
        for (String token : tokens) {
            if (token.equals("ParOpen")) {
                openParens++;
            } else if (token.equals("ParClose")) {
                closeParens++;
            }
        }

        // Lanzamos una excepción si los paréntesis no están equilibrados
        if (openParens != closeParens) {
            ErrorAt.ezError(220, "Mismatched parentheses in expression");
        }

        // Procesamos las expresiones dentro de los paréntesis
        while (tokens.contains("ParOpen") && tokens.contains("ParClose")) {
            int start = tokens.lastIndexOf("ParOpen");
            int end = start;
            int count = 1;
            while (count > 0) {
                end++;
                if (tokens.get(end).equals("ParOpen")) {
                    count++;
                } else if (tokens.get(end).equals("ParClose")) {
                    count--;
                }
            }

            // Evaluamos la subexpresión y la reemplazamos en la lista original de tokens
            List<String> subExpr = tokens.subList(start + 1, end);
            String result = "";
            try {
                result = evaluate(subExpr);// Si se produce error
            } catch (Exception e) {
                // ALex.ezError(235, result);//Todo Throw detailed error
            }
            tokens.subList(start, end + 1).clear();
            tokens.add(start, result);// revisar ?? Añadir o eliminar?
        }

        // Creamos la pila de operadores y la cola de salida
        Stack<String> operators = new Stack<>();
        Queue<String> output = new LinkedList<>();

        // Procesamos los tokens, manejando los operadores y los operandos
        for (String token : tokens) {
            if (token.equals("TypeInt")) {
                output.add(token);
            } else if (token.equals("TypeBool")) {
                output.add(token);
            } else if (isOperator(token)) {
                while (!operators.empty() && isOperator(operators.peek())
                        && (precedence(token) <= precedence(operators.peek()))) {
                    output.add(operators.pop());
                }
                operators.push(token);
            }
        }

        // Agregamos los operadores restantes a la cola de salida
        while (!operators.empty()) {
            output.add(operators.pop());
        }

        // Creamos la pila de evaluación
        Stack<String> evalStack = new Stack<>();

        // Procesamos la cola de salida
        while (!output.isEmpty()) {
            String token = output.poll();
            if (token.equals("TypeInt") || token.equals("TypeBool")) {
                evalStack.push(token);
            } else {
                // Verificamos que hay suficientes operandos en la pila de evaluación
                String val1 = "", val2 = "";
                if (!evalStack.isEmpty()) {
                    val2 = evalStack.pop();
                }

                if (!evalStack.isEmpty()) {
                    val1 = evalStack.pop();
                }
                // Verificamos la compatibilidad de los operandos
                if (val1.equals("TypeInt") && val2.equals("TypeBool")
                        || val1.equals("TypeBool") && val2.equals("TypeInt")) {

                    ErrorAt.ezError(232, null);
                }

                // Evaluamos el operador
                else if (token.equals("GT")) {
                    evalStack.push(val1.equals("TypeInt") && val2.equals("TypeInt") ? "TypeBool" : "NULL");
                } else if (token.equals("AND")) {
                    evalStack.push(val1.equals("TypeBool") && val2.equals("TypeBool") ? "TypeBool" : "NULL");
                } else if (token.equals("MOD")) {
                    evalStack.push(val1.equals("TypeInt") && val2.equals("TypeInt") ? "TypeInt" : "NULL");
                } else {
                    ErrorAt.ezError(234, null);
                }
            }
        }

        // Verificamos que hay un resultado en la pila de evaluación
        if (evalStack.isEmpty()) {
            ErrorAt.ezError(235, null);

        }

        // Devolvemos el resultado
        return evalStack.pop();
    }

    // Método para obtener la precedencia de los operadores
    private static int precedence(String operator) {
        switch (operator) {
            case "ParOpen":
                return 4;
            case "MOD":
                return 3;
            case "GT":
                return 2;
            case "AND":
                return 1;
            default:
                return -1;
        }
    }

    // Método para verificar si un token es un operador
    private static boolean isOperator(String token) {
        return token.equals("GT") || token.equals("AND") || token.equals("MOD") || token.equals("ParClose");
    }
}

/**
 * Este código implementa el algoritmo Shunting Yard para evaluar una expresión
 * booleana.
 * El algoritmo utiliza una pila para los operadores y una cola para la salida.
 * Cuando encuentra un operador, verifica si hay operadores de mayor o igual
 * precedencia en la pila y los agrega a la cola de salida antes de agregar el
 * operador actual.
 * Cuando encuentra un operando, lo agrega directamente a la cola de salida.
 * Una vez que se han procesado todos los tokens, vacía la pila de operadores en
 * la cola de salida.
 *
 * Luego, el código evalúa la expresión utilizando una pila de evaluación.
 * Recorre la cola de salida, agregando operandos a la pila de evaluación y
 * aplicando operadores a los operandos en la pila.
 * Finalmente, el resultado de la expresión es el último elemento en la pila de
 * evaluación.
 */