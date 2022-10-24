%{
#include "bison.tab.h"
#include <string.h>
int line =1;
extern int atoi (const char*);	
%}


%option noyywrap

/*rules*/
%%
[\t ]+ /*if encounters at least one of those, it will skip*/
\n {line++;}
"** TENNIS INFO **" {return TITLE;}
"<name>" {return NAME;}
"<gender>" {return GENDER;}
"Woman" {yylval.gender =0; return PLAYER_GENDER;}
"Man" {yylval.gender =1; return PLAYER_GENDER;}
\"[A-Z][a-z]+(" "[A-Z][a-z]+)\" {strcpy(yylval.name,yytext);return PLAYER_NAME;}
[1-2][0-9]{3} {yylval.year =atoi(yytext); return NUM;}
"<Wimbledon>" {return WIMBLEDON;}
"<Australian Open>" {return AUSTRALIAN_OPEN;}
,+  {return ',';}
-+ {return '-';}
%%


