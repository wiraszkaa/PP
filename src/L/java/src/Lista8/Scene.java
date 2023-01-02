package Lista8;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;

import java.util.ArrayList;
import java.util.List;



public class Scene {
    private final List<Item> items;
    private final Mat blank;

    public Scene(int width, int height) {
        this.items = new ArrayList<>();
        this.blank = new Mat(height, width, CvType.CV_8UC3, new Scalar(255, 255, 255));
    }

    public void draw() {
        items.forEach(item -> item.draw(blank));
        HighGui.imshow("Drawing", blank);
        HighGui.waitKey();
    }

    void add(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }
}
