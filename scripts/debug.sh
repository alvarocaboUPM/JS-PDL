#!/bin/bash

# Compile Java source files
javac -cp "lib/*" src/lexer/ALex.java
javac -cp "lib/*" src/lexer/lib/SymbolAt.java
javac -cp "lib/*" src/lexer/lib/Token.java

javac -cp "lib/*" src/lib/ErrorAt.java
javac -cp "lib/*" src/lib/Pretty.java
javac -cp "lib/*" src/lib/Tables.java
javac -cp "lib/*" src/lib/TS_interface.java
javac -cp "lib/*" src/lib/TS.java

javac -cp "lib/*" src/main/Compiler.java

javac -cp "lib/*" src/sintax/ASin.java
javac -cp "lib/*" src/sintax/expresion/ExpNode.java
javac -cp "lib/*" src/sintax/expresion/Expresion.java
javac -cp "lib/*" src/sintax/expresion/ExpTree.java
javac -cp "lib/*" src/sintax/ParseLib.java

# Create a temporary directory for compiled class files
mkdir -p bin

# Move compiled class files to the bin directory
mv src/lexer/*.class bin
mv src/lexer/lib/*.class bin
mv src/lib/*.class bin
mv src/main/*.class bin
mv src/sintax/*.class bin
mv src/sintax/expresion/*.class bin

# Create a JAR file
jar cvf JS_Compiler.jar -C bin .

# Run the JAR file
java -jar JS_Compiler.jar

# Clean up - remove the temporary bin directory and JAR file
rm -rf bin
rm JS_Compiler.jar
