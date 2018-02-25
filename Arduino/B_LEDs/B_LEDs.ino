#include <Adafruit_NeoPixel.h> //adds the neopixel library to the code. This library handles the data going to the strip.
#include "Headers.h"

static bool charMode = true; //Set to true to look for chars on serial instead of ints, excludes control character
static bool controlCheck = true; //Set to true to check to ignore CL/NL characters
static bool rioMode = true; //Set to work with rio (bytes instead of chars)

//Physical strips attached to sepperate data pins //NumLEDs, Pin, Code
static Adafruit_NeoPixel ledObjs[] = {Adafruit_NeoPixel(120, 2, NEO_GRB + NEO_KHZ800)};//, Adafruit_NeoPixel(15, 3, NEO_GRB + NEO_KHZ800)};
//Strip segments in software, they are a part of the above strips //StripNum, FromLED, ToLED
static LedStrip ledStrips[] = {LedStrip(ledObjs[0], 0, 59), LedStrip(ledObjs[0], 60, 119)};//, LedStrip(ledObjs[0], 19, 24), LedStrip(ledObjs[0], 25, 29)};

static int numLedObjs = sizeof(ledObjs) / sizeof(ledObjs[0]); //Number of software objects, these are parts of physical strips
static int numLedStrips = sizeof(ledStrips) / sizeof(ledStrips[0]); //Number of physical strips
//---------------------------------------------------------------

void setup() {
  for (int i = 0; i < numLedObjs; i++) {
    ledObjs[i].begin();
    ledObjs[i].show();
  }
  Serial.begin(9600);//9600
  Serial.println("COMPLETE");
  randomSeed(analogRead(0));
}

void loop() {
  if (Serial.available() > 0) {
    if (complete) {
      Serial.println("NEW");
      msgMode = Serial.read();
      Serial.println(msgMode);
      complete = false;
    }
    if (msgMode == 0 && Serial.available() > 1) {
      msgStrip = Serial.read();
      msgAni = Serial.read();
      Serial.println(msgStrip);
      Serial.println(msgAni);
      complete = true;
      newCmd = true;
    }
    if (msgMode == 1 && Serial.available() > 3) {
      msgStrip = Serial.read();
      msgR = Serial.read();
      msgG = Serial.read();
      msgB = Serial.read();
      Serial.println(msgR);
      Serial.println(msgG);
      Serial.println(msgB);
      complete = true;
      newCmd = true;
    }
  }

  if (newCmd) {
    if (msgMode == 0) {
      ledStrips[msgStrip].switchAnimation(AnimationType(msgAni));
    }
    if (msgMode == 1) {
      ledStrips[msgStrip].setRGB(msgR, msgG, msgB);
    }
    newCmd = false;
  }

  for (int i = 0; i < numLedStrips; i++) {
    ledStrips[i].animate();
  }
  for (int i = 0; i < numLedObjs; i++) {
    ledObjs[i].show();
  }
}

//---------------------------------------------------------------

LedStrip::LedStrip(Adafruit_NeoPixel t_obj, int t_from, int t_to) {
  obj = t_obj;
  from = t_from;
  to = t_to;
}

int LedStrip::numPixels() {
  return (to - from) + 1;
}

void LedStrip::setRGB(int r, int g, int b) {
  this->r = r;
  this->g = g;
  this->b = b;
}

void LedStrip::switchAnimation(AnimationType animation) {
  this->currentAnimation = animation;
}

void LedStrip::animate() {
  switch (this->currentAnimation) {

    case AnimationType::blackout:
      this->solidColor(0, 0, 0);
      break;

    case AnimationType::solidColor:
      this->solidColor(this->r, this->g, this->b);
      break;

    case AnimationType::fade:
      this->fade(2000);
      break;

    case AnimationType::blink:
      this->blink(500);
      break;

    case AnimationType::carnival:
      this->carnival(80);
      break;

    case AnimationType::rippleReverse:
      this->rippleReverse(6, 60); //magnitude (changes how big the animation is) :: wait (changes how long between each LED flash)
      break;

    case AnimationType::rainbowCycle:
      this->rainbowCycle(255, 10);
      break;

    case AnimationType::rainbowRandom:
      this->rainbowRandom(255, 75);
      break;

    case AnimationType::rippleCentre:
      this->rippleCentre(8, 100);
      break;
    
    case AnimationType::rippleForwards:
      this->rippleForwards(6, 60);
      break;
  }
}

uint32_t wheelBrightness(byte position, byte brightness) {
  if (position < 85) {
    return Adafruit_NeoPixel::Color((position * 3 * brightness) / 255, ((255 - position * 3) * brightness) / 255, 0);
  } else if (position < 170) {
    position -= 85;
    return Adafruit_NeoPixel::Color(((255 - position * 3) * brightness) / 255, 0, (position * 3 * brightness) / 255);
  } else {
    position -= 170;
    return Adafruit_NeoPixel::Color(0, (position * 3 * brightness) / 255, ((255 - position * 3) * brightness) / 255);
  }
}
