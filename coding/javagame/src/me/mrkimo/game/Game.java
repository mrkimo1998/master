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

public class Game extends Canvas implements Runnable{

  private static final long serialVersionUID = 42l;

  private static final short WIDTH = 160;
  private static final short HEIGHT = WIDTH / 12 * 9;
  private static final short SCALE = 5;
  public static final String NAME = "Game";
  public boolean running = false;
  public int tickCount = 0;

  private BufferedImage image = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_RGB);
  private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
  private int[] colors = new int[6 * 6 * 6];


  private JFrame frame;
  public Random r = new Random();

  private Screen screen;
  public InputHandler input;

  public Game() {
    setMinimumSize(new Dimension(WIDTH * SCALE,HEIGHT * SCALE));
    setMaximumSize(new Dimension(WIDTH * SCALE,HEIGHT * SCALE));
    setPreferredSize(new Dimension(WIDTH * SCALE,HEIGHT * SCALE));

    frame = new JFrame(this.NAME);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());

    frame.add(this, BorderLayout.CENTER);
    frame.pack();
    frame.setResizable(false);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  private static short getWIDTH(){
    return WIDTH*SCALE;
  }
  private static short getHEIGHT(){
    return HEIGHT*SCALE;
  }

  public void init(){
    int index = 0;
    for(int r = 0; r < 6; r++){
      for(int g = 0; g < 6; g++){
        for(int b = 0; b < 6; b++){
          int rr = (r * 255 / 5);
          int gg = (g * 255 / 5);
          int bb = (b * 255 / 5);
          colors[index++] = rr << 16 | gg << 8 | bb;
        }
      }
    }

    screen = new Screen(WIDTH, HEIGHT, new Spritesheet("/res/spritesheet.png"));
    input = new InputHandler(this);
  }

  public synchronized void start(){
    running = true;
    new Thread(this).start();
  }

  public synchronized void stop(){
    running = false;
  }

  public void run(){
    long lastTime = System.nanoTime();
    double nsPerTick = 1000000000D/60D;

    int ticks = 0;
    int frames = 0;

    long lastTimer = System.currentTimeMillis();
    double delta = 0;

    init();

    while(running) {
      long now = System.nanoTime();
      delta += (now - lastTime) / nsPerTick;
      lastTime = now;
      boolean shouldRender = false;
      while(delta >= 1){
        ticks++;
        tick();
        delta -= 1;
        shouldRender = true;
      }
      if(shouldRender){
      frames++;
      render();
      }

      if(System.currentTimeMillis() - lastTimer >= 1000){
        lastTimer += 1000;
        frame.setTitle(NAME + " | " + "FPS: " + frames + " | Ticks: " + ticks);
        frames = 0;
        ticks = 0;
      }
    }
  }

  private void tick(){
    tickCount++;

    if(input.up.isPressed()){ screen.yOffset--;}
    if(input.down.isPressed()){ screen.yOffset++;}
    if(input.left.isPressed()){ screen.xOffset--;}
    if(input.right.isPressed()){ screen.xOffset++;}
  }

  private void render(){
    BufferStrategy bs = getBufferStrategy();
    if(bs == null){
      createBufferStrategy(3);
      return;
    }

    for(int y = 0; y < 32; y++){
      for(int x = 0; x < 32; x++){
        screen.render(x << 3, y << 3, 0, Colors.get(555, 500, 050, 005));
      }
    }
    for(int y = 0; y < screen.height; y++){
      for(int x = 0; x < screen.width; x++){
        int colorCode = screen.pixels[x + y * screen.width];
        if(colorCode < 255) pixels[x + y * WIDTH] = colors[colorCode];
      }
    }

    Graphics g = bs.getDrawGraphics();
    g.drawImage(image, 0, 0, getWIDTH(), getHEIGHT(), null);
    g.dispose();
    bs.show();
  }

  public static void main(String[] args) {
    new Game().start();
  }
}
