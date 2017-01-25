#include <iostream>
#include <cmath>
#include <limits>

using namespace std;

long double g(double n){
  if(n >= 0 && n <= 1999) return 1;
  if(n >= 2000) return (g(n-2000) + g(n-1999));
	else return 0;
}

int main() {
  cout << "G(k=10^18) = " << g(pow(10, 18)) << endl;
}
