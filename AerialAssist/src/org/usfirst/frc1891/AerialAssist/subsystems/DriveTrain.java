// RobotBuilder Version: 1.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.
package org.usfirst.frc1891.AerialAssist.subsystems;
import edu.wpi.first.wpilibj.CANJaguar;
import org.usfirst.frc1891.AerialAssist.RobotMap;
import org.usfirst.frc1891.AerialAssist.commands.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class DriveTrain extends Subsystem {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    CANJaguar master1 = RobotMap.driveTrainmaster1;
    CANJaguar master2 = RobotMap.driveTrainmaster2;
    CANJaguar slave1 = RobotMap.driveTrainslave1;
    CANJaguar slave2 = RobotMap.driveTrainslave2;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public DriveTrain() {
          LiveWindow.addActuator("Drive Train", "Dual Jaguar 1", RobotMap.dualJag1);
          LiveWindow.addActuator("Drive Train", "Dual Jaguar 2", RobotMap.dualJag2);
    }
  
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
        setDefaultCommand(new DriveUsingJoysticks());
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void driveUsingVoltage(double leftValue, double rightValue) {
        RobotMap.dualJag1.driveUsingVoltage(leftValue);
        RobotMap.dualJag2.driveUsingVoltage(rightValue);
    }
    
    public void driveUsingSpeed(double leftValue, double rightValue) {
        RobotMap.dualJag1.driveUsingSpeed(leftValue);
        RobotMap.dualJag2.driveUsingSpeed(rightValue);
    }
    
    public void driveUsingPosition(double leftValue, double rightValue) {
        RobotMap.dualJag1.driveUsingPosition(leftValue);
        RobotMap.dualJag2.driveUsingPosition(rightValue);
    }
    
    public void stopDriving() {
       RobotMap.dualJag1.stop();
       RobotMap.dualJag2.stop();
    }
}
