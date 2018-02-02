package org.usfirst.frc.team4537.robot.subsystems;

import org.usfirst.frc.team4537.robot.RobotMap;
import org.usfirst.frc.team4537.robot.commands.RecordData;
import org.usfirst.frc.team4537.robot.utilities.Logger;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Telemetry extends Subsystem {

	private PowerDistributionPanel pdp;
	private UsbCamera[] camObjs = new UsbCamera[RobotMap.CAM_NAMES.length];
	private AHRS navX;

	public Logger logger;
	private String[] sensors = {
			"encL",
			"encR",
			"velL",
			"velR",
			"btn1"
	};

	public Telemetry() {
		pdp = new PowerDistributionPanel();
		navX = new AHRS(RobotMap.NAVX_PORT);
		SmartDashboard.putData("PDP", pdp);
		SmartDashboard.putData("NavX", navX);

		logger = new Logger(sensors, RobotMap.LOGGER_DELAY, RobotMap.LOGGER_ENABLE);

		//Initialize camera capture servers
		for (int i = 0; i <= RobotMap.CAM_NAMES.length-1; i++) {
			UsbCamera camObj = CameraServer.getInstance().startAutomaticCapture(RobotMap.CAM_NAMES[i], RobotMap.CAM_PATHS[i]);
			camObjs[i] = camObj;
			camObj.setResolution(RobotMap.CAM_RESOLUTION[0], RobotMap.CAM_RESOLUTION[1]);
			camObj.setFPS(RobotMap.CAM_FPS);
			//camObj.setExposureManual(RobotMap.CAM_EX);
			//camObj.setWhiteBalanceManual(RobotMap.CAM_WB);
			camObj.setExposureAuto();
			camObj.setWhiteBalanceAuto();
		}
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new RecordData());
	}
	
	public double getGyroAngle() {
		return navX.getAngle();
	}
	
	public void resetGyroAngle() {
		navX.zeroYaw();
	}

	public boolean getUserButton() {
		return RobotController.getUserButton();
	}
}

