#!/usr/bin/python
import RPi.GPIO as GPIO
from time import sleep
import datetime
from firebase import firebase
import urllib2, urllib, httplib
import json
import os 
from functools import partial

GPIO.setmode(GPIO.BCM)
GPIO.cleanup()
GPIO.setwarnings(False)

#GPIO SETUP
channel = 24
GPIO.setmode(GPIO.BCM)
GPIO.setup(channel, GPIO.IN)

firebase = firebase.FirebaseApplication('https://bmsc-9215d.firebaseio.com/', None)

def update_firebase():
	print('Sound Detected')
	data = {"Sound": "Sound Detected"}
	firebase.post('/sensor/sound', data)
		
def update_firebase2():
	print('Sound Detected')
	data2={"Sound": "Sound Not Detected"}
	firebase.post('/sensor/sound',data2)
	

def callback(channel):
	if GPIO.input(channel):
			update_firebase()
	else:
			update_firebase2()

GPIO.add_event_detect(channel, GPIO.BOTH, bouncetime=300)  # let us know when the pin goes HIGH or LOW
GPIO.add_event_callback(channel, callback)  # assign function to GPIO PIN, Run function on change

# infinite loop
while True:
		callback(channel)
        #sleepTime = int(sleepTime)
		sleep(10)