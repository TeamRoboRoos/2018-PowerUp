/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4537.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4537.robot.commands.*;
import org.usfirst.frc.team4537.robot.enums.ArmPosition;
import org.usfirst.frc.team4537.robot.enums.LEDCodes;
import org.usfirst.frc.team4537.robot.subsystems.*;

import com.ctre.phoenix.motorcontrol.NeutralMode;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static final ExampleSubsystem kExampleSubsystem = new ExampleSubsystem();
	public static final DriveBase driveBase = new DriveBase();
	public static final Arm arm = new Arm();
	public static final Grabber grabber = new Grabber();
	public static final Climber climber = new Climber();
	public static final Telemetry telemetry = new Telemetry();
	public static final Arduino arduino = new Arduino();
	public static OI oi;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();
	
	SendableChooser<String> stationChooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		
		stationChooser.addDefault("Left", "L");
		stationChooser.addObject("Mid", "M");
		stationChooser.addObject("Right", "R");
		SmartDashboard.putData("Robot Position", stationChooser);
		
		m_chooser.addDefault("Sit Still!", null);
		m_chooser.addObject("EXPERIMENTAL! CubeFence?", new AutoCubeFence(stationChooser));
		m_chooser.addObject("Auto Encoder Baseline", new AutoLineEnc());
		
		SmartDashboard.putData("Auto mode", m_chooser);

		SmartDashboard.putData("SetLights", new SetLightsSDB());
		SmartDashboard.putNumber("ArmMultiplier", 0.0);

//		SmartDashboard.putData("Arm_Load", new SetArm2(ArmPosition.load));
//		SmartDashboard.putData("Arm_Home", new SetArm2(ArmPosition.home));
//		SmartDashboard.putData("Arm_Fence", new SetArm2(ArmPosition.fence));
//		SmartDashboard.putData("Arm_ScaleLM", new SetArm2(ArmPosition.scaleLowMid));
//		SmartDashboard.putData("Arm_ScaleH", new SetArm2(ArmPosition.scaleHigh));
//		SmartDashboard.putData("Arm_Home", new SetArm2(ArmPosition.climb));
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		driveBase.setAllNeutralMode(NeutralMode.Brake);
		arduino.setLights(LEDCodes.m_animation, 0, LEDCodes.a_blackout);
		arduino.setLights(LEDCodes.m_colour, 0, LEDCodes.c_yellow);
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		driveBase.setAllNeutralMode(NeutralMode.Coast);
		arduino.setLights(LEDCodes.m_animation, 0, LEDCodes.a_fade);
		arduino.setLights(LEDCodes.m_colour, 0, LEDCodes.c_yellow);
		
		m_autonomousCommand = m_chooser.getSelected();
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
			
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		arduino.setLights(LEDCodes.m_animation, 0, LEDCodes.a_carnival);
		arduino.setLights(LEDCodes.m_colour, 0, LEDCodes.c_green);
		driveBase.setAllNeutralMode(NeutralMode.Brake);
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.		
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	public void testInit() {
		arduino.setLights(LEDCodes.m_animation, 0, LEDCodes.a_rippleReverse);
		arduino.setLights(LEDCodes.m_colour, 0, LEDCodes.c_teal);
	}
	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
