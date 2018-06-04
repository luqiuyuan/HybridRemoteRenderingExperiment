#include "video_encoder.h"
#include <opencv2/opencv.hpp>
#include <iostream>
#include <vector>

using namespace cv;
using namespace std;

int main() {
  const int NUM_FRAMES = 100;
  
  vector<Mat> images;
  for (int i = 1; i <= NUM_FRAMES; i++) {
    char buffer[23];
    int n = sprintf(buffer, "../frames/frame%04d.png", i);
    string file_name(buffer);

    Mat image = imread(file_name);

    if (image.empty()) {
      cout << "Could not open or find the image" << endl;
      cin.get(); //wait for any key press
      return -1;
    }

    images.push_back(image);
  }

  const int WIDTH = images[0].size().width;
  const int HEIGHT = images[0].size().height;

  VideoEncoder encoder("./output/frames.h264", Size(WIDTH, HEIGHT), VideoEncoder::VIDEO_CODEC_H264);
  encoder.disableBitrateControl();
	encoder.init();
	int initialization_flag = encoder.getInitializationFlag();
	if(initialization_flag < 0) {
		cerr<<"Error: main.cpp: Video encoder initialization failed with error code: "<<initialization_flag<<endl;
		return 1;
	} else if(initialization_flag > 0)
		cerr<<"Warning: main.cpp: Video encoder initialization warning with warning code: "<<initialization_flag<<endl;
  
  for (vector<Mat>::iterator it = images.begin(); it != images.end(); ++it) {
    encoder.write(*it);
  }

  encoder.flush_close();

  return 0;
}
