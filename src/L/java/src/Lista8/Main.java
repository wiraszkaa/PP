package Lista8;

import org.opencv.core.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.List;

class Main {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Scene scene = new Scene(1000, 800);

        // Item rect = new Rect(new MyPoint(3, 3), new MyPoint(400, 400));
        // Item circle = new Circle(new MyPoint(100, 200), 100);
        // Item triangle = new Triangle(new MyPoint(400, 40), new MyPoint(300, 300), new
        // MyPoint(0, 0));
        // ComplexItem item = new ComplexItem(Stream.of(rect, circle,
        // triangle).toList());
        // scene.add(item);

        Circle head = new Circle(new MyPoint(300, 100), 50);
        Circle middle = new Circle(new MyPoint(300, 250), 100);
        Circle bottom = new Circle(new MyPoint(300, 550), 200);
        TextItem text = new TextItem(new MyPoint(150, 550), "Balwan XD");

        ComplexItem snowman = new ComplexItem(Stream.of(head, middle, bottom, text).collect(Collectors.toList()));
        scene.add(snowman);

        Shape star = new Star(new MyPoint(400, 400), 100);
//        star.setFilled(true);
        scene.add(star);
        drawBoundingBox(scene, star);

        scene.draw();
    }

    private static void drawBoundingBox(Scene scene, Item item) {
        List<MyPoint> bb = item.getBoundingBox();
        Item boundingBox = new Rect(bb.get(0), bb.get(1));
        scene.add(boundingBox);
    }
}
