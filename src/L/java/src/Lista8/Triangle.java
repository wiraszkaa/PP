package Lista8;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import java.util.LinkedList;
import java.util.List;

class Triangle extends Shape {
    private static Triangle INSTANCE;

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
        super.draw(src);
        Scalar color = new Scalar(64, 64, 64);
        List<MatOfPoint> points = new LinkedList<>();
        points.add(new MatOfPoint( getP1().toPoint(), getP2().toPoint(), getP3().toPoint()));
        if (getFilled()) {
            Imgproc.fillPoly(src, points, color);
        } else {
            Imgproc.polylines(src, points, true, color);
        }
    }

    @Override
    public String toString() {
        return "Triangle " + super.toString();
    }
}
