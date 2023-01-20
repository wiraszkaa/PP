package Lista8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class ItemsList {
    private final JFrame frame;
    public ItemsList(List<Item> items, ActionListener onClick) {
        frame = new JFrame();

        for (Item i: items) {
            JButton button = new JButton(i.toString());
            button.addActionListener(e -> i.toggleBoundingBox());
            frame.add(button);
        }

        JButton button = new JButton("OK");
        button.addActionListener(onClick);
        frame.add(button);

        frame.setLayout(new FlowLayout());
    }

    public void show() {
        frame.pack();
        frame.setVisible(true);
    }
}
