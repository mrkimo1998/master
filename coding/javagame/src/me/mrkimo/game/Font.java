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
import java.io.IOException;
import java.util.Random;
import java.lang.System;
import java.lang.Integer;
import java.lang.Thread;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Font{

  private static String chars = "  !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";

  public static void render(String msg, Screen screen, int x, int y, int color){

    for(int i = 0; i < msg.length(); i++){
      int charIndex = chars.indexOf(msg.charAt(i));
      if (charIndex >= 0) screen.render(x+i*8, y, charIndex + 29 * 32, color);

    }

  }

}
