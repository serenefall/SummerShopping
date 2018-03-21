package ui;

public class JFrames {

    private static javax.swing.JFrame frame = null;

    private JFrames(){
        frame = new javax.swing.JFrame();
    }

    public static javax.swing.JFrame get_frame(){
        if(frame == null) {
            new JFrames();
        }
        return frame;
    }
}
