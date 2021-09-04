import humidity_second as h
import sound_second as s
#import Camero as c
#import rpi_motor as r
import threading
import time
channel=24

if __name__=="__main__":
	thread1=threading.Thread(target=h.update_firebase())
	thread2=threading.Thread(target=s.callback(channel))
	while(true):
		thread1.start()
		thread1.sleep(5)
		thread2.start()
		thread2.sleep(5)