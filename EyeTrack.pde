/**
 * Eye Tribe for Processing library
 * Weighted Grid example: Simple example showing how to get gaze data.
 * August 2015
 * http://jorgecardoso.eu
 **/
 
// import org.jorgecardoso.processing.eyetribe.*;
// import com.theeyetribe.client.data.*;

// EyeTribe eyeTribe;
float grid[][];
PVector point;
PImage img,birdImage;
Bird bird;
int R,G,B = 0;
int frame = 60;
PGraphics pg,birdPg;
int i = 0;
int moveSpeed = 10;

void setup() {
  fullScreen();
  // size(800, 600);
  frameRate(frame);
  img = loadImage("data/kumo2.png");
  pg = createGraphics(width, height);
  pg.beginDraw();
  pg.image(img,0, 0, width, height);
  pg.endDraw();
  // smooth();
  // point = new PVector();
  // eyeTribe = new EyeTribe(this);
  bird = new Bird();
  bird.setup();

}

void draw() {
  // background(0);
  image(pg, 0, 0);
  noStroke();
  Mouse(mouseX,mouseY);
  bird.draw();
  bird.collision();
  textSize(30);
  text("" + i, 10, 30);
}

void Mouse(float mX, float mY){
  fill(R,G,B,255);
  ellipse(mX, mY, 10, 10);
}

void keyPressed(){
  if (key == ENTER) {
    exit();
  }
}

void mousePressed(){
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

  void setup() {
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

  void draw() {
    image(birdPg, birdX, birdY);
    noStroke();
    if (i % 2 == 0) {
      birdY += moveSpeed;
      if (birdY >= height) {
        birdX = (int)random(0, width);
        birdY = 0;
      }
    } else {
      birdX -= moveSpeed;
      if (birdX <= 0) {
        birdY = (int)random(0, height);
        birdX = width;
      }
    }
  }

  void collision(){
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
    }
  }
}