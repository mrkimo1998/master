#include <stdio.h>

unsigned long int fib(unsigned long int x){
  if(x <= 2){
    return 1;
  }
  else {
    return fib(x - 1) + fib(x - 2);
  }
}

main(int argc, char *argv[]){
  unsigned short i = 1;
  while(1) {
    printf("%d %ld \n", i, fib(i));
    i+=1;
  }
}
