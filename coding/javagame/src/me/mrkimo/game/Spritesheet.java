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

public class Spritesheet{

  private String path;
  public int width;
  public int height;

  public int[] pixels;

  public Spritesheet(String path){
    BufferedImage image = null;
    try {
      image = ImageIO.read(Spritesheet.class.getResourceAsStream(path));
    } catch (IOException e){
      e.printStackTrace();
    }

    if(image == null){
      return;
    }

    this.path = path;
    this.width = image.getWidth();
    this.height = image.getHeight();

    this.pixels = image.getRGB(0, 0, this.width, this.height, null, 0, this.width);

    for(int i = 0; i < pixels.length; i++){
      pixels[i] = (pixels[i] & 0xFF) / 64;
    }
  }

}
