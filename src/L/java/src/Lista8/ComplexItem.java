package Lista8;

import org.opencv.core.Mat;
import java.util.List;

class ComplexItem extends Item {
    private final List<Item> children;

    public ComplexItem(List<Item> children) {
        this.children = children;
    }

    public List<Item> getChildren() {
        return children;
    }

    @Override
    public void translate(MyPoint p) {
        for (Item i : children) {
            i.translate(p);
        }
    }

    @Override
    public MyPoint getPosition() {
        if (position == null) {
            MyPoint curr = children.get(0).getPosition();
            int x = curr.getX();
            int y = curr.getY();
            for (Item i : children) {
                curr = i.getPosition();
                x = Math.min(x, curr.getX());
                y = Math.max(y, curr.getY());
            }
            position = new MyPoint(x, y);
        }
        return position.copy();
    }

    @Override
    public List<MyPoint> getBoundingBox() {
        int x = children.get(0).getPosition().getX();
        int y = children.get(0).getPosition().getY();

        for (Item i : children) {
            List<MyPoint> boundingBox = i.getBoundingBox();
            MyPoint curr = boundingBox.get(1);
            x = Math.max(x, curr.getX());
            y = Math.min(y, curr.getY());
        }

        boundingBox = createBoundingBox(getPosition(), new MyPoint(x, y));
        return boundingBox;
    }

    @Override
    public void draw(Mat src) {
        for (Item child: children) {
            child.draw(src);
        }
    }
}
