/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4537.robot;

import org.usfirst.frc.team4537.robot.commands.*;
import org.usfirst.frc.team4537.robot.enums.ArmPosition;
import org.usfirst.frc.team4537.robot.enums.ArmPositions;
import org.usfirst.frc.team4537.robot.enums.LEDCodes;
import org.usfirst.frc.team4537.robot.triggers.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
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
	
	private Joystick controlDrive;
	private Joystick controlOperate;
	private EndGame endGame;
	
	private JoystickButton armFwd, armMid, armBak;
	
	//PS4 buttons
	private JoystickButton load, home, loadSwitch, loadScaleLow, loadScaleNeutral, loadScaleHigh, climb;
	private JoystickButton loadCube, placeCube;
	private JoystickButton climbUp, climbDown;
	public OI() {
		controlDrive = new Joystick(RobotMap.CONTROL_DRIVE_0);
		controlOperate = new Joystick(RobotMap.CONTROL_OPERATE_0);
		endGame = new EndGame();
		endGame.whenActive(new SetLights(new int[]{0,1,2,3}, LEDCodes.a_blink, LEDCodes.c_orange));

		armFwd = new JoystickButton(controlDrive, 7);
		armFwd.whenPressed(new PneuTest(ArmPositions.p_forward));
		armMid = new JoystickButton(controlDrive, 6);
		armMid.whenPressed(new PneuTest(ArmPositions.p_upright));
		armBak = new JoystickButton(controlDrive, 5);
		armBak.whenPressed(new PneuTest(ArmPositions.p_back));
		
		//Buttons for the ps4 controller
		load = new JoystickButton(controlOperate, 1);
		load.whenPressed(new SetArm2(ArmPosition.load));
		home = new JoystickButton(controlOperate, 2);
		home.whenPressed(new SetArm2(ArmPosition.home));
		loadSwitch = new JoystickButton(controlOperate, 3);
		loadSwitch.whenPressed(new SetArm2(ArmPosition.fence));
		loadScaleLow = new JoystickButton(controlOperate, 4);
		loadScaleLow.whenPressed(new SetArm2(ArmPosition.scaleLowMid));
		loadScaleNeutral = new JoystickButton(controlOperate, 5);
//		loadScaleNeutral.whenPressed(new SetArm2(ArmPosition.scaleMid)); not done in armposition?
		loadScaleHigh = new JoystickButton(controlOperate, 6);
		loadScaleHigh.whenPressed(new SetArm2(ArmPosition.scaleHigh));
		climb = new JoystickButton(controlOperate, 7);
//		climb.whenPressed(new SetArm2(ArmPosition.climb)); not done in armposition?
		loadCube = new JoystickButton(controlOperate, 8);
		loadCube.whileHeld(new RunGrabber(RobotMap.CHAINSAW_LOAD));
		placeCube = new JoystickButton(controlOperate, 9);
		placeCube.whileHeld(new RunGrabber(RobotMap.CHAINSAW_PLACE));
		climbUp = new JoystickButton(controlOperate, 10);
		climbUp.whileHeld(new Climb(RobotMap.CLIMB_UP));
		climbDown = new JoystickButton(controlOperate, 11);
		climbDown.whileHeld(new Climb(RobotMap.CLIMB_DOWN));
	}
	
	public double getDriveRawAxis(int axis) {
		return controlDrive.getRawAxis(axis);
	}
	
	public boolean getDriveRawButton(int button) {
		return controlDrive.getRawButton(button);
	}
}
