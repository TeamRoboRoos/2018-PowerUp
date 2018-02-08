//package org.usfirst.frc.team4537.robot.commands;
//
//import org.usfirst.frc.team4537.robot.Robot;
//import org.usfirst.frc.team4537.robot.enums.ArmPositions;
//import edu.wpi.first.wpilibj.command.Command;
//
///**
// *
// */
//public class SetArm extends Command {
//	
//    ArmPositions setPosition;
//    ArmPositions startArmPos;
//    ArmPositions currentArmPos;
//    
//    boolean done = false;
//    int state = 0;
//
//	public SetArm(ArmPositions position) {
//        // Use requires() here to declare subsystem dependencies
//        requires(Robot.arm);
//    	this.setPosition = position;
//    }
//
//    // Called just before this Command runs the first time
//    protected void initialize() {
//    }
//
//    // Called repeatedly when this Command is scheduled to run
//    protected void execute() {
//    	startArmPos = Robot.arm.getArmPosition();
//    	
//    	if (startArmPos == setPosition) {}
//    	else if(startArmPos.value < setPosition.value) {
//    		switch(startArmPos) {
//
//			/**
//			 * Move from m_grab to m_home
//			 */
//    		case m_grab:
//    			switch(state) {
//    			case 0:
//    				Robot.arm.setPneumaticPosition(ArmPositions.p_back);
//    				moveState(false);
//    				break;
//    			case 1:
//    				Robot.arm.setArmPosition(ArmPositions.m_home);
//    				moveState(false);
//    				break;
//    			case 2:
//    				Robot.arm.setPneumaticPosition(ArmPositions.p_upright);
//    				moveState(true);
//    				break;
//    			}
//    			break;
//    		
//    		/**
//    		 * Move from m_home to m_fence 
//    		 */
//    		case m_home:
//    			switch(state) {
//    			case 0:
//    				Robot.arm.setPneumaticPosition(ArmPositions.p_back);
//    				moveState(false);
//    				break;
//    			case 1:
//    				Robot.arm.setArmPosition(ArmPositions.m_home);
//    				moveState(false);
//    				break;
//    			case 2:
//    				Robot.arm.setPneumaticPosition(ArmPositions.p_upright);
//    				moveState(false);
//    				break;
//    			}
//    			break;
//    			
//    		case m_fence:
//    			break;
//    			
//    		case m_scaleLow:
//    			break;
//    			
//    		case m_scaleMid:
//    			break;
//    			
//    		case m_scaleHigh:
//    			break;
//    			
//    		case m_climb: //Would already be here
//    			break; //Therefore do nothing
//    			
//			default:
//				break;
//    		}
//    		
//    		for (int i = startArmPos.value; i <= setPosition.value; i++) {
//    			if (i == 0) {
//    				Robot.arm.setArmPosition(ArmPositions.m_grab);
//    				Robot.arm.setPneumaticPosition(ArmPositions.p_forward);
//    			}
//    			if (i == 1) {
//    				Robot.arm.setArmPosition(ArmPositions.m_home);
//    				Robot.arm.setPneumaticPosition(ArmPositions.p_upright);
//    			}
//    			if (i == 2) {
//    				Robot.arm.setArmPosition(ArmPositions.m_fence);
//    				Robot.arm.setPneumaticPosition(ArmPositions.p_back);
//    			}
//    			if (i == 3) {
//    				Robot.arm.setArmPosition(ArmPositions.m_scaleLow);
//    				Robot.arm.setPneumaticPosition(ArmPositions.p_back);
//    			}
//    			if (i == 4) {
//    				Robot.arm.setArmPosition(ArmPositions.m_scaleMid);
//    				Robot.arm.setPneumaticPosition(ArmPositions.p_back);
//    			}
//    			if (i == 5) {
//    				Robot.arm.setArmPosition(ArmPositions.m_scaleHigh);
//    				Robot.arm.setPneumaticPosition(ArmPositions.p_back);
//    			}
//    			if (i == 6) {
//    				Robot.arm.setArmPosition(ArmPositions.m_climb);
//    				Robot.arm.setPneumaticPosition(ArmPositions.p_upright);
//    			}
//    		}
//    	}
// 
//    	else if (startArmPos.value > setPosition.value) {
//    		for (int i = startArmPos.value; i >= setPosition.value; i++) {
//    			if (i == 0) {
//    				Robot.arm.setArmPosition(ArmPositions.m_grab);
//    				Robot.arm.setPneumaticPosition(ArmPositions.p_forward);
//    			}
//    			if (i == 1) {
//    				Robot.arm.setArmPosition(ArmPositions.m_home);
//    				Robot.arm.setPneumaticPosition(ArmPositions.p_upright);
//    			}
//    			if (i == 2) {
//    				Robot.arm.setArmPosition(ArmPositions.m_fence);
//    				Robot.arm.setPneumaticPosition(ArmPositions.p_back);
//    			}
//    			if (i == 3) {
//    				Robot.arm.setArmPosition(ArmPositions.m_scaleLow);
//    				Robot.arm.setPneumaticPosition(ArmPositions.p_back);
//    			}
//    			if (i == 4) {
//    				Robot.arm.setArmPosition(ArmPositions.m_scaleMid);
//    				Robot.arm.setPneumaticPosition(ArmPositions.p_back);
//    			}
//    			if (i == 5) {
//    				Robot.arm.setArmPosition(ArmPositions.m_scaleHigh);
//    				Robot.arm.setPneumaticPosition(ArmPositions.p_back);
//    			}
//    			if (i == 6) {
//    				Robot.arm.setArmPosition(ArmPositions.m_climb);
//    				Robot.arm.setPneumaticPosition(ArmPositions.p_upright);
//    			}
//    		}
//    	}
//    }
//
//    // Make this return true when this Command no longer needs to run execute()
//    protected boolean isFinished() {
//        return (startArmPos == setPosition);
//    }
//
//    // Called once after isFinished returns true
//    protected void end() {
//    }
//
//    // Called when another command which requires one or more of the same
//    // subsystems is scheduled to run
//    protected void interrupted() {
//    }
//    
//    private void moveState(boolean last) {
//		if(!Robot.arm.getArmMoving() && !Robot.arm.getPneumaticsMoving()) {
//			if(!last) {
//				state++;
//			} else {
//				state = 0;
//				done = true;
//			}
//		}
//	}
//    
//}
