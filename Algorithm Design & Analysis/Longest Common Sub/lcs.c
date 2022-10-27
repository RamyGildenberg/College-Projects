#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "lcs.h"

char *lcs_init_string()
{
    char *string, temp[256];
    printf("please enter a string:\n");
    scanf("%s", temp);
    string = (char *)malloc(sizeof(char) * (strlen(temp) + 1));
    if (!string)
    {
        perror("malloc");
        exit(-1);
    }
    strcpy(string, temp);
    return string;
}

int **lcs_init(int m, int n)
{
    int **c = (int **)malloc(sizeof(int *) * m + 1), i, j;
    if (!c)
    {
        perror("malloc");
        exit(-1);
    }
    for (i = 0; i <= m; i++)
    {
        c[i] = (int *)calloc(n + 1, sizeof(int));
        if (!c[i])
        {
            perror("malloc");
            exit(-1);
        }
        c[i][0] = 0;
    }
    for (i = 0; i <= n; i++)
    {
        c[0][i] = 0;
    }
    return c;
}

void lcs_length(int **c, char *x, int m, char *y, int n)
{
    int i, j;
    for (i = 1; i <= m; i++)
    {
        for (j = 1; j <= n; j++)
        {
            if (x[i - 1] == y[j - 1])
            {
                c[i][j] = c[i - 1][j - 1] + 1;
            }
            else
            {
                c[i][j] = (c[i - 1][j] > c[i][j - 1] ? c[i - 1][j] : c[i][j - 1]);
            }
        }
    }
}

void lcs_build_string(int **c, char *x, int m, char *y, int n)
{
    if ((m == 0) || (n == 0))
    {
        return;
    }
    if (c[m - 1][n] == c[m][n])
    {
        lcs_build_string(c, x, m - 1, y, n);
    }
    else if (c[m][n - 1] == c[m][n])
    {
        lcs_build_string(c, x, m, y, n - 1);
    }
    else
    {
        lcs_build_string(c, x, m - 1, y, n - 1);
        printf("%c", x[m - 1]);
    }
}

void lcs_print_mat(int **c, char *x, int m, char *y, int n)
{
    int i, j;
    printf("\nC Matrix:\n%c ", ' ');
    for (j = 0; j <= n; j++)
    {
        printf("%5c ", (j == 0 ? ' ' : y[j - 1]));
    }
    printf("\n");
    for (i = 0; i <= m; i++)
    {
        printf("%c ", (i == 0 ? ' ' : x[i - 1]));
        for (j = 0; j <= n; j++)
        {
            printf("%5d ", c[i][j]);
        }
        printf("\n");
    }
}

int main()
{
    char *x, *y;
    x = lcs_init_string();
    y = lcs_init_string();
    int **c = lcs_init(strlen(x), strlen(y));
    lcs_length(c, x, strlen(x), y, strlen(y));
    printf("\nX: %s - Y: %s\nLongest Common Subsequence: ", x, y);
    lcs_build_string(c, x, strlen(x), y, strlen(y));
    lcs_print_mat(c, x, strlen(x), y, strlen(y));
    system("pause");
    return EXIT_SUCCESS;
}