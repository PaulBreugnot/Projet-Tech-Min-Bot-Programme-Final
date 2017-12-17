#include <SoftwareSerial.h>
#include "CaptorSet.h"
#include "Motors.h"

// XBee's DOUT (TX) is connected to pin 11 (Arduino's Software RX)
// XBee's DIN (RX) is connected to pin 12 (Arduino's Software TX)
SoftwareSerial XBee(11, 12); // RX, TX

char GO_FORWARD[5] = {'F', 'R', 'W', 'D', '\0'};
char TURN_LEFT[5] = {'L', 'E', 'F', 'T', '\0'};
char TURN_RIGHT[5] = {'R', 'G', 'H', 'T', '\0'};
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
    else if (strcmp(GO_FORWARD, receivedQuerry) == 0) {
      Serial.println("OK");
      setLeftMotorSpeed(180);
      setRightMotorSpeed(0);
    }
    else if (strcmp(TURN_LEFT, receivedQuerry) == 0) {
      Serial.println("OK");
      setLeftMotorSpeed(90);
      setRightMotorSpeed(0);
    }
    else if (strcmp(TURN_RIGHT, receivedQuerry) == 0) {
      Serial.println("OK");
      setLeftMotorSpeed(180);
      setRightMotorSpeed(90);
    }
    else if (strcmp(STOP, receivedQuerry) == 0) {
      Serial.println("OK");
      fullStop();
    }
  }
}
