package org.usfirst.frc.team4537.robot.commands;

import org.usfirst.frc.team4537.robot.Robot;
import org.usfirst.frc.team4537.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ArmSet extends Command {
	
    public ArmSet() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.arm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.arm.setPneumaticPosition(1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//    	boolean holdMode = false;
    	int encPos = Robot.arm.getEncoderPosition();
    	double power = Robot.oi.getOperateRawAxis(RobotMap.CONTROL_DRIVE_0_Y);
//    	System.out.println("Drive: "+power);//XXX
//    	if(power <= 0.1 && power >= -0.1) {
//    		holdMode = true;
//    	}
    	power *= 0.9;
    	
//    	boolean outOfBounds = false;
//    	if(pnuPos == )
    	
//    	if(holdMode) {
//    		double error = encPos - Robot.arm.armHoldPos;
//    		System.out.println("Error: "+error+" / "+error/1000);
//    		power = -error/1000;
//    	} else {
//    		Robot.arm.armHoldPos = encPos;
//    		
//    		if(encPos <= 10 && power > 0) {
//    			power = 0.0;
//    		}
//    		if(power > 0) {
//    			power *= 0.4;
//    		}
//    	}
    	
    	if(!(Robot.oi.getOperateRawAxis(3)<=-0.75)) {
    		if(encPos <= -3300) {
    			int error = encPos+3300;
    			power = -error/500.0;
    			System.out.println(error+" / "+power);
    		}
    	}
    	
//		System.out.println("Hold: "+holdMode+" Power: "+power);//XXX
    	
    	Robot.arm.driveArm(power);
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
