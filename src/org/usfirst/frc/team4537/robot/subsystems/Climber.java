package org.usfirst.frc.team4537.robot.subsystems;

import org.usfirst.frc.team4537.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem {
	private TalonSRX climbMotor1;
	private TalonSRX climbMotor2;
	
	
	public Climber() {
		climbMotor1 = new TalonSRX(RobotMap.CAN_MOTOR_CLIMB_1);
		climbMotor2 = new TalonSRX(RobotMap.CAN_MOTOR_CLIMB_2);

	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void climb(boolean direction) {
    	if (direction == true) {
    		climbMotor1.set(ControlMode.PercentOutput, RobotMap.CLIMB_SPEED);
    		climbMotor2.set(ControlMode.PercentOutput, RobotMap.CLIMB_SPEED);
    	}
    else if(direction == false) {
    	climbMotor1.set(ControlMode.PercentOutput, -RobotMap.CLIMB_SPEED);
    	climbMotor2.set(ControlMode.PercentOutput, -RobotMap.CLIMB_SPEED);
    	}
    }
}

