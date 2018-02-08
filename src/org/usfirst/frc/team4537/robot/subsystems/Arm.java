package org.usfirst.frc.team4537.robot.subsystems;

import org.usfirst.frc.team4537.robot.RobotMap;
import org.usfirst.frc.team4537.robot.enums.ArmPosition;
import org.usfirst.frc.team4537.robot.enums.ArmPositions;
import org.usfirst.frc.team4537.robot.utilities.ArmPositioner;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Arm extends Subsystem {

	public ArmPositioner armPositioner;
	
	private boolean invertArm = true;
	private boolean sensorPhase = true;
	private NeutralMode neutralMode = NeutralMode.Coast;
	public int armSetPoint;
	private ArmPositions pneuSetPoint = ArmPositions.p_upright;
	private int encBound = 100;
	public TalonSRX armMotor;
	private Compressor compressor;
	private AnalogInput pressureSens;
	private DigitalInput cylTop;
	private DigitalInput cylBottom;
	private Solenoid solBottom;
	private Solenoid solTop;

	private boolean compressorEnabled = true;

	public Arm() {
		armPositioner = new ArmPositioner();
		
		armMotor = new TalonSRX(RobotMap.CAN_MOTOR_ARM);
		armMotor.set(ControlMode.PercentOutput, 0.0);
//		armMotor.configClosedloopRamp(0.2, 10);
		armMotor.setSensorPhase(sensorPhase);
		armMotor.setInverted(invertArm);

		compressor = new Compressor(RobotMap.CAN_PCM_0);
		pressureSens = new AnalogInput(RobotMap.ANI_PRESSURE);
		cylTop = new DigitalInput(0);
		cylBottom = new DigitalInput(1);
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
		armMotor.config_kP(0, 0.25, 10);
		armMotor.config_kI(0, 0.001, 10);
		armMotor.config_kD(0, 0, 10);
		armMotor.config_IntegralZone(0, 0, 10);

		/*
		 * lets grab the 360 degree position of the MagEncoder's absolute
		 * position, and initially set the relative sensor to match.
		 */
		int absolutePosition = armMotor.getSensorCollection().getPulseWidthPosition();
//		/* mask out overflows, keep bottom 12 bits */
//		absolutePosition &= 0xFFF;
		if (sensorPhase)
			absolutePosition *= -1;
		if (invertArm)
			absolutePosition *= -1;
		/* set the quadrature (relative) sensor to match absolute */
		armMotor.setSelectedSensorPosition(absolutePosition, 0, 10);
		
		//Set motor to hold position
		armMotor.set(ControlMode.Position, absolutePosition);
		armSetPoint = absolutePosition;
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}
	
	public void driveArm(double power) {
		armMotor.set(ControlMode.PercentOutput, power);
	}
	
	public boolean armPositionerCompleted() {
		return armPositioner.hasCompleted();
	}
	
	public void controlArmPositioner() {
		armPositioner.control();
	}
	
	public void requestArmPositioner(ArmPosition position) {
		armPositioner.requestArmPosition(position);
	}

	/**
	 * Set position of arm in encoder ticks
	 * @param setPosition to set
	 */
	public void setArmPosition(int pos) {
		armMotor.set(ControlMode.Position, pos);
		armSetPoint = pos;
	}
	
	/**
	 * Set position of arm to value in enum
	 * @param pos
	 */
	public void setArmPosition(ArmPosition pos) {
		setArmPosition(pos.arm);
		SmartDashboard.putString("ArmPosition LastSet", pos.toString());
	}
	
	public int getArmPosition() {
		return armSetPoint;
	}
	
	public boolean[] getPnuBools() {
		return new boolean[] {cylTop.get(), cylBottom.get()};
	}
	
	/**
	 * Get if pneumatics have finished moving
	 * @return <b>true</b> if moving, <b>false</b> if done 
	 */
	public boolean getPneumaticsInPos() {
		boolean inPosition;
		System.out.println(pneuSetPoint);
		System.out.println(cylTop.get()+" ::: "+cylBottom.get());
		switch(pneuSetPoint) {
		case p_back:
			inPosition = cylTop.get() && cylBottom.get(); break;
		case p_upright:
			inPosition = !cylTop.get() && cylBottom.get(); break;
		case p_forward:
			inPosition = !cylTop.get() && !cylBottom.get(); break;
		default:
			inPosition = false; break;
		}
		SmartDashboard.putBoolean("Moving_P", inPosition);
		return inPosition;
	}
	
	/**
	 * Get if arm has finished moving (within bounds)
	 * @return <b>true</b> if moving, <b>false</b> if done 
	 */
	public boolean getArmInPos() {
		boolean inPosition;
		if(getEncoderPosition() > armSetPoint-encBound && getEncoderPosition() < armSetPoint+encBound) {
			inPosition = true;
		} else {
			inPosition = false;
		}
		SmartDashboard.putBoolean("Moving_A", inPosition);
		return inPosition;
	}
	
	/**
	 * Set vertical arm support position
	 * @param pos
	 */
	public void setPneumaticPosition(ArmPositions pos) {
		/*
		 * solTop: true = extend, false = retract
		 * solBottom: true = retract, false = extend
		 */
		switch(pos) {
		case p_back:
			solTop.set(true); //Extend
			solBottom.set(false); //Extend
			break;
		case p_upright:
			solTop.set(false); //Retract
			solBottom.set(false); //Extend
			break;
		case p_forward:
			solTop.set(false); //Retract
			solBottom.set(true); //Retract
			break;
		default:
			break;
		}
		pneuSetPoint = pos;
	}
	
	public void setPneumaticPosition(int pos) {
		switch(pos) {
		case 0:
			setPneumaticPosition(ArmPositions.p_forward);
			break;
		case 1:
			setPneumaticPosition(ArmPositions.p_upright);
			break;
		case 2:
			setPneumaticPosition(ArmPositions.p_back);
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