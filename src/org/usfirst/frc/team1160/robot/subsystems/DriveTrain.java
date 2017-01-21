package org.usfirst.frc.team1160.robot.subsystems;

import org.usfirst.frc.team1160.robot.OI;
import org.usfirst.frc.team1160.robot.RobotMap;
import org.usfirst.frc.team1160.robot.commands.drive.HumanDrive;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem implements RobotMap
{
	public static DriveTrain instance;
	protected CANTalon frontLeft, backLeft, frontRight, backRight;
	private Timer time;
	private DriveTrain()
	{
		time = new Timer();
		frontLeft  = new CANTalon(DT_FRONTLEFT);
		frontLeft.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		backLeft = new CANTalon(DT_BACKLEFT);
		backLeft.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		frontRight = new CANTalon(DT_FRONTRIGHT);
		backRight = new CANTalon(DT_BACKRIGHT);
		
		
	}
	public static DriveTrain getInstance()
	{
		if (instance == null)
		{
			instance = new DriveTrain();
		}
		return instance;
	}
	public void startTime()
	{
		time.reset();
		time.start();
	}
	public boolean timeDone (double var)
	{
		return time.get() > var;
	}
	public void manualDrive()
	{
		frontLeft.set(.7*(OI.getInstance().getStick().getZ() - OI.getInstance().getStick().getY()));
		backLeft.set(.7*(OI.getInstance().getStick().getZ() - OI.getInstance().getStick().getY()));
		frontRight.set(.7*(OI.getInstance().getStick().getZ() + OI.getInstance().getStick().getY()));
		backRight.set(.7*(OI.getInstance().getStick().getZ() + OI.getInstance().getStick().getY()));
	}
	public void resetPos()
	{
		//System.out.println("Talon positions set to 0.");
		backLeft.setPosition(0);
		backRight.setPosition(0);
	}
	public void setAuto()
	{
		resetPos();
		backLeft.configMaxOutputVoltage(9);
		backRight.configMaxOutputVoltage(9);
		
	}
	public void setManual()
	{
		
	}
	protected void initDefaultDrive()
	{
		//setDefaultCommand(new HumanDrive());
	}
	protected void initDefaultCommand()
	{
		
	}
	public void driveDistance(double distance)
	{
		
	}
}