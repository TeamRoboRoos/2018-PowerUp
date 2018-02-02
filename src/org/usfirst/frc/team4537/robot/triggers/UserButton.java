package org.usfirst.frc.team4537.robot.triggers;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class UserButton extends Trigger {

    public boolean get() {
        return RobotController.getUserButton();
    }
}
