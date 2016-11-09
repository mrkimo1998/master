import java.lang.System;
import java.lang.Runtime;
import java.lang.Math;
import java.lang.Integer;
import java.lang.Double;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import javax.swing.JOptionPane;

public class Goal2 extends Application implements EventHandler<ActionEvent> {

  public static final String VERSION = "0.2.0";
  public static final String TITLE = "GOAL2";
  public static final int HEIGHT = 350;
  public static final int WIDTH = 600;

  public static PrintStream out = System.out;
  public static InputStream in = System.in;

  Button but_calc;
  Button but_close;

  Label l_hPlays;
  TextField t_hPlays;
  Label l_gPlays;
  TextField t_gPlays;
  Label l_h_gGoals;
  TextField t_h_gGoals;
  Label l_g_gGoals;
  TextField t_g_gGoals;
  Label l_h_bGoals;
  TextField t_h_bGoals;
  Label l_g_bGoals;
  TextField t_g_bGoals;

  double d_hPlays = 0;
  double d_gPlays = 0;
  double d_h_gGoals = 0;
  double d_g_gGoals = 0;
  double d_h_bGoals = 0;
  double d_g_bGoals = 0;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle(TITLE);

    but_calc = new Button("Berechnen");
    but_calc.setOnAction(this);
    but_close = new Button("Schlie\u00dfen");
    but_close.setOnAction(this);

    l_hPlays = new Label("Spiele der Heimmannschaft:");
    t_hPlays = new TextField ();
    l_gPlays = new Label("Spiele der Gastmannschaft:");
    t_gPlays = new TextField ();
    l_h_gGoals = new Label("Geschossene Tore der Heimmannschaft:");
    t_h_gGoals = new TextField ();
    l_g_gGoals = new Label("Geschossene Tore der Gastmannschaft:");
    t_g_gGoals = new TextField ();
    l_h_bGoals = new Label("Gegentore der Heimmannschaft:");
    t_h_bGoals = new TextField ();
    l_g_bGoals = new Label("Gegentore der Gastmannschaft:");
    t_g_bGoals = new TextField ();

    GridPane layout = new GridPane();
    layout.setAlignment(Pos.CENTER);
    layout.setHgap(5.0);
    layout.setVgap(5.0);

    layout.add(l_hPlays, 1 , 1);
    layout.add(t_hPlays, 2, 1);
    layout.add(l_gPlays, 1, 2);
    layout.add(t_gPlays, 2, 2);
    layout.add(l_h_gGoals, 1, 3);
    layout.add(t_h_gGoals, 2, 3);
    layout.add(l_g_gGoals, 1, 4);
    layout.add(t_g_gGoals, 2, 4);
    layout.add(l_h_bGoals, 1, 5);
    layout.add(t_h_bGoals, 2, 5);
    layout.add(l_g_bGoals, 1, 6);
    layout.add(t_g_bGoals, 2, 6);
    layout.add(but_calc, 1, 7);
    layout.add(but_close, 2, 7);

    Scene scene = new Scene(layout, WIDTH, HEIGHT);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  @Override
  public void handle(ActionEvent event){
    if(event.getSource() == but_calc){
      calc();
    }
    if(event.getSource() == but_close){
      System.exit(0);
    }
  }

  public void calc(){
    try {
      d_hPlays = Double.parseDouble(t_hPlays.getText());
      d_gPlays = Double.parseDouble(t_gPlays.getText());
      d_h_gGoals = Double.parseDouble(t_h_gGoals.getText());
      d_g_gGoals = Double.parseDouble(t_g_gGoals.getText());
      d_h_bGoals = Double.parseDouble(t_h_bGoals.getText());
      d_g_bGoals = Double.parseDouble(t_g_bGoals.getText());

      double goals = Math.round((((d_h_gGoals / d_hPlays) + (d_g_gGoals / d_gPlays)) / ((d_h_bGoals / d_hPlays) + (d_g_bGoals / d_gPlays))) + ((((d_h_gGoals / d_hPlays) + (d_g_gGoals / d_gPlays)) / ((d_h_bGoals / d_hPlays) + (d_g_bGoals / d_gPlays))) / 2.0));

      JOptionPane.showMessageDialog(null, ("Die wahrscheinliche Anzahl von Toren in diesem Spiel beträgt:\n " + goals + " Tore"), "Ergebnis", JOptionPane.PLAIN_MESSAGE);

    } catch(IllegalArgumentException e) {
      out.println(e.toString());
      System.exit(406);
    }
  }

}
