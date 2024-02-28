// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Auto.Auto_express;
import frc.robot.commands.Auto.Right_auto;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.TankDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  //private final Intake s_intake = new Intake();
  private final TankDrive s_drive = new TankDrive();
  private final Intake s_intake = new Intake();
  private final CommandXboxController m_driverController = new CommandXboxController(OperatorConstants.kDriverControllerPort);
  
  Trigger intake= m_driverController.b();
  
  Trigger armPositive = m_driverController.x();
  Trigger armNegative= m_driverController.y(); 

  Trigger intakeOut = m_driverController.leftTrigger();
  Trigger shooter = m_driverController.rightTrigger(); 

  SendableChooser<Command> chooser = new SendableChooser<>();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    s_drive.setDefaultCommand(new RunCommand(() -> s_drive.drive(-m_driverController.getLeftY(), m_driverController.getRightX()), s_drive));

    chooser.addOption("Auto_center", new Auto_express(s_drive, s_intake));
    SmartDashboard.putData(chooser);
    configureBindings();
  }


  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
     //-----SHOOTER----//
    shooter.onTrue(new RunCommand(()-> s_intake.shooter(1),s_intake));
    shooter.onFalse(new RunCommand(()->s_intake.shooter(0),s_intake));

    //-----INTAKE----//
    intake.onTrue(new InstantCommand(()-> s_intake.intake(-.3),s_intake));
    intake.onFalse(new InstantCommand(()-> s_intake.intake(0),s_intake));

    intakeOut.onTrue(new InstantCommand(()-> s_intake.intake(.3),s_intake));
    intakeOut.onFalse(new InstantCommand(()-> s_intake.intake(0),s_intake));

    //-----ARM----//
    armPositive.onTrue(new InstantCommand(()-> s_intake.arm(.3),s_intake));
    armPositive.onFalse(new InstantCommand(()-> s_intake.arm(0),s_intake));
    armNegative.onTrue(new InstantCommand(()-> s_intake.arm(-.3),s_intake));
    armNegative.onFalse(new InstantCommand(()-> s_intake.arm(0),s_intake));
    
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return new Right_auto(s_drive, s_intake);//chooser.getSelected();
  }
}
