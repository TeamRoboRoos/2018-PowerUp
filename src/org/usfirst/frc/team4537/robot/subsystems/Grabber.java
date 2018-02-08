package org.usfirst.frc.team4537.robot.subsystems;

import org.usfirst.frc.team4537.robot.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Grabber extends Subsystem {
	private Spark leftGrab;
	private Spark rightGrab; //both from the robot's perspective

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public Grabber() {
	leftGrab = new Spark(RobotMap.PWM_MOTOR_GRAB_L);
	rightGrab = new Spark(RobotMap.PWM_MOTOR_GRAB_R);
}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void driveGrabber (boolean direction) {
    	if (direction == true) {
        	leftGrab.set(RobotMap.CHAINSAW_SPEED);
        	rightGrab.set(RobotMap.CHAINSAW_SPEED);
    	}
    	else if (direction == false) {
    		leftGrab.set(-RobotMap.CHAINSAW_SPEED);
        	rightGrab.set(-RobotMap.CHAINSAW_SPEED);
    	}

    }
}

