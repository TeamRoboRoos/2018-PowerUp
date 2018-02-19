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
	
	//PS4 buttons
	private JoystickButton loadCube, placeCube;
	private JoystickButton climbUp, climbDown;
	private JoystickButton pnuFwd, pnuVet, pnuRev;
	
	private JoystickButton reverseDirection;
	
	public OI() {
		controlDrive = new Joystick(RobotMap.CONTROL_DRIVE_0);
		controlOperate = new Joystick(RobotMap.CONTROL_OPERATE_0);
		endGame = new EndGame();
		endGame.whenActive(new SetLights(new int[]{0,1,2,3}, LEDCodes.a_blink, LEDCodes.c_orange));
		
		loadCube = new JoystickButton(controlDrive, 16);
		loadCube.whileHeld(new RunGrabber(1)); //In
		placeCube = new JoystickButton(controlDrive, 11);
		placeCube.whileHeld(new RunGrabber(-1)); //Out
		
		climbUp = new JoystickButton(controlOperate, 6);
		climbUp.whileHeld(new Climb(1));
		climbDown = new JoystickButton(controlOperate, 4);
		climbDown.whileHeld(new Climb(-1));
		
		loadCube = new JoystickButton(controlOperate, 3);
		loadCube.whileHeld(new RunGrabber(1));
		placeCube = new JoystickButton(controlOperate, 5);
		placeCube.whileHeld(new RunGrabber(-1));
		
		pnuFwd = new JoystickButton(controlOperate, 7);
		pnuFwd.whenPressed(new PnuSet(0));
		pnuVet = new JoystickButton(controlOperate, 9);
		pnuVet.whenPressed(new PnuSet(1));
		pnuRev = new JoystickButton(controlOperate, 11);
		pnuRev.whenPressed(new PnuSet(2));
		
		reverseDirection = new JoystickButton(controlDrive, 2);
		reverseDirection.whenPressed(new ReverseDirection());
	}
	
	public double getDriveRawAxis(int axis) {
		return controlDrive.getRawAxis(axis);
	}
	
	public double getOperateRawAxis(int axis) {
		return controlOperate.getRawAxis(axis);
	}
	
	public boolean getDriveRawButton(int button) {
		return controlDrive.getRawButton(button);
	}
	public Joystick getControlOperate() {
		return controlOperate;
	}
}
