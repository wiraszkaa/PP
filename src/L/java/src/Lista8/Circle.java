package Lista8;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.List;

class Circle extends Shape {
    private final int radius;

    public Circle(MyPoint center, int radius) {
        anchorPoints.add(center);
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    @Override
    public MyPoint getPosition() {
        if (position == null) {
            position = new MyPoint(anchorPoints.get(0).getX() - radius, anchorPoints.get(0).getY() + radius);
        }

        return position;
    }

    @Override
    public List<MyPoint> getBoundingBox() {
        MyPoint position = getPosition();
        MyPoint furthestRight = new MyPoint(anchorPoints.get(0).getX() + radius, anchorPoints.get(0).getY() - radius);

        return createBoundingBox(furthestRight, position);
    }

    @Override
    public void draw(Mat src) {
        Scalar color = new Scalar(64, 64, 64);
        Imgproc.circle(src, anchorPoints.get(0).toPoint(), radius, color);
    }
}
