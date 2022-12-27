
class Segment extends Primitive {
    public Segment(Point x, Point y) {
        anchorPoints.add(x);
        anchorPoints.add(y);
    }

    public int getLength() {
        return (int) Math.round(anchorPoints.get(0).getDistance(anchorPoints.get(1)));
    }

    public Point getStart() {
        return anchorPoints.get(0).copy();
    }

    public Point getEnd() {
        return anchorPoints.get(1).copy();
    }
    
    @Override
    public void draw() {
        // TODO Auto-generated method stub
    }
}

