import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class EyeTrack extends PApplet {

// import org.jorgecardoso.processing.eyetribe.*;
// import com.theeyetribe.client.data.*;

// EyeTribe eyeTribe;
float grid[][];
PVector point;
PImage img,birdImage;
Bird[] bird = new Bird[10];
int R,G,B = 0;
int frame = 60;
PGraphics pg,birdPg;
int i = 0;
int moveSpeed = 10;
int collisionNum = 0;
PFont font;
int a = 0;

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
  // eyeTribe = new EyeTribe(this);
  for (int i = 0; i < bird.length; i++) {
    bird[i] = new Bird();
    bird[i].setup();
  }
}

public void draw() {
  // background(0);
  image(pg, 0, 0);
  noStroke();
  Mouse(mouseX,mouseY);
  for (int i = 0; i < bird.length; i++) {
    bird[i].draw();
    bird[i].collision();
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
}

public void mousePressed(){
  i++;
}

// void onGazeUpdate(PVector gaze, PVector leftEye_, PVector rightEye_, GazeData data) {
//   if ( gaze != null ) {
//     point = gaze.get();
//     int x = (int)constrain(round(map(point.x, 0, width, 0, COLS-1)), 0, COLS-1);
//     int y = (int)constrain(round(map(point.y, 0, height, 0, ROWS-1)), 0, ROWS-1);
//     grid[y][x] = constrain( grid[y][x]+10, 0, 255);
//   }
// }

// void trackerStateChanged(String state) {
//   println("Tracker state: " + state);
// }


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
  }

  public void draw() {
    image(birdPg, birdX, birdY);
    noStroke();
    if (i % 2 == 0) {
      birdY += moveSpeed;
      if (birdY >= height) {
        birdX = (int)random(0, width);
        birdY = 0;
      }
    } else {
      // birdX -= moveSpeed;
      a += moveSpeed;
      float x = a * (4*PI/width);
      float y = sin(x);
      birdX = (int)x;
      birdY = (int)y;
      if (birdX <= 0) {
        birdY = (int)random(0, height);
        birdX = width;
      }
    }
  }

  public void collision(){
    int bX,bY,mX,mY;
    bX = birdX;
    bY = birdY;
    mX = mouseX;
    mY = mouseY;
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
}

int CountX=0;

public void setup() {
  size(300, 300);                           // \u753b\u9762\u30b5\u30a4\u30ba
  frameRate(40);                          // 1\u79d2\u9593\u306b40\u30b3\u30de\u63cf\u304f
  background(255);                       // \u80cc\u666f\u8272\u3092\u8a2d\u5b9a
}

public void draw() {
  if (CountX < width) {
    CountX+=2;
  } else {
    CountX = 0;
    background(255);                    // \u80cc\u666f\u8272\u3092\u767d\u306b\u8a2d\u5b9a
  }
  float x = CountX * (4*PI/width);
  float y = sin(x);                         // \u8868\u793a\u30c7\u30fc\u30bf\uff08sin\u6ce2\uff09
  myPrintChart(CountX, y);
  myPrintText(y);
}

/* \u30b0\u30e9\u30d5\u8868\u793a */
public void myPrintChart(int x, float y) {
  stroke(50, 180, 200);                    // \u8f2a\u90ed\u7dda\u306e\u8272\u3092\u6307\u5b9a
  fill(255);                                     // \u5857\u308a\u3064\u3076\u3057\u8272
  int posY= (int)((-y+2)*(height/4));
  ellipse(x, posY, 5, 5);                   // ellipse(x, y, width, height)
}

/* \u6570\u5024\u8868\u793a */
public void myPrintText(float y) {
  noStroke();                               // \u67a0\u3092\u63cf\u753b\u3057\u306a\u3044
  fill(230, 230, 255);                      // \u5857\u308a\u3064\u3076\u3057\u306e\u8272\u3092\u6307\u5b9a  
  rect(5, 5, 50, 20);                       // \u6587\u5b57\u8868\u793a\u57df rect(x, y, width, height); 
  fill(0);                                       // \u6587\u5b57\u306e\u8272\u3092\u6307\u5b9a  
  text(y, 10, 20);
// println(y);
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
