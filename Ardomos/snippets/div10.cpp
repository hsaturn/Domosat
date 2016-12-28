#include <iostream>
#include <stdlib.h>

using namespace std;

long div10(long l)
{
	cout << '.';
	if (l<10) return 0;

	return (l>>3)-div10(l>>2);
}

long div10_nr(long l)
{
	l>>=3;
	long d(l);
	bool bAdd=false;
	while(l>3)
	{
		l>>=2;
		if (bAdd)
		{
			d+=l;
		}
		else
		{
			d-=l;
		}
		bAdd = !bAdd;
	}
	return d;
}
int main(int argc, const char* argv[])
{
	if (argc>1)
	{
		long l(atol(argv[1]));

		cout << l << '/' << 10 << '=';
		cout << div10(l) << endl;

		cout << " Autre méthode : resultat=" << div10_nr(l) << endl;
	}
	return 0;
}
