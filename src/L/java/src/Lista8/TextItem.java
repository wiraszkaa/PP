package Lista8;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.List;

class TextItem extends Item {
    private final String text;
    private final double scale;
    private final int face = Imgproc.FONT_HERSHEY_SIMPLEX;
    private final int thickness = 1;
    
    public TextItem(MyPoint position, String text) {
        anchorPoints.add(position);
        this.position = position;
        this.text = text;
        this.scale = 1;
    }

    public TextItem(MyPoint position, String text, double scale) {
        anchorPoints.add(position);
        this.position = position;
        this.text = text;
        this.scale = scale;
    }

    public String getText() {
        return text;
    }

    @Override
    public List<MyPoint> getBoundingBox() {
        Size size = Imgproc.getTextSize(text, face, scale, thickness, null);
        return createBoundingBox(position, new MyPoint((int) (position.getX() + size.width) + 1, (int) (position.getY() - size.height) - 1));
    }

    @Override
    public void draw(Mat src) {
        Scalar color = new Scalar(64, 64, 64);
        Imgproc.putText(src, text, anchorPoints.get(0).toPoint(), face, scale, color);
    }
}
