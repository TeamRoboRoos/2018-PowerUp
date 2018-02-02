package org.usfirst.frc.team4537.robot.triggers;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class EndGame extends Trigger {

    public boolean get() {
    	double matchTime = DriverStation.getInstance().getMatchTime();
    	boolean opControl = DriverStation.getInstance().isOperatorControl();
    	boolean endGame = matchTime <= 30;
    	boolean teleopStarted = matchTime != -1;
    	return (opControl && endGame && teleopStarted);
    }
}
