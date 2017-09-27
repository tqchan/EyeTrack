import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import org.jorgecardoso.processing.eyetribe.*; 
import com.theeyetribe.client.data.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class EyeTrack extends PApplet {




EyeTribe eyeTribe;
float grid[][];
PVector point;
PImage img,birdImage;
Bird[] bird = new Bird[10];
int R,G,B = 0;
int frame = 60;
PGraphics pg,birdPg;
int i = 0;
int _sinMove = 0;
int moveSpeed = 10;
int collisionNum = 0;
PFont font;
int a = 0;
int eyeX, eyeY;
//sin\u6ce2\u4f5c\u6210\u7528
float x, y;  //x, y\u5ea7\u6a19
float A;  //\u632f\u5e45
float w;  //\u89d2\u5468\u6ce2\u6570\uff08\u5468\u671f\uff09
float p2;  //\u52d5\u753b\u7528\u521d\u671f\u4f4d\u76f8
float t2;  //\u30a2\u30cb\u30e1\u30fc\u30b7\u30e7\u30f3\u7528\u7d4c\u904e\u6642\u9593\uff08X\u5ea7\u6a19\uff09
float rad = (TWO_PI/60.0f)/3;//1\u79d2\u30671\u56de\u8ee2\u3059\u308b\u3088\u3046\u306b30\u3067\u5272\u308b\u3002\u5ea6\u6570\u6cd5\u3060\u306812\u00b0,\u66f4\u306b3\u3067\u5272\u308b\u30681\u5468\u671f3\u79d2
float w_r = 0.0f;
float myScale = 100.0f;  //\u753b\u9762\u4e0a\u3067\u898b\u3084\u3059\u3044\u3088\u3046\u306b\u62e1\u5927

public void setup() {
  
  frameRate(frame);
  img = loadImage("data/kumo2.png");
  pg = createGraphics(width, height);
  pg.beginDraw();
  pg.image(img,0, 0, width, height);
  pg.endDraw();
  //\u65e5\u672c\u8a9e\u3092\u8868\u793a\u3059\u308b\u305f\u3081\u306b\u30d5\u30a9\u30f3\u30c8\u3092\u6307\u5b9a
  font = createFont("Yu Gothic",48,true);
  textFont(font);
  // smooth();
  // point = new PVector();
  eyeTribe = new EyeTribe(this);
  for (int i = 0; i < bird.length; i++) {
    bird[i] = new Bird();
    bird[i].setup();
  }
  a = width;
  w_r = width / rad;

}

public void draw() {
  // background(0);
  image(pg, 0, 0);
  noStroke();
  // Mouse(mouseX,mouseY);
  if ((_sinMove % 2) == 0) {
    for (int i = 0; i < bird.length; i++) {
      bird[i].draw();
      bird[i].collision();
    }
  } else {
    bird[0].draw();
    // bird[0].collision();
  }
  textSize(30);
  text("\u5f97\u70b9\uff1a" + collisionNum, 10, 30);
}

public void Mouse(float mX, float mY){
  fill(R,G,B,255);
  ellipse(mX, mY, 10, 10);
}

public void keyPressed(){
  if (key == ENTER) {
    exit();
  }
  if (key == BACKSPACE) {
    _sinMove++;
  }
}

public void mousePressed(){
  i++;
}

public void onGazeUpdate(PVector gaze, PVector leftEye_, PVector rightEye_, GazeData data) {
  if ( gaze != null ) {
    point = gaze.get();
    // int x = (int)constrain(round(map(point.x, 0, width, 0, COLS-1)), 0, COLS-1);
    // int y = (int)constrain(round(map(point.y, 0, height, 0, ROWS-1)), 0, ROWS-1);
    // grid[y][x] = constrain(grid[y][x]+10, 0, 255);
    eyeX = (int)point.x;
    eyeY = (int)point.y;
    fill(R,G,B,255);
    ellipse(eyeX, eyeY, 20, 20);
  }
}

public void trackerStateChanged(String state) {
  println("Tracker state: " + state);
}


class Bird {
  int birdX,birdY;

  public void setup() {
    birdImage = loadImage("data/bird.png");
    birdPg = createGraphics(width/10, height/10);
    if (i % 2 == 0) {
      birdX = (int)random(0, width);
      birdY = 0;
    } else {
      birdY = (int)random(0, height);
      birdX = width;
    }
    birdPg.beginDraw();
    birdPg.image(birdImage, 0, 0);
    birdPg.endDraw();
    A = 200.0f;    //\u632f\u5e45\u3092\u8a2d\u5b9a
    w = 1.0f;    //\u89d2\u5468\u6ce2\u6570\u3092\u8a2d\u5b9a
    p2 = 0.0f;    //\u521d\u671f\u4f4d\u76f8\u3092\u8a2d\u5b9a
    t2 = 0.0f;    //\u7d4c\u904e\u6642\u9593\u3092\u521d\u671f\u5316
  }

  public void draw() {
    image(birdPg, birdX, birdY);
    noStroke();
    sinMove();
    if ((_sinMove % 2) == 0) {
      if (i % 2 == 0) {
        birdY += moveSpeed;
        if (birdY >= height) {
          birdX = (int)random(0, width);
          birdY = 0;
        }
      } else {
        birdX -= moveSpeed;
        if ((birdX <= 0) || (width <= birdX)) {
          birdY = (int)random(0, height);
          birdX = width;
        }
      }
    }
  }

  public void collision(){
    int bX,bY,mX,mY;
    bX = birdX;
    bY = birdY;
    // mX = mouseX;
    // mY = mouseY;
    mX = eyeX;
    mY = eyeY;
    if (bX <= mX && mX <= bX + birdImage.width && bY <= mY && mY <= bY + birdImage.height){
      if (i % 2 == 0) {
        birdX = (int)random(0, width);
        birdY = 0;
      } else {
        birdY = (int)random(0, height);
        birdX = width;
      }
      collisionNum ++;
    }
  }

  public void sinMove(){
    if ((_sinMove % 2) == 1 ) {
      x = t2*myScale;
      y = -A*sin(w*t2 + p2);
      t2 += rad;    //\u6642\u9593\u3092\u9032\u3081\u308b
      birdX = (int)x;
      birdY = (int)y + height / 2;
      float gamen = width/4 * rad;
      if (t2 > gamen) {
        t2 = 0.0f;//\u753b\u9762\u306e\u7aef\u306b\u884c\u3063\u305f\u3089\u539f\u70b9\u306b\u623b\u308b
      }
      // text(t2, 10, 20);
    } else {
      t2 = 0.0f;
    }
  }
}
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "EyeTrack" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
