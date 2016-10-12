package compilers.lexical_syntactic_analysis;

import java_cup.runtime.*;

%%
%class lexer
%public
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
    
    /* comments */
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
    float_literal = {digit}+"."{digit}+
    id = {alpha} | {alpha} {alpha_num}+
    alpha_num = {alpha} | {digit} | _
    
   
    
%%

/* keywords */ 

<YYINITIAL> {
	
	"bool"							{ return symbol(sym.BOOL, yytext());}
    "break"							{ return symbol(sym.BREAK, yytext());}
    "class"							{ return symbol(sym.CLASS, yytext());}
    "continue"						{ return symbol(sym.CONTINUE, yytext());}
    "else"							{ return symbol(sym.ELSE, yytext());}
    "false"							{ return symbol(sym.FALSE, yytext());}
    "float"							{ return symbol(sym.FLOAT, yytext());}
    "for"							{ return symbol(sym.FOR, yytext());}
    "if"							{ return symbol(sym.IF, yytext());}
    "integer"						{ return symbol(sym.INTEGER, yytext());}
    "return"						{ return symbol(sym.RETURN, yytext());}
    "true"							{ return symbol(sym.TRUE, yytext());} 
    "void"							{ return symbol(sym.VOID, yytext());}
    "while"							{ return symbol(sym.WHILE, yytext());}
    "extern"						{ return symbol(sym.EXTERN, yytext());}
}

/* operations */

<YYINITIAL> {    
    /* arithmetical operations */
    
	"-"								{ return symbol(sym.MINUS, yytext());}
	"+"								{ return symbol(sym.PLUS, yytext());}
    "*"								{ return symbol(sym.PRODUCT, yytext());}
    "/"								{ return symbol(sym.DIVIDE, yytext());}
    "%"								{ return symbol(sym.MOD, yytext());}
}   

<YYINITIAL> {
    /* relation operations */
    "<"								{ return symbol(sym.LESS, yytext());}
    ">"								{ return symbol(sym.HIGH, yytext());}
    "<="							{ return symbol(sym.LESS_EQ, yytext());}
    ">="							{ return symbol(sym.HIGH_EQ, yytext());}
}

<YYINITIAL> {    
    /* equal operations */
    "=="							{ return symbol(sym.EQUAL, yytext());}
    "!="							{ return symbol(sym.DISTINCT, yytext());}
}

<YYINITIAL> {    
    /* conditional operations */ 
    "&&"							{ return symbol(sym.AND, yytext());}
	"||"							{ return symbol(sym.OR, yytext());} 
}

<YYINITIAL> {    
    /* assing operations */
    "="								{ return symbol(sym.ASSIGN, yytext());}
	"+="							{ return symbol(sym.INC, yytext());}
	"-="							{ return symbol(sym.DEC, yytext());}  
}	  

/* types */
	
 
/* literals */
<YYINITIAL> { 	
	
	{int_literal}					{ return symbol(sym.INTEGER_LITERAL, Integer.parseInt(yytext()));}
	{float_literal}					{ return symbol(sym.FLOAT_LITERAL, Float.parseFloat(yytext()));}
}

<YYINITIAL> { 	

	{id} { return symbol(sym.ID, yytext());}
	"." { return symbol(sym.POINT, yytext());}
	"," { return symbol(sym.COMA, yytext());}
	";" { return symbol(sym.SEMICOLON, yytext());}
	"!" { return symbol(sym.NOT, yytext());}
	"(" { return symbol(sym.LPAR, yytext());}
	")" { return symbol(sym.RPAR, yytext());}
	"[" { return symbol(sym.LBRACKET, yytext());}
	"]" { return symbol(sym.RBRACKET, yytext());}
	"{" { return symbol(sym.LKEY, yytext());}
	"}" { return symbol(sym.RKEY, yytext());}
}

	/* comments */
	{Comment} { /* ignore */ }

	/* whitespace */
	{WhiteSpace} { /* ignore */ }



	/* error fallback */
	[^] { throw new Error("Illegal character <"+yytext()+">"); }


