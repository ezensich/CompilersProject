#!/bin/bash
# -*- ENCODING: UTF-8 -*-

echo "Running Compiler.."

java -cp lib/java-cup-11b.jar:lib/jflex-1.6.1.jar:bin/compilers/ SemanticCheckTest $@
 
 
exit
