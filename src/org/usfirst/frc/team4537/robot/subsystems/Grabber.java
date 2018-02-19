package org.usfirst.frc.team4537.robot.subsystems;

import org.usfirst.frc.team4537.robot.RobotMap;
import org.usfirst.frc.team4537.robot.commands.*;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Grabber extends Subsystem {
	
	private Spark grabLeft;
	private Spark grabRight; //both from the robot's perspective

	public Grabber() {
		grabLeft = new Spark(RobotMap.PWM_MOTOR_GRAB_L);
		grabRight = new Spark(RobotMap.PWM_MOTOR_GRAB_R);
		
		SmartDashboard.putNumber("GrabberTestRun", 0.0);
	}
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
//		setDefaultCommand(new GrabTest());
	}
	
	public void driveGrabber (double speed) {
		driveGrabbersRaw(-speed, speed);
	}
	
	public void driveGrabbersRaw(double left, double right) {
		grabLeft.set(left);
		grabRight.set(right);
	}
}

