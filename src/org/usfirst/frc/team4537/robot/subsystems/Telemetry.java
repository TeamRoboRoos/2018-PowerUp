package org.usfirst.frc.team4537.robot.subsystems;

import org.usfirst.frc.team4537.robot.commands.RecordData;

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
			"velR"
	};
	
    public Telemetry() {
    	logger = new Logger(sensors);
    }
	
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new RecordData());
    }
}

