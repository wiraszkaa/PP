package Lista8;

import org.opencv.core.Mat;

import java.util.LinkedList;
import java.util.List;

class ComplexItem extends Item {
    private List<Item> children;

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
        return position;
    }

    @Override
    public List<MyPoint> getBoundingBox() {
        int xRight = children.get(0).getPosition().getX();
        int yRight = children.get(0).getPosition().getY();

        for (Item i : children) {
            List<MyPoint> boundingBox = i.getBoundingBox();
            MyPoint currRight = boundingBox.get(1);
            xRight = Math.max(xRight, currRight.getX());
            yRight = Math.min(yRight, currRight.getY());
        }
        
        MyPoint position = getPosition();
        List<MyPoint> boundingBox = new LinkedList<>();
        boundingBox.add(position.copy());
        boundingBox.add(new MyPoint(xRight, yRight));
        boundingBox.add(new MyPoint(position.getX(), yRight));
        boundingBox.add(new MyPoint(xRight, position.getY()));

        return boundingBox;
    }

    @Override
    public void draw(Mat src) {
        for (Item child: children) {
            child.draw(src);
        }
    }
}
