package org.usfirst.frc.team4537.robot.commands;

import org.usfirst.frc.team4537.robot.Robot;
import org.usfirst.frc.team4537.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RecordData extends Command {

	private DriverStation ds = DriverStation.getInstance();
	
    public RecordData() {
    	setRunWhenDisabled(true);
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.telemetry);
    }
    

    // Called just before this Command runs the first time
    protected void initialize() {
    	SmartDashboard.putNumber("Arm_DrivePW", 0.0);
    	SmartDashboard.putNumber("Drive_PW", 0.0);
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	int[] enc = Robot.driveBase.getEncoderDistances();
    	int[] vel = Robot.driveBase.getEncoderVelocities();
    	
    	if(Robot.telemetry.getUserButton()) {
    		if(!Robot.driveBase.getAllNeutralMode()) {
    			Robot.driveBase.setAllNeutralMode(NeutralMode.Coast);
    		}
    	}

    	//Subsystems
    	SmartDashboard.putData("DriveBase", Robot.driveBase);
		SmartDashboard.putData("Arm", Robot.arm);
		SmartDashboard.putData("Grabber", Robot.grabber);
		SmartDashboard.putData("Telemetry", Robot.telemetry);
		SmartDashboard.putData("Arduino", Robot.arduino);
		SmartDashboard.putData("Scheduler", Scheduler.getInstance());
		
		//Encoders & Velocity
    	SmartDashboard.putNumber("encL", enc[0]);
    	SmartDashboard.putNumber("encR", enc[1]);
    	SmartDashboard.putNumber("velL", vel[0]);
    	SmartDashboard.putNumber("velR", vel[1]);
    	
    	//Drive stuff
    	double dpw = SmartDashboard.getNumber("Drive_PW", 0.0);
//    	Robot.driveBase.set	RightMotor(dpw, true);
    	SmartDashboard.putNumber("Drive_SP", dpw*3702);
    	
    	//Game data and time
    	SmartDashboard.putNumber("Time Remaining", ds.getMatchTime());
    	SmartDashboard.putString("GameData", ds.getGameSpecificMessage());
    	
    	//User input
    	SmartDashboard.putBoolean("UserButton", Robot.telemetry.getUserButton());
    	SmartDashboard.putNumber("JC POV", Robot.oi.getControlOperate().getPOV(0));
    	
    	//Pressure
    	SmartDashboard.putNumber("PressureV", Robot.arm.pressureGet());

    	//Arm stuff
    	SmartDashboard.putBoolean("CylTop", Robot.arm.getPnuBools()[0]);
    	SmartDashboard.putBoolean("CylBottom", Robot.arm.getPnuBools()[1]);
    	SmartDashboard.putNumber("Arm_Quad", Robot.arm.armMotor.getSensorCollection().getQuadraturePosition());
    	SmartDashboard.putNumber("Arm_Pulse", Robot.arm.armMotor.getSensorCollection().getPulseWidthPosition());
    	SmartDashboard.putNumber("Arm_SetPT", Robot.arm.armSetPoint);
    	
//    	Robot.arm.setArmPosition((int)SmartDashboard.getNumber("Arm_DrivePW", 0.0));
    	
    	SmartDashboard.putNumber("Arm_Cur", Robot.arm.armPositioner.curPosition.index);
    	SmartDashboard.putNumber("Arm_Set", Robot.arm.armPositioner.setPosition.index);
    	SmartDashboard.putNumber("Arm_Mov", Robot.arm.armPositioner.curPosMovingTo);
    	SmartDashboard.putBoolean("Arm_Done", Robot.arm.armPositioner.done);
    	SmartDashboard.putNumber("Arm_Step", Robot.arm.armPositioner.step);
    	SmartDashboard.putNumber("Arm_Loop", Robot.arm.armPositioner.loopCnt);
    	SmartDashboard.putBoolean("Arm_Comp", Robot.arm.armPositioner.complete);
    	SmartDashboard.putNumber("Arm_NXT", Robot.arm.armPositioner.nextPosTmp);
    	SmartDashboard.putBoolean("ARM_DIR", Robot.arm.armPositioner.dirTemp);
    	
    	//Logger
    	Robot.telemetry.logger.updateSensor("encL", enc[0]);
    	Robot.telemetry.logger.updateSensor("encR", enc[1]);
    	Robot.telemetry.logger.updateSensor("velL", vel[0]);
    	Robot.telemetry.logger.updateSensor("velR", vel[1]);
    	
    	Robot.telemetry.logger.updateSensor("btn1", Robot.oi.getDriveRawButton(1));

    	if(RobotMap.LOGGER_ENABLE) {
        	Robot.telemetry.logger.log();
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
