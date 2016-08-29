import java_cup.runtime.*;

%%
%class lexer
%unicode
%cup
%line
%column

%{
      StringBuffer string = new StringBuffer();

      private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
      }
      private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
      }
%}
    LineTerminator = \r|\n|\r\n
    InputCharacter = [^\r\n]
    WhiteSpace     = {LineTerminator} | [ \t\f]
    
    /* comments (REVISAR LAS EXPRESIONES DE ACUERDO AL ENUNCIADO)*/
    Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}
    TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
    // Comment can be the last line of the file, without line terminator.
    EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?
    DocumentationComment = "/**" {CommentContent} "*"+ "/"
    CommentContent       = ( [^*] | \*+ [^/*] )*
    
    /* Project expression  */
    
    alpha = [a-z] | [A-Z]
    digit = [0-9]
    int_literal = {digit}+
    bool_literal = true | false
    float_literal = {digit}+ "."{digit}+
    type = integer | float | bool | id | void
    
    
    
   
    
%%

/* keywords */ 

<YYINITIAL> {
	
	"bool"							{return symbol(sym.BOOL, new String(yytext()));}
    "break"							{return symbol(sym.BREAK, new String(yytext()));}
    "class"							{return symbol(sym.CLASS, new String(yytext()));}
    "continue"						{return symbol(sym.CONTINUE, new String(yytext()));}
    "else"							{return symbol(sym.ELSE, new String(yytext()));}
    "false"							{return symbol(sym.FALSE, new String(yytext()));}
    "float"							{return symbol(sym.FLOAT, new String(yytext()));}
    "for"							{return symbol(sym.FOR, new String(yytext()));}
    "if"							{return symbol(sym.IF, new String(yytext()));}
    "integer"						{return symbol(sym.INTEGER, new String(yytext()));}
    "return"						{return symbol(sym.RETURN, new String(yytext()));}
    "true"							{return symbol(sym.TRUE, new String(yytext()));} 
    "void"							{return symbol(sym.VOID, new String(yytext()));}
    "while"							{return symbol(sym.WHILE, new String(yytext()));}
    "extern"						{return symbol(sym.EXTERN, new String(yytext()));}
}

/* operations */

<YYINITIAL> {    
    /* arithmetical operations */
    
	"-"								{return symbol(sym.MINUS, new String(yytext()));}
	"+"								{return symbol(sym.PLUS, new String(yytext()));}
    "*"								{return symbol(sym.PRODUCT, new String(yytext()));}
    "/"								{return symbol(sym.DIVIDE, new String(yytext()));}
    "%"								{return symbol(sym.MOD, new String(yytext()));}
}   

<YYINITIAL> {
    /* relation operations */
    "<"								{return symbol(sym.LESS, new String(yytext()));}
    ">"								{return symbol(sym.HIGH, new String(yytext()));}
    "<="							{return symbol(sym.LESS_EQ, new String(yytext()));}
    ">="							{return symbol(sym.HIGH_EQ, new String(yytext()));}
}

<YYINITIAL> {    
    /* equal operations */
    "=="							{return symbol(sym.EQUAL, new String(yytext()));}
    "!="							{return symbol(sym.DISTINCT, new String(yytext()));}
}

<YYINITIAL> {    
    /* conditional operations */ 
    "&&"							{return symbol(sym.AND, new String(yytext()));}
	"||"							{return symbol(sym.OR, new String(yytext()));} 
}

<YYINITIAL> {    
    /* conditional operations */
    "="								{return symbol(sym.ASSIGN, new String(yytext()));}
	"=+"							{return symbol(sym.INC, new String(yytext()));}
	"=-"							{return symbol(sym.DEC, new String(yytext()));}  
}	  

/* types */
	
 
/* literals */
<YYINITIAL> { 	
	
	{int_literal}					{return symbol(sym.INTEGER_LITERAL, new Integer(yytext()));}
	{float_literal}					{return symbol(sym.FLOAT_LITERAL, new Float(yytext()));}
	{bool_literal}					{return symbol(sym.BOOLEAN_LITERAL, new Boolean(yytext()));}
}

	/* comments */
	{Comment} { /* ignore */ }

	/* whitespace */
	{WhiteSpace} { /* ignore */ }



	/* error fallback */
	[^] { throw new Error("Illegal character <"+yytext()+">"); }


