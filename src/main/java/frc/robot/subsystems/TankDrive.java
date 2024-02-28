// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TankDrive extends SubsystemBase {
  private final CANSparkMax m_right_top =  new CANSparkMax(4, MotorType.kBrushless);
  private final CANSparkMax m_right_down =  new CANSparkMax(7, MotorType.kBrushless);

  private final CANSparkMax m_left_top = new CANSparkMax(2, MotorType.kBrushless);
  private final CANSparkMax m_left_down = new CANSparkMax(1, MotorType.kBrushless); 
  
  private final DifferentialDrive drive = new DifferentialDrive(m_left_top, m_right_top);

  private final AHRS navX = new AHRS(SPI.Port.kMXP);

  private final DutyCycleEncoder encoder_left = new DutyCycleEncoder(3);
  private final DutyCycleEncoder encoder_right = new DutyCycleEncoder(4);

  /** Creates a new TankDrive. */
  public TankDrive() {
    //--Follow--//
    m_right_down.follow(m_right_top);
    m_left_down.follow(m_left_top);

    //---IdleMODE---//
    
    m_right_top.setIdleMode(IdleMode.kBrake); 
    m_right_down.setIdleMode(IdleMode.kBrake);
    m_left_top.setIdleMode(IdleMode.kBrake);
    m_left_down.setIdleMode(IdleMode.kBrake);
    

    m_right_top.setInverted(true);
    m_right_down.setInverted(true);

    encoder_left.reset();
    encoder_right.reset();
    navX.reset();

    encoder_left.setDistancePerRotation(((Units.inchesToMeters(6) * Math.PI)));
    encoder_right.setDistancePerRotation(((Units.inchesToMeters(6) * Math.PI)));
    
   // odometry.resetPosition(navX.getRotation2d(), encoder_left.getDistance(), encoder_right.getDistance(), new Pose2d());
  }

  @Override
  public void periodic() {

    //var gyroAngle = navX.getRotation2d();    // variable de angulo navX
    //var m_pose = odometry.update(gyroAngle, encoder_left.getDistance(), encoder_right.getDistance());
   //-----------------//
   //SmartDashboard.putNumber("Pose X", this.getPose().getX());
   //SmartDashboard.putNumber("Pose Y", this.getPose().getY());
   SmartDashboard.putNumber("DIRECTION", this.getHeading());
   SmartDashboard.putNumber("Average Distance", average_distance());
   SmartDashboard.putNumber("ecoder_left",encoder_left.getDistance());
   SmartDashboard.putNumber("encoder_right", encoder_right.getDistance());
  }

  public void drive(double speed,double rot){
    drive.arcadeDrive(speed,rot);
  }

  public void autoDrive(double setpoint, double speed)
  {
    if(average_distance() == setpoint)
    {
      drive(0, 0);
    }
    else
    {
      drive(speed, 0);
    }
  }

  /*
  public Pose2d getPose(){
    return odometry.getPoseMeters();
  }
   */
  public double getHeading(){
    return navX.getRotation2d().getDegrees();
  }

  public double average_distance(){
    return ((-encoder_left.getDistance()+encoder_right.getDistance())/2.0);
  }

  public double stop_rot(double angle,double rot){
    if (angle == getHeading()) {
      rot = 0;
      return rot;
    }
    return rot;
  }
}
