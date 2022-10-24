%{

#include "mylex.tab.h"
// atoi - convert string to int
extern int atoi (const char *);

%}

%option noyywrap
%option yylineno

%x COMMENT

%%

"Olympic Games" { return TITLE; }
"<sport>" { return SPORT; }
"<years>" { return YEARS; }
"since" { return SINCE; }
"all" { return ALL; }
"through" { return THROUGH; }
"-" { return THROUGH_H; }
"," { return COMMA; }
\[[A-Za-z ]*\] { strcpy(yylval.sport_name, yytext); return NAME; }
[0-9]* 	{ yylval.year_num = atoi(yytext); return YEAR_NUM; }
[\n\r\t ]+  { /* skip white space */ }
. { fprintf (stderr, "unrecognized token in line %d: %c(%x)\n", yylineno, yytext[0], yytext[0]); } /*  */

%%




