#include <math.h>
#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
//VARIABLEN
const short cpm = 16;
int time_trick = 1;
short clx,cly;
const char cell_alive = 'O';
const char cell_dead = 'X';
const char cell_empty = ' ';
const short HEIGHT = 16;
const short WIDTH = 16;
char cell_array[16][16];

int game();
int ausgabe();
int cycle();
int coord_rand();
int cell_logic(short,short);



/*
	Hauptprogramm - Startet Game
*/
void main(){
	game();
}

int game(){
	

	short cell_count = 75;//Zellanzahl

	//Check ob die maximale mögliche Zellen Anzahl überschritten wurde
	if(!cell_count > HEIGHT*WIDTH) {/** ((!cell_count) > HEIGHT*WIDTH) ist nicht das gleiche wie (cell_count < HEIGHT*WIDTH)**/
		printf("ERROR: Argument muss kleiner sein als " + (HEIGHT*WIDTH));/** printf("Ausgabetext %d", Varialble); **/
		printf("\n");
		return 1;
	}
	short x,y,i,c;
	short cx,cy;
	
	
	printf("STARTING...\n"); //DEBUG
	printf("________________________________\n");
	
	//FILL
	memset(cell_array, cell_empty, HEIGHT*WIDTH);
	//Cast Cell - ruft coord_rand() zwei mal auf 
	for(c=0; c < cell_count; c += 1){
		//Aufruf von coord_rand
		cx=coord_rand();
		cy=coord_rand();
		if(cell_array[cx][cy] == cell_empty){
			cell_array[cx][cy] = cell_alive;

		}
		else {
			c -= 1;
		}
		
	}
	//Startet die Ausgabe
    ausgabe();
    while (1) {
  	    cycle();
    }
	//Rückgabe an Game das alles Funktioniert - wird bewusst nicht aufgerufen
	return 0;
}

/*
	Ausgabefunction - Gibt cell_array und formatierungen aus - startet cycle()
*/
int ausgabe(){
	short x,y;
	for(x=0; x < WIDTH; x++) {
		printf("|\n|");
		for(y=0; y < HEIGHT; y++) {
			printf("%c",cell_array[x][y]);
	}	}
	
	printf("\n\n________________________________\n");
	printf("Press [ENTER] for next cycle\n");	
	cycle();
	return 0;
}

/*
	Zyklusfunktion - wartet auf ein ENTER - prüft jede Zelle mit cell_logic
*/
int cycle(){

	short xx,yy =0;
	char enter[1];
	scanf("%c",enter);
	printf("\n\n\n\n\n\n\n________________________________\n");
		for(xx=0; xx < WIDTH; xx++){
			for(yy=0; yy < HEIGHT; yy++){
				// Aufruf von cell_logic
				int krn = cell_logic(xx,yy);
				if(krn == 1){ //Wenn cell_logic 1 war, setze char in cell_array auf tot
					cell_array[xx][yy] = cell_dead;
				}
				else if(krn == 2){//Wenn cell_logic 2 war, setze char in cell_array auf lebend
					cell_array[xx][yy] = cell_alive;
				}
				else {//Wenn cell_logic 0 oder etwas anderes war, setze char in cell_array auf leer
					cell_array[xx][yy] = cell_empty;
				}
			}
		}
    ausgabe();
	return 0;
}

/*
	Zufallsfunktion berechnet Zufallszahl für Zellkoordinaten
*/
int coord_rand(){
	short r;
	time_trick = time_trick * time(NULL) % 87;
	time_trick = abs(time_trick);
	time_trick = abs(time_trick);
	r=((rand() % 80) * (rand() % 99) * time_trick) % 16 + 1;	/** %16 + 1 ergibt Zufallszahlen von 1-16, du brauchst aber 0-15 **/
	return r;
}

/*Zelllogikfuinktion - checkt umgebung der Zelle bei @param cell_array[clx][cly] und sagt ob die Zelle
	@return kill_revive_no = 0 -> Nix tut
	@return kill_revive_no = 1 -> stirbt
	@return kill_revive_no = 2 -> wieder lebt
*/
int cell_logic(short clx, short cly){
	//cell_array[clx][cly] ZELLPOSITION
	short all_c = 0;

	int kill_revive_no;
	short x,y;
	
	/*
		Check ob Momentane Zelle lebt
	*/

	if(cell_array[clx][cly] == cell_alive){
		//Zählen der lebenden Zellen im cell_array
		for(x=clx - 1; x < clx + 1; x++) {
			for(y=cly - 1; y < cly + 1; y++) {
				if(cell_array[x][y] == cell_alive) {
					all_c++;
				}
			}
		}
        all_c--;
        printf("|%i|",all_c);
		//KILL?
		if(all_c <= 2 || all_c >= 3){
			kill_revive_no = 1; //TÖTE ZELLE
		}
		else {
			kill_revive_no = 0; //NIX TUN
		}
		
		
		
		}
	/*
		Check ob Momentane Zelle tot ist
	*/
	else if(cell_array[clx][cly] == cell_dead){
		//Zählen der lebenden Zellen in ncells
		for(x=clx - 1; x < clx + 1; x++) {
			for(y=cly - 1; y < cly + 1; y++) {
				if(cell_array[x][y] == cell_alive) {
					all_c += 1;
				}
			}
		}
		
		//REVIVE?
		if(all_c == 3){
			kill_revive_no = 2; //WIEDERBELEBEN
		}
		else{
			kill_revive_no = 0; //NIX TUN
		}
		
	}
	/*
		Wenn die Zelle nicht lebt und nicht tot ist, lass sie in ruhe
	*/
	else{
		//NOCELL
		kill_revive_no = 0;
		
	}
//Rückgabe an cell_logic
    all_c = 0;
return kill_revive_no;
}
