#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>

int main(int args, char *argv[])
{
   int f1 = 0, f2 = 1, fn;
   int i, value;
   
   pid_t pid;
   value = atoi(argv[1]);
   
   if (value < 0)
      printf("Error : Number is negative.\n");
   else
   {
      pid = fork();
      if (pid == 0)
      {
         printf("Child process is running\n");
         printf("%d %d ",f1,f2);
         for (i=1; i<value; i++)
         {
            fn = f1 + f2;
            printf("%d ", fn);
            f1 = f2;
            f2 = fn;
         }
         printf("Child process over\n"); 
      }
      else 
      {
         printf("Parent is in wait state\n");
         wait(NULL);
         printf("Parent process over\n");
      }
   }
   return 0;
}