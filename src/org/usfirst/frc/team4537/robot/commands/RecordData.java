package org.usfirst.frc.team4537.robot.commands;

import org.usfirst.frc.team4537.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RecordData extends Command {

	private int[] valsMax = {0,0,0,0};
	
    public RecordData() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.telemetry);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setRunWhenDisabled(true);
    	Robot.telemetry.logger.startAutoLog();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	int[] enc = Robot.driveBase.getEncoderDistances();
    	int[] vel = Robot.driveBase.getEncoderVelocities();
    	int[] vals = {enc[0],enc[1],vel[0],vel[1]};
    	
    	for(int i=0; i < valsMax.length; i++) {
    		vals[i] = Math.abs(vals[i]);
    		if(vals[i] > valsMax[i]) {
    			valsMax[i] = vals[i];
    		}
    	}

    	SmartDashboard.putNumber("encL", enc[0]);
    	SmartDashboard.putNumber("encR", enc[1]);
    	SmartDashboard.putNumber("velL", vel[0]);
    	SmartDashboard.putNumber("velR", vel[1]);
    	
    	SmartDashboard.putNumber("velL_MAX", valsMax[2]);
    	
    	Robot.telemetry.logger.updateSensor("encL", enc[0]);
    	Robot.telemetry.logger.updateSensor("encR", enc[1]);
    	Robot.telemetry.logger.updateSensor("velL", vel[0]);
    	Robot.telemetry.logger.updateSensor("velR", vel[1]);
    	
    	Robot.telemetry.logger.updateSensor("btn1", Robot.oi.getDriveRawButton(1));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.telemetry.logger.stopAutoLog();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
