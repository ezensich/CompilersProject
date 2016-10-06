#!/bin/bash
# -*- ENCODING: UTF-8 -*-
echo "-----------------------------------------------------------------------"
echo "Running Invalid Semantic Tests.."

java -jar jar/Compiler.jar test/semantic_tests/invalid_tests/*.*
 
exit
