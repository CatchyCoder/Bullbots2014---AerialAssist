// RobotBuilder Version: 1.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc1891.AerialAssist.commands;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;
import org.bullbots.core.Jaguar;
import org.usfirst.frc1891.AerialAssist.Robot;
import org.usfirst.frc1891.AerialAssist.RobotMap;

    /*
    Autonomous:
    1: Robot will start facing one of the sides,
    start loading and looking for tape instantly
    2: Record if we can see the tape
    3: Straiten the robot up, and drive a certain distance
    4: Turn towards the side that didn't have its tape lit
    5: Fire.. then reload and tilt the shooter down
    */

/**
 * @author Clay Kuznia
 */
public class  AutonomousCommand extends Command {
    
    private Robot robot;
    
    private final double 
            // The maximum amount of times to check tape at the start
            // of autonomous before it will move on to the next step
            MAX_TAPE_CHECK_COUNT = 10,
            
            // Turning
            TURN_DISTANCE = 0.05,
            TURN_SPEED = 120,
            
            // Moving forward
            DISTANCE = 0.75,
            FORWARD_SPEED = 180,
            
            // Turning the second time
            TURN_DISTANCE_2 = 0.04,
            TURN_SPEED_2 = 180;
    
    private boolean 
            isDone = false,
            tableReady = false,
            
            // Tape finding
            isLookingForTape = true,
            tapeFound = false,
            
            // Turning
            straight = false,
            
            // Going straight
            inPosition = false,
            
            // Turning again
            readyToFire = false,
            hasFired = false;
    
    private int tapeCheckCount = 0;
    private double startPosition;
    
    public AutonomousCommand(Robot robot) {
        this.robot = robot;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
	
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.shooter);
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
        // Setting the mode on the table
        robot.getTable().putString("robotMode", "AUTO");
        resetStartPos();
        
