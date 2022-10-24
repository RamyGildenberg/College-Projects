%code {


#include <stdio.h>
#include <string.h> 

  /* yylex () and yyerror() need to be declared here */
extern int yylex (void);
void yyerror (const char *s);
int sum=0;
int amountOfSports=0;



}
%code requires {
    struct sport {
          char name[40];  
        
    };
}




%union {
  struct sport my_sport;
  int year_num;
  char sport_name [30];
  
}

%token TITLE

%token YEARS
%token <sport_name> NAME
%token COMMA
%token THROUGH
%token THROUGH_H
%token SINCE
%token ALL
%token <year_num> YEAR_NUM
%token SPORT

%type <my_sport> sports
%type <sport_name> sport
%type <year_num> numOfOlympics


%error-verbose

%%
start: TITLE sports
{
             
};
sports: sports sport  
{
	strcpy($$.name,$2);
	
}|{};

//sports: %empty;
sport:  SPORT NAME YEARS numOfOlympics
{
	strcpy($$,$2);
	amountOfSports++;
	sum+=$4;
	

	//printf ("the sport : %s appearing in the olympics :%d times\n",$2,$4);
	if($4 >= 7)
	{
		printf("%s\n",$2);
		
	}

		
	
};


numOfOlympics:SINCE YEAR_NUM{$$=(int)((2021-$2)/4+1);} | 
	      ALL{$$ = 31;}
               |
	      YEAR_NUM{$$=1;}
	       |
	      YEAR_NUM THROUGH YEAR_NUM{$$=(int)(($3-$1)/4)+1;}
	       |
	      YEAR_NUM THROUGH_H YEAR_NUM{$$=(int)(($3-$1)/4)+1;}
               |
	      numOfOlympics COMMA numOfOlympics{$$=(int)(($1+$3));}
	      
;

      
            
%%


int main (int argc, char **argv)
{
  extern FILE *yyin;
  
  if (argc != 2) {
     fprintf (stderr, "Usage: %s <input-file-name>\n", argv[0]);
     
	 return 1;
  }
  yyin = fopen (argv [1], "r");
  if (yyin == NULL) {
       fprintf (stderr, "failed to open %s\n", argv[1]);
	   return 2;

  }
 
  printf("‫‪\nsports‬‬ ‫‪which‬‬ ‫‪appeared‬‬ ‫‪in‬‬ ‫‪at‬‬ ‫‪least‬‬ ‫‪7‬‬ ‫‪olympic‬‬ ‫‪games:‬‬\n");
  
  yyparse ();
  printf("‫‪\naverage‬‬ ‫‪number‬‬ ‫‪of‬‬ ‫‪games‬‬ ‫‪per‬‬ ‫‪sport:‬‬%.2f\n\n",(double)sum/(double)amountOfSports);
  
  fclose (yyin);
  return 0;
}
void yyerror (const char *s)
{
  
  fprintf (stderr, "line %s:\n", s);
}




