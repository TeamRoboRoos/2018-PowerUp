package org.usfirst.frc.team4537.robot.commands;

import org.usfirst.frc.team4537.robot.Robot;
import org.usfirst.frc.team4537.robot.enums.ArmPositions;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetArm extends Command {
	
    ArmPositions setPosition;
    ArmPositions currentPosition;

	public SetArm(ArmPositions position) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.setPosition = position;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	currentPosition = Robot.arm.getArmPosition();
    	
    	if (currentPosition == setPosition) {
    		
    	}
    	
    	else if (currentPosition.value < setPosition.value) {
    		for (int i = currentPosition.value; i <= setPosition.value; i++) {
    			if (i == 0) {
    				Robot.arm.setArmPosition(ArmPositions.m_grab);
    				Robot.arm.setPneumaticPosition(ArmPositions.p_forward);
    			}
    			if (i == 1) {
    				Robot.arm.setArmPosition(ArmPositions.m_home);
    				Robot.arm.setPneumaticPosition(ArmPositions.p_upright);
    			}
    			if (i == 2) {
    				Robot.arm.setArmPosition(ArmPositions.m_fence);
    				Robot.arm.setPneumaticPosition(ArmPositions.p_back);
    			}
    			if (i == 3) {
    				Robot.arm.setArmPosition(ArmPositions.m_scaleLow);
    				Robot.arm.setPneumaticPosition(ArmPositions.p_back);
    			}
    			if (i == 4) {
    				Robot.arm.setArmPosition(ArmPositions.m_scaleMid);
    				Robot.arm.setPneumaticPosition(ArmPositions.p_back);
    			}
    			if (i == 5) {
    				Robot.arm.setArmPosition(ArmPositions.m_scaleHigh);
    				Robot.arm.setPneumaticPosition(ArmPositions.p_back);
    			}
    			if (i == 6) {
    				Robot.arm.setArmPosition(ArmPositions.m_climb);
    				Robot.arm.setPneumaticPosition(ArmPositions.p_upright);
    			}
    		}
    		switch(currentPosition) {
    		case m_grab:
    			
    			break;
    		case m_home:
    			break;
    		case m_fence:
    			break;
    		case m_scaleLow:
    			break;
    		case m_scaleMid:
    			break;
    		case m_scaleHigh:
    			break;
    		case m_climb: //Would already be here
    			break;
			default:
				break;
    		
    		}
    	}
 
    	else if (currentPosition.value > setPosition.value) {
    		for (int i = currentPosition.value; i >= setPosition.value; i++) {
    			if (i == 0) {
    				Robot.arm.setArmPosition(ArmPositions.m_grab);
    				Robot.arm.setPneumaticPosition(ArmPositions.p_forward);
    			}
    			if (i == 1) {
    				Robot.arm.setArmPosition(ArmPositions.m_home);
    				Robot.arm.setPneumaticPosition(ArmPositions.p_upright);
    			}
    			if (i == 2) {
    				Robot.arm.setArmPosition(ArmPositions.m_fence);
    				Robot.arm.setPneumaticPosition(ArmPositions.p_back);
    			}
    			if (i == 3) {
    				Robot.arm.setArmPosition(ArmPositions.m_scaleLow);
    				Robot.arm.setPneumaticPosition(ArmPositions.p_back);
    			}
    			if (i == 4) {
    				Robot.arm.setArmPosition(ArmPositions.m_scaleMid);
    				Robot.arm.setPneumaticPosition(ArmPositions.p_back);
    			}
    			if (i == 5) {
    				Robot.arm.setArmPosition(ArmPositions.m_scaleHigh);
    				Robot.arm.setPneumaticPosition(ArmPositions.p_back);
    			}
    			if (i == 6) {
    				Robot.arm.setArmPosition(ArmPositions.m_climb);
    				Robot.arm.setPneumaticPosition(ArmPositions.p_upright);
    			}
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (currentPosition == setPosition);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
