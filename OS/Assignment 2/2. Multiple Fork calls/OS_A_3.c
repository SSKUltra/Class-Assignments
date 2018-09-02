#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>

int main(int args, char *argv[])
{
 
    pid_t pid1,pid2,pid3,pid4;
 
    pid1 = fork();
    pid2 = fork();
 
    if(pid1 == 0 && pid2 == 0)
    {
    printf("This is Child 3\n");
    int n, i;
    int factorial = 1;

    n = atoi(argv[1]);
    if (n < 0)
        printf("Factorial not possible \n");

    else
    {
        for(i=1; i<=n; ++i)
        {
            factorial *= i;             
        }
        printf("Factorial of %d = %d\n", n, factorial);
    }

    }

    else if(pid1 == 0 && pid2>0 ) {
    printf("This is Child 1\n");
    int i, fibo, f1 = 0, f2 = 1, fn;

    fibo = atoi(argv[2]);

    printf("Fibonacci Series: ");

    printf("%d %d ",f1,f2);
    for (i=1; i<fibo; i++)
    {
        fn = f1 + f2;
        printf("%d ", fn);
        f1 = f2;
        f2 = fn;
        }
    printf("\n");
    }

    else if(pid1 > 0 && pid2==0 ) {
    printf("This is Child 2\n");
    int num, j, flag;
    num = atoi(argv[3]);
    if (num <= 1)
    {
        printf("%d is not a prime numbers \n", num);
        exit(1);
    }
    flag = 0;
    for (j = 2; j <= num / 2; j++)
    {
        if ((num % j) == 0)
        {
            flag = 1;
            break;
        }
    }

    if (flag == 0)
        printf("%d is prime \n", num);

    else
        printf("%d is not prime \n", num);
    }
    
    else if(pid1 > 0 && pid2>0 ) {
    printf("This is parent process\n");
    int number;

    number = atoi(argv[4]);

    if(number % 2 == 0)
        printf("%d is even.\n", number);
    else
        printf("%d is odd.\n", number);

    return 0;
}
return 0; 
}