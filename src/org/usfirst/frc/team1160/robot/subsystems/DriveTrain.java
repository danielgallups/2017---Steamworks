package org.usfirst.frc.team1160.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;

import org.usfirst.frc.team1160.robot.OI;
import org.usfirst.frc.team1160.robot.RobotMap;
import org.usfirst.frc.team1160.robot.commands.drive.ManualDrive;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

public class DriveTrain extends Subsystem implements RobotMap{

	public static DriveTrain instance;
	private CANTalon frontLeft, frontRight, backLeft, backRight;
	private Timer time;
	private double maxRightVelocity, maxLeftVelocity;

	
	public static DriveTrain getInstance(){
		if (instance == null){
			instance = new DriveTrain();
		}
		return instance;
	}
	
	private DriveTrain(){
		time = new Timer();
		frontLeft  = new CANTalon(DT_FRONTLEFT);
		backLeft = new CANTalon(DT_BACKLEFT);
		frontRight = new CANTalon(DT_FRONTRIGHT);
		backRight = new CANTalon(DT_BACKRIGHT);
		
		frontLeft.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		frontRight.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		frontLeft.configEncoderCodesPerRev(DT_LEFT_ENC_COUNT_COMP);
		frontRight.configEncoderCodesPerRev(DT_RIGHT_ENC_COUNT_COMP);
		
		maxRightVelocity = 0;
		maxLeftVelocity = 0;
		setManual();

	}
	
	/*
	 * Talon Mode change functions
	 */
	public void setAuto(){
		resetPos();

		frontLeft.setProfile(1);
		frontRight.setProfile(1);

		
		System.out.println("Talons set to autonomous mode.");
		frontLeft.changeControlMode(CANTalon.TalonControlMode.Position);
		frontRight.changeControlMode(CANTalon.TalonControlMode.Position);
		frontRight.reverseSensor(false);
		
		
		frontLeft.setVoltageRampRate(2.85);
		frontRight.setVoltageRampRate(3);
		frontLeft.configMaxOutputVoltage(4);
		frontRight.configMaxOutputVoltage(4);
		
		
	}
	
	public void setManual(){
		frontLeft.setProfile(0);
		frontRight.setProfile(0);
		
		frontLeft.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		frontRight.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		backLeft.changeControlMode(CANTalon.TalonControlMode.Follower);
		backRight.changeControlMode(CANTalon.TalonControlMode.Follower);
		frontRight.reverseSensor(false);
	
		frontLeft.setVoltageRampRate(100);
		frontRight.setVoltageRampRate(100);
		frontLeft.configMaxOutputVoltage(13);
		frontRight.configMaxOutputVoltage(13);
		
		
		System.out.println("DT set to Manual" );
	}
	
	public void setSlowManual(){
		frontLeft.setProfile(0);
		frontRight.setProfile(0);
		
		frontLeft.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		frontRight.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		backLeft.changeControlMode(CANTalon.TalonControlMode.Follower);
		backRight.changeControlMode(CANTalon.TalonControlMode.Follower);
		frontRight.reverseSensor(false);
	
		frontLeft.setVoltageRampRate(100);
		frontRight.setVoltageRampRate(100);
		frontLeft.configMaxOutputVoltage(6);
		frontRight.configMaxOutputVoltage(6);
		
		
		System.out.println("DT set to Manual" );
	}
	
	public void setSlowPositionMode(){
		resetPos();

		
		frontLeft.setVoltageRampRate(1.5);
		frontRight.setVoltageRampRate(1.5);
		frontLeft.configMaxOutputVoltage(2);
		frontRight.configMaxOutputVoltage(2);
		

	}
	/*
	 * Basic Autonomous Functions
	 */
	
	public void resetPos(){
		System.out.println("Talon positions set to 0.");
		frontLeft.setEncPosition(0);
		frontRight.setEncPosition(0);
	}
	
	public void set(double speed){
		frontLeft.set(speed);
		frontRight.set(speed);
	}
	
	public void driveDistance(double distance){
		// TODO After Closed Loop Control Works, tune this
		frontLeft.set(distance);
		frontRight.set(-(distance*0.97));
	}
	
	public void turnAngle(double angle){
		resetPos();
		
		double arcLength = (angle/360)*DT_TURN_CIRC_FT;
		double targetPosition = arcLength/DT_WHEEL_CIRC_FT;
		
		frontLeft.set(-targetPosition);
		frontRight.set(-targetPosition);
	}
	
