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

public class Key{
  private int numPressed = 0;
  private boolean pressed = false;

  public int getNumPressed(){
    return numPressed;
  }

  public boolean isPressed(){
    return pressed;
  }
  public void toggle(boolean isPressed){
    pressed = isPressed;
    if(isPressed){numPressed++;}
  }
}
