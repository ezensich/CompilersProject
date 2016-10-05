package compilers.int_code_gen;

public enum ICGOperation {
	ADD, //adicion
	AND, //and logico
	ASSIGN, //asignacion
	CALL, //llamada a funcion
	CMP, //compare
	DEF, //definicion
	DISTINCT, //distinto
	DIV, //division
	EQUAL, //igual
	GDEF, //definicion de variables globales
	HIGH, //mayor
	HIGH_EQ, //mayor-igual
	INC, //incrementar
	JE, //salto por igual (jump equal)
	JMP, //salto (jump)
	JNE, //salto por distinto (jump not equal)
	LABEL, //etiqueta
	LESS, //menor
	LESS_EQ, //menor-igual
	MIN, //signo menos (-)
	MOD, //resto
	MUL, //multiplicacion
	NOT, //negacion (!)
	OR, // or logico
	RETURN, //return
	SUB //resta
}
