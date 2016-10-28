import java.lang.System;
import java.lang.Runtime;
import java.lang.Math;
import java.lang.Integer;
import java.lang.Double;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public class Goal {

  public static final String VERSION = "0.0.2";
  public static PrintStream out = System.out;
  public static InputStream in = System.in;

  public static void main(String[] args) {

    int int_hPlays = 0;
    int int_gPlays = 0;
    int int_h_gGoals = 0;
    int int_g_gGoals = 0;
    int int_h_bGoals = 0;
    int int_g_bGoals = 0;


    out.println("Willkommen! \n Dies ist Goal : Version " + VERSION + " \n Dieses Programm berechnet die mögliche Anzahl an Toren die in diesem Spiel fallen!");
    out.println("\n Zum Fortfahren bitte [Enter] drücken!");

    //PAUSE
    try{
      System.in.read();
    } catch (IOException e){
      System.exit(500);
    }
    clearScreen();

    out.println("Geben sie nun nacheinander die verlangten Angaben ein!\n");
    out.print("Spiele der Heimmannschaft: ");
    String hPlays = System.console().readLine();
    out.print("Spiele der Gastmannschaft: ");
    String gPlays = System.console().readLine();
    out.print("Geschossene Tore der Heimmannschaft: ");
    String h_gGoals = System.console().readLine();
    out.print("Geschossene Tore der Gastmannschaft: ");
    String g_gGoals = System.console().readLine();
    out.print("Gegentore der Heimmannschaft: ");
    String h_bGoals = System.console().readLine();
    out.print("Gegentore der Gastmannschaft: ");
    String g_bGoals = System.console().readLine();

    clearScreen();

    //PARSING
    try {
      int_hPlays = Integer.parseInt(hPlays);
      int_gPlays = Integer.parseInt(gPlays);
      int_h_gGoals = Integer.parseInt(h_gGoals);
      int_g_gGoals = Integer.parseInt(g_gGoals);
      int_h_bGoals = Integer.parseInt(h_bGoals);
      int_g_bGoals = Integer.parseInt(g_bGoals);

      double goals = ((((double)int_h_gGoals / (double)int_hPlays) + ((double)int_g_gGoals / (double)int_gPlays)) / (((double)int_h_bGoals / (double)int_hPlays) + ((double)int_g_bGoals / (double)int_gPlays))) + (((((double)int_h_gGoals / (double)int_hPlays) + ((double)int_g_gGoals / (double)int_gPlays)) / (((double)int_h_bGoals / (double)int_hPlays) + ((double)int_g_bGoals / (double)int_gPlays))) / (double)2);

      out.println("Die wahrscheinliche Anzahl von Toren in diesem Spiel beträgt:\n " + Math.round(goals) + " Tore");

    } catch(IllegalArgumentException e) {
      System.exit(406);
    }
  }
  public static void clearScreen() {
    for(int i = 0; i < 300; i++) out.println("\b");
  }
}
