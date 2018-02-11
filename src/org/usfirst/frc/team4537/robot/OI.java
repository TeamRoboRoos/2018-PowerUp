/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4537.robot;

import org.usfirst.frc.team4537.robot.commands.*;
import org.usfirst.frc.team4537.robot.enums.ArmPosition;
import org.usfirst.frc.team4537.robot.enums.LEDCodes;
import org.usfirst.frc.team4537.robot.triggers.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
	
	private Joystick controlDrive; //14 13
	private Joystick controlOperate;
	private EndGame endGame;
//	private ControlPOV0 controlPOV0;
//	private ControlPOV90 controlPOV90;
//	private ControlPOV180 controlPOV180;
//	private ControlPOV270 controlPOV270;
	
	//PS4 buttons
	private JoystickButton loadPos, home, fence, scaleLow, scaleNeutral, scaleHigh, climbPos;
	private JoystickButton loadCube, placeCube;
	private JoystickButton climbUp, climbDown;
	
	public OI() {
		controlDrive = new Joystick(RobotMap.CONTROL_DRIVE_0);
		controlOperate = new Joystick(RobotMap.CONTROL_OPERATE_0);
		endGame = new EndGame();
		endGame.whenActive(new SetLights(new int[]{0,1,2,3}, LEDCodes.a_blink, LEDCodes.c_orange));
		
		//Buttons for the ps4 controller
		loadPos = new JoystickButton(controlOperate, RobotMap.CONTROL_BUTTON_LOAD_POS);
		loadPos.whenPressed(new SetArm2(ArmPosition.load));
		home = new JoystickButton(controlOperate, RobotMap.CONTROL_BUTTON_HOME);
		home.whenPressed(new SetArm2(ArmPosition.home));
		fence = new JoystickButton(controlOperate, RobotMap.CONTROL_BUTTON_FENCE);
		fence.whenPressed(new SetArm2(ArmPosition.fence));
		scaleLow = new JoystickButton(controlOperate, RobotMap.CONTROL_BUTTON_SCALE_LOW);
		scaleLow.whenPressed(new SetArm2(ArmPosition.scaleLowMid));
		scaleNeutral = new JoystickButton(controlOperate, RobotMap.CONTROL_BUTTON_SCALE_NEUTRAL);
//		scaleNeutral.whenPressed(new SetArm2(ArmPosition.scaleMid)); not done in armposition?
		scaleHigh = new JoystickButton(controlOperate, RobotMap.CONTROL_BUTTON_SCALE_HIGH);
		scaleHigh.whenPressed(new SetArm2(ArmPosition.scaleHigh));
		climbPos = new JoystickButton(controlOperate, RobotMap.CONTROL_BUTTON_CLIMB_POS);
//		climbPos.whenPressed(new SetArm2(ArmPosition.climb)); not done in armposition?

		
//		loadCube = new JoystickButton(controlOperate, RobotMap.CONTROL_BUTTON_GRAB_IN);
//		loadCube.whileHeld(new RunGrabber(1)); //In
//		controlPOV90 = new ControlPOV90();
//		controlPOV90.whileActive(new RunGrabber(1));
//		placeCube = new JoystickButton(controlOperate, RobotMap.CONTROL_BUTTON_GRAB_OUT);
//		placeCube.whileHeld(new RunGrabber(-1)); //Out
//		controlPOV270 = new ControlPOV270();
//		controlPOV270.whileActive(new RunGrabber(-1));
		
//		climbUp = new JoystickButton(controlOperate, RobotMap.CONTROL_BUTTON_CLIMB_UP);
//		climbUp.whileHeld(new Climb(1)); //Climb up
//		controlPOV0 = new ControlPOV0();
//		controlPOV0.whileActive(new Climb(1));
//		climbDown = new JoystickButton(controlOperate, RobotMap.CONTROL_BUTTON_CLIMB_DOWN);
//		climbDown.whileHeld(new Climb(-1)); //Climb down
//		controlPOV180 = new ControlPOV180();
//		controlPOV180.whileActive(new Climb(-1));
	}
	
	public double getDriveRawAxis(int axis) {
		return controlDrive.getRawAxis(axis);
	}
	
	public boolean getDriveRawButton(int button) {
		return controlDrive.getRawButton(button);
	}
	public Joystick getControlOperate() {
		return controlOperate;
	}
}
