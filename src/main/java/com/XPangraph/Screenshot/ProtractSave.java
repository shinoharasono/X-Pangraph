package com.XPangraph.Screenshot;

import com.XPangraph.tools.ToolsWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProtractSave extends JWindow
{
    private int orgx, orgy, endx, endy;
    private BufferedImage image=null;
    private BufferedImage tempImage=null;
    private BufferedImage saveImage=null;
    private ToolsWindow tools=null;
    private  Point dragStartPoint;
    public ProtractSave() throws AWTException {
        //获取屏幕尺寸
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(0, 0, d.width, d.height);
        //截取屏幕
        Robot robot = new Robot();
        image = robot.createScreenCapture(new Rectangle(0, 0, d.width,d.height));

        //为当前窗口添加鼠标事件监听器，监听鼠标按下和松开的事件。
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            //鼠标松开时记录结束点坐标，并隐藏操作窗口
                orgx = e.getX();
                orgy = e.getY();
                if(tools!=null){
                    tools.setVisible(false);
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                //鼠标松开时，显示操作窗口
                if(tools==null){
                    tools=new ToolsWindow(ProtractSave.this,e.getX(),e.getY());
                }else{
                    tools.setLocation(e.getX(),e.getY());
                }
                tools.setVisible(true);
                tools.toFront();
            }
        });
        //为当前窗口添加鼠标移动事件监听器，监听鼠标拖动的事件。
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                //鼠标拖动时，记录坐标并重绘窗口
                if (dragStartPoint != null) {
                    int deltaX = e.getX() - dragStartPoint.x;
                    int deltaY = e.getY() - dragStartPoint.y;

                    dragStartPoint = e.getPoint();

                    int newOrgX = orgx + deltaX;
                    int newOrgY = orgy + deltaY;
                    int newEndX = endx + deltaX;
                    int newEndY = endy + deltaY;

                    orgx = newOrgX;
                    orgy = newOrgY;
                    endx = newEndX;
                    endy = newEndY;

                    ProtractSave.this.repaint();
                } else {
                    endx = e.getX();
                    endy = e.getY();
                }


                //临时图像，用于缓冲屏幕区域放置屏幕闪烁
                Image tempImage2=createImage(ProtractSave.this.getWidth(), ProtractSave.this.getHeight());
                Graphics g =tempImage2.getGraphics();
                g.drawImage(tempImage, 0, 0, null);
                int x = Math.min(orgx, endx);
                int y = Math.min(orgy, endy);
                int width = Math.abs(endx - orgx)+1;
                int height = Math.abs(endy - orgy)+1;

                // 加上1防止width或height0
                g.setColor(Color.BLUE);
                g.drawRect(x-1, y-1, width+1, height+1);
                //减1加1都了防止图片矩形框覆盖掉
                saveImage = image.getSubimage(x, y, width, height);

                g.drawImage(saveImage, x, y, null);
                ProtractSave.this.getGraphics().drawImage(tempImage2,0,0, ProtractSave.this);
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        RescaleOp ro = new RescaleOp(0.8f, 0, null);
        tempImage = ro.filter(image, null);
        g.drawImage(tempImage, 0, 0, this);
    }
    //保存图像到文件
    public void saveImage() throws Exception {
        JFileChooser jfc=new JFileChooser();
        jfc.setDialogTitle("保存");
        //文件过滤器，用户过滤可选择文件
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG", "jpg");
        jfc.setFileFilter(filter);
        //初始化一个默认文件(此文件会生成到桌面上)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddHHmmss");
        String fileName = sdf.format(new Date());
        File filePath = FileSystemView.getFileSystemView().getHomeDirectory();
        File defaultFile = new File(filePath + File.separator + fileName + ".jpg");
        jfc.setSelectedFile(defaultFile);
        int flag = jfc.showSaveDialog(this);
        if(flag==JFileChooser.APPROVE_OPTION){
            File file=jfc.getSelectedFile();
            String path=file.getPath();
            //检查文件后缀，防止用户忘记输入后缀或者输入不正确的后缀
            if(!(path.endsWith(".jpg")||path.endsWith(".JPG"))){
                path+=".jpg";
            }
            //写入文件
            ImageIO.write(saveImage,"jpg",new File(path));
            System.exit(0);
        }
    }
}
