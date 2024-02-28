// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.TankDrive;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Auto_beta extends SequentialCommandGroup {
  /** Creates a new Auto_neta. */
  public Auto_beta(TankDrive s_drive,Intake s_intake) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new RunCommand(()->s_intake.shooter(1),s_intake).withTimeout(1),
      new InstantCommand(()->s_intake.intake(-1), s_intake).withTimeout(1),
        new InstantCommand(()->s_intake.shooter(0),s_intake),
      new InstantCommand(()->s_intake.intake(0), s_intake),
      new InstantCommand(()->s_intake.arm(-0.3), s_intake).withTimeout(0.3),
      new DriveToDistance(s_drive, -2).alongWith(
      new InstantCommand(() -> s_intake.intake(0.7), s_intake).withTimeout(0.3)),
      new InstantCommand(() -> s_intake.intake(0), s_intake),
      new InstantCommand(() -> s_intake.arm(0.3), s_intake).withTimeout(0.4),
      new DriveToDistance(s_drive, 0.75),
      new RunCommand(()->s_intake.shooter(1),s_intake).withTimeout(1),
      new InstantCommand(()->s_intake.intake(-1), s_intake).withTimeout(1)
    );
  }
}