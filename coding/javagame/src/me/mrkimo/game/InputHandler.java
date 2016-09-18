package me.mrkimo.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.Image;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Random;
import java.util.List;
import java.lang.System;
import java.lang.Integer;
import java.lang.Thread;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class InputHandler implements KeyListener {

  public InputHandler(Game game){
    game.addKeyListener(this);
  }
  public Key up = new Key();
  public Key down = new Key();
  public Key left = new Key();
  public Key right = new Key();



  public void keyPressed(KeyEvent e){
    toggleKey(e.getKeyCode(), true);
  }

  public void keyReleased(KeyEvent e){
    toggleKey(e.getKeyCode(), false);

  }

  public void keyTyped(KeyEvent e){

  }

  public void toggleKey(int keyCode, boolean isPressed){
    if(keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP){up.toggle(isPressed);}
    if(keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN){down.toggle(isPressed);}
    if(keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT){left.toggle(isPressed);}
    if(keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT){right.toggle(isPressed);}

  }

}
