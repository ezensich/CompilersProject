#! /bin/bash
#

echo "-----------------------------------------------------------------------"
echo "COMPILING JFLEX.."

java -jar lib/jflex-1.6.1.jar -d src/compilers src/compilers/Lexer.jflex

echo "-----------------------------------------------------------------------"
echo "COMPILING CUP.."

java -jar lib/java-cup-11b.jar -destdir src/compilers src/compilers/Parser.cup 

echo "-----------------------------------------------------------------------"
echo "COMPILING PROJECT.."
javac -d bin -cp src:lib/java-cup-11b.jar:lib/jflex-1.6.1.jar src/compilers/SemanticCheckTest.java

if [ $? = 0 ]; then
	echo "the compilation finished successfully!"
else
	echo "compilation failed!"
fi

echo "-----------------------------------------------------------------------"

exit
