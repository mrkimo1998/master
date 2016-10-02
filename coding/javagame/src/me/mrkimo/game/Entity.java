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


public abstract class Entity {

  public int x, y;
  protected Level level;

  public Entity(Level level){
    init(level);
  }

  public final void init(Level level){
    this.level = level;
  }

  public abstract void tick();

  public abstract void render(Screen screen);

}
