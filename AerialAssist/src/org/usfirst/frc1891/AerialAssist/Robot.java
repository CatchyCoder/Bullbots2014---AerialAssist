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
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc1891.AerialAssist.commands.*;
import org.usfirst.frc1891.AerialAssist.subsystems.*;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    Command autonomousCommand;
    public static OI oi;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static Shooter shooter;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
    public static DriveTrain driveTrain;
    
    public static final double MAX_RPM = 240.0; // 120 RPMs = ~4 FPS
        
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
	RobotMap.init();
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        shooter = new Shooter();
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveTrain = new DriveTrain();
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        oi = new OI();
	
        // instantiate the command used for the autonomous period
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
        autonomousCommand = new AutonomousCommand();
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
    }
    public void autonomousInit() {
        // schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
//        Scheduler.getInstance().run();
    }
    public void teleopInit() {
	// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }
    
    int x = 0;
    boolean called = false;
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        
        /*
        Stuff i found out:
            - CANJaguars will keep running at the speed you set them at,
            they do NOT need to be reassigned their value.
        */
        
        Scheduler.getInstance().run();
        try {
            
            
            //RobotMap.driveJags1.getMasterJag().setPID(0.5, 0, 0.002);
            
            //RobotMap.driveJags1.driveUsingVoltage(0.25);
            //RobotMap.driveJags1.driveUsingSpeed(60);
            
            /*
            voltage : moves until stopped
            speed :
            position :
            current :
            */
            
            //int maxCurrent = 200;
            if(!called) {
                System.out.println("SPEED called on driveJags1");
                RobotMap.driveJags1.driveUsingSpeed(60);
                //called = true;
            }
            
            //int iterations = 55;
            
            /*if(x == 0) {
                System.out.println("\t\tspeed " + x);
                RobotMap.driveJags1.driveUsingSpeed(60);
            }
            else if (x == iterations){
                System.out.println("\t\tvoltage " + x);
                RobotMap.driveJags1.driveUsingVoltage(0.25);
            }
            else if (x == iterations * 2){
                System.out.println("\t\tposition " + x);
                RobotMap.driveJags1.driveUsingPosition(60);
            }
            else if (x == iterations * 3){
                System.out.println("\t\tcurrent " + x);
                RobotMap.driveJags1.driveUsingCurrent(100);
            }
            else if(x == iterations * 4) {
                RobotMap.driveJags1.stop();
            }
            x++;*/
            
            
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        
        
        
        
        /*
        
        
        OLD CODE IS COMMENTED OUT BELOW, ABOVE IS FOR TESTING ONLY
        
        
        */
        
        
        
        
        
        
        
        /*
        Scheduler.getInstance().run();
        
        //driveTrain.driveUsingVoltage(Robot.oi.joystickController1.getYAxis(), -Robot.oi.joystickController2.getYAxis());
        
        //if(!shooter.isCalibrated()) ;//Robot.shooter.calibrate();
        //else {
            //shooter.update();
            // Driving with joysticks
            //shooter.test();
            driveTrain.driveUsingSpeed(Robot.oi.joystickController1.getYAxis() * Robot.MAX_RPM, -Robot.oi.joystickController2.getYAxis() * Robot.MAX_RPM);
        //}
        
        
        */
    }
    /**
     * This function called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
        
        //RobotMap.winchJags.showPosition();
        
        // Continually driving the motors to the setpoint configured in the LiveWindow
        driveTrain.driveUsingSpeed(RobotMap.driveJags1.setPoint, RobotMap.driveJags2.setPoint);
    }
    public void testInit() {
         LiveWindow.setEnabled(true);
    }
}
