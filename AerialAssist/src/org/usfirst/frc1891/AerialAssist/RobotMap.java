// RobotBuilder Version: 1.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc1891.AerialAssist;
    
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.bullbots.core.DualJaguar;
import org.bullbots.core.Winch;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number
 * 
 * @author Clay Kuznia
 */
public class RobotMap {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static DigitalInput shooterloadSwitch;
    public static SpeedController shootershootMotor;
    public static DigitalInput shootershootSwitch;
    public static AnalogChannel shooterIRSensor1;
    public static AnalogChannel shooterpotentiometer;
    public static SpeedController shooterangleMotor;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
    public static DualJaguar driveJags1, driveJags2;
    public static Winch winchJags;
    
    public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        shooterloadSwitch = new DigitalInput(1, 7);
	LiveWindow.addSensor("Shooter", "loadSwitch", shooterloadSwitch);
        
        shootershootMotor = new Victor(1, 2);
	LiveWindow.addActuator("Shooter", "shootMotor", (Victor) shootershootMotor);
        
        shootershootSwitch = new DigitalInput(1, 6);
	LiveWindow.addSensor("Shooter", "shootSwitch", shootershootSwitch);
        
        shooterIRSensor1 = new AnalogChannel(1, 4);
	LiveWindow.addSensor("Shooter", "IR", shooterIRSensor1);
        
        shooterpotentiometer = new AnalogChannel(1, 3);
	LiveWindow.addSensor("Shooter", "potentiometer", shooterpotentiometer);
        
        //shooterangleMotor = new Victor(1, 1);
	//LiveWindow.addActuator("Shooter", "angleMotor", (Victor) shooterangleMotor);
        
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        
        // The default PID values
        final double
                defaultP = 0.5,
                defaultI = 0.0,
                defaultD = 0.05;
        
        driveJags1 = new DualJaguar(1, 2, defaultP, defaultI, defaultD);  // Real Robot: 1, 2  Second Robot: 5, 6
        driveJags2 = new DualJaguar(3, 4, defaultP, defaultI, defaultD);  // Real Robot: 3, 4  Second Robot: 3, 4
        winchJags = new Winch(6, 5, defaultP, defaultI, defaultD);        // Real Robot: 6, 5  Second Robot: 1, 2
        
        // Adding components to the live window
        LiveWindow.addActuator("Drive Train", "Dual Jaguar 1", RobotMap.driveJags1);
        LiveWindow.addActuator("Drive Train", "Dual Jaguar 2", RobotMap.driveJags2);
        LiveWindow.addActuator("Shooter", "Dual Jaguar 1", RobotMap.winchJags);
    }
}
