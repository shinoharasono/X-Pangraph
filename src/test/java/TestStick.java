import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

class ImagePanel extends JPanel {
    private BufferedImage image;

    public ImagePanel() {
        try {
            image = ImageIO.read(new File("D:\\AProject\\X-Pangraph\\screenshot.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, null);
        }
}
public class TestStick {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Image Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ImagePanel());
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}