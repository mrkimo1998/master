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

public class Player extends Mob{

  private InputHandler input;
  private int color = Colors.get(-1, 111, 145, 543);

  public Player(Level level, int x, int y, InputHandler input){
    super(level, "Player", x, y, 1);
    this.input = input;
  }

  public boolean hasCollided(int xa, int ya){
    return false;
  }

  public void tick(){
    int xa = 0;
    int ya = 0;

    if(input.up.isPressed()){ ya--;}
    if(input.down.isPressed()){ ya++;}
    if(input.left.isPressed()){ xa--;}
    if(input.right.isPressed()){ xa++;}

    if(xa != 0 || ya != 0){
      move (xa, ya);
      isMoving = true;
    } else {
      isMoving = false;
    }
  }

  public void render(Screen screen){
    int xTile = 0;
    int yTile = 27;

    int modifier = 8 * scale;
    int xOffset = x - modifier/2;
    int yOffset = y - modifier/2 - 4;

    screen.render(xOffset, yOffset, xTile + yTile * 32, color, 0x00);
    screen.render(xOffset + modifier, yOffset, (xTile + 1) + yTile * 32, color, 0x00);
    screen.render(xOffset, yOffset + modifier, xTile + (yTile + 1) * 32, color, 0x00);
    screen.render(xOffset + modifier, yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, color, 0x00);
  }

}
