package org.usfirst.frc.team4537.robot.commands;

import org.usfirst.frc.team4537.robot.Robot;
import org.usfirst.frc.team4537.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutoLineEnc extends Command {

	private int driveTarget = 5000; //Rotations
	
	private double gyroTarget; //For zeroing the gyro 
	private int[] encOSet;
	private int encoderMismatch = 0; //Error generated by difference between encoders
	private double encoderAvg = 0; //Encoder Average
	private double gyroError = 0; //Error generated by incorrect gyro heading
	
	private double driveSpeed = 0.4;
	
	private double weightGyro = 0.6; //gyro weighting
	private double weightEnc = 0.4; //encoder weighting
	
    public AutoLineEnc() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.driveBase);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	gyroTarget = Robot.telemetry.getGyroAngle();
    	encOSet = Robot.driveBase.getEncoderDistances();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	int[] encDists = Robot.driveBase.getEncoderDistances();
    	gyroError = Robot.telemetry.getGyroAngle() - gyroTarget;
    	int encL = encDists[0] - encOSet[0];
    	int encR = encDists[1] - encOSet[1];
    	encoderAvg = (encL + encR) / 2;
    	encoderMismatch = encL - encR;
    	
    	//driveOffset = (Encoder scaled) * weight + (Gyro scaled) * weight;
    	double driveOffset = (encoderMismatch/500);//*weightEnc + (gyroError/50)*weightGyro;
    	driveOffset = -driveOffset;
    	double leftSpeed = driveSpeed + driveOffset;
    	double rightSpeed = driveSpeed - driveOffset;
//    	Robot.driveBase.setLeftRightMotors(leftSpeed, rightSpeed, true);
    	SmartDashboard.putNumber("leftSpeed", leftSpeed);
    	SmartDashboard.putNumber("rightSpeed", rightSpeed);
    	SmartDashboard.putNumber("encoderMismatch", encoderMismatch);
    	
    	SmartDashboard.putNumber("DriveOffset", driveOffset);
    	SmartDashboard.putNumber("GyroReading", gyroError);
    	SmartDashboard.putNumber("EncoderMisMatch", encoderMismatch);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(driveTarget - encoderAvg <= 0) {
    		return true;
    	} else {
    		return false;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveBase.setLeftRightMotors(0, 0, true);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
