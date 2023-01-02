package Lista8;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

class Rect extends Shape {
    public Rect(MyPoint p1, MyPoint p2) {
        anchorPoints.add(p1);
        anchorPoints.add(p2);
    }

    public int getWidth() {
        return Math.abs(anchorPoints.get(0).getX() - anchorPoints.get(1).getX());
    }

    public int getHeight() {
        return Math.abs(anchorPoints.get(0).getY() - anchorPoints.get(1).getY());
    }

    @Override
    public void draw(Mat src) {
        Scalar color = new Scalar(64, 64, 64);
        Imgproc.rectangle (src, anchorPoints.get(0).toPoint(), anchorPoints.get(1).toPoint(), color);
    }
}
