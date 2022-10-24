win_flex.exe ast.lex
win_bison.exe -d ast.y
g++ -o myprog.exe ast.tab.c lex.yy.c gen.cpp symtab.cpp ast.cpp
myprog.exe examples/repeat.txt
myprog.exe examples/nand.txt
myprog.exe examples/nestedWhile.txt
myprog.exe examples/switch.txt
PAUSE