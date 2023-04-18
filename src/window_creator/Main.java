package window_creator;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        //runs the program on a certain thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                @SuppressWarnings("unused")
                myWindow window = new myWindow(500, 500);
            };
        });
        
    }
}
