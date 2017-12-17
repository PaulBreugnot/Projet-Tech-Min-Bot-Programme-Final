//To pilot Servos
#include <Servo.h>

//Servos definition
Servo rightServo;
Servo leftServo;

static int rightMotorSpeed = 90;
static int leftMotorSpeed = 90;

void initMotors() {
  //Setup Servos outputs
  rightServo.attach(9);
  rightServo.write(90);  // set servo to mid-point

  leftServo.attach(10);
  leftServo.write(90);  // set servo to mid-point
}

//ServoMotors are commanded with a speed that is included between 0 and 180, with 90 behing the fix point.
//So the speed up or down notions depend of the orientations of the robot's motors.

void SpeedUpRightMotor(int increment) {
  if (rightMotorSpeed - increment >= 0) {
    rightMotorSpeed = rightMotorSpeed - increment;
    Serial.println(rightMotorSpeed);
    rightServo.write(rightMotorSpeed);
  }
  else {
    rightMotorSpeed = 0;
    Serial.println(rightMotorSpeed);
    rightServo.write(rightMotorSpeed);
  }
}

void SpeedUpLeftMotor(int increment) {
  if (leftMotorSpeed + increment <= 180) {
    leftMotorSpeed = leftMotorSpeed + increment;
    leftServo.write(leftMotorSpeed);
  }
  else {
    leftMotorSpeed = 180;
    leftServo.write(leftMotorSpeed);
  }
}

void SlowDownRightMotor(int increment) {
  if (rightMotorSpeed + increment <= 180) {
    rightMotorSpeed = rightMotorSpeed + increment;
    rightServo.write(rightMotorSpeed);
  }
  else {
    rightMotorSpeed = 180;
    rightServo.write(rightMotorSpeed);
  }
}

void SlowDownLeftMotor(int increment) {
  if (leftMotorSpeed - increment >= 0) {
    leftMotorSpeed = leftMotorSpeed - increment;
    leftServo.write(leftMotorSpeed);
  }
  else {
    leftMotorSpeed = 0;
    leftServo.write(leftMotorSpeed);
  }
}

void fullStop(){
  rightServo.write(90);
  leftServo.write(90);
}

