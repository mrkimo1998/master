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

public abstract class Tile {

  public static final Tile[] tiles = new Tile[256];
  public static final Tile VOID = new BasicTile(0, 0, 0, Colors.get(000, -1, -1, -1));
  public static final Tile STONE = new BasicTile(1, 1, 0, Colors.get(-1, 333, -1, -1));
  public static final Tile GRASS = new BasicTile(2, 2, 0, Colors.get(-1, 131, 141, -1));


  protected byte id;
  protected boolean solid;
  protected boolean emitter;

  public Tile(int id, boolean isSolid, boolean isEmitter){

    this.id = (byte) id;
    if(tiles[id] != null) throw new RuntimeException("Duplicate Tile ID on " + id);
    this.solid = isSolid;
    this.emitter = isEmitter;
    tiles[id] = this;
  }

  public byte getID() {
    return id;
  }

  public boolean isSolid(){
    return solid;
  }

  public boolean isEmitter(){
    return emitter;
  }

  public abstract void render(Screen screen, Level level, int x, int y);

}
