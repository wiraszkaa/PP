package Lista8;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.w3c.dom.Text;

import java.util.List;
import java.util.LinkedList;
import java.util.stream.Stream;

class Main {
    public static void main(String[] args) {
//        Item rect = new Rect(new MyPoint(3, 3), new MyPoint(400, 400));
//        Item circle = new Circle(new MyPoint(100, 200), 100);
//        Item triangle = new Triangle(new MyPoint(400, 40), new MyPoint(300, 300), new MyPoint(0, 0));
//
//        ComplexItem complexItem = new ComplexItem(Stream.of(rect, circle, triangle).toList());

        Circle head = new Circle(new MyPoint(300, 100), 50);
        Circle middle = new Circle(new MyPoint(300, 250), 100);
        Circle bottom = new Circle(new MyPoint(300, 550), 200);
        TextItem text = new TextItem(new MyPoint(10, 550), "Balwan XD");

        ComplexItem snowman = new ComplexItem(Stream.of(head, middle, bottom, text).toList());

        List<MyPoint> bb = snowman.getBoundingBox();
        Item boundingBox = new Rect(bb.get(0), bb.get(1));

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = new Mat(1000, 1000, CvType.CV_8UC3, new Scalar(255, 255, 255));
        snowman.draw(src);
        boundingBox.draw(src);

        HighGui.imshow("Drawing", src);
        HighGui.waitKey();
    }
}
