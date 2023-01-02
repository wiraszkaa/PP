package Lista8;

import org.opencv.core.Point;

class MyPoint {
    private int x;
    private int y;

    public MyPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public Point toPoint() {
        return new Point(x, y);
    }

    public MyPoint copy() {
        return new MyPoint(x, y);
    }

    public double getDistance(MyPoint p) {
        return Math.sqrt(Math.pow(x - p.x, 2) + Math.pow(y - p.y, 2));
    }

    @Override
    public String toString() {
        return "[x=" + x + ", y=" + y + "]";
    }

    
}
