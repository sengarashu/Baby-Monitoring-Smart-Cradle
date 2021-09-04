import picamera

print("About to take a picture")

with picamera.PiCamera() as camera:
	camera.resolution = (1280,720)
	camera.capture("new_image.jpg")
print("Picture taken..")