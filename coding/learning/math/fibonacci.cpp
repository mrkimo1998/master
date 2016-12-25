#include <iostream>
#include <cmath>
#include <limits>

using namespace std;

long double fib(long n){
  if(n == 0) return 0;
  if(n == 1) return 1;
  else {
    return ((pow(1+sqrt(5),n))-(pow(1-sqrt(5),n)))/(pow(2,n)*sqrt(5));
  }
}

int main() {
  long i = 0;
  while (fib(i) < numeric_limits<long double>::max()) {
    cout << "Fib(" << i << ") = " << fib(i) << endl;
    i++;
  }
}
