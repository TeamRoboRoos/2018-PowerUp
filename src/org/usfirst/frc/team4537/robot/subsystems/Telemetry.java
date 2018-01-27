package org.usfirst.frc.team4537.robot.subsystems;

import org.usfirst.frc.team4537.robot.RobotMap;
import org.usfirst.frc.team4537.robot.commands.RecordData;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.command.Subsystem;
import utilities.Logger;

/**
 *
 */
public class Telemetry extends Subsystem {

	public Logger logger;
	private String[] sensors = {
			"encL",
			"encR",
			"velL",
			"velR",
			"btn1"
	};
	
    public Telemetry() {
    	logger = new Logger(sensors, RobotMap.LOGGER_DELAY, RobotMap.LOGGER_ENABLE);
    }
	
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new RecordData());
    }
	
	public boolean getUserButton() {
		return RobotController.getUserButton();
	}
}

