package org.firstinspires.ftc.teamcode.opmode.auton.blueBuildingFoundationPark;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Drivetrain;
import org.firstinspires.ftc.teamcode.hardware.Hook;

@Autonomous(name="Blue-Building-Foundation-BackPark")
public class BBFbP extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private Drivetrain drive = new Drivetrain(this);
    private Hook hook = new Hook(this);

    @Override
    public void runOpMode() throws InterruptedException {
        drive.init(hardwareMap);
        hook.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        // facing parallel to bridge
        hook.unhook();
        sleep(1000);

        //get completely in front of foundation
        drive.strafe(0.4, 0.6);
        sleep(800);
        drive.setPower(0, 0, 0, 0);
        sleep(500);

        //drive towards foundation
        drive.driveToPos(31, 0.3);
        sleep(1000);

        // clamp down on foundation
        hook.hook();
        sleep(1000);

        // drive back
        drive.driveToPos(32.5, -0.3);
        sleep(1000);

        //unhook
        hook.unhook();
        sleep(1000);

        //park under bridge
        drive.strafe(-0.4, 2.2);
        sleep(3500);
        drive.setPower(0, 0, 0, 0);
        }
    }
