// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  private final CANSparkMax ShooterMotor = new CANSparkMax(6, MotorType.kBrushless); 
  private final CANSparkMax ShooterMotor2 = new CANSparkMax(5, MotorType.kBrushless); //
  private final TalonFX intake = new TalonFX(8);
  private final CANSparkMax wristMotor = new CANSparkMax(3, MotorType.kBrushless); 

  private final ColorSensorV3 sensor = new ColorSensorV3(I2C.Port.kOnboard);
  double IR = sensor.getIR();
  double proximity = sensor.getProximity();

  private final DutyCycleEncoder wristEncoder = new DutyCycleEncoder(2);
  private final RelativeEncoder leftShooter = ShooterMotor.getEncoder();
  private final RelativeEncoder rightShooter = ShooterMotor2.getEncoder();

  /** Creates a new Intake. */
  public Intake() {
        wristEncoder.reset();
    ShooterMotor.restoreFactoryDefaults();
    ShooterMotor.setIdleMode(IdleMode.kCoast);
   wristMotor.setIdleMode(IdleMode.kBrake);
    ShooterMotor.setInverted(false);
    ShooterMotor2.setInverted(true);

    wristEncoder.setDistancePerRotation(2*Math.PI);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("WRIST", wristEncoder.getDistance());
    SmartDashboard.putNumber("Left Shoot Vel", leftShooter.getVelocity());
    SmartDashboard.putNumber("Rught Shoot Vel", rightShooter.getVelocity());

    SmartDashboard.putNumber("IR", IR);
    SmartDashboard.putNumber("Proximity", proximity);
    // This method will be called once per scheduler run
  }
  public void shooter(double speed)
  {
    ShooterMotor.set(speed);
    ShooterMotor2.set(speed);
    }

  public void intake(double output){
  intake.set(output);
   
  }
    public void arm(double output){
      wristMotor.set(output);
    }
}
