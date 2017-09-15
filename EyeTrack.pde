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

void setup() {
  fullScreen();
  // size(800, 600);
  img = loadImage("data/kumo2.png");
  
  smooth();
  grid = new float[ROWS][COLS];
  point = new PVector();
  // eyeTribe = new EyeTribe(this);
  bird = new Bird();
}

void draw() {
  background(0);
  image(img, 0, 0, width, height);
  noStroke();
  Mouse(mouseX,mouseY);
}

void Mouse(float mX, float mY){
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