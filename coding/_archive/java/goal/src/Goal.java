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

    double d_hPlays = 0;
    double d_gPlays = 0;
    double d_h_gGoals = 0;
    double d_g_gGoals = 0;
    double d_h_bGoals = 0;
    double d_g_bGoals = 0;


    out.println("Willkommen! \n Dies ist Goal : Version " + VERSION + " \n Dieses Programm berechnet die mögliche Anzahl an Toren die in diesem Spiel fallen!");
    out.println("\n Zum Fortfahren bitte [Enter] drücken!");

    //PAUSE
    try{
      in.read();
    } catch (IOException e){
      out.println(e.toString());
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
      d_hPlays = Double.parseDouble(hPlays);
      d_gPlays = Double.parseDouble(gPlays);
      d_h_gGoals = Double.parseDouble(h_gGoals);
      d_g_gGoals = Double.parseDouble(g_gGoals);
      d_h_bGoals = Double.parseDouble(h_bGoals);
      d_g_bGoals = Double.parseDouble(g_bGoals);

      double goals = (((d_h_gGoals / d_hPlays) + (d_g_gGoals / d_gPlays)) / ((d_h_bGoals / d_hPlays) + (d_g_bGoals / d_gPlays))) + ((((d_h_gGoals / d_hPlays) + (d_g_gGoals / d_gPlays)) / ((d_h_bGoals / d_hPlays) + (d_g_bGoals / d_gPlays))) / 2.0);

      out.println("Die wahrscheinliche Anzahl von Toren in diesem Spiel beträgt:\n " + Math.round(goals) + " Tore");

    } catch(IllegalArgumentException e) {
      out.println(e.toString());
      System.exit(406);
    }
  }
  public static void clearScreen() {
    for(int i = 0; i < 300; i++) out.println("\b");
  }
}
