// TODO list
// readCurrentPlanif pour mettre la bonne consigne 
// (voir mode auto).

#include <iostream>
#include <time.h>
#include <unistd.h>
#include <fstream>

using namespace std;

#define manual 0
#define hg 1
#define auto 2

const long undefined = -999;
long time_on = 0;
long temp_off_on = undefined;
long temp_manual = 170;
long temp_auto = 170;
long temp_auto_default = 170;
long time_on_recalc_c = 15;

#define MAXC 10
#define TEXT_MIN -100
#define TEXT_MAX 400

float C[MAXC];
float C_DEF = 0.01;	// delta T ( degrees / min )

float C_INIT = C_DEF;
short mode = auto;
int temp_hors_gel;
int text = 0;			// température extérieure

// Paramètres de simulation
int more=10;

struct planif 
{
	int temp;
	int time;	// Nbre minutes de l'évènement
};

int getCIndex(int temp)
{
	int i = (MAXC * (temp-TEXT_MIN))/(TEXT_MAX-TEXT_MIN);
	if (i<0) i=0;
	if (i>=MAXC) i=MAXC;
	return i;
}

// Return coeff C = f(T°)
float getC()
{
	return C[getCIndex(text)];
}

void setC(float c)
{
	int index = getCIndex(text);
	C[index] = c;
}

long ellapsed_seconds=0;

long get_ellapsed_seconds()
{
	return ellapsed_seconds;
	static bool first = true;
	static struct timespec start,stop;
	if (first)
	{
		clock_gettime(CLOCK_PROCESS_CPUTIME_ID, &start);
		first = false;
	}
	clock_gettime(CLOCK_PROCESS_CPUTIME_ID, &stop);
	double result = (stop.tv_sec - start.tv_sec) * 1e6 + (stop.tv_nsec - start.tv_nsec) / 1e3;    // in seconds
	return result;
}


bool readNextPlanif(struct planif& p)
{
	long elapsed = get_ellapsed_seconds();

	long time = 2*3600;
	p.temp = 0;

	if (elapsed < time)
	{
		p.temp = 190;
		p.time = (time - elapsed) / 60;
		return true;
	}

	time = 3*3600;
	if (elapsed < time)
	{
		p.temp = 180;
		p.time = (time -elapsed) / 60;
		return true;
	}
	return false;
}

int sign(float f)
{
	if (f==0)
		return 0;
	if (f>0)
		return 1;
	return -1;
}

long next_event=0;

float T=166;	// curT 15.6°
int R=0;	// relay 

int rd16(const char* dev, char m)
{
	if (m=='R')
		return R;
	if (m=='T')
		return T;
	cerr << "ERREUR RD16 " << m << endl;
	return 0;
}

void wr16(const char* dev, char m, int value)
{
	cout << "WR16 " << dev << '.' << m << ':' << value << endl;
	if (m=='R')
		R=value;
	else if (m=='T')
		T=value;
	else
		cerr << "ERREUR WR16 " << m << endl;
}

void loop()
{
	int tcur = rd16("SDB",'T');
	int state = rd16("SDB", 'R');
	int elapsed = get_ellapsed_seconds();

	// FAKE T increase
	/*
	float deltaT = C_INIT * elapsed / 15;
	if (R)
		T += deltaT;
	else
		T -= deltaT;
		*/
	{
		static int last_elapsed = elapsed;
		int el = elapsed - last_elapsed;

		if (R)
		{
			T += sign(getC())*C_INIT * el / 2;
		}

		float dext = (T-text)/40000;
		T -= dext * el;

		last_elapsed = elapsed;
	}

	if (next_event == 0)
		next_event = elapsed+60;
	else if (elapsed >= next_event)
	{
		next_event += 60;

		if (state != 0)
		{
			if (temp_off_on == undefined)
				temp_off_on  = tcur;
			time_on++;
			if (temp_off_on != undefined && time_on == time_on_recalc_c)
			{
				const float coeff = 0.9;
				float newc = (float)(tcur - temp_off_on)/10/time_on;
				if (sign(newc) == sign(getC()))
					setC(coeff*getC() + (1-coeff)*newc);

				// init next C recalc
				time_on = 0;
				temp_off_on = tcur;
			}
		}
		else
		{
			time_on = 0;
			temp_off_on = undefined;
		}
	}

	int consigne = undefined;

	switch(mode)
	{
		case hg:
			consigne = temp_hors_gel;
			break;

		case manual:
			consigne = temp_manual;
			break;

		case auto:
			// TODO consigne = readCurrentPlanif().temp
			consigne = temp_auto;
			struct planif p;
			if (readNextPlanif(p))
			{
				int deltaT = p.temp - tcur;
				if (sign(deltaT) == sign(getC()))
				{
					int mins_to_reach_consigne = 60*deltaT / 10 / getC();
					if (mins_to_reach_consigne > p.time*60)
					{
						temp_auto = p.temp;
					}
					else
						temp_auto = temp_auto_default;
				}
			}
			else
			{
				if (more>0)
					more--;
			}
			break;
	}

	if (consigne != undefined)
	{
		int deltaT = consigne - tcur;
		int new_state = undefined;

		if (sign(deltaT) == sign(getC()))
			new_state = 1;
		else
			new_state = 0;

		if (new_state != undefined && new_state != rd16("SDB", 'R'))
			wr16("SDB", 'R', new_state);
	}
}

int main(int argc, const char* argv[])
{
	for(int i=0; i<MAXC; i++)
		C[i] = C_DEF;

	ifstream cfile;
	cfile.open("c.val");
	int i=0;
	while(cfile.good() && i<MAXC)
	{
		float c;
		cfile >> c;
		C[i++] = c;
		cout << c << ' ';
	}
	cout << endl;


	if (argc==2)
	{
		text = atof(argv[1])*10;
		cout << "Text " << text << endl;
	}
	while(more)
	{
		cout << "time : " << get_ellapsed_seconds() << "\t" << (R ? "ON ":"--") << "\t T:" << T/10 << "\t C:" << getC() << " consigne_auto: " << temp_auto;
		struct planif x;
		if (!readNextPlanif(x))
			cout << " -- no planif";

	   	cout <<  endl;
		loop();
		//	sleep(1);
		ellapsed_seconds += 50;
	}

	{
		ofstream cfile;
		cfile.open("c.val");
		for(int i=0; i<MAXC; i++)
		{
			if (i == getCIndex(text))
				cout << '*';
			else
				cout << ' ';
			cout << C[i] << endl;
			cfile << C[i] << endl;
		}
		cfile.close();
		cout << "written" << endl;
	}
}
