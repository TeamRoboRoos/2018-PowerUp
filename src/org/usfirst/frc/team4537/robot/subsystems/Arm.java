package org.usfirst.frc.team4537.robot.subsystems;

import org.usfirst.frc.team4537.robot.RobotMap;
import org.usfirst.frc.team4537.robot.enums.ArmPositions;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Arm extends Subsystem {

	private boolean invertArm = false;
	private boolean sensorPhase = true;
	private NeutralMode neutralMode = NeutralMode.Coast;
	private TalonSRX armMotor;
	private Compressor compressor;
	private AnalogInput pressureSens;
	private Solenoid solBottom;
	private Solenoid solTop;

	private boolean compressorEnabled = true;

	public Arm() {
		armMotor = new TalonSRX(RobotMap.CAN_MOTOR_ARM);
		armMotor.set(ControlMode.PercentOutput, 0);
//		armMotor.configClosedloopRamp(0.2, 10);
//		armMotor.setSensorPhase(sensorPhase);
		armMotor.setInverted(invertArm);

		compressor = new Compressor(RobotMap.CAN_PCM_0);
		pressureSens = new AnalogInput(RobotMap.ANI_PRESSURE);
		solBottom = new Solenoid(RobotMap.CAN_PCM_0, RobotMap.PCM_ARM_BOTTOM);
		solTop = new Solenoid(RobotMap.CAN_PCM_0, RobotMap.PCM_ARM_TOP);

		//Setup position control
		/* first choose the sensor */
		armMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

		/* set the peak and nominal outputs, 12V means full */ 
		armMotor.configNominalOutputForward(0, 10);
		armMotor.configNominalOutputReverse(0, 10); 
		armMotor.configPeakOutputForward(1, 10);
		armMotor.configPeakOutputReverse(-1, 10);

		/*
		 * set the allowable closed-loop error, Closed-Loop output will be
		 * neutral within this range. See Table in Section 17.2.1 for native
		 * units per rotation.
		 */
		armMotor.configAllowableClosedloopError(0, 0, 10);

		/* set closed loop gains in slot0 */
		armMotor.config_kF(0, 0, 10); 
		armMotor.config_kP(0, 0.1, 10);
		armMotor.config_kI(0, 0, 10);
		armMotor.config_kD(0, 0, 10);

		/*
		 * lets grab the 360 degree position of the MagEncoder's absolute
		 * position, and intitally set the relative sensor to match.
		 */
		int absolutePosition = armMotor.getSensorCollection().getPulseWidthPosition();
		/* mask out overflows, keep bottom 12 bits */
		absolutePosition &= 0xFFF;
		if (sensorPhase)
			absolutePosition *= -1;
		if (invertArm)
			absolutePosition *= -1;
		/* set the quadrature (relative) sensor to match absolute */
		armMotor.setSelectedSensorPosition(absolutePosition, 0, 10);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}

	/**
	 * Set position of arm in encoder ticks
	 * @param position to set
	 */
	private void setArmPosition(int pos) {
		armMotor.set(ControlMode.Position, pos);
	}
	
	/**
	 * Set position of arm to value in enum
	 * @param pos
	 */
	public void setArmPosition(ArmPositions pos) {
		setArmPosition(pos.position);
	}
	
	/**
	 * Set vertical arm support position
	 * @param pos
	 */
	public void setPneumaticPosition(ArmPositions pos) {
		switch(pos) {
		case p_back:
			solBottom.set(true);
			solTop.set(true);
			break;
		case p_upright:
			solBottom.set(true);
			solTop.set(false);
			break;
		case p_forward:
			solBottom.set(false);
			solTop.set(false);
			break;
		default:
			break;
		}
	}

	/**
	 * Set neutral mode of motor
	 * @param mode
	 */
	public void setNeutralMode(NeutralMode mode) {
		armMotor.setNeutralMode(mode);
		neutralMode = mode;
	}
    
    /**
     * Get neutral mode of motor
     * @return <b>true</b> if coasting, <b>false</b> if breaking
     */
    public boolean getAllNeutralMode() {
    	return neutralMode == NeutralMode.Coast ? true : false;
    }

    /**
     * Gets quadrature displacement
     * @return displacement
     */
	public int getEncoderPosition() {
		return armMotor.getSensorCollection().getQuadraturePosition();
	}

	/**
     * Gets quadrature velocity
     * @return velocity (units per 100ms)
     */
	public int getEncoderVelocity() {
		return armMotor.getSensorCollection().getQuadratureVelocity();
	}

    /**
     * gets motor output
     * @return output (%Output)
     */
	public double getMotorOutput() {
		return armMotor.getMotorOutputPercent();
	}

	/**
	 * Sets compressor enabled state
	 * @param enabled
	 */
	public void compressorEnabled(boolean enabled) {
		if(enabled && !compressorEnabled) {
			compressor.start();
			compressorEnabled = true;
		} else if(!enabled && compressorEnabled) {
			compressor.stop();
			compressorEnabled = false;
		}
	}

	/**
	 * Gets compressor enabled stats
	 * @return boolean
	 */
	public boolean compressorGetEnabled() {
		return compressorEnabled;
	}

	/**
	 * Gets whether the compressor is running
	 * @return boolean
	 */
	public boolean compressorGetRunning() {
		return compressor.enabled();
	}

	/**
	 * Gets current draw of compressor
	 * @return current in amps
	 */
	public double compressorGetCurrent() {
		return compressor.getCompressorCurrent();
	}

	/**
	 * Get if pressure switch is low
	 * @return boolean
	 */
	public boolean pressureGetLow() {
		return compressor.getPressureSwitchValue();
	}

	/**
	 * Get current pressure of system
	 * @return pressure in psi
	 */
	public double pressureGet() {
		return 250 * (pressureSens.getVoltage() / (RobotMap.PRES_V * 10) /*4.90722606*/) - 25; 
	}

	/**
	 * Get pressure sensor voltage
	 * @return voltage
	 */
	public double pressureGetRaw() {
		return pressureSens.getVoltage();
	}
}