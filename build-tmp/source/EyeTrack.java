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

/**
 * Eye Tribe for Processing library
 * Weighted Grid example: Simple example showing how to get gaze data.
 * August 2015
 * http://jorgecardoso.eu
 **/
 
// import org.jorgecardoso.processing.eyetribe.*;
// import com.theeyetribe.client.data.*;

int COLS = 10;
int ROWS = 10;

// EyeTribe eyeTribe;

float grid[][];
PVector point;
PImage img;
Bird bird;
int R,G,B = 255;

public void setup() {
  
  // size(800, 600);
  img = loadImage("data/kumo2.png");
  
  
  grid = new float[ROWS][COLS];
  point = new PVector();
  // eyeTribe = new EyeTribe(this);
  bird = new Bird();
}

public void draw() {
  background(0);
  image(img, 0, 0, width, height);
  noStroke();
  Mouse(mouseX,mouseY);
}

public void Mouse(float mX, float mY){
  fill(R,G,B,100);
  ellipse(mX, mY, 10, 10);
  // int x = (int)constrain(round(map(mX, 0, width, 0, COLS-1)), 0, COLS-1);
  // int y = (int)constrain(round(map(mY, 0, height, 0, ROWS-1)), 0, ROWS-1);
  // grid[y][x] = constrain(grid[y][x]+20, 0, 255);
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

}
  public void settings() {  fullScreen();  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "EyeTrack" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
