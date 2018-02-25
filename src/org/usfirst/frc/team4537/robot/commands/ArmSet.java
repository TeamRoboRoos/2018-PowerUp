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
    	double power = Robot.oi.getOperateRawAxis(RobotMap.CONTROL_DRIVE_0_Y);
    	power *= 0.8;
    	
    	boolean outOfBounds = false;
//    	if(pnuPos == )
    	
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
