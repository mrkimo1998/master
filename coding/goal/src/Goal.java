import java.util.Random;
import java.lang.System;
import java.lang.Integer;
import java.lang.Double;
import java.io.Console;
import java.io.IOException;

public class Goal {
  public static void main(String[] args) {

    int int_hWins = 0;
    int int_gWins = 0;
    int int_h_gGoals = 0;
    int int_g_gGoals = 0;
    int int_h_bGoals = 0;
    int int_g_bGoals = 0;


    System.out.println("Willkommen! \n Dies ist Goal, \n ein Gewinnchancenberechner (TM) für ihr nächstes Fußballmatch!");
    try{
      System.in.read();
    } catch (IOException e){
      System.exit(500);
    }
    clearScreen();

    System.out.println("Geben sie nun nacheinander die verlangten Angaben ein!");

    System.out.print("Siege der Heimmannschaft: ");
    String hWins = System.console().readLine();
    System.out.println("\n");

    System.out.print("Siege der Gastmannschaft: ");
    String gWins = System.console().readLine();
    System.out.println("\n");

    System.out.print("Geschossene Tore der Heimmannschaft: ");
    String h_gGoals = System.console().readLine();
    System.out.println("\n");

    System.out.print("Geschossene Tore der Gastmannschaft: ");
    String g_gGoals = System.console().readLine();
    System.out.println("\n");

    System.out.print("Gegentore der Heimmannschaft: ");
    String h_bGoals = System.console().readLine();
    System.out.println("\n");

    System.out.print("Gegentore der Gastmannschaft: ");
    String g_bGoals = System.console().readLine();
    System.out.println("\n");

    try {

      int_hWins = Integer.parseInt(hWins);
      int_gWins = Integer.parseInt(gWins);
      int_h_gGoals = Integer.parseInt(h_gGoals);
      int_g_gGoals = Integer.parseInt(g_gGoals);
      int_h_bGoals = Integer.parseInt(h_bGoals);
      int_g_bGoals = Integer.parseInt(g_bGoals);
      clearScreen();

      double chance = ((((double)int_h_gGoals / (double)int_hWins) + ((double)int_g_gGoals / (double)int_gWins)) / (((double)int_h_bGoals / (double)int_hWins) + ((double)int_g_bGoals / (double)int_gWins))) + (((((double)int_h_gGoals / (double)int_hWins) + ((double)int_g_gGoals / (double)int_gWins)) / (((double)int_h_bGoals / (double)int_hWins) + ((double)int_g_bGoals / (double)int_gWins))) / (double)2);

      System.out.println("Die Gewinnchance der Heimmannschaft liegt bei " + ((double) chance * 10) + " % !");

    } catch(IllegalArgumentException e) {
      System.exit(406);
    }
  }
  public static void clearScreen() {
      System.out.print("\033[H\033[2J");
      System.out.flush();
  }
}
