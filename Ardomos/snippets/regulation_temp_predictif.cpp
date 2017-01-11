#include <iostream>
#include <time.h>
#include <unistd.h>

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
long time_on_recalc_c = 20;
float C = 0.01;	// delta T ( degrees / min )
float C_INIT = C;
short mode = auto;
int temp_hors_gel;

struct planif 
{
	int temp;
	int time;	// Nbre minutes de l'évènement
};

long get_ellapsed_seconds()
{
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

	long time = 3600;

	if (elapsed < time)
	{
		p.temp = 190;
		p.time = (time - elapsed) / 60;
		return true;
	}
	return false;
}

int getNextConsigne()
{
	// TODO
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

int wr16(const char* dev, char m, int value)
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
	float deltaT = C_INIT * elapsed / 15;
	if (R)
		T += deltaT;
	else
		T -= deltaT;

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
				if (sign(newc) == sign(C))
					C = coeff*C + (1-coeff)*newc;

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
			consigne = temp_auto;
			struct planif p;
			if (readNextPlanif(p))
			{
				int deltaT = p.temp - tcur;
				if (sign(deltaT) == sign(C))
				{
					int mins_to_reach_consigne = 60*deltaT / 10 / C;
					if (mins_to_reach_consigne > p.time*60)
					{
						cout << " MIN TO REACH " << mins_to_reach_consigne << " deltaT = " << deltaT << endl;
						temp_auto = p.temp;
					}
					else
						temp_auto = temp_auto_default;
				}
			}
			else
			{
				cout << "no planif" << endl;
				exit(1);
			}
			break;
	}

	if (consigne != undefined)
	{
		int deltaT = consigne - tcur;
		int new_state = undefined;

		if (sign(deltaT) == sign(C))
			new_state = 1;
		else
			new_state = 0;

		if (new_state != undefined && new_state != rd16("SDB", 'R'))
			wr16("SDB", 'R', new_state);
	}
}

int main(int argc, const char* argv[])
{
	if (argc==2)
	{
		C = atof(argv[1]);
		cout << "C = " << C << endl;
	}
	while(1)
	{
		cout << "time : " << get_ellapsed_seconds() << "\tR:" << R << "\t T:" << T << "\t C:" << C << " consigne_auto: " << temp_auto << endl;
		loop();
		sleep(1);
	}
}
