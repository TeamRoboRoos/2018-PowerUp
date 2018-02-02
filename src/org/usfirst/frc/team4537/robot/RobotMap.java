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
	
	//Logger
	public static final boolean LOGGER_ENABLE = false;
	public static final int LOGGER_DELAY = 10;
	
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
	
	//Camera server variables
	public static final String[] CAM_NAMES = {"Front"};//, "Gear"};
	public static final String[] CAM_PATHS = {"/dev/video0"};//, "/dev/video1"};
	public static final int[] CAM_RESOLUTION = {160, 120};  //{Width, Height}
	public static final int CAM_FPS = 30;
	public static final int CAM_EX = 50;
	public static final int CAM_WB = 50;
	
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
	public static final int PCM_ARM_BOTTOM = 0;
	public static final int PCM_ARM_TOP = 1;
	
	//Robot Maximum Speed
	public static final int ROBOT_MAX_SPEED = 3702;
	
	//Digital and Analog ports
	public static final int ANI_PRESSURE = 0;
	
	//Serial Configuration
	public static final SerialPort.Port ARD_PORT = SerialPort.Port.kMXP;
	public static final int ARD_BAUDR = 9600;
}
