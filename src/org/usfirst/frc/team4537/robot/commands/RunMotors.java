package org.usfirst.frc.team4537.robot.commands;

import org.usfirst.frc.team4537.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RunMotors extends Command {

	double speedRun = 0.6;
	double speed;
	int state = 0;
	double delaySwap = 15000; //15s
	double delayStop = 1000; //1s
	double delay;
	long lastTime;
	
    public RunMotors() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveBase);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	lastTime = System.currentTimeMillis();
    	delay = delaySwap;
    	speed = speedRun;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveBase.setLeftRightMotors(speed, -speed, false);
    	
    	if(System.currentTimeMillis()-delay >= lastTime) {
    		switch(state) {
    		case 0:
    			speed = speedRun;
    			state = 1;
    			delay = delaySwap;
    			break;
    		case 1:
    			speed = 0.0;
    			state = 2;
    			delay = delayStop;
    			break;
    		case 2:
    			speed = -speedRun;
    			state = 3;
    			delay = delaySwap;
    			break;
    		case 3:
    			speed = 0.0;
    			state = 0;
    			delay = delayStop;
    			break;
    		}
    		lastTime = System.currentTimeMillis();
    	}
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
