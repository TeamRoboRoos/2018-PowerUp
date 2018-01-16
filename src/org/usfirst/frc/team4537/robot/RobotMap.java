/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4537.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	
	//Controller IDs and axis mappings
	//Drive controller
	public static final int CONTROL_DRIVE_0 = 0;
	public static final int CONTROL_DRIVE_0_X = 0;
	public static final int CONTROL_DRIVE_0_Y = 1;
	public static final int CONTROL_DRIVE_0_Z = 2;
	public static final int CONTROL_DRIVE_0_T = 3;
	//Operator controller
	public static final int CONTROL_OPERATE_0 = 1;
	public static final int CONTROL_OPERATE_0_X0 = 0;
	public static final int CONTROL_OPERATE_0_Y0 = 1;
	public static final int CONTROL_OPERATE_0_X1 = 2;
	public static final int CONTROL_OPERATE_0_Y1 = 3;
	
	//Controller button mappings
	//Drive controller
	//Operator controller
	
	//CAN IDs
	//TalonSRX IDs
	public static final int CAN_MOTOR_DL_1 = 1;
	public static final int CAN_MOTOR_DL_2 = 2;
	public static final int CAN_MOTOR_DL_3 = 3;
	public static final int CAN_MOTOR_DR_4 = 4;
	public static final int CAN_MOTOR_DR_5 = 5;
	public static final int CAN_MOTOR_DR_6 = 6;
	//Module IDs
	public static final int CAN_PDP_0 = 0;
	public static final int CAN_PCM_0 = 7;
	
	//PCM Ports
	public static final int PCM_ARM_0 = 0;
	public static final int PCM_ARM_1 = 1;
	
	//Robot Maximum Speed
	public static final int ROBOT_MAX_SPEED = 3702;
}
