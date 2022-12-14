package Lista8;

import org.opencv.core.Mat;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

abstract class Item {
    List<MyPoint> anchorPoints;
    MyPoint position;
    List<MyPoint> boundingBox;

    public Item() {
        anchorPoints = new ArrayList<>();
    }

    public MyPoint getPosition() {
        if (position == null) {
            position = getFurthest(true);
        }
        return position.copy();
    }

    public void translate(MyPoint p) {
        for (MyPoint i: anchorPoints) {
            i.move(p.getX(), p.getY());
        }
        if (position != null) {
            position.move(p.getX(), p.getY());
        }
    }

    public abstract void draw(Mat src);

    public List<MyPoint> getBoundingBox() {
        if (boundingBox == null) {
            MyPoint position = getPosition();
            MyPoint furthestRight = getFurthest(false);

            boundingBox = createBoundingBox(position, furthestRight);
        }

        return boundingBox;
    }

    static List<MyPoint> createBoundingBox(MyPoint position, MyPoint furthestRight) {
        List<MyPoint> boundingBox = new LinkedList<>();
        boundingBox.add(position);
        boundingBox.add(furthestRight);
        boundingBox.add(new MyPoint(position.getX(), furthestRight.getY()));
        boundingBox.add(new MyPoint(furthestRight.getX(), position.getY()));

        return boundingBox;
    }

    private MyPoint getFurthest(boolean isLeft) {
        int x = anchorPoints.get(0).getX();
        int y = anchorPoints.get(0).getY();
        for (MyPoint p : anchorPoints) {
            if (isLeft) {
                x = Math.min(x, p.getX());
                y = Math.max(y, p.getY());
            } else {
                x = Math.max(x, p.getX());
                y = Math.min(y, p.getY());
            }
        }
        return new MyPoint(x, y);
    }
}
