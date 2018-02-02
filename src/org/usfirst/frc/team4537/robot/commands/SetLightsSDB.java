package org.usfirst.frc.team4537.robot.commands;

import org.usfirst.frc.team4537.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SetLightsSDB extends Command {

	public SetLightsSDB() {
		setRunWhenDisabled(true);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		int[] cmds;
		if(SmartDashboard.getNumber("Mode", 0) == 0) {
			cmds = new int[] {
					(int)SmartDashboard.getNumber("Mode", 0),
					(int)SmartDashboard.getNumber("vStrip", 0),
					(int)SmartDashboard.getNumber("Animation", 0),
			};
		} else {
			cmds = new int[] {
					(int)SmartDashboard.getNumber("Mode", 0),
					(int)SmartDashboard.getNumber("vStrip", 0),
					(int)SmartDashboard.getNumber("R", 0),
					(int)SmartDashboard.getNumber("G", 0),
					(int)SmartDashboard.getNumber("B", 0)
			};

		}
		for(int i : cmds) {
			Robot.arduino.writeInt(i);
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
