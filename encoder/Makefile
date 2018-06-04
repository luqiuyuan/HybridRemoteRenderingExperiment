LIBS = \
	-lavcodec \
	-lavutil \
	-lopencv_core \
	-lopencv_highgui \
	-lopencv_imgcodecs \

all:	main

main:	main.o video_encoder.o
		g++ main.o video_encoder.o -o main $(LIBS)

main.o:	main.cpp
		g++ -c main.cpp

video_encoder.o:	video_encoder.cpp
					g++ -c video_encoder.cpp

clean:
	rm -f main.o video_encoder.o