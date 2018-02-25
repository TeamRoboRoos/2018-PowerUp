package org.usfirst.frc.team4537.robot.subsystems;

import org.usfirst.frc.team4537.robot.Robot;
import org.usfirst.frc.team4537.robot.RobotMap;
import org.usfirst.frc.team4537.robot.commands.*;
import org.usfirst.frc.team4537.robot.utilities.Functions;
import org.usfirst.frc.team4537.robot.utilities.MotionProfile;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveBase extends Subsystem {
	
	public TalonSRX leftMaster;
	private TalonSRX leftSlave1;
	private TalonSRX leftSlave2;
	private TalonSRX rightMaster;
	private TalonSRX rightSlave1;
	private TalonSRX rightSlave2;
	
	private int direction = 1;
	private NeutralMode neutralMode = NeutralMode.Coast; 
	
	public MotionProfile profileLeft;

	public DriveBase() {
    	//Initialize Motor Controllers
		leftMaster = new TalonSRX(RobotMap.CAN_MOTOR_DL_1);
    	leftMaster.set(ControlMode.PercentOutput, 0.0);
    	leftMaster.configClosedloopRamp(0.2, 10); 
    	leftMaster.setSensorPhase(true);
    	leftMaster.setInverted(false);
    	
    	leftSlave1 = new TalonSRX(RobotMap.CAN_MOTOR_DL_2);
    	leftSlave1.set(ControlMode.Follower, leftMaster.getDeviceID());
    	leftSlave1.setInverted(leftMaster.getInverted());
    	
    	leftSlave2 = new TalonSRX(RobotMap.CAN_MOTOR_DL_3);
    	leftSlave2.set(ControlMode.Follower, leftMaster.getDeviceID());
    	leftSlave2.setInverted(leftMaster.getInverted());
    	
    	rightMaster = new TalonSRX(RobotMap.CAN_MOTOR_DR_4);
    	rightMaster.set(ControlMode.PercentOutput, 0.0);
    	rightMaster.configClosedloopRamp(0.2, 10); 
    	rightMaster.setSensorPhase(true);
    	rightMaster.setInverted(true);
    	
    	rightSlave1 = new TalonSRX(RobotMap.CAN_MOTOR_DR_5);
    	rightSlave1.set(ControlMode.Follower, rightMaster.getDeviceID());
    	rightSlave1.setInverted(rightMaster.getInverted());
    	
    	rightSlave2 = new TalonSRX(RobotMap.CAN_MOTOR_DR_6);
    	rightSlave2.set(ControlMode.Follower, rightMaster.getDeviceID());
    	rightSlave2.setInverted(rightMaster.getInverted());
    	
    	//Setup velocity control
    	/* Left Side */
    	/* first choose the sensor */
    	leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    	leftMaster.getSensorCollection().setQuadraturePosition(0, 10);

    	/* set the peak and nominal outputs, 12V means full */ 
    	leftMaster.configNominalOutputForward(0, 10);
    	leftMaster.configNominalOutputReverse(0, 10); 
    	leftMaster.configPeakOutputForward(1, 10);
    	leftMaster.configPeakOutputReverse(-1, 10); 

    	/* set closed loop gains in slot0 */
    	leftMaster.config_kF(0, 0.34, 10); 
    	leftMaster.config_kP(0, 0.2, 10);
    	leftMaster.config_kI(0, 0.0005, 10);
    	leftMaster.config_IntegralZone(0, 100, 10);
    	leftMaster.config_kD(0, 0, 10);

    	/* Right Side */
    	/* first choose the sensor */
    	rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    	rightMaster.getSensorCollection().setQuadraturePosition(0, 10);

    	/* set the peak and nominal outputs, 12V means full */ 
    	rightMaster.configNominalOutputForward(0, 10);
    	rightMaster.configNominalOutputReverse(0, 10); 
    	rightMaster.configPeakOutputForward(1, 10);
    	rightMaster.configPeakOutputReverse(-1, 10); 

    	/* set closed loop gains in slot0 */
    	rightMaster.config_kF(0, 0.34, 10); 
    	rightMaster.config_kP(0, 0.2, 10);
    	rightMaster.config_kI(0, 0.0005, 10);
    	rightMaster.config_IntegralZone(0, 100, 10);
    	rightMaster.config_kD(0, 0, 10);
    	
    	//Setup motion profile
    	profileLeft = new MotionProfile(leftMaster, Functions.readProfileFile("testprofile2.csv"));
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new DriveArcade());
    }
    
    /**
	 * Set neutral mode of all motors
	 * @param mode
	 */
    public void setAllNeutralMode(NeutralMode mode) {
    	leftMaster.setNeutralMode(mode);
    	leftSlave1.setNeutralMode(mode);
    	leftSlave2.setNeutralMode(mode);
    	rightMaster.setNeutralMode(mode);
    	rightSlave1.setNeutralMode(mode);
    	rightSlave2.setNeutralMode(mode);
    	neutralMode = mode;
    }
    
    /**
     * Get neutral mode of all motors
     * @return <b>true</b> if coasting, <b>false</b> if breaking
     */
    public boolean getAllNeutralMode() {
    	return neutralMode == NeutralMode.Coast ? true : false;
    }
    
    /**
     * Sets speed of left motor
     * @param leftValue
     * @param vel Velocity control?
     */
    public void setLeftMotor(double leftValue, boolean vel) {
    	if(vel) {
    		//Convert to %robot velocity
    		leftValue *= RobotMap.ROBOT_MAX_SPEED;
    		leftMaster.set(ControlMode.Velocity, leftValue);
    	} else {
    		leftMaster.set(ControlMode.PercentOutput, leftValue);
    	}
    }
    
    /**
     * Sets speed of right motor
     * @param leftValue
     * @param vel Velocity control?
     */
    public void setRightMotor(double rightValue, boolean vel) {
    	if(vel) {
    		//Convert to %robot velocity
    		rightValue *= RobotMap.ROBOT_MAX_SPEED;
    		rightMaster.set(ControlMode.Velocity, rightValue);
    	} else {
    		rightMaster.set(ControlMode.PercentOutput, rightValue);
    	}
    }
    
    /**
     * Sets speed of left and right motors
     * @param leftValue
     * @param rightValue
     * @param vel Velocity control?
     */
    public void setLeftRightMotors(double leftValue, double rightValue, boolean vel) {
		setLeftMotor(leftValue, vel);
		setRightMotor(rightValue, vel);
    }
    
    /**
     * Gets left and right quadrature displacements
     * @return [left,right]
     */
    public int[] getEncoderDistances() {
    	return new int[] {leftMaster.getSensorCollection().getQuadraturePosition(), rightMaster.getSensorCollection().getQuadraturePosition()};
    }
    
    /**
     * Gets left and right quadrature velocities
     * @return [left,right] (units per 100ms)
     */
    public int[] getEncoderVelocities() {
    	return new int[] {leftMaster.getSensorCollection().getQuadratureVelocity(), rightMaster.getSensorCollection().getQuadratureVelocity()};
    }
    
    /**
     * gets left and right motor outputs
     * @return [left,right] (%Output)
     */
    public double[] getMotorOutputs() {
    	return new double[] {leftMaster.getMotorOutputPercent(), rightMaster.getMotorOutputPercent()};
    }
    
    /**
     * Arcade drive implements single stick driving. This function lets you directly provide
     * joystick values from any source.
     *
     * @param speed     The value to use for forwards/backwards
     * @param turn   The value to use for the rotate right/left
     * @param squaredInputs If set, decreases the sensitivity at low speeds
     */
    public void arcadeDrive(double speed, double turn, boolean squaredInputs){
		double moveValue = speed * direction;
		double rotateValue = turn;
		int moveSign = (int)(moveValue/Math.abs(moveValue));
		int rotateSign = (int)(rotateValue/Math.abs(rotateValue));
		
//		//Joystick deadzones to remove jitter from the joystick
//		if (moveValue < Config.DEADZONE_M && moveValue > -Config.DEADZONE_M) {
//			moveValue = 0;
//		}
//		if (rotateValue < Config.DEADZONE_R && rotateValue > -Config.DEADZONE_R) {
//			rotateValue = 0;
//		}
//		//Halve speed code
//		if (speedHalved) {
//			moveValue *= Config.JOYSTICK_HALF_MOVE_MULTIPLIER;
//			rotateValue *= Config.JOYSTICK_HALF_ROTATE_MULTIPLIER;
//		}

		//Apply squared inputs
		if(squaredInputs) {
			moveValue = Math.pow(moveValue, 2) * moveSign;
			rotateValue = Math.pow(rotateValue, 2) * rotateSign;
		}

		//Mathy arcadey stuffy
		double leftSpeed, rightSpeed;
		if (moveValue > 0) {
			if (rotateValue > 0) {
				leftSpeed = moveValue - rotateValue;
				rightSpeed = Math.max(moveValue, rotateValue);
			}
			else {
				leftSpeed = Math.max(moveValue, -rotateValue);
				rightSpeed = moveValue + rotateValue;
			}
		}
		else {
			if (rotateValue > 0) {
				leftSpeed = -Math.max(-moveValue, rotateValue);
				rightSpeed = moveValue + rotateValue;
			}
			else {
				leftSpeed = moveValue - rotateValue;
				rightSpeed = -Math.max(-moveValue, -rotateValue);
			}
		}

//		//Apply speed limit
//		leftSpeed *= Config.SPEED_MULTIPLIER;
//		rightSpeed *= Config.SPEED_MULTIPLIER;

		//Set Maximum and Minimum
		leftSpeed = Math.max(leftSpeed, -1);
		leftSpeed = Math.min(leftSpeed, 1);
		rightSpeed = Math.max(rightSpeed, -1);
		rightSpeed = Math.min(rightSpeed, 1);
		
		//Output to motors
		setLeftRightMotors(leftSpeed, rightSpeed, RobotMap.DRIVE_VEL);
	}
    
    /**
     * Arcade drive implements single stick driving. This function lets you directly provide
     * joystick values from any source. The calculated values will be squared to decrease
     * sensitivity at low speeds.
     *
     * @param moveValue   The value to use for forwards/backwards
     * @param rotateValue The value to use for the rotate right/left
     */
    public void arcadeDrive(double speed, double turn) {
    	arcadeDrive(speed, turn, true);
    }
    
    public void tankDrive(double left, double right) {
    	tankDrive(left, right, true);
    }
    
    public void tankDrive(double left, double right, boolean squaredInputs) {
    	double leftSpeed = left;
		double rightSpeed = right;
		if(direction == -1) {
			leftSpeed = -right;
			rightSpeed = -left;
		}
		
		int leftSign = (int)(leftSpeed/Math.abs(leftSpeed));
		int rightSign = (int)(rightSpeed/Math.abs(rightSpeed));

		//Apply squared inputs
		if(squaredInputs) {
			leftSpeed = Math.pow(leftSpeed, 2) * leftSign;
			rightSpeed = Math.pow(rightSpeed, 2) * rightSign;
		}

		//Set Maximum and Minimum
		leftSpeed = Math.max(leftSpeed, -1);
		leftSpeed = Math.min(leftSpeed, 1);
		rightSpeed = Math.max(rightSpeed, -1);
		rightSpeed = Math.min(rightSpeed, 1);
		
		//Output to motors
		setLeftRightMotors(leftSpeed, rightSpeed, RobotMap.DRIVE_VEL);
    }
    
    public void flipDirection() {
    	direction *= -1;
    }
}

