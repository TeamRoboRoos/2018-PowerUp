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
//	private int maxSpd = 0;
	
    public RecordData() {
    	setRunWhenDisabled(true);
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.telemetry);
    }
    

    // Called just before this Command runs the first time
    protected void initialize() {
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	int[] enc = Robot.driveBase.getEncoderDistances();
    	int[] vel = Robot.driveBase.getEncoderVelocities();
    	
    	if(Robot.telemetry.getUserButton()) {
    		if(!Robot.driveBase.getAllNeutralMode()) {
    			Robot.driveBase.setAllNeutralMode(NeutralMode.Coast);
    		}
    	}

    	//Subsystems
    	SmartDashboard.putData("DriveBase", Robot.driveBase);
		SmartDashboard.putData("Arm", Robot.arm);
		SmartDashboard.putData("Grabber", Robot.grabber);
		SmartDashboard.putData("Telemetry", Robot.telemetry);
		SmartDashboard.putData("Arduino", Robot.arduino);
		SmartDashboard.putData("Scheduler", Scheduler.getInstance());
		
		//Encoders & Velocity
    	SmartDashboard.putNumber("encL", enc[0]);
    	SmartDashboard.putNumber("encR", enc[1]);
    	SmartDashboard.putNumber("velL", vel[0]);
    	SmartDashboard.putNumber("velR", vel[1]);
    	
    	//Game data and time
    	SmartDashboard.putNumber("Time Remaining", ds.getMatchTime());
    	SmartDashboard.putString("GameData", ds.getGameSpecificMessage());
    	
    	//User input
    	SmartDashboard.putBoolean("UserButton", Robot.telemetry.getUserButton());
    	
    	//Pressure
    	SmartDashboard.putNumber("PressureV", Robot.arm.pressureGet());
    	SmartDashboard.putNumber("PressRAW", Robot.arm.pressureGetRaw());

    	//Arm stuff
    	SmartDashboard.putBoolean("CylTopExt", Robot.arm.getPnuBools()[0]);
    	SmartDashboard.putBoolean("CylBottomExt", Robot.arm.getPnuBools()[2]);
    	SmartDashboard.putBoolean("CylTopRet", Robot.arm.getPnuBools()[1]);
    	SmartDashboard.putBoolean("CylBottomRet", Robot.arm.getPnuBools()[3]);
    	SmartDashboard.putNumber("Arm_Quad", Robot.arm.getEncoderPosition());
    	
//    	//Logger
//    	Robot.telemetry.logger.updateSensor("encL", enc[0]);
//    	Robot.telemetry.logger.updateSensor("encR", enc[1]);
//    	Robot.telemetry.logger.updateSensor("velL", vel[0]);
//    	Robot.telemetry.logger.updateSensor("velR", vel[1]);
//    	
//    	Robot.telemetry.logger.updateSensor("btn1", Robot.oi.getDriveRawButton(1));
//
//    	if(RobotMap.LOGGER_ENABLE) {
//        	Robot.telemetry.logger.log();
//    	}
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
