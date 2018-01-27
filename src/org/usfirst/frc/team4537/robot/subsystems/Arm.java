package org.usfirst.frc.team4537.robot.subsystems;

import org.usfirst.frc.team4537.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Arm extends Subsystem {
	
	private TalonSRX armMotor;
	private Compressor compressor;
	private AnalogInput pressureSens;
	private Solenoid solBottom;
	private Solenoid solTop;
	
	private boolean compressorEnabled = true;

	public Arm() {
		armMotor = new TalonSRX(0);
		compressor = new Compressor(RobotMap.CAN_PCM_0);
		pressureSens = new AnalogInput(RobotMap.ANI_PRESSURE);
		solBottom = new Solenoid(RobotMap.CAN_PCM_0, RobotMap.PCM_ARM_BOTTOM);
		solTop = new Solenoid(RobotMap.CAN_PCM_0, RobotMap.PCM_ARM_TOP);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void compressorEnabled(boolean enabled) {
    	if(enabled) {
    		compressor.start();
    		compressorEnabled = true;
    	} else {
    		compressor.stop();
    		compressorEnabled = false;
    	}
    }
    
    public boolean compressorGetEnabled() {
    	return compressorEnabled;
    }
    
    public boolean compressorGetRunning() {
    	return compressor.enabled();
    }
    
    public double compressorGetCurrent() {
    	return compressor.getCompressorCurrent();
    }
    
    public boolean pressureGetLow() {
    	return compressor.getPressureSwitchValue();
    }
    
//    public double pressureGet() {
//    	return 
//    }
}

