package Lista8;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

class Segment extends Primitive {
    public Segment(MyPoint x, MyPoint y) {
        anchorPoints.add(x);
        anchorPoints.add(y);
    }

    public int getLength() {
        return (int) Math.round(anchorPoints.get(0).getDistance(anchorPoints.get(1)));
    }

    public MyPoint getStart() {
        return anchorPoints.get(0).copy();
    }

    public MyPoint getEnd() {
        return anchorPoints.get(1).copy();
    }

    @Override
    public void draw(Mat src) {
        super.draw(src);
        Scalar color = new Scalar(64, 64, 64);
        Imgproc.line(src, anchorPoints.get(0).toPoint(), anchorPoints.get(1).toPoint(), color);
    }

    @Override
    public String toString() {
        return "Segment " + super.toString();
    }
}

