package Lista8;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

class Triangle extends Shape {
    public Triangle(MyPoint p1, MyPoint p2, MyPoint p3) {
        anchorPoints.add(p1);
        anchorPoints.add(p2);
        anchorPoints.add(p3);
    }

    public MyPoint getP1() {
        return anchorPoints.get(0).copy();
    }

    public MyPoint getP2() {
        return anchorPoints.get(1).copy();
    }

    public MyPoint getP3() {
        return anchorPoints.get(2).copy();
    }

    @Override
    public void draw(Mat src) {
        Scalar color = new Scalar(64, 64, 64);
        Imgproc.line(src, anchorPoints.get(0).toPoint(), anchorPoints.get(1).toPoint(), color);
        Imgproc.line(src, anchorPoints.get(0).toPoint(), anchorPoints.get(2).toPoint(), color);
        Imgproc.line(src, anchorPoints.get(2).toPoint(), anchorPoints.get(1).toPoint(), color);
    }
}