        // Setting values to ensure they are set correctly
        resetValues();
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        try {
            Robot.shooter.update();
            // If finished with autonomous, don't do anything
            if(isDone) return;
            
            
            
            
            // Rounding the value in order not to overload the cRIO
            double roundedCurrentPos = Math.abs(Jaguar.roundValue(RobotMap.driveJags2.getMasterJag().getPosition()));
            
            // Waiting until the NetworkTable values are actually initialized
            // because there is a good chance that there will be a delay
            // before they are set onto the NetworkTable
            if(!tableReady) {
                System.out.println("Step 1");
                // Attempting to get values from the NetworkTable
                try {
                    tapeFound = robot.getTable().getBoolean("tapefound");
                    tableReady = true;
                    System.out.println("\tSUCCESS reading tapeFound");
                }
                catch(TableKeyNotDefinedException e) {
                    e.printStackTrace();
                    tableReady = false;
                    System.out.println("\tFAILED reading tapeFound");
                }
            }
            // Robot is looking for goal tape
            else if(isLookingForTape) {
                System.out.println("Step 2");
                // If the tape has been found
                if(robot.getTable().getBoolean("tapefound")) {
                    System.out.println("\tTape found");
                    tapeFound = true;
                    isLookingForTape = false;
                    
                    // Resetting the start position again, to ensure
                    // that it is really reset
                    resetStartPos();
                }
                // If the robot has not checked for tape at least
                // a certain amount of times, and the tape has not
                // been found, keep checking for tape
                else if(tapeCheckCount < MAX_TAPE_CHECK_COUNT) {
                    System.out.println("\tLooking for tape");
                    tapeCheckCount++;
                }
                // Otherwise stop checking for tape and begin the next step
                else {
                    System.out.println("\tDTape NOT found");
                    tapeFound = isLookingForTape = false;
                    
                    // Resetting the start position again, to ensure
                    // that it is really reset
                    resetStartPos();
                }
            }
            // Robot is straightening up
            /*else if(!straight) {
                //System.out.println("\tStraightening up : dif = " + (Math.abs(roundedCurrentPos - startPosition)));
                
                // If we are not straight
                if(Math.abs(roundedCurrentPos - startPosition) < TURN_DISTANCE) {
                    System.out.println("\t\tTurning : ");
                    straight = false;
                    // Turning the robot clockwise (right)
                    Robot.driveTrain.driveUsingSpeed(TURN_SPEED, TURN_SPEED);
                }
                // Otherwise the robot is now straight, begin the next step
                else {
                    Robot.driveTrain.stop();
                    straight = true;
                    //System.out.println("\tStart pos: " + startPosition);
                    //System.out.println("\tReal pos: " + RobotMap.driveJags2.getMasterJag().getPosition());
                    System.out.println("\tDONE Straightening up");
                    
                    resetStartPos();
                }
            }
            // Robot is moving forward a certain distance
            */else if(!inPosition) {
                System.out.println("Step 3");
                
                // If not in position
                if(Math.abs(roundedCurrentPos - startPosition) < DISTANCE) {
                    Robot.driveTrain.driveUsingSpeed(FORWARD_SPEED, -FORWARD_SPEED);
                    inPosition = false;
                    System.out.println("\tDriving into position");
                }
                // Otherwise, we are in position so stop and move to the next step
                else {
                    Robot.driveTrain.stop();
                    inPosition = true;
                    System.out.println("\tIn position");
                    
                    resetStartPos();
                }
            }
            // Robot in in position and will now turn towards the correct goal and fire
            else if(!hasFired) {
                System.out.println("Step 4");
                // If we are not facing the goal yet
                if(!readyToFire) {
                    // Check if we are done turning
                    if(Math.abs(roundedCurrentPos - startPosition) < TURN_DISTANCE_2) {
                        readyToFire = false;
                        
                        // Turning toward the correct goal...
                        
                        // Turning toward right goal (clockwise)
                        if(tapeFound) {
                            Robot.driveTrain.driveUsingSpeed(TURN_SPEED_2, TURN_SPEED_2);
                            System.out.println("\tTurning LEFT");
                        }
                        // Turning toward left goal (counter-clockwise)
                        else {
                            Robot.driveTrain.driveUsingSpeed(-TURN_SPEED_2, -TURN_SPEED_2);
                            System.out.println("\tTurning RIGHT");
                        }
                    }
                    // We are done turning, stop the drive train
                    else {
                        Robot.driveTrain.stop();
                        
                        // If its done loading
                        if(Robot.shooter.isLoaded()){
                            System.out.println("Loaded");
                            readyToFire = true;
                        }   
                    }
                }
                // Otherwise fire at the goal
                else {
                    System.out.println("FIRING!!!");
                    Robot.shooter.setLoading(false);
                    Robot.shooter.setLoaded(false);
                    Robot.shooter.setShooting(true);
                    readyToFire = false;
                    hasFired = true;
                }
            }
            // Robot will now finish up Autonomous by turning around,
            // tilting the shooter, and loading
            else {
                // Now turn the robot around, load, and tilt the shooter down
                System.out.println("Ready to turn around, load, and tilt...");
                
                // (Shooter will automatically load) so just do tilting and turning
                
                
                // DO THIS WHEN FINISHED WITH AUTO
                isDone = true;
                
            }
            
            
            
            
            
            
            
            
            
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private void resetStartPos() {
        try {
            // Resetting the start position so we can use it for going forward
            startPosition = Math.abs(Jaguar.roundValue(RobotMap.driveJags2.getMasterJag().getPosition()));
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
    
    public void resetValues() {
        // Resetting values
        isLookingForTape = true;
        isDone = tapeFound = straight = inPosition = readyToFire = hasFired = false;
    }
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        // Making sure we are done with autonomous and the robot has reloaded
        return (isDone && Robot.shooter.isLoaded());
    }
    
    // Called once after isFinished returns true
    protected void end() {
        resetValues();
    }
    
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
