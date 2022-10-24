#ifndef __AST_H
#define __AST_H 1

#include <string.h>

#include "gen.h"

/*
enum myType { _INT, _FLOAT, UNKNOWN };
enum op { PLUS = 0, MINUS, MUL, DIV, LT, GT, LE, GE, EQ, NE };
*/

/*  FALL_THROUGH may be used as an argument to BoolExp::genBoolExp().
    Labels  are represented as positive integers so FALL_THROUGH has negative value */
const int FALL_THROUGH = -1;

// all nodes  in the AST (Abstract Syntax Tree) are of types derived from ASTnode 
class ASTnode {
   
};

// expressions 

// this is an abstract class
class Exp : public  ASTnode {
public:
    
    
    // return value  is the  variable in which the result of the expression will be stored.
    // In case the expression is a numerical literal (for example 2 or 3.14) the literal 
    // is returned.  Both variables and literals are represented here as Objects.
        
	
    virtual Object genExp() = 0; // every subclass should override this (or be abstract too)
    
    Exp() { /*_result = -1;*/ _type = UNKNOWN; }	
    // Exp (int result, myType _type) : ASTnode () { this->_result = result; this->_type = _type; }
#if 0
    //  _result field no longer used.  Instead, return value of genExp() is used  
    int _result;/* example: _result is 17 means result of expression will be in  _t17.
                   _result is -1 means: not known yet (_result is set to a new temporary when
				    code for the expression is generated) */
#endif
	myType _type;
    
};

class BinaryOp : public Exp {
public:
    BinaryOp (enum op op, Exp *left, Exp *right, int line);

	Object genExp(); // override

    enum op _op;
	Exp *_left; // left operand
	Exp *_right; // right operand
	int _line;  // source line number of operator
};

class NumNode : public Exp {
public:
	Object genExp(); // override
	
    NumNode (int ival) { _u.ival = ival; _type = _INT;}
    NumNode (double fval) { _u.fval = fval; _type = _FLOAT;}
	
	union {
	    int ival;
		double fval;
	} _u;
};

class IdNode : public Exp {
public: 
     Object genExp(); // override

     IdNode (char *name, int line);
			          
     char _name [100];
	 int _line;  // source line number
};

//  boolean expressions
// this is an abstract class

class BoolExp : public  ASTnode {
public:  //  some members  should be private ...
    /*  generate code for boolean expression. The code  should jump to
	    truelabel or to falselabel depending on the value of the expression.
        truelabel may  be  FALL_THROUGH meaning  code should fall through to 
		next instruction (rather than jump to a label)  when the expression is 
		true. falselabel  may also be  FALL_TROUGH */		
    virtual void genBoolExp (int truelabel, int falselabel) = 0; // every subclass should
                                            // override this (or be abstract too)
};
 
// nodes for  simple boolean expressions having the form 
// expression RELOP expression  
// for example  a < b
// another example:   (a + 3) < (z * 5 + y).    
class SimpleBoolExp : public BoolExp {
public:
    SimpleBoolExp (enum op op, Exp *left, Exp *right)
  	     { this->_op = op; this->_left = left; this->_right = right;}

    void genBoolExp (int truelabel, int falselabel); // override
	
    enum op _op;
	Exp *_left; // left operand
	Exp *_right; // right operand
};

class Or : public BoolExp {
public:
     Or (BoolExp *left, BoolExp *right) { _left = left; _right = right; }
	 void genBoolExp (int truelabel, int falselabel); // override
	 
	 BoolExp *_left; // left operand
	 BoolExp *_right; // right operand
};

class And : public BoolExp {
public:
     And (BoolExp *left, BoolExp *right) { _left = left; _right = right; }
	 void genBoolExp (int truelabel, int falselabel); // override
	 
	 BoolExp *_left; // left operand
	 BoolExp *_right; // right operand
};


class Not : public BoolExp {
public:
     Not (BoolExp *operand) { _operand = operand;}
	 void genBoolExp (int truelabel, int falselabel); // override
	 
	 BoolExp *_operand; 
};

	 // statements

// this is an abstract class
class Stmt: public ASTnode {
public:
   Stmt () {_next = NULL; } 
   
   virtual void genStmt () = 0; // every subclass should override this (or be abstract too)

   Stmt *_next;  // used to link together statements in the same block
};

class ReadStmt: public Stmt {
public:
      ReadStmt (IdNode *id, int line) : Stmt() { _id = id; _line = line;};

      void genStmt (); // override	  

      IdNode *_id;  // note: IdNode is subclass of Exp but this IdNode is not really an expression
	  int _line; // line in source code 
};

class AssignStmt : public Stmt {
public:
      AssignStmt (IdNode *lhs, Exp *rhs, int line);

      void genStmt (); // override	  

      IdNode *_lhs; // left hand side
	  Exp *_rhs; // right hand side
	  int _line; // line in source code 
};
   
class IfStmt : public Stmt {
public:
     IfStmt (BoolExp *condition, Stmt *thenStmt, Stmt *elseStmt)
	          : Stmt ()
	          { this->_condition = condition; this->_thenStmt = thenStmt; this->_elseStmt = elseStmt; } 
	 void genStmt (); // override
	 
     BoolExp *_condition;
	 Stmt *_thenStmt;
	 Stmt *_elseStmt;
};

class WhileStmt: public Stmt {
public: 
     WhileStmt (BoolExp *condition, Stmt *body)
	          : Stmt ()
	          { _condition = condition; _body = body; }

	 void genStmt (); // override			  

     BoolExp *_condition;
	 Stmt *_body;
};

//  a block contains a list of statements. For now -- no declarations in a block
class Block: public Stmt {
public:
	 Block (Stmt *stmtlist) { _stmtlist = stmtlist; }
	 void genStmt (); // override
	 
     Stmt* _stmtlist; // pointer to the first statement. each statement points to the next stmt
};

class BreakStmt : public Stmt {
public:
     BreakStmt (int line) { _line = line; }
	 
	 void genStmt(); // override
	 
     int _line;  // source line
};


class ContinueStmt : public Stmt {
public:
     ContinueStmt (int line) { _line = line; }
	 
	 void genStmt(); // override
	 
     int _line;  // source line
};

class  Case : public ASTnode {
public:
      Case (int number, Stmt *stmt, bool hasBreak) {
          _number = number; 
          _stmt = stmt;
		  _hasBreak = hasBreak;
          _next = NULL;
          _label = -1;  // will be set when code is generated
      }

      int _number;
	  Stmt *_stmt;
	  bool _hasBreak; 
	  Case *_next;
       int _label; 
};

class SwitchStmt : public Stmt {
public:
      SwitchStmt (Exp *exp, Case *caselist, Stmt *default_stmt, int line);
	  		   
	  void genStmt (); // override
	  
	  Exp *_exp;
	  Case *_caselist;
	  Stmt *_default_stmt;
	  int _line;  // source line of switch token
};

class RepeatStmt : public Stmt {
public:
      RepeatStmt (Exp *exp, Stmt *body): Stmt ()
      { _exp = exp; _body = body; }
	  		   
	  void genStmt (); // override
	  
	 Exp *_exp; 
     Stmt *_body;
	 
};

class Nand : public BoolExp {
public:
     Nand (BoolExp *left, BoolExp *right) 
	 { 
	 _left = left; 
	 _right = right; 
	 }
	 void genBoolExp (int truelabel, int falselabel); // override
	 
	 BoolExp *_left; // left operand
	 BoolExp *_right; // right operand
};




#endif  // not defined __AST_H