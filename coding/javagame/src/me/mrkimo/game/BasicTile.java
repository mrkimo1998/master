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

public class BasicTile extends Tile {

  protected int tileID;
  protected int tileColor;

  public BasicTile(int id, int x, int y, int tileColor){
    super(id, false, false);

    this.tileID = x + y;
    this.tileColor = tileColor;
  }

  public void render(Screen screen, Level level, int x, int y){
    screen.render(x, y, tileID, tileColor);
  }

}
