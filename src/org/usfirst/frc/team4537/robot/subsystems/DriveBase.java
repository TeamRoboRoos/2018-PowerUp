package org.usfirst.frc.team4537.robot.subsystems;

import org.usfirst.frc.team4537.robot.Robot;
import org.usfirst.frc.team4537.robot.RobotMap;
import org.usfirst.frc.team4537.robot.commands.DriveArcade;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveBase extends Subsystem {
	
	private TalonSRX leftMaster;
	private TalonSRX leftSlave1;
	private TalonSRX leftSlave2;
	private TalonSRX rightMaster;
	private TalonSRX rightSlave1;
	private TalonSRX rightSlave2;
	
	private int direction = 1;

	public DriveBase() {
    	//Initialize Motor Controllers
		leftMaster = new TalonSRX(RobotMap.CAN_MOTOR_DL_1);
    	leftMaster.set(ControlMode.PercentOutput, 0.0);
    	leftMaster.setInverted(true);
    	
    	leftSlave1 = new TalonSRX(RobotMap.CAN_MOTOR_DL_2);
    	leftSlave1.set(ControlMode.Follower, leftMaster.getDeviceID());
    	leftSlave1.setInverted(true);
    	
    	leftSlave2 = new TalonSRX(RobotMap.CAN_MOTOR_DL_3);
    	leftSlave2.set(ControlMode.Follower, leftMaster.getDeviceID());
    	leftSlave2.setInverted(true);
    	
    	rightMaster = new TalonSRX(RobotMap.CAN_MOTOR_DR_4);
    	rightMaster.set(ControlMode.PercentOutput, 0.0);
    	
    	rightSlave1 = new TalonSRX(RobotMap.CAN_MOTOR_DR_5);
    	rightSlave1.set(ControlMode.Follower, rightMaster.getDeviceID());
    	
    	rightSlave2 = new TalonSRX(RobotMap.CAN_MOTOR_DR_6);
    	rightSlave2.set(ControlMode.Follower, rightMaster.getDeviceID());
    	

    	/* first choose the sensor */ 
    	leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10); 
    	leftMaster.setSensorPhase(true); 

    	/* set the peak and nominal outputs, 12V means full */ 
    	leftMaster.configNominalOutputForward(0, 10); 
    	leftMaster.configNominalOutputReverse(0, 10); 
    	leftMaster.configPeakOutputForward(1, 10); 
    	leftMaster.configPeakOutputReverse(-1, 10); 

    	/* set closed loop gains in slot0 */ 
    	leftMaster.config_kF(0, 0.34, 10); 
    	leftMaster.config_kP(0, 0.2, 10); 
    	leftMaster.config_kI(0, 0, 10);  
    	leftMaster.config_kD(0, 0, 10); 

	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new DriveArcade());
    }
    
    public void setAllNeutralMode(NeutralMode mode) {
    	leftMaster.setNeutralMode(mode);
    	leftSlave1.setNeutralMode(mode);
    	leftSlave2.setNeutralMode(mode);
    	rightMaster.setNeutralMode(mode);
    	rightSlave1.setNeutralMode(mode);
    	rightSlave2.setNeutralMode(mode);
    }
    
    /**
     * Sets speed of left motor
     * @param leftValue
     */
    public void setLeftMotor(double leftValue) {
    	//Convert to %robot velocity
    	leftValue *= RobotMap.ROBOT_MAX_SPEED;
		leftMaster.set(ControlMode.Velocity, leftValue);
    }
    
    /**
     * Sets speed of right motor
     * @param leftValue
     */
    public void setRightMotor(double rightValue) {
    	//Convert to %robot velocity
//    	rightValue *= RobotMap.ROBOT_MAX_SPEED;
		rightMaster.set(ControlMode.PercentOutput, rightValue);
    }
    
    /**
     * Sets speed of left and right motors
     * @param leftValue
     * @param rightValue
     */
    public void setLeftRightMotors(double leftValue, double rightValue) {
		setLeftMotor(leftValue);
		setRightMotor(rightValue);
    }
    
    public int[] getEncoderDistances() {
    	System.out.println(rightMaster.getSensorCollection().getQuadraturePosition() +" "+ rightSlave1.getSensorCollection().getQuadraturePosition() +" "+ leftMaster.getSensorCollection().getQuadraturePosition() +" "+ leftSlave1.getSensorCollection().getQuadraturePosition());
    	return new int[] {leftMaster.getSensorCollection().getQuadraturePosition(), rightMaster.getSensorCollection().getQuadraturePosition()};
    }
    
    public int[] getEncoderVelocities() {
    	return new int[] {leftMaster.getSensorCollection().getQuadratureVelocity(), rightMaster.getSensorCollection().getQuadratureVelocity()};
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

//		moveSign = -1;
//		if (moveValue < 0) {
//			moveSign = 1;
//		}
//
//		rotateSign = 1;
//		if (rotateValue < 0) {
//			rotateSign = -1;
//		}
		
//		if (speedHalved) {
//			moveValue *= Config.JOYSTICK_HALF_MOVE_MULTIPLIER;
//			rotateValue *= Config.JOYSTICK_HALF_ROTATE_MULTIPLIER;
//		}

		//Apply squared inputs
		if(squaredInputs) {
			moveValue = Math.pow(moveValue, 2) * moveSign;
			rotateValue = Math.pow(rotateValue, 2) * rotateSign;
		}

//		//Check acceleraton limitation
//		moveValue = Math.abs(moveValue) * moveSign;
//		rotateValue = Math.abs(rotateValue) * rotateSign;

//		//Use a PID for acceleration control
//		movePID.setTarget(moveValue);
//		moveValue = movePID.calculate(previousMV);
//		previousMV = moveValue;
//		
//		turnPID.setTarget(rotateValue);
//		rotateValue = turnPID.calculate(previousRV);
//		previousRV = rotateValue;

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
		setLeftRightMotors(leftSpeed, rightSpeed);

//		//Calculate robot drive power draw
//		leftCurrent = pdp.getCurrent(0) + pdp.getCurrent(1) + pdp.getCurrent(2);
//		rightCurrent = pdp.getCurrent(13) + pdp.getCurrent(14) + pdp.getCurrent(15);

//		Timer.delay(0.005); //wait for a motor update time //Is this even necessary?
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
}

