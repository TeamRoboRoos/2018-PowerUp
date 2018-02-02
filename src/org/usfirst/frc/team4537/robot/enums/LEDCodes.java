package org.usfirst.frc.team4537.robot.enums;

public enum LEDCodes {
	//Animations
	a_blackout(0),
	a_solidColor(1),
	a_blink(2),
	a_fade(3),
	a_carnival(4),
	a_rippleReverse(5),
	a_rainbowCycle(6),
	a_rainbowRandom(7),
	a_rippleCentre(8),
	a_rippleForwards(9),
	
	//Preset Colors
	c_red(255,0,0),
	c_green(0,255,0),
	c_blue(0,0,255),
	c_yellow(126,84,0),
	c_orange(158,63,0),
	c_teal(0,127,127),
	c_violet(127,0,127),
	c_white(255,255,255),
	
	//Modes
	m_animation(0),
	m_colour(1);
	
	//Initializers
	public byte value;
	LEDCodes(int value) {
		this.value = (byte)value;
	}

	public byte r,g,b;
	LEDCodes(int r, int g, int b) {
		this.r = (byte)r;
		this.g = (byte)g;
		this.b = (byte)b;
	}
}
