package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Acquirer;
import org.firstinspires.ftc.teamcode.hardware.Claw;
import org.firstinspires.ftc.teamcode.hardware.Drivetrain;
import org.firstinspires.ftc.teamcode.hardware.Hook;
import org.firstinspires.ftc.teamcode.hardware.Lift;
import org.firstinspires.ftc.teamcode.hardware.Lock;
import org.firstinspires.ftc.teamcode.hardware.Picker;

@TeleOp(name="TeleMain", group="Teleop")
public class TeleMain extends LinearOpMode {

    private Drivetrain drive = new Drivetrain(this);
    private Claw claw = new Claw(this);
    private Lift lift = new Lift(this);
    private Hook hook = new Hook(this);
    private Lock lock = new Lock(this);
    private Acquirer acquirer = new Acquirer(this);
    private Picker picker = new Picker(this);

    @Override
    public void runOpMode() throws InterruptedException{
        drive.init(hardwareMap);
        claw.init(hardwareMap);
        hook.init(hardwareMap);
        lift.init(hardwareMap);
        lock.init(hardwareMap);
        acquirer.init(hardwareMap);
        picker.init(hardwareMap);

        while(!opModeIsActive() && !isStopRequested()){
            telemetry.addData("Status", "Waiting in init");
            telemetry.update();
        }

        while(opModeIsActive()){
            double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
            double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
            double rightX = gamepad1.right_stick_x;

            drive.teleDrive(r, robotAngle, rightX);

            if (gamepad1.dpad_up) {
                if(lock.getLocked()) lock.unlock();
                else lock.lock();
            }

            if(gamepad1.dpad_down) drive.reverse();

            if(gamepad1.right_bumper) acquirer.teleIntake(1);
            else if (gamepad1.left_bumper) acquirer.teleOuttake(1);
            else acquirer.stop();

            if (gamepad1.a) claw.close();
            if (gamepad1.b) claw.open();
            if (gamepad1.x) claw.back();
            if (gamepad1.y) claw.front();

            if (gamepad1.dpad_left) hook.hook();
            if (gamepad1.dpad_right) hook.unhook();

            if (gamepad1.left_trigger > 0) lift.liftDown(gamepad1.left_trigger);
            else if (gamepad1.right_trigger > 0 ) lift.liftUp(gamepad1.right_trigger);
            else lift.liftUp(0);

            telemetry.addData("slow mode", drive.getSlow());
            telemetry.addData("reverse mode", drive.getReverse());
            telemetry.addData("r", r);
            telemetry.addData("robot angle", robotAngle);
            telemetry.addData("rightX", rightX);
            telemetry.addData("a", gamepad1.a);
            telemetry.addData("b", gamepad1.b);
            telemetry.addData("l", gamepad1.left_bumper);
            telemetry.addData("r", gamepad1.right_bumper);
            telemetry.addData("lt", gamepad1.left_trigger);
            telemetry.addData("rt", gamepad1.right_trigger);
            telemetry.update();
        }
    }
}
