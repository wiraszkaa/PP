package Lista8;

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
    public void translate(Point p) {
        for (Item i : children) {
            i.translate(p);
        }
    }

    @Override
    public Point getPosition() {
        if (position == null) {
            Point curr = children.get(0).getPosition();
            int x = curr.getX();
            int y = curr.getY();
            for (Item i : children) {
                curr = i.getPosition();
                x = Math.min(x, curr.getX());
                y = Math.max(y, curr.getY());
            }
            position = new Point(x, y);
        }
        return position;
    }

    @Override
    public List<Point> getBoundingBox() {
        int xRight = children.get(0).getPosition().getX();
        int yRight = children.get(0).getPosition().getY();

        for (Item i : children) {
            List<Point> boundingBox = i.getBoundingBox();
            Point currRight = boundingBox.get(1);
            xRight = Math.max(xRight, currRight.getX());
            yRight = Math.min(yRight, currRight.getY());
        }
        
        Point position = getPosition();
        List<Point> boundingBox = new LinkedList<>();
        boundingBox.add(position.copy());
        boundingBox.add(new Point(xRight, yRight));
        boundingBox.add(new Point(position.getX(), yRight));
        boundingBox.add(new Point(xRight, position.getY()));

        return boundingBox;
    }

    @Override
    public void draw() {
        // TODO Auto-generated method stub

    }

}
