/** The lexer for scanning command tokens. */
package Parse;

import java_cup.runtime.*;

%%

%class Lexer
%type java_cup.runtime.Symbol
%line
%column
%cup

%{
	private Symbol symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn+1);
	}

	private Symbol symbol(int type, Object value) {
        LocValue lv = new LocValue(yyline+1, yycolumn+1, value);
		return new Symbol(type, yyline+1, yycolumn+1, lv);
	}
%}

id = [a-zA-Z][a-zA-Z0-9_]*
intnum = [:digit:]+
floatnum = [:digit:]+ \. [:digit:]+
whiteSpace = [ \n\t\r]

%%

/* Reserved */
"if"							{ return symbol(sym.IF); }
"else"							{ return symbol(sym.ELSE); }
"return"						{ return symbol(sym.RETURN, "return"); }
"while"							{ return symbol(sym.WHILE); }
"do"							{ return symbol(sym.DO); }
"for"							{ return symbol(sym.FOR); }
"switch"						{ return symbol(sym.SWITCH); }
"case"							{ return symbol(sym.CASE); }
"default"						{ return symbol(sym.DEFAULT); }
"break"							{ return symbol(sym.BREAK); }

/* Types */
"int"                   		{ return symbol(sym.INT); }
"float"                 		{ return symbol(sym.FLOAT); }

{id}							{ return symbol(sym.id, yytext()); }
{intnum}						{ return symbol(sym.intnum, new Integer(yytext())); }
{floatnum}						{ return symbol(sym.floatnum, new Float(yytext())); }
{whiteSpace}					{}

/* operators */

"*"							{ return symbol(sym.MULT); }
"/"							{ return symbol(sym.DIV); }
"+"							{ return symbol(sym.PLUS); }
"-"							{ return symbol(sym.MINUS); }
">"							{ return symbol(sym.GT); }
"<"							{ return symbol(sym.LT); }
">="						{ return symbol(sym.GTEQ); }
"<="						{ return symbol(sym.LTEQ); }
"="                         { return symbol(sym.EQ); }
"=="						{ return symbol(sym.EQEQ); }
"!="						{ return symbol(sym.NOTEQ); }


/* separators */

"("							{ return symbol(sym.LPAREN); }
")"							{ return symbol(sym.RPAREN); }
"{"							{ return symbol(sym.LBRACE); }
"}"							{ return symbol(sym.RBRACE); }
"["							{ return symbol(sym.LBRACK); }
"]"							{ return symbol(sym.RBRACK); }
";"							{ return symbol(sym.SEMICOLON); }
":"							{ return symbol(sym.COLON); }
","							{ return symbol(sym.COMMA); }
"."							{ return symbol(sym.DOT); }
