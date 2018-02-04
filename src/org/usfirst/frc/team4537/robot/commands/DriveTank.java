package org.usfirst.frc.team4537.robot.commands;

import org.usfirst.frc.team4537.robot.Robot;
import org.usfirst.frc.team4537.robot.RobotMap;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveTank extends Command {

	public DriveTank() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveBase);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double left = -Robot.oi.getDriveRawAxis(1) *1* RobotMap.DRIVE_OPP_LIMIT;
		double right = -Robot.oi.getDriveRawAxis(5) *1* RobotMap.DRIVE_OPP_LIMIT;

		Robot.driveBase.tankDrive(left, right, true);
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
		Robot.driveBase.profileLeft.reset();
	}
}
