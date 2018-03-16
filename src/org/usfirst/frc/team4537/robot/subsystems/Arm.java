package org.usfirst.frc.team4537.robot.subsystems;

import org.usfirst.frc.team4537.robot.Robot;
import org.usfirst.frc.team4537.robot.RobotMap;
import org.usfirst.frc.team4537.robot.commands.*;
//import org.usfirst.frc.team4537.robot.enums.ArmPosition;
//import org.usfirst.frc.team4537.robot.enums.ArmPositions;
//import org.usfirst.frc.team4537.robot.utilities.ArmPositioner;

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

//	public ArmPositioner armPositioner;
	
	private boolean invertArm = false;
	private boolean sensorPhase = false;
	private NeutralMode neutralMode = NeutralMode.Coast;
//	private int encBound = 75;
	public TalonSRX armMaster;
	private TalonSRX armSlave;
	private Compressor compressor;
	private AnalogInput pressureSens;
	private DigitalInput cylTopExt;
	private DigitalInput cylTopRet;
	private DigitalInput cylBottomExt;
	private DigitalInput cylBottomRet;
	private Solenoid solBottom;
	private Solenoid solTop;

	private boolean compressorEnabled = true;
	
	public int curPnuPos = 1;
	public int armZone = 1;
	
	public int armHoldPos = 0;

	public Arm() {
		armMaster = new TalonSRX(RobotMap.CAN_MOTOR_ARM_2);
		armMaster.set(ControlMode.PercentOutput, 0.0);
		armMaster.configClosedloopRamp(0.2, 10);
		armMaster.setSensorPhase(sensorPhase);
		armMaster.setInverted(invertArm);
		
		armSlave = new TalonSRX(RobotMap.CAN_MOTOR_ARM_1);
		armSlave.set(ControlMode.Follower, armMaster.getDeviceID());
		armSlave.setInverted(!armMaster.getInverted());

		compressor = new Compressor(RobotMap.CAN_PCM_0);
		pressureSens = new AnalogInput(RobotMap.ANI_PRESSURE);
		cylTopExt = new DigitalInput(RobotMap.DGI_CYL_TOP_EXT);
		cylTopRet = new DigitalInput(RobotMap.DGI_CYL_TOP_RET);
		cylBottomExt = new DigitalInput(RobotMap.DGI_CYL_BTM_EXT);
		cylBottomRet = new DigitalInput(RobotMap.DGI_CYL_BTM_RET);
		solBottom = new Solenoid(RobotMap.CAN_PCM_0, RobotMap.PCM_ARM_BOTTOM);
		solTop = new Solenoid(RobotMap.CAN_PCM_0, RobotMap.PCM_ARM_TOP);

		//Setup position control
		/* first choose the sensor */
		armMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

		/* set the peak and nominal outputs, 12V means full */ 
		armMaster.configNominalOutputForward(0, 10);
		armMaster.configNominalOutputReverse(0, 10); 
		armMaster.configPeakOutputForward(1, 10);
		armMaster.configPeakOutputReverse(-1, 10);

		/*
		 * set the allowable closed-loop error, Closed-Loop output will be
		 * neutral within this range. See Table in Section 17.2.1 for native
		 * units per rotation.
		 */
		armMaster.configAllowableClosedloopError(0, 0, 10);

		/* set closed loop gains in slot0 */
		armMaster.config_kF(0, 0, 10); 
		armMaster.config_kP(0, 0.25, 10);
		armMaster.config_kI(0, 0.001, 10);
		armMaster.config_kD(0, 0, 10);
		armMaster.config_IntegralZone(0, 0, 10);

		/*
		 * lets grab the 360 degree position of the MagEncoder's absolute
		 * position, and initially set the relative sensor to match.
		 */
		int absolutePosition = armMaster.getSensorCollection().getPulseWidthPosition();
//		/* mask out overflows, keep bottom 12 bits */
//		absolutePosition &= 0xFFF;
		if (sensorPhase)
			absolutePosition *= -1;
		if (invertArm)
			absolutePosition *= -1;
		/* set the quadrature (relative) sensor to match absolute */
		//only 0 if at home
		absolutePosition -= RobotMap.ROBOT_HOME_ENC;
		armMaster.setSelectedSensorPosition(absolutePosition, 0, 10);
		
		//Set motor to hold position
		armMaster.set(ControlMode.Position, absolutePosition);

		armMaster.enableVoltageCompensation(true);
		
		armMaster.configForwardSoftLimitThreshold(0, 10);
		armMaster.configForwardSoftLimitThreshold(RobotMap.THRESHOLD_FWD_VERTICAL, 10);
		armMaster.configForwardSoftLimitEnable(true, 10);
		armMaster.configReverseSoftLimitThreshold(RobotMap.THRESHOLD_REV_VERTICAL, 10);
		armMaster.configReverseSoftLimitEnable(true, 10);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new ArmSet());
	}
	
	public void driveArm(double power) {
//    	System.out.println("POWER" + power);//XXX
    	boolean rev = false;
    	if(power > 0) {
    		rev = true;
    	}
    	System.out.println(Boolean.toString(!(Robot.oi.getOperateRawAxis(3)<=-0.75)));
    	if(!(Robot.oi.getOperateRawAxis(3)<=-0.75)) {
    		if(getEncoderPosition() < -3250 && !rev) {
    			power *= -1;
    		}
    	}
		armMaster.set(ControlMode.PercentOutput, power);
	}
	
	public boolean[] getPnuBools() {
		return new boolean[] {!cylTopExt.get(), !cylTopRet.get(), !cylBottomExt.get(), !cylBottomRet.get()};
	}
	
	private void setArmLimitReverse(int limit) {
		armMaster.configReverseSoftLimitThreshold(limit, 10);
	}
	
	private void setArmLimitReverse(boolean enable) {
		armMaster.configReverseSoftLimitEnable(enable, 10);
	}
	
	private void setArmLimitForward(int limit) {
		armMaster.configForwardSoftLimitThreshold(limit, 10);
	}
	
	private void setArmLimitForward(boolean enable) {
		armMaster.configForwardSoftLimitEnable(enable, 10);
	}
	
	/**
	 * Set vertical arm support position
	 * @param pos
	 */
	public void setPneumaticPosition(int pos) {
		/*
		 * solTop: true = extend, false = retract
		 * solBottom: true = retract, false = extend
		 */
		curPnuPos = pos;
		switch(pos) {
		case 2:
			solTop.set(true); //Extend
			solBottom.set(false); //Extend
			setArmLimitReverse(false);
//			setArmLimitReverse(RobotMap.THRESHOLD_REV_BACK);
			setArmLimitForward(true);
			setArmLimitForward(RobotMap.THRESHOLD_FWD_BACK);
			break;
		case 1:
			solTop.set(false); //Retract
			solBottom.set(false); //Extend
			setArmLimitReverse(true);
			setArmLimitReverse(RobotMap.THRESHOLD_REV_VERTICAL);
			setArmLimitForward(true);
			setArmLimitForward(RobotMap.THRESHOLD_FWD_VERTICAL);
			break;
		case 0:
			solTop.set(false); //Retract
			solBottom.set(true); //Retract
			setArmLimitReverse(true);
			setArmLimitReverse(RobotMap.THRESHOLD_REV_FORWARD);
			setArmLimitForward(true);
			setArmLimitForward(RobotMap.THRESHOLD_FWD_FORWARD);
			break;
		default:
			break;
		}
	}
	
	public boolean[] getSolenoidsEnabled() {
		return new boolean[] {solBottom.get(), solTop.get()};
	}

	/**
	 * Set neutral mode of motor
	 * @param mode
	 */
	public void setNeutralMode(NeutralMode mode) {
		armMaster.setNeutralMode(mode);
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
		return armMaster.getSensorCollection().getQuadraturePosition();
	}

	/**
     * Gets quadrature velocity
     * @return velocity (units per 100ms)
     */
	public int getEncoderVelocity() {
		return armMaster.getSensorCollection().getQuadratureVelocity();
	}

    /**
     * gets motor output
     * @return output (%Output)
     */
	public double getMotorOutput() {
		return armMaster.getMotorOutputPercent();
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