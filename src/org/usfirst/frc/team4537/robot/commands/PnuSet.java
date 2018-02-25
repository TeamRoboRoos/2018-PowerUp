package org.usfirst.frc.team4537.robot.commands;

import org.usfirst.frc.team4537.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PnuSet extends Command {

	int pos;
	
    public PnuSet(int pos) {
        //Use requires() here to declare subsystem dependencies
        //requires(chassis);
    	this.pos = pos;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	int armPos = Robot.arm.curPnuPos;
    	
    	boolean move = true;
    	
    	if(armPos == 1 && pos == 0) {
    		move = false;
    		if(Robot.arm.getEncoderPosition() <= -600) {
    			move = true;
    		}
    	}
    	if(armPos == 0 && pos == 1) {
    		move = false;
    		if(Robot.arm.getEncoderPosition() <= -600) {
    			move = true;
    		}
    	}
    	
    	if(move) {
    		if(!((armPos == 0 && pos == 2) || (armPos == 2 && pos == 0))) {
    			Robot.arm.setPneumaticPosition(pos);
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
