
class Triangle extends Shape {
    public Triangle(Point p1, Point p2, Point p3) {
        anchorPoints.add(p1);
        anchorPoints.add(p2);
        anchorPoints.add(p3);
    }

    public Point getP1() {
        return anchorPoints.get(0).copy();
    }

    public Point getP2() {
        return anchorPoints.get(1).copy();
    }

    public Point getP3() {
        return anchorPoints.get(2).copy();
    }

    @Override
    public void draw() {
        // TODO Auto-generated method stub

    }
}
