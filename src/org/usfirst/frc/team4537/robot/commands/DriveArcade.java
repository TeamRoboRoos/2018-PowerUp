package org.usfirst.frc.team4537.robot.commands;

import org.usfirst.frc.team4537.robot.Robot;
import org.usfirst.frc.team4537.robot.RobotMap;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveArcade extends Command {

	boolean bLast = false;
	
    public DriveArcade() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveBase);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double fwd = Robot.oi.getDriveRawAxis(RobotMap.CONTROL_DRIVE_0_Y);
    	double rot = Robot.oi.getDriveRawAxis(RobotMap.CONTROL_DRIVE_0_Z);
    	
//    	Robot.driveBase.arcadeDrive(fwd, rot, true);
//    	Robot.driveBase.setLeftRightMotors(fwd, fwd);
    	
    	Robot.driveBase.profile.control();
    	boolean b = Robot.oi.getDriveRawButton(2);
    	
    	if(b) {
    		SetValueMotionProfile setOutput = Robot.driveBase.profile.getSetValue();
    		Robot.driveBase.leftMaster.set(ControlMode.MotionProfile, setOutput.value);
    		if(b && !bLast) {
    			Robot.driveBase.profile.startMotionProfile();
    		}
    	}
    	
    	bLast = b;
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveBase.profile.reset();
    }
}
