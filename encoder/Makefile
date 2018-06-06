INC = \
	-IE:\Libraries\ffmpeg\4.0\include \
	-IE:\Libraries\opencv\build\include

LINK_DIR = \
	-L"E:\Libraries\ffmpeg\4.0\lib" \
	-L"E:\Libraries\opencv\build\x64\vc15\lib"

#LIBS = \
	-lavcodec \
	-lavutil \
	-lopencv_core \
	-lopencv_highgui \
	-lopencv_imgcodecs

LIBS = \
	-lavcodec \
	-lavutil \
	-lopencv_world341

all:	main

main:	main.o video_encoder.o
		g++ main.o video_encoder.o -o main $(LINK_DIR) $(LIBS)

main.o:	main.cpp
		g++ -c main.cpp $(INC)

video_encoder.o:	video_encoder.cpp
					g++ -c video_encoder.cpp $(INC)

clean:
	rm -f main.o video_encoder.o