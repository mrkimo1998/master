#include <iostream>
#include <cmath>
#include <limits>

using namespace std;

int fib(int n){
  if(n == 0) return 0;
  if(n == 1) return 1;
  else {
    return ((pow(1+sqrt(5),n))-(pow(1-sqrt(5),n)))/(pow(2,n)*sqrt(5));
  }
}

int main() {
  int i = 0;
	int result = 0;
  while (fib(i) < 4000000) {
		if (!(fib(i) % 2)) {
			result += fib(i);
    	cout << "Fib(" << i << ") = " << fib(i) << endl;
		}
		i++;
  }
	cout << "Ergebnis = " << result << endl;
}
