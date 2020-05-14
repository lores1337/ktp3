
import java.awt.*;
import java.awt.image.BufferedImage;

public class JImageDisplay extends javax.swing.JComponent {
    private BufferedImage A; // для последующего управления приложением
    private int width;       // ширина изображения
    private int height;      // высота изображения

    public BufferedImage getImage(){
        return A;
    }

    JImageDisplay(int width,int height){ // конструктор
        this.width = width;
        this.height = height;
        A = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        java.awt.Dimension B = new java.awt.Dimension(width,height);
        setPreferredSize(B);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);   // чтобы объекты отображались правильно
        g.drawImage(A,0,0,A.getWidth(),A.getHeight(),null); // рисование изображения в компоненте
    }

    public void clearImage(){   // функция устанавливает все пиксели изображения в черный цвет
        for(int i = 0;i<width;i++){
            for(int j = 0;j<height;j++){
                A.setRGB(i,j,0);
            }
        }
    }

    public void drawPixel(int x,int y,int rgbColor){  // функция устанавливает один пиксель в определенный цвет
        A.setRGB(x,y,rgbColor);
    }
}
