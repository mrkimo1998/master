package de.mrkimo.lottogen;

import java.util.Random;

public class Main {

	public static void main(String[] args) {
		short x;
		// n = 6aus49 | e = EuroJackpot 5aus50 |
		int n[] = {1,2,3,4,5,6};
		int e[] = {1,2,3,4,5};
		int sz; //Superzahl
		int ez[] = {1,2}; //Eurozahl
		boolean valid = false;
		Random r = new Random();

		for(x = 0; x < 6; x++){
			n[x] = r.nextInt(48) + 1;
			System.out.print(".");
		}
		for(x = 0; x < 5; x++){
			e[x] = r.nextInt(49) + 1;
			System.out.print(".");
		}
		for(x = 0; x < 2; x++){
			ez[x] = r.nextInt(9) + 1;
			System.out.print(".");
		}
		sz = r.nextInt(9);
		
		while(!valid){
			if(n[0] != n[1] && n[1] != n[2] && n[2] != n[3] && n[3] != n[4] && n[4] != n[5] && n[0] != n[2] && n[1] != n[3] && n[2] != n[4] && n[3] != n[5] && n[4] != n[0] && n[0] != n[3] && n[1] != n[4] && n[2] != n[5] && n[3] != n[0] && n[4] != n[1] && n[0] != n[4] && n[1] != n[5] && n[2] != n[0] && n[3] != n[1] && n[4] != n[2] && n[0] != n[5] && n[1] != n[0] && n[2] != n[1] && n[3] != n[2] && n[4] != n[3] && ez[0] != ez[1] && e[0] != e[1] && e[0] != e[2] && e[0] != e[3] && e[0] != e[4] && e[1] != e[2] && e[1] != e[3] && e[1] != e[4] && e[2] != e[3] && e[2] != e[4]) valid = true;
			else{
				for(x = 0; x < 6; x++){
					n[x] = r.nextInt(48) + 1;
					System.out.print(".");
				}
				for(x = 0; x < 5; x++){
					e[x] = r.nextInt(49) + 1;
					System.out.print(".");
				}
				for(x = 0; x < 2; x++){
					ez[x] = r.nextInt(9) + 1;
					System.out.print(".");
				}
			}
		}
		if(valid){
			System.out.println("\n###################################");
			System.out.println("## Ein Programm von Imo Kossmann ##");
			System.out.println("## ----------------------------- ##");
			System.out.println("## Lottozahlgenerator   |   v1.2 ##");
			System.out.println("###################################\n");
			
			System.out.println(" Ihre Lottozahlen:");
			
			System.out.print(" 6 aus 49:");
			for(x = 0; x < 6; x++){
				System.out.print(" " + n[x]);
			}
			System.out.print(" | SZ: " + sz);
			System.out.print("\n Euro Jackpot:");
			for(x=0;x<5;x++){
				System.out.print(" " + e[x]);
			}
			System.out.print(" | EZ:");
			for(x=0;x<2;x++){
				System.out.print(" " + ez[x]);
			}
			System.out.print("\n\n");
			System.out.println("##################################");
		}
		else{
			System.out.println("ERROR: Etwas ist schiefgelaufen!");
		}
	}

}
