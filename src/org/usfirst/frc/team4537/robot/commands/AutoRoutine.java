package org.usfirst.frc.team4537.robot.commands;

import org.usfirst.frc.team4537.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutoRoutine extends Command {

	private String fieldData;
	private int stationNumber;

	
    public AutoRoutine() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveBase);
    	requires(Robot.arm);
    	requires(Robot.grabber);
    	requires(Robot.telemetry);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	stationNumber = (int)SmartDashboard.getNumber("stationChooser", 0);
    	fieldData = DriverStation.getInstance().getGameSpecificMessage();
    	
    	SmartDashboard.putString("fieldConfiguration", fieldData);
    
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	int state;
    	
    	// Deals with where the robot is starting at
    	switch(stationNumber) {
    	// If in player station/starting position 1
    	case 1:
    		
    		// Deals with the configuration of the field
    		switch(fieldData) {
    		// If field is in [Left, Right, Left] configuration
    		case "LRL":
    		
    			break;
    		// If field is in [Right, Left, Right) configuration
    		case "RLR":
    		
    			break;
    		// If field is in [Left, Left, Left] configuration
    		case "LLL":
    			
    			break;
        	// If field is in [Right, Right, Right) configuration
    		case "RRR":
    			
    			break;
    		}
    		
    		break;
    	
    	// If in player station/starting position 1
    	case 2:
    		
    		// Deals with the configuration of the field
    		switch(fieldData) {
    		// If field is in [Left, Right, Left] configuration
    		case "LRL":
    		
    			break;
    		// If field is in [Right, Left, Right) configuration
    		case "RLR":
    		
    			break;
    		// If field is in [Left, Left, Left] configuration
    		case "LLL":
    			
    			break;
        	// If field is in [Right, Right, Right) configuration
    		case "RRR":
    			
    			break;
    		}
    		
    		break;
    	
    	// If in player station/starting position 1
    	case 3:
    		
    		// Deals with the configuration of the field
    		switch(fieldData) {
    		// If field is in [Left, Right, Left] configuration
    		case "LRL":
    		
    			break;
    		// If field is in [Right, Left, Right) configuration
    		case "RLR":
    		
    			break;
    		// If field is in [Left, Left, Left] configuration
    		case "LLL":
    			
    			break;
        	// If field is in [Right, Right, Right) configuration
    		case "RRR":
    			
    			break;
    		}
    		
    		break ;
    	
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
    
    public void setFieldData(String fieldData) {
    	this.fieldData = fieldData;
    }
    
    public void setStationNumber(int stationNumber) {
    	this.stationNumber = stationNumber;
    }
    
}
