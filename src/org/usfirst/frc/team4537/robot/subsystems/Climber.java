package org.usfirst.frc.team4537.robot.subsystems;

import org.usfirst.frc.team4537.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem {
	
	private TalonSRX climbMaster;
	private TalonSRX climbSlave;
	
	public Climber() {
		climbMaster = new TalonSRX(RobotMap.CAN_MOTOR_CLIMB_1);
		climbMaster.set(ControlMode.PercentOutput, 0.0);
		climbMaster.setInverted(true);
		
		climbSlave = new TalonSRX(RobotMap.CAN_MOTOR_CLIMB_2);
		climbSlave.set(ControlMode.Follower, climbMaster.getDeviceID());
		climbSlave.setInverted(climbMaster.getInverted());
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void climb(int direction) {
    	climbMaster.set(ControlMode.PercentOutput, RobotMap.CLIMB_SPEED * direction);
    }
}

