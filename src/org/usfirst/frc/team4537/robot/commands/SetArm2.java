//package org.usfirst.frc.team4537.robot.commands;
//
//import org.usfirst.frc.team4537.robot.Robot;
//import org.usfirst.frc.team4537.robot.enums.ArmPosition;
//
//import edu.wpi.first.wpilibj.command.Command;
//
///**
// *
// */
//public class SetArm2 extends Command {
//
//	ArmPosition position;
//	
//    public SetArm2(ArmPosition position) {
//        // Use requires() here to declare subsystem dependencies
//        requires(Robot.arm);
//        this.position = position; 
//    }
//
//    // Called just before this Command runs the first time
//    protected void initialize() {
//    	Robot.arm.requestArmPositioner(position);
//    }
//
//    // Called repeatedly when this Command is scheduled to run
//    protected void execute() {
//    	Robot.arm.controlArmPositioner();
//    }
//
//    // Make this return true when this Command no longer needs to run execute()
//    protected boolean isFinished() {
//        return Robot.arm.armPositionerCompleted();
//    }
//
//    // Called once after isFinished returns true
//    protected void end() {
//    }
//
//    // Called when another command which requires one or more of the same
//    // subsystems is scheduled to run
//    protected void interrupted() {
//    }
//}