	public boolean isDone(double setPoint){
		if(Math.abs(Math.abs(frontLeft.get())-setPoint)<(setPoint*0.1) && Math.abs(Math.abs(frontRight.get())-setPoint)<(setPoint*0.1)){
			return true;
		}
		return false;
	}

	
	/*
	 * Timing Functions
	 */
	public void startTime(){
		time.reset();
		time.start();
	}
	
	public double getTime(){
		return(time.get());
	}
	
	public boolean timeDone (double var){
		return time.get() > var;
	}
	
	/*
	 * Basic Teleop Functions
	 */
	public void manualDrive(){
		frontLeft.set((OI.getInstance().getStick().getZ() - OI.getInstance().getStick().getY()));
		backLeft.set(frontLeft.getDeviceID());
		frontRight.set((505.0/535.0)*(OI.getInstance().getStick().getZ() + OI.getInstance().getStick().getY()));
		//frontRight.set((OI.getInstance().getStick().getZ() + OI.getInstance().getStick().getY()));
		backRight.set(frontRight.getDeviceID());
		
		//System.out.println("Left Drive Position: " + frontLeft.getPosition()*DT_WHEEL_CIRC_FT + " feet.");
		//System.out.println("Right Drive Position: " + frontRight.getPosition()*DT_WHEEL_CIRC_FT + " feet.");

		//if(frontLeft.getSpeed() != 0)
			//you want output that looks like: time, (left/right), fps (I think)
			//System.out.println("Left Drive Velocity in ft: " + frontLeft.getSpeed()*DT_WHEEL_CIRC_FT);
		//if(frontRight.getSpeed() != 0)
			//System.out.println("Right Drive Velocity in ft: " + frontRight.getSpeed()*DT_WHEEL_CIRC_FT);
		if(frontRight.getSpeed()*DT_WHEEL_CIRC_FT>maxRightVelocity)
			maxRightVelocity = frontRight.getSpeed()*DT_WHEEL_CIRC_FT;
		
		if(frontLeft.getSpeed()*DT_WHEEL_CIRC_FT<maxLeftVelocity)
			maxLeftVelocity = frontLeft.getSpeed()*DT_WHEEL_CIRC_FT;
		System.out.println("Max Left Drive Velocity in ft: " + maxLeftVelocity);
		System.out.println("Max Right Drive Velocity in ft: " + maxRightVelocity);
	}
	public void slowManualDrive(){
		frontLeft.set(0.35*(OI.getInstance().getStick().getZ() - OI.getInstance().getStick().getY()));
		backLeft.set(frontLeft.getDeviceID());
		frontRight.set(0.35*(505.0/535.0)*(OI.getInstance().getStick().getZ() + OI.getInstance().getStick().getY()));
		backRight.set(frontRight.getDeviceID());
		
		//System.out.println("Left Drive Position: " + frontLeft.getPosition()*DT_WHEEL_CIRC_FT + " feet.");
		//System.out.println("Right Drive Position: " + frontRight.getPosition()*DT_WHEEL_CIRC_FT + " feet.");

		//if(frontLeft.getSpeed() != 0)
			//you want output that looks like: time, (left/right), fps (I think)
			//System.out.println("Left Drive Velocity in ft: " + frontLeft.getSpeed()*DT_WHEEL_CIRC_FT);
		//if(frontRight.getSpeed() != 0)
			//System.out.println("Right Drive Velocity in ft: " + frontRight.getSpeed()*DT_WHEEL_CIRC_FT);
		
	}
	
	public void printPosition(){
		System.out.println("Left Position: " + frontLeft.getPosition());
		System.out.println("Right Position: " + frontRight.getPosition());

	}
	
	public void setLeft(double leftOutput) {
		frontLeft.set(leftOutput);	
	}

	public void setRight(double rightOutput) {
		frontRight.set(rightOutput);
	}
	
	public void logData(){
		SmartDashboard.putString("DT: ",getTime() +
				 " " + DT_WHEEL_CIRC_FT*getLeftSpeed()/60.0 +
				 " " + DT_WHEEL_CIRC_FT*getLeftPosition() +
				 " " + DT_WHEEL_CIRC_FT*getRightSpeed()/60.0 +
				 " " + DT_WHEEL_CIRC_FT*getRightPosition()
				 );
	}
	
	public double getLeftPosition(){
		return frontLeft.getPosition();
	}
	
	public double getRightPosition(){
		return frontRight.getPosition();
	}
	
	public double getLeftSpeed(){
		return frontLeft.getSpeed();
	}
	
	public double getRightSpeed(){
		return frontRight.getSpeed();
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new ManualDrive());
	}

	
}
