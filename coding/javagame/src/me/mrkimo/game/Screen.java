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

public class Screen {

  private static final int MAP_WIDTH = 64;
  private static final int MAP_WIDTH_MASK = MAP_WIDTH - 1;

  public int[] pixels;

  public int xOffset = 0;
  public int yOffset = 0;

  public int width;
  public int height;

  private Spritesheet spritesheet;

  public Screen(int width, int height, Spritesheet spritesheet){
    this.width = width;
    this.height = height;
    this.spritesheet = spritesheet;

    pixels = new int[width * height];

  }

  /*public void render(int[] pixels, int offset, int row){
    for(int yTile = yOffset >> 3; yTile <= (yOffset + height) >> 3; yTile++){
      int yMin = yTile * 8 - yOffset;
      int yMax = yMin + 8;

      if(yMin < 0){ yMin = 0;}
      if(yMax > height){ yMax = height;}

      for(int xTile = xOffset >> 3; xTile <= (xOffset + width) >> 3; xTile++){
        int xMin = xTile * 8 - xOffset;
        int xMax = xMin + 8;

        if(xMin < 0){ xMin = 0;}
        if(xMax > width){ xMax = width;}

        int tileIndex = (xTile & (MAP_WIDTH_MASK)) + (yTile & (MAP_WIDTH_MASK)) * MAP_WIDTH;

        for(int y = yMin; y < yMax; y++){
          int sheetPixel = ((y + yOffset) & 7) * spritesheet.width + ((xMin + xOffset) & 7);
          int tilePixel = offset + xMin + y * row;
          for(int x = xMin; x < xMax; x++){
            int color = tileIndex * 4 + spritesheet.pixels[sheetPixel++];
            pixels[tilePixel++] = colors[color];
          }
        }
      }
    }
  }*/

  public void render(int xPos, int yPos, int tile, int color){
    xPos -= xOffset;
    yPos -= yOffset;

    int xTile = tile % 32;
    int yTile = tile / 32;

    int tileOffset = (xTile << 3) + (yTile << 3) * spritesheet.width;

    for(int y = 0; y < 8; y++){
      int ySheet = y;
      if(y + yPos < 0 || y + yPos >= height){ continue;}
      for(int x = 0; x < 8; x++){
        if(x + xPos < 0 || x + xPos >= width){ continue;}
        int xSheet = x;

        int col = (color >> (spritesheet.pixels[xSheet + ySheet * spritesheet.width + tileOffset] * 8)) & 255;
        if(col < 255) pixels[(x+xPos) + (y+yPos) * width] = col;
      }
    }

  }
}
