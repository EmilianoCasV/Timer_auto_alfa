// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.TankDrive;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DriveToDistance extends PIDCommand {
  /** Creates a new DriveToDistance. */
  public DriveToDistance(TankDrive drive,double setpoint) {
    super(
        // The controller that the command will use
        new PIDController(1, 0.2, 0),
        // This should return the measurement
        drive::average_distance,
        // This should return the setpoint (can also be a constant)
        setpoint,
        // This uses the output
        output -> {
          // Use the output here
          drive.drive(output, 0);
        });
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drive);
    // Configure additional PID options by calling `getController` here.
    getController().setTolerance(0.15);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
