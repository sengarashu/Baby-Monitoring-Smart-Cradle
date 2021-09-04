/* Noisy Neighbors
 */
 

// these constants won't change:
const int sensorPin = A0; // the mic is connected to analog pin 0
const int threshold = 500;  // threshold value to decide when the detected sound is loud enough
const int ledPin[] = {
  2,3,4,5,6,7,8,9,10,11,12,13};     // led bars
const int ledCount = sizeof(ledPin)/sizeof(int);        // number of LEDs
const int waitingPin = 26;
const int playPin = 24;
const int playDelay = 30; // delay for knight rider play pattern
const long waitTime = 10000.0; // how long after trigger to wait before playing music 
const long cancelTime = 4000.0; // during wait, how long of a lull will cancel wait
const long playTime = 30000.0; // how long to play payback music for

// these variables will change:
int sensorReading = 0;      // variable to store the value read from the sensor pin
int ledLevel = 0; 
boolean waiting = 0;
boolean playing = 0;
long waitStart; // when trigger initiated waiting
long waitDuration; // how long we have been waiting
long playStart; // when play was initiated
long playDuration; // how long we've been playing
long cancelStart; // when the last trigger was
long cancelDuration; // how long of a lull we've had

void setup() {
  for(int i=0; i<ledCount; i++)
  {
    pinMode(ledPin[i], OUTPUT);
  } // declare the ledPin as as OUTPUT
  pinMode(waitingPin, OUTPUT);
  pinMode(playPin, OUTPUT);
 // digitalWrite(playPin, HIGH);
 Serial.begin(9600);       // use the serial port
}

void loop() {
  // read the sensor and store it in the variable sensorReading:
  sensorReading = analogRead(sensorPin);    
  
  // map the result to a range from 0 to the number of LEDs:
  ledLevel = map(sensorReading, 200, threshold, 0, ledCount);

  // loop over the LED array:
  for (int i = 0; i < ledCount; i++) {
    // if the array element's index is less than ledLevel,
    // turn the pin for this element on:
    if (i < ledLevel) {
      digitalWrite(ledPin[i], HIGH);
    } 
    // turn off all pins higher than the ledLevel:
    else {
      digitalWrite(ledPin[i], LOW); 
    }
  }
  
  // over the threshhold
  if (ledLevel == ledCount) {
    cancelStart = millis();
    if (!waiting) {
      waiting = 1;
      digitalWrite(waitingPin, HIGH);
      waitStart = millis();
    }
  }
  
  if (waiting) {
    cancelDuration = millis() - cancelStart;
    waitDuration = millis() - waitStart;

   if (cancelDuration > cancelTime) {
      waiting = 0;
      digitalWrite(waitingPin, LOW);
    }
     else if (waitDuration > waitTime) {
      waiting = 0;
      digitalWrite(waitingPin, LOW);
      playing = 1;
      digitalWrite(playPin, LOW);
      delay(200);
      digitalWrite(playPin, HIGH);
      Serial.print("Hello Pi\n");
      playStart = millis();
      
      while (playing) {
         playDuration = millis() - playStart;
         if (playDuration > playTime) {
           digitalWrite(playPin, LOW);
           delay(200);
           //digitalWrite(playPin, HIGH);
           playing = 0;
         }
         
         for (int i = 0; i < ledCount; i++) {
           digitalWrite(ledPin[i], HIGH);
           delay(playDelay);
           digitalWrite(ledPin[i], LOW);
           delay(playDelay);
          }
          for (int i = (ledCount - 1); i >= 0; i--) {
           digitalWrite(ledPin[i], HIGH);
           delay(playDelay);
           digitalWrite(ledPin[i], LOW);
           delay(playDelay);
          }
      }
    }
  }
}

