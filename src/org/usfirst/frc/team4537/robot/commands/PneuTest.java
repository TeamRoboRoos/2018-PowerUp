package org.usfirst.frc.team4537.robot.commands;

import org.usfirst.frc.team4537.robot.Robot;
import org.usfirst.frc.team4537.robot.enums.ArmPositions;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PneuTest extends Command {

	ArmPositions pos;
	
    public PneuTest(ArmPositions pos) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
		this.pos = pos;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//    	Robot.arm.setPneumaticPosition(pos);//FIXME
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
