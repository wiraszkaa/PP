
class Rect extends Shape {
    public Rect(Point p1, Point p2) {
        anchorPoints.add(p1);
        anchorPoints.add(p2);
    }

    public int getWidth() {
        return Math.abs(anchorPoints.get(0).getX() - anchorPoints.get(1).getX());
    }

    public int getHeight() {
        return Math.abs(anchorPoints.get(0).getY() - anchorPoints.get(1).getY());
    }

    @Override
    public void draw() {
        // TODO Auto-generated method stub
    }
    
}
