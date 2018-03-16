/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4537.robot;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;

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
	
	//Drive parameters
	//Arcade drive velocity control? true = %velocity, false = %output
	public static final boolean DRIVE_VEL = true;
	//Operator speed limit
	public static final double DRIVE_OPP_LIMIT = 1.0;
	
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
	//Positions
	public static final int CONTROL_BUTTON_LOAD_POS = 5;
	public static final int CONTROL_BUTTON_HOME = 6;
	public static final int CONTROL_BUTTON_FENCE = 2;
	public static final int CONTROL_BUTTON_SCALE_LOW = 3;
	public static final int CONTROL_BUTTON_SCALE_NEUTRAL = 1;
	public static final int CONTROL_BUTTON_SCALE_HIGH = 4;
	public static final int CONTROL_BUTTON_CLIMB_POS = 9;
	//Grabbers
	public static final int CONTROL_BUTTON_CLIMB_UP = 1;
	public static final int CONTROL_BUTTON_CLIMB_DOWN = 1;
	//Climber
	public static final int CONTROL_BUTTON_GRAB_IN = 1;
	public static final int CONTROL_BUTTON_GRAB_OUT = 1;
	
	//Camera server variables
	public static final String[] CAM_NAMES = {"Front"};//, "Gear"};
	public static final String[] CAM_PATHS = {"/dev/video0"};//, "/dev/video1"};
	public static final int[] CAM_RESOLUTION = {320, 240};  //{Width, Height}
	public static final int CAM_FPS = 30;
	public static final int CAM_EX = 50;
	public static final int CAM_WB = 50;
	
	//CAN IDs
	//TalonSRX IDs
	//Drive motors
	public static final int CAN_MOTOR_DL_1 = 1;
	public static final int CAN_MOTOR_DL_2 = 2;
	public static final int CAN_MOTOR_DL_3 = 3;
	public static final int CAN_MOTOR_DR_4 = 4;
	public static final int CAN_MOTOR_DR_5 = 5;
	public static final int CAN_MOTOR_DR_6 = 6;
	//Arm motors
	public static final int CAN_MOTOR_ARM_1 = 9;
	public static final int CAN_MOTOR_ARM_2 = 10;
	//Climb motors
	public static final int CAN_MOTOR_CLIMB_1 = 7;
	public static final int CAN_MOTOR_CLIMB_2 = 8;
	//Module IDs
	public static final int CAN_PDP_0 = 0;
	public static final int CAN_PCM_0 = 11;
	
	//PWM IDs
	//Grabbers
	public static final int PWM_MOTOR_GRAB_L = 0;
	public static final int PWM_MOTOR_GRAB_R = 1;
	
	//PCM Ports
	public static final int PCM_ARM_BOTTOM = 0;
	public static final int PCM_ARM_TOP = 1;
	
	//Pressure Voltage at 0psi 
	public static final double PRES_V = 0.469970655;
	
	//Setups
	//Arm setup
//	Code?
	//Grabber setup
	public static final double GRAB_SPEED = 0.5;
	public static final double GRAB_EJECT = -1.0;
	//Climber setup
	public static final double CLIMB_SPEED = 1.0;
	//Robot Maximum Speed
	public static final int ROBOT_MAX_SPEED = 3573; //Encoder ticks per 100ms
	//Arm zero Position, AKA home
	public static final int ROBOT_HOME_ENC = 1788; //Encoder home position
	
	//Arm Encoder Limits
	public static final int THRESHOLD_REV_FORWARD = -554;
	public static final int THRESHOLD_FWD_FORWARD = -295;
	public static final int THRESHOLD_REV_VERTICAL = -734;
	public static final int THRESHOLD_FWD_VERTICAL = 0;
	public static final int THRESHOLD_REV_VERTICAL2 = -734;
	public static final int THRESHOLD_FWD_VERTICAL2 = 0;
	public static final int THRESHOLD_REV_BACK = -3454;
	public static final int THRESHOLD_FWD_BACK = 0;
	
	//Autonomous
	public static final int AUTO_DRIVE_DST = 23100;//(int)(8114*3.05); //26204 Encoder Ticks
	
	
	//Digital and Analog ports
	//Digital
	public static final int DGI_CYL_TOP_EXT = 7; //Red
	public static final int DGI_CYL_TOP_RET = 8; //Yellow
	public static final int DGI_CYL_BTM_EXT = 6; //Blue
	public static final int DGI_CYL_BTM_RET = 9; //Black
	//Analog
	public static final int ANI_PRESSURE = 0;
	
	//Arduino Configuration
	//Port
	public static final SerialPort.Port ARD_PORT = SerialPort.Port.kMXP;
	//Baud Rate
	public static final int ARD_BAUDR = 9600;
	
	//NavX Setup
	//Port
	public static final SPI.Port NAVX_PORT = SPI.Port.kMXP;
}
