package org.usfirst.frc.team4537.robot.subsystems;

import org.usfirst.frc.team4537.robot.RobotMap;
import org.usfirst.frc.team4537.robot.enums.LEDCodes;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Arduino extends Subsystem {

	private SerialPort arduino = new SerialPort(RobotMap.ARD_BAUDR, RobotMap.ARD_PORT);

	public Arduino() {
		SmartDashboard.putNumber("vStrip", 0);
		SmartDashboard.putNumber("R", 0);
		SmartDashboard.putNumber("G", 0);
		SmartDashboard.putNumber("B", 0);
		SmartDashboard.putNumber("Animation", 0);
		SmartDashboard.putNumber("Mode", 0);
	}
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}

	public void setLights(LEDCodes mode, int vStrip, LEDCodes cmd) {
		byte vStripB = (byte)vStrip;
		if(mode == LEDCodes.m_animation) {
			writeBytes(new byte[] {mode.value, vStripB, cmd.value});
		} else {
			writeBytes(new byte[] {mode.value, vStripB, cmd.r, cmd.g, cmd.b});
		}
	}
	
	public void writeInt(int in) {
		writeBytes(new byte[] {(byte)in});
	}

	private void writeBytes(byte[] bytes) {
		arduino.write(bytes, bytes.length);
	}
}