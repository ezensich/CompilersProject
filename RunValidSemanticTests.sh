#!/bin/bash
# -*- ENCODING: UTF-8 -*-
echo "-----------------------------------------------------------------------"
echo "Running Valid Semantic Tests.."

java -jar jar/Compiler.jar test/semantic_tests/valid_tests/*.*
 
exit
