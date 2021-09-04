
int threshold = 1022; //Change This
int volume;

void setup() {                
  Serial.begin(9600); // For debugging
      
}

void loop() {
  
  while(1){
    delay(3000);
    volume = analogRead(A10); // Reads the value from the Analog PIN A0
    /*
      //Debug mode
      Serial.println(volume);
      delay(100);
    */
    Serial.print(volume);
    if(volume>=threshold){
      Serial.println("Sound Detected"); //Turn ON Led
    }  
    else{
      Serial.println("No Sound Detected"); // Turn OFF Led
    }
  }
}
