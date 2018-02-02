package org.usfirst.frc.team4537.robot.commands;

import org.usfirst.frc.team4537.robot.Robot;
import org.usfirst.frc.team4537.robot.enums.LEDCodes;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetLights extends Command {

	boolean mirror;
	int[] vStrips;
	LEDCodes[] animationsArray;
	LEDCodes[] coloursArray;
	LEDCodes animation;
	LEDCodes colour;
	
    public SetLights(int[] vStrips, LEDCodes[] animations, LEDCodes[] colours) {
		setRunWhenDisabled(true);
		this.vStrips = vStrips;
		this.animationsArray = animations;
		this.coloursArray = colours;
		this.mirror = false;
    }
    
    public SetLights(int[] vStrips, LEDCodes animation, LEDCodes colour) {
    	setRunWhenDisabled(true);
		this.vStrips = vStrips;
		this.animation = animation;
		this.colour = colour;
		this.mirror = true;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	for(int i=0; i < vStrips.length; i++) {
    		if(!mirror) {
    			Robot.arduino.setLights(LEDCodes.m_animation, vStrips[i], animationsArray[i]);
    			Robot.arduino.setLights(LEDCodes.m_colour, vStrips[i], coloursArray[i]);
    		} else {
    			Robot.arduino.setLights(LEDCodes.m_animation, vStrips[i], animation);
    			Robot.arduino.setLights(LEDCodes.m_colour, vStrips[i], colour);
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
