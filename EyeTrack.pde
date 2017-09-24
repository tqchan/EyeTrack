// import org.jorgecardoso.processing.eyetribe.*;
// import com.theeyetribe.client.data.*;

// EyeTribe eyeTribe;
float grid[][];
PVector point;
PImage img,birdImage;
Bird bird;
Bird[] bird2 = new Bird[10];
int R,G,B = 0;
int frame = 60;
PGraphics pg,birdPg;
int i = 0;
int moveSpeed = 10;
int collisionNum = 0;
PFont font;

void setup() {
  fullScreen();
  frameRate(frame);
  img = loadImage("data/kumo2.png");
  pg = createGraphics(width, height);
  pg.beginDraw();
  pg.image(img,0, 0, width, height);
  pg.endDraw();
  //日本語を表示するためにフォントを指定
  font = createFont("Yu Gothic",48,true);
  textFont(font);
  // smooth();
  // point = new PVector();
  // eyeTribe = new EyeTribe(this);
  // bird = new Bird();
  // bird.setup();
  for (int i = 0; i < bird2.length; i++) {
    bird2[i] = new Bird();
    bird2[i].setup();
  }
  // bird2 = new Bird();
  // bird2.setup();

}

void draw() {
  // background(0);
  image(pg, 0, 0);
  noStroke();
  Mouse(mouseX,mouseY);
  // bird.draw();
  // bird.collision();
  // bird2.draw();
  // bird2.collision();
  for (int i = 0; i < bird2.length; i++) {
    bird2[i].draw();
    bird2[i].collision();
  }
  textSize(30);
  text("得点：" + collisionNum, 10, 30);
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
      collisionNum ++;
    }
  }
}