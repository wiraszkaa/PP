import java.util.LinkedList;
import java.util.List;

class Circle extends Shape {
    private final int radius;

    public Circle(Point center, int radius) {
        anchorPoints.add(center);
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    @Override
    public Point getPosition() {
        if (position == null) {
            position = new Point(anchorPoints.get(0).getX() - radius, anchorPoints.get(0).getY() + radius);
        }

        return position;
    }

    @Override
    public List<Point> getBoundingBox() {
        Point position = getPosition();
        Point furthestRight = new Point(anchorPoints.get(0).getX() + radius, anchorPoints.get(0).getY() - radius);
        
        List<Point> boundingBox = new LinkedList<>();
        boundingBox.add(position.copy());
        boundingBox.add(furthestRight);
        boundingBox.add(new Point(position.getX(), furthestRight.getY()));
        boundingBox.add(new Point(furthestRight.getX(), position.getY()));

        return boundingBox;
    }

    @Override
    public void draw() {
        // TODO Auto-generated method stub
    }
  
}
