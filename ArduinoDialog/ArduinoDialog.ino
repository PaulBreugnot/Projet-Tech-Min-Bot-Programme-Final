#include <SoftwareSerial.h>
#include "CaptorSet.h"
#include "Motors.h"

// XBee's DOUT (TX) is connected to pin 11 (Arduino's Software RX)
// XBee's DIN (RX) is connected to pin 12 (Arduino's Software TX)
SoftwareSerial XBee(11, 12); // RX, TX

char RIGHT_MOTOR_UP[5] = {'R', 'M', 'U', 'P', '\0'};
char LEFT_MOTOR_UP[5] = {'L', 'M', 'U', 'P', '\0'};
char RIGHT_MOTOR_DOWN[5] = {'R', 'M', 'D', 'W', '\0'};
char LEFT_MOTOR_DOWN[5] = {'L', 'M', 'D', 'W', '\0'};
char CAPTORS[5] = {'C', 'P', 'T', 'R', '\0'};
char STOP[5] = {'S', 'T', 'O', 'P', '\0'};

void setup()
{
  // Set up both ports at 115200 baud. This value is most important
  // for the XBee. Make sure the baud rate matches the config
  // setting of your XBee.
  XBee.begin(115200);
  Serial.begin(115200);
  initCaptors();
  initMotors();
}


void loop() {
  if (XBee.available() > 3) {
    //Assuming orders are always given with 4 characters
    char receivedQuerry[5] = {' ', ' ', ' ', ' ', '\0'};
    for (int i = 0; i < 4; i++) {
      receivedQuerry[i] = XBee.read();
    }
    Serial.println(receivedQuerry);
    //
    if (strcmp(CAPTORS, receivedQuerry) == 0) {
      delay(200);
      int * Distances;
      Distances = getDistances();
      char buf[25];
      sprintf(buf, "%04i-%04i-%04i-%04i-%04i\0", Distances[0], Distances[1], Distances[2], Distances[3], Distances[4]);
      Serial.println(buf);
      XBee.write(buf);
    }
    else if (strcmp(RIGHT_MOTOR_UP, receivedQuerry) == 0) {
      Serial.println("OK");
      SpeedUpRightMotor(10);
    }
    else if (strcmp(LEFT_MOTOR_UP, receivedQuerry) == 0) {
      Serial.println("OK");
      SpeedUpLeftMotor(10);
    }
    else if (strcmp(RIGHT_MOTOR_DOWN, receivedQuerry) == 0) {
      Serial.println("OK");
      SlowDownRightMotor(10);
    }
    else if (strcmp(LEFT_MOTOR_DOWN, receivedQuerry) == 0) {
      Serial.println("OK");
      SlowDownLeftMotor(10);
    }
    else if (strcmp(STOP, receivedQuerry) == 0) {
      Serial.println("OK");
      fullStop();
    }
  }
}
