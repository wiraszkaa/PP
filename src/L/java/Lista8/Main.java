
import java.util.List;
import java.util.LinkedList;

class Main {
    public static void main(String[] args) {
        Rect rect = new Rect(new Point(0, 0), new Point(1, 1));
        Circle circle = new Circle(new Point(-3, -1), 1);
        Triangle triangle = new Triangle(new Point(1, 1), new Point(0, 0), new Point(-3, -3));
        
        List<Item> children = new LinkedList<>();
        children.add(rect);
        children.add(circle);
        children.add(triangle);

        ComplexItem complexItem = new ComplexItem(children);

       System.out.println(complexItem);
    }
}
