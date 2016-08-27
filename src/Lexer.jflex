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
    
    // EXPRESIONES DEL PROYECTO //
    
    
    digit = 0 | [1-9]
    int_literal = {digit} {digit}*
    bool_literal = true | false
    float_literal = {digit} {digit}* "." {digit} {digit}*
    
    
    
    //
    
%%

<YYINITIAL> {

 /* literals */
 {int_literal} { return symbol(sym.INTEGER_LITERAL, new Integer(yytext()));}
 {float_literal} { return symbol(sym.FLOAT_LITERAL, new Float(yytext()));}
 {bool_literal} { return symbol(sym.BOOLEAN_LITERAL, new Boolean(yytext()));}

 /* comments */
 {Comment} { /* ignore */ }

 /* whitespace */
 {WhiteSpace} { /* ignore */ }

}

/* error fallback */
 [^] { throw new Error("Illegal character <"+yytext()+">"); }


