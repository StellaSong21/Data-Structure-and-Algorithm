package TOOL;

import java.awt.*;

public class GUI {
    public static Dimension getScreenSize(Window window) {
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int w = screensize.width;
        int h = screensize.height;
        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(
                window.getGraphicsConfiguration());
        w = w - (screenInsets.left + screenInsets.right);
        h = h - (screenInsets.top + screenInsets.bottom);
        return new Dimension(w, h);
    }

}
