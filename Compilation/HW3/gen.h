#ifndef __GEN_H
#define __GEN_H 1

#include <stdio.h>
#include <string.h>

void emit (const char *format, ...); 
void emitlabel (int label);
extern int errors;

void errorMsg (const char *format, ...);

enum myType { _INT, _FLOAT, UNKNOWN };
enum op { PLUS = 0, MINUS, MUL, DIV, XOR_OP, LT, GT, LE, GE, EQ, NE };

/* convert operator  to  string  suitable for the given type
  e.g  opName (PLUS, _INT)  returns "+"
       opName (PLUS, _FLOAT) returns  "plus"
*/
const char *opName (enum op, myType t);

// An Object is either a variable (e.g. bar or _t5) or a numeric literal (e.g. 17 or 3.14)

enum objectType {INT_NUMBER, FLOAT_NUMBER, VARIABLE, ERROR };

class Object {
public:
    Object(char *variable) {strcpy(this->_string, variable);
                            this->_type = VARIABLE; }

    Object(int ival) {sprintf(this->_string, "%d", ival);
                      this->_type = INT_NUMBER; }

    Object(double fval) {sprintf(this->_string, "%.2f", fval); 
                         this->_type = FLOAT_NUMBER; }
                         
    Object() { strcpy(this->_string, "error found"); this->_type = ERROR; } 
                            
    enum objectType _type;
    char _string[100]; // for example "bar"  or "17" or "3.14"
}; 

#endif // not defined __GEN_H
