package org.usfirst.frc.team4537.robot.utilities;

import org.usfirst.frc.team4537.robot.Robot;
import org.usfirst.frc.team4537.robot.enums.ArmPosition;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class ArmPositioner {

	public ArmPosition setPosition = ArmPosition.home; //Final destination
	public ArmPosition curPosition = ArmPosition.home; //Current position
	public int step = 0; //Progress to next destination
	public boolean done = true; //At next destination?
	public boolean complete = true; //At final destination?
	public int curPosMovingTo = ArmPosition.home.index; //Next destination
	public int loopCnt = 0; //Number of loops called (debugging)
	public int nextPosTmp = -1;
	public boolean dirTemp = false;
	
	private final int FWD=0,VET=1,BCK=2; //Pneumatic variables

	public ArmPositioner() {}

	public void requestArmPosition(ArmPosition position) {
		setPosition = position; //Set final destination
	}
	
	public boolean hasCompleted() {
		return complete;
	}

	/**
	 * Basically this should always be called as it controls
	 * the movements of the arms, if it stops being called,
	 * the arm will wait at the last step and will continue
	 * off where it left off.
	 * Call <b>hasCompleted()</b> to find out if the loop
	 * still needs to be called, eg, to finish a command.
	 */
	public void control() {
		loopCnt++;
//		doneLast = done;
		int nextPos = -1;
		boolean dir = false;
		if(curPosition.index < setPosition.index) { //Moving up?
			nextPos = curPosition.index; //Next requested destination
			dir = true; //Moving up.
			complete = false;
		}
		else if(curPosition.index > setPosition.index) { //Moving down?
			nextPos = curPosition.index-1; //Next requested destination
			dir = false; //Moving down.
			complete = false;
		}
		else {
			done = true; //Mark done so we can load the next destination when requested
			complete = true; //Mark everything complete
		}
		
		if(done && !complete) { //Are we ready to move to most recent request?
			curPosMovingTo = nextPos; //Mark next destination
			done = false; //Obviously we arn't done, we just started
		}
		nextPosTmp = nextPos;
		dirTemp = dir;
		
		if(!done) { //Do we need to move?
			//Do the move to the next destination
			//The function will mark itself as done and move through its own steps
			switch(curPosMovingTo) {
			case 0: loadHome(dir); break;
			case 1: homeFence(dir); break;
			case 2: fenceScaleLow(dir); break;
			case 3: scaleLowScaleHigh(dir); break;
//			case 4: scaleHighClimb(dir); break;
			default: break;
			}
		}
	}
	
	private void doneMoving() {
		doneMoving(null);
	}
	
	private void doneMoving(ArmPosition position) {
		//Are the arm and pneumatics at their set points?
		if(Robot.arm.getPneumaticsInPos() && Robot.arm.getArmInPos()) {
			if(position == null) { //Is it the last step
				step++; //If not, move to next step
			} else { //If it is,
				step = 0; //Reset step for next destination
				done = true; //Mark arrived at destination
				curPosition = position;
			}
		}
	}

	/*
	 * ----------------------------------------
	 * Here on are sequences for moving the arm
	 * ----------------------------------------
	 */
	
	private void loadHome(boolean dir) {
		if(dir) { //Moving up
			SmartDashboard.putString("Sequence Ran", "Load-Home: "+step);
			switch(step) { //What step are we up to?
			case 0:
				Robot.arm.setPneumaticPosition(BCK); //Move pneumatics back
				doneMoving(); break; //Wait for this to happen
			case 1:
				Robot.arm.setArmPosition(ArmPosition.home); //Move arm down
				doneMoving(); break; //Wait for this to happen
			case 2:
				Robot.arm.setPneumaticPosition(VET); //Move pneumatics vertical
				doneMoving(ArmPosition.home); break; //Wait for this to happen and flag as last step and set the current position to where we are
			}
		} else { //Moving down
			SmartDashboard.putString("Sequence Ran", "Home-Load: "+step);
			switch(step) {
			case 0:
				Robot.arm.setPneumaticPosition(BCK);
				doneMoving(); break;
			case 1:
				Robot.arm.setArmPosition(ArmPosition.load);
				doneMoving(); break;
			case 2:
				Robot.arm.setPneumaticPosition(FWD);
				doneMoving(ArmPosition.load); break;
			}
		}
	}

	private void homeFence(boolean dir) {
		if(dir) { //Moving up
			SmartDashboard.putString("Sequence Ran", "Home-Fence: "+step);
			switch(step) {
			case 0:
				Robot.arm.setPneumaticPosition(BCK);
				doneMoving(); break;
			case 1:
				Robot.arm.setArmPosition(ArmPosition.fence);
				doneMoving(ArmPosition.fence); break;
			}
		} else { //Moving down
			SmartDashboard.putString("Sequence Ran", "Fence-Home: "+step);
			switch(step) {
			case 0:
				Robot.arm.setArmPosition(ArmPosition.home);
				doneMoving(); break;
			case 1:
				Robot.arm.setPneumaticPosition(VET);
				doneMoving(ArmPosition.home); break;
			}
		}
	}

	private void fenceScaleLow(boolean dir) {
		if(dir) { //Moving up
			switch(step) {
			case 0:
				Robot.arm.setArmPosition(ArmPosition.scaleLowMid);
				doneMoving(ArmPosition.scaleLowMid); break;
			}
		} else { //Moving down
			switch(step) {
			case 0:
				Robot.arm.setArmPosition(ArmPosition.fence);
				doneMoving(ArmPosition.fence); break;
			}
		}
	}

	private void scaleLowScaleHigh(boolean dir) {
		if(dir) { //Moving up
			switch(step) {
			case 0:
				Robot.arm.setArmPosition(ArmPosition.scaleHigh);
				doneMoving(ArmPosition.scaleHigh); break;
			}
		} else { //Moving down
			switch(step) {
			case 0:
				Robot.arm.setArmPosition(ArmPosition.scaleLowMid);
				doneMoving(ArmPosition.scaleLowMid); break;
			}
		}
	}

//	private void scaleHighClimb(boolean dir) {
//		
//	}
}
