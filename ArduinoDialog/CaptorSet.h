/* Test Capteur Multiple */

// définition des broches utilisées
int trig1 = 4;
int echo1 = 14;

int trig2 = 5;
int echo2 = 15;

int trig3 = 6;
int echo3 = 16;

int trig4 = 7;
int echo4 = 17;

int trig5 = 8;
int echo5 = 18;

int TrigArray[5] = {trig1, trig2, trig3, trig4, trig5};
int EchoArray[5] = {echo1, echo2, echo3, echo4, echo5};

long lecture_echo;
long cm;

void initCaptors()
{
  for (int i = 0; i < 5; i++) {
    pinMode(TrigArray[i], OUTPUT);
    digitalWrite(TrigArray[i], LOW);
    pinMode(EchoArray[i], INPUT);
  }
}

int * getDistances()
{
  static int Distances[5];
  for (int i = 0; i < 5; i++) {
    digitalWrite(TrigArray[i], HIGH);
    delayMicroseconds(10);
    digitalWrite(TrigArray[i], LOW);
    lecture_echo = pulseIn(EchoArray[i], HIGH);
    Distances[i] = lecture_echo / 58;
    delay(60);
  }
  return Distances;
}

int * getDistance_1(){
  static int Distances[5];
  digitalWrite(TrigArray[0], HIGH);
    delayMicroseconds(10);
    digitalWrite(TrigArray[0], LOW);
    lecture_echo = pulseIn(EchoArray[0], HIGH);
    Distances[0] = lecture_echo/58;
    delay(60);
    for (int i = 1; i < 5; i++) {
      Distances[i] = 0;
    }
    return Distances;
}

