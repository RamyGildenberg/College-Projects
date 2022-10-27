char *lcs_init_string();
int **lcs_init(int m, int n);
void lcs_length(int **c, char *x, int m, char *y, int n);
void lcs_build_string(int **c, char *x, int m, char *y, int n);
void lcs_print_mat(int **c, char *x, int m, char *y, int n);