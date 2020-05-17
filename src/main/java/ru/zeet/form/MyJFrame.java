package ru.zeet.form;

import javax.swing.*;
import java.awt.*;

public class MyJFrame extends JDialog {
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int sizeWidth;
    private int sizeHeight;
    private int locationX;
    private int locationY;


    public void setCenter(int width, int height) {
        sizeWidth = width;
        sizeHeight = height;
        locationX = (screenSize.width - sizeWidth) / 2;
        locationY = (screenSize.height - sizeHeight) / 2;

        super.setBounds(locationX, locationY, sizeWidth, sizeHeight);
    }
}
