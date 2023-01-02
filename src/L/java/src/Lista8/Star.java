package Lista8;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.LinkedList;
import java.util.List;

public class Star extends Shape {
    private final MyPoint center;

    public Star(MyPoint center, int radius) {
        this.center = center;

        int smallRadius = radius / 2;
        double smallStart = Math.PI * 0.1;
        double bigStart = Math.PI * 0.3;
        double distance = Math.PI * 0.4;
        for (int i = 0; i < 5; i++) {
            anchorPoints.add(createPoint(radius, smallStart + i * distance));
            anchorPoints.add(createPoint(smallRadius, bigStart + i * distance));
        }
    }

    private MyPoint createPoint(int radius, double angle) {
        return new MyPoint(center.getX() + (int) (radius * Math.cos(angle)), center.getY() + (int) (radius * Math.sin(angle)));
    }

    private Point get(int index) {
        return anchorPoints.get(index).toPoint();
    }

    @Override
    public void draw(Mat src) {
        Scalar color = new Scalar(64, 64, 64);
        List<MatOfPoint> points = new LinkedList<>();
        points.add(new MatOfPoint(get(0), get(1), get(2), get(3), get(4), get(5), get(6), get(7), get(8), get(9)));

        if (getFilled()) {
            Imgproc.fillPoly(src, points, color);
        } else {
            Imgproc.polylines(src, points, true, color);
        }
    }
}
