enum AnimationType {//enums let us give names to different states.
  blackout,         //0
  solidColor,       //1
  blink,            //2
  fade,             //3
  carnival,         //4
  rippleReverse,    //5
  rainbowCycle,     //6
  rainbowRandom,    //7
  rippleCentre,     //8
  rippleForwards    //9
}; //Creates a list of our animations.

class LedStrip { //A class is a group of other data and functions.
  private:
    int r = 0, g = 255, b = 0;
    int from, to;
    Adafruit_NeoPixel obj;
    AnimationType currentAnimation = AnimationType(1);
    long lastTime = 0; //Used in animations
  protected: // protected members can only be accessed by this class or other classes that inherit it.
  public:
    LedStrip(Adafruit_NeoPixel t_obj, int t_from, int t_to); //this function sets up the led strip accoring to the NeoPixel library.
    int numPixels();
    void setRGB(int r, int g, int b);
    void switchAnimation(AnimationType animation); //function to change cirrent animation on strip portion
    void animate(); //function to cycle the animation
    void solidColor(int r, int g, int b); // these are functions for animations.
    void fade(int wavelength);
    void blink(int wait);
    void carnival(int wait);
    void rippleReverse(byte magnitude, byte wait);
    void rainbowCycle(byte brightness, int wait);
    void rainbowRandom(byte brightness, int wait);
    void rippleCentre(byte magnitude, byte wait);
    void rippleForwards(byte magnitude, byte wait);
};

byte msgMode;           //Mode select 0=animation, 1=colour
byte msgStrip;          //Strip select
byte msgAni;            //Animation to set
byte msgR;              //Colour R to set
byte msgG;              //Colour G to set
byte msgB;              //Colour B to set
boolean complete = true;//Is input finished being read?
boolean newCmd = false; //Is there a new command?
