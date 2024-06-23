import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Screenshot {
    public static void main(String[] args) throws Exception {
        // 创建一个Robot对象
        Robot robot = new Robot();
        // 获取屏幕的大小
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        // 创建一个Rectangle对象，用于指定截屏的区域
        Rectangle rectangle = new Rectangle(dimension);
        // 捕获屏幕上的内容
        BufferedImage bufferedImage = robot.createScreenCapture(rectangle);
        // 将捕获的内容保存到文件
        ImageIO.write(bufferedImage, "png", new File("screenshot.png"));
    }
}
