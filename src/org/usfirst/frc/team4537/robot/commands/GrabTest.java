package org.usfirst.frc.team4537.robot.commands;

import org.usfirst.frc.team4537.robot.Robot;
import org.usfirst.frc.team4537.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class GrabTest extends Command {

    public GrabTest() {
        // Use requires() here to declare subsystem dependencies
//        requires(Robot.driveBase);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double pow = SmartDashboard.getNumber("GrabberTestRun", 0.0);
//    	Robot.grabber.driveGrabbersRaw(pow, pow);
    	Robot.driveBase.setLeftRightMotors(pow, pow, false);
    	System.out.println(pow);
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
