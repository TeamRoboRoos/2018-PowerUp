package org.usfirst.frc.team4537.robot.enums;

public enum ArmPosition {
	//(index, pneumatic position, arm position)
	load(0,0,4000),
	home(1,1,3000),
	fence(2,2,2000),
	scaleLowMid(3,2,1000),
//	scaleMid(4,2,0),
	scaleHigh(4,2,0);
//	climb(5,1,0);
	
	public int index, pnu, arm;
	ArmPosition(int index, int pnu, int arm) {
		this.index = index;
		this.pnu = pnu;
		this.arm = arm;
	}
}
