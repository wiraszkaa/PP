import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

abstract class Item {
    List<Point> anchorPoints;
    Point position;

    public Item() {
        anchorPoints = new ArrayList<>();
    }

    public Point getPosition() {
        if (position == null) {
            position = getFurthest(true);
        }
        return position;
    }

    public void translate(Point p) {
        for (Point i: anchorPoints) {
            i.move(p.getX(), p.getY());
        }
        if (position != null) {
            position.move(p.getX(), p.getY());
        }
    }

    public abstract void draw();

    public List<Point> getBoundingBox() {
        if (position == null) {
            position = getFurthest(true);
        }
        Point furthestRight = getFurthest(false);

        List<Point> boundingBox = new LinkedList<>();
        boundingBox.add(position.copy());
        boundingBox.add(furthestRight);
        boundingBox.add(new Point(position.getX(), furthestRight.getY()));
        boundingBox.add(new Point(furthestRight.getX(), position.getY()));

        return boundingBox;
    }

    private Point getFurthest(boolean isLeft) {
        int x = anchorPoints.get(0).getX();
        int y = anchorPoints.get(0).getY();
        for (Point p : anchorPoints) {
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
        return new Point(x, y);
    }
}
