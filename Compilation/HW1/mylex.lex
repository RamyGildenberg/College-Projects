%{

const char TITLE_STR[] = "Olympic Games";
const char SPORT_STR[] = "<sport>";
const char YEARS_STR[] = "<years>";
const char SINCE_STR[] = "since";
const char ALL_STR[] = "all";
const char THROUGH_STR[] = "through";

#define TITLE 300
#define SPORT 301
#define YEARS 302
#define NAME 303
#define YEAR_NUM 304
#define COMMA 305
#define THROUGH 306
#define THROUGH_H 307
#define SINCE 308
#define ALL 309

union {
  int year_num;
  char sport_name [30];
} yylval;

#include <string.h> 

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

int main (int argc, char **argv)
{
   int token;

   extern FILE *yyin;

   if (argc != 2) {
      fprintf(stderr, "Usage: mylex <input file name>\n", argv [0]);
      exit (1);
   }

   yyin = fopen (argv[1], "r");

   printf("TOKEN\t\t\tLEXEME\t\t\tSEMANTIC VALUE\n");
   printf("-------------------------------------------------------------------\n\n");

   

   while ((token = yylex ()) != 0)
   {
     switch (token) {
	case TITLE: printf("TITLE\t\t\t%s", TITLE_STR); break;
	case SPORT: printf("SPORT\t\t\t%s", SPORT_STR); break;
	case YEARS: printf("YEARS\t\t\t%s", YEARS_STR); break;
	case COMMA: printf("COMMA\t\t\t,"); break;
	case THROUGH: printf("THROUGH\t\t\t%s", THROUGH_STR); break;
	case THROUGH_H: printf("THROUGH\t\t\t-"); break;
	case SINCE: printf("SINCE\t\t\t%s", SINCE_STR); break;
	case ALL: printf("ALL\t\t\t%s", ALL_STR); break;
	case NAME: 
		printf("NAME\t\t\t%s", yylval.sport_name); 
		memmove(yylval.sport_name, yylval.sport_name+1, strlen(yylval.sport_name));
		yylval.sport_name[strlen(yylval.sport_name)-1] = '\0';
		printf("\t\t%s", yylval.sport_name); 
		break;
	case YEAR_NUM:
		printf("YEAR_NUM\t\t"); 
		
		if (yylval.year_num == 2020)
			printf("2021");
		else
			printf("%d", yylval.year_num);

		printf("\t\t\t%d", yylval.year_num);

	break;
     } 

	printf("\n");
   }

   fclose (yyin);
   exit (0);
}


