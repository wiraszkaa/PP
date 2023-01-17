package Lista8;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;



public class Scene implements SceneInterface, ItemSingleton {
    private final List<Item> items;
    private final int width;
    private final int height;
    private Mat blank;

    public Scene(int width, int height) {
        this.width = width;
        this.height = height;
        this.items = new ArrayList<>();
        this.blank = new Mat(height, width, CvType.CV_8UC3, new Scalar(255, 255, 255));
    }

    public void clear() {
        blank = new Mat(height, width, CvType.CV_8UC3, new Scalar(255, 255, 255));
    }

    @Override
    public void draw() {
        clear();
//        items.forEach(item -> item.draw(blank));
        removeSingletonDuplicates(getItems(), Triangle.class).forEach(item -> item.draw(blank));
        HighGui.imshow("Drawing", blank);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        HighGui.moveWindow("Drawing", (int) (screenSize.getWidth() - width) / 2, (int) (screenSize.getHeight() - height) / 2);
        HighGui.waitKey(10);
    }

    void add(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }
}
