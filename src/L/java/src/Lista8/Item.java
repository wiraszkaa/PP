package Lista8;

import org.opencv.core.Mat;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

abstract class Item {
    List<MyPoint> anchorPoints;
    MyPoint position;

    public Item() {
        anchorPoints = new ArrayList<>();
    }

    public MyPoint getPosition() {
        if (position == null) {
            position = getFurthest(true);
        }
        return position;
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
        if (position == null) {
            position = getFurthest(true);
        }
        MyPoint furthestRight = getFurthest(false);

        return createBoundingBox(position, furthestRight);
    }

    static List<MyPoint> createBoundingBox(MyPoint position, MyPoint furthestRight) {
        List<MyPoint> boundingBox = new LinkedList<>();
        boundingBox.add(position.copy());
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
                if (p.getX() < x) {
                    x = p.getX();
                }
                if (p.getY() > y) {
                    y = p.getY();
                }
            } else {
                if (p.getX() > x) {
                    x = p.getX();
                }
                if (p.getY() < y) {
                    y = p.getY();
                }
            }
        }
        return new MyPoint(x, y);
    }
}
