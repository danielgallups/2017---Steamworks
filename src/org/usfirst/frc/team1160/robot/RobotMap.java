package org.usfirst.frc.team1160.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public interface RobotMap
{
<<<<<<< HEAD
	//drivetrain
	public static final int DT_BACKRIGHT = 14;
	public static final int DT_FRONTRIGHT = 15;
	public static final int DT_BACKLEFT = 1;
	public static final int DT_FRONTLEFT = 0;
	
	//everything else
	public static final int turntable = 4;
	public static final int shooter = 5;
	public static final int climber = 6;
	public static final int pickup = 7;
	public static final int conveyor_belt = 8;
=======
	
	/*
	 * CAN Values
	 */
		public static final int DT_FRONTLEFT = 0;
		public static final int DT_FRONTRIGHT = 1;
		public static final int DT_BACKLEFT = 2;
		public static final int DT_BACKRIGHT = 3;
	
	/*
	 * Driving Constants
	 */	
		//Scales down DT speed
		public static final double DT_SCALE = 1;
		//Radius of Wheel
		public static final double DT_WHEEL_RAD_IN = 2; 
		public static final double DT_WHEEL_RAD_FT = DT_WHEEL_RAD_IN/12;
		//Diameter of Wheel
		public static final double DT_WHEEL_DIA_IN = 2*DT_WHEEL_RAD_IN; 
		public static final double DT_WHEEL_DIA_FT = 2*DT_WHEEL_RAD_FT;
		//Circumference of Wheel
		public static final double DT_WHEEL_CIRC_IN = DT_WHEEL_DIA_IN*Math.PI;
		public static final double DT_WHEEL_CIRC_FT = DT_WHEEL_DIA_FT*Math.PI;
>>>>>>> 45af4924e0f0be7b3df6c270e102adf15cff77ee
}