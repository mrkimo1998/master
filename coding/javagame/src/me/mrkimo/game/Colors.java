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

public class Colors{
  public static int get(int color1, int color2, int color3, int color4){
    return (get(color4) << 24) + (get(color3) << 16) + (get(color2) << 8) + get(color1);
  }
  public static int get(int color){
    if(color < 0) return 255;
    int r = color / 100 % 10;
    int g = color / 10 % 10;
    int b = color % 10;

    return r * 36 + g * 6 + b;
  }
}
