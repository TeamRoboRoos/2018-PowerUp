package org.usfirst.frc.team4537.robot.commands;

import org.usfirst.frc.team4537.robot.Robot;
import org.usfirst.frc.team4537.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RecordData extends Command {

	private DriverStation ds = DriverStation.getInstance();
	private int[] valsMax = {0,0,0,0};
	
    public RecordData() {
    	setRunWhenDisabled(true);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.telemetry);
    }
    

    // Called just before this Command runs the first time
    protected void initialize() {
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
    	
    	if(Robot.telemetry.getUserButton()) {
    		if(!Robot.driveBase.getAllNeutralMode()) {
    			Robot.driveBase.setAllNeutralMode(NeutralMode.Coast);
    		}
    	}

    	SmartDashboard.putNumber("encL", enc[0]);
    	SmartDashboard.putNumber("encR", enc[1]);
    	SmartDashboard.putNumber("velL", vel[0]);
    	SmartDashboard.putNumber("velR", vel[1]);
    	
    	SmartDashboard.putNumber("Time Remaining", ds.getMatchTime());
    	SmartDashboard.putString("GameData", ds.getGameSpecificMessage());
    	double matchTime = DriverStation.getInstance().getMatchTime();
    	boolean opControl = DriverStation.getInstance().isOperatorControl();
    	boolean endGame = matchTime <= 30;
    	boolean teleopStarted = matchTime != -1;
    	SmartDashboard.putBoolean("EndGame", opControl && endGame && teleopStarted);

    	SmartDashboard.putNumber("EncA", Robot.driveBase.leftMaster.getSensorCollection().getPulseWidthPosition());
    	
    	SmartDashboard.putBoolean("UserButton", Robot.telemetry.getUserButton());
    	
    	SmartDashboard.putNumber("PressureV", Robot.arm.pressureGet());
    	
    	Robot.telemetry.logger.updateSensor("encL", enc[0]);
    	Robot.telemetry.logger.updateSensor("encR", enc[1]);
    	Robot.telemetry.logger.updateSensor("velL", vel[0]);
    	Robot.telemetry.logger.updateSensor("velR", vel[1]);
    	
    	Robot.telemetry.logger.updateSensor("btn1", Robot.oi.getDriveRawButton(1));

    	if(RobotMap.LOGGER_ENABLE) {
        	Robot.telemetry.logger.log();
    	}
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
    }
}
