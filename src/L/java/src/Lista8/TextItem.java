package Lista8;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

class TextItem extends Item {
    private String text;
    
    public TextItem(MyPoint position, String text) {
        anchorPoints.add(position);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public void draw(Mat src) {
        Scalar color = new Scalar(64, 64, 64);
        Imgproc.putText(src, text, anchorPoints.get(0).toPoint(), 0, 1, color);
    }
}
