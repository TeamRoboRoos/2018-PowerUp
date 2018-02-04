package org.usfirst.frc.team4537.robot.enums;

public enum ArmPositions {
	//Motor positions
	//ex(num,pos)
	/** Grabber position **/
	m_grab(0,0),
	/** Home position **/
	m_home(1,0),
	/** Switch fence position **/
	m_fence(2,0),
	/** Scale low position**/
	m_scaleLow(3,0),
	/** Scale mid position**/
	m_scaleMid(4,0),
	/** Scale high position**/
	m_scaleHigh(5,0),
	/** Climb position **/
	m_climb(6,0),
	
	//Pneumatic positions
	/** Arm forward position **/
	p_forward(0),
	/** Arm upright position **/
	p_upright(1),
	/** Arm back position **/
	p_back(2);
	
	public int value, position;
	ArmPositions(int v, int p) {
		value = v;
		position = p;
	}
	
	ArmPositions(int v) {
		value = v;
	}
}
