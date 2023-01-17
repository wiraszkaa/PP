package Lista8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.BatchUpdateException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Flow;

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

//        frame.setSize(400,500);
        frame.setLayout(new FlowLayout());
    }

    public void show() {
        frame.pack();
        frame.setVisible(true);
    }
}
