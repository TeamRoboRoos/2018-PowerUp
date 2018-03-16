package org.usfirst.frc.team4537.robot.commands;

import org.usfirst.frc.team4537.robot.Robot;
import org.usfirst.frc.team4537.robot.RobotMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutoCubeFence extends Command {

	double driveSpeed = 0.4;
	int driveTarget = RobotMap.AUTO_DRIVE_DST;
	int state = 0;
	boolean switchOwned = false;
	SendableChooser<String> stationChooser;
	String station;
	String switchSide;
	int armPosition = -1800; //TODO
	long initTime, stateTime;
	boolean holdArm;
	private int[] encOSet;

	public AutoCubeFence(SendableChooser<String> stationChooser) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.arm);
		requires(Robot.driveBase);
		requires(Robot.grabber);

		this.stationChooser = stationChooser;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.arm.setPneumaticPosition(2);

		initTime = System.currentTimeMillis();
    	encOSet = Robot.driveBase.getEncoderDistances();

		station = stationChooser.getSelected();
		switchSide = DriverStation.getInstance().getGameSpecificMessage();
		switchOwned = (station.charAt(0) == switchSide.charAt(0)) ? true : false;

		state = 0;
		holdArm = true;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if(System.currentTimeMillis()-1000 >= initTime && holdArm) {
//			System.out.println(switchOwned);//XXX

			//Move Arm
			double power = 0.0;
			int encPos = Robot.arm.getEncoderPosition();

			double error = encPos - armPosition;
			power = error/1000.0;
			System.out.println("Error/Power: "+error+" / "+power);//XXX

			power = Math.min(power, 0.8);
			power = Math.max(power, -0.8);
			Robot.arm.driveArm(-power);
		}
		
		switch(state) {
		case 0:
	    	int[] encDists = Robot.driveBase.getEncoderDistances();
//	    	gyroError = Robot.telemetry.getGyroAngle() - gyroTarget;
	    	int encL = encDists[0] - encOSet[0];
	    	int encR = encDists[1] - encOSet[1];
//	    	int encL = -encDists[0] + encOSet[0];
//	    	int encR = -encDists[1] + encOSet[1];
	    	int encoderAvg = (-encL + encR) / 2;
//	    	encoderMismatch = encL - encR;
	    	
//	    	double driveOffset = (encoderMismatch/50000);
	    	double leftSpeed = driveSpeed;// + driveOffset;
	    	double rightSpeed = driveSpeed;// - driveOffset;
	    	Robot.driveBase.setLeftRightMotors(leftSpeed, rightSpeed, true);
			
			//end condition
	    	if(driveTarget - encoderAvg <= 0 || System.currentTimeMillis()-4000 >= initTime) {
	    		stateTime = System.currentTimeMillis();
		    	Robot.driveBase.setLeftRightMotors(0.1, 0.1, true);
	    		state++;
	    	}
			break;
			
		case 1:
			if(switchOwned) {
			Robot.grabber.driveGrabber(-1);
			}
			if(System.currentTimeMillis()-1000 >= stateTime) {
				holdArm = false;
				state++;
			}
			break;
			
		default:
			break;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return (state == 2);
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.grabber.driveGrabber(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
