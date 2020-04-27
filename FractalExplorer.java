

import java.awt.geom.Rectangle2D;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FractalExplorer {  // исследование разных областей фрактала
    private int size;          // размер экрана
    private double centerX=0;
    private double centerY=0;
    private double zoom;

    private JImageDisplay JD;  // для обновления отображения в процессе вычисления фрактала
    private FractalGenerator FG = new Mandelbrot(); // для следующих фракталов
    private Rectangle2D.Double R2D = new Rectangle2D.Double(); // для указания диапозона комплексной плоскости

    FractalExplorer(int size){
        this.size = size; // сохранение размера экрана
        this.zoom = 0.5;
        FG.getInitialRange(R2D); // инициализация фрактального генератора
    }

    public void createAndShowGUI(){ // инициализирует графический интерфейс Swing
        JFrame JF = new JFrame("Fractal_Generator"); // заголовок окна
        JF.setLocation(100,50);
        JButton B1 = new JButton("Discharge"); // кнопка для сброса отображения
        JLabel L1 = new JLabel("Fractal"); // метка
        JPanel P1 = new JPanel();
        JPanel P2 = new JPanel();
        P1.add(B1);
        P2.add(L1); // добавление метки
        JD = new JImageDisplay(size,size);
        JF.add(P1, BorderLayout.SOUTH);
        JF.add(JD, BorderLayout.CENTER);
        JF.add(P2, BorderLayout.NORTH);
        JF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // закрытие окна по умолчанию
        B1.addActionListener(new TestActionListener());
        JD.addMouseListener(new TestMouseListener());
        JF.setVisible(true); // окно видимое
        JF.setResizable(false); // размер окна нельзя менять
        JD.setVisible(true);
        drawFractal();
        JF.pack();
        JD.repaint(); // для обновления изображения на экране
        JD.validate();
    }

    private void drawFractal(){ // для вывода фрактала на экран
        for(int x=0;x<size;x++){
            for(int y = 0;y<size;y++){
                double xCoord = FractalGenerator.getCoord(R2D.x, R2D.x + R2D.width, size, x);
                double yCoord = FractalGenerator.getCoord(R2D.y, R2D.y + R2D.height, size, y);
                if (FG.numIterations(xCoord, yCoord) == -1) {
                    JD.drawPixel(x, y, 0);
                } else {
                    float hue = 0.7f + (float) FG.numIterations(xCoord, yCoord) / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    JD.drawPixel(x, y, rgbColor);
                }
            }
        }
        JD.repaint();
    }

    public class TestActionListener implements ActionListener { // обработчик для кнопки сброса
        public void actionPerformed(ActionEvent ae){
            JD.clearImage();
            FG.getInitialRange(R2D);
            drawFractal();
            centerX=centerY=0;
        }
    }

    public class TestMouseListener implements MouseListener { // для обработки событий мыши
        public void mouseClicked(MouseEvent mouseEvent) {
            double mouseX = FractalGenerator.getCoord(R2D.x, R2D.x + R2D.width, size, mouseEvent.getX());
            double mouseY = FractalGenerator.getCoord(R2D.y, R2D.y + R2D.width, size, mouseEvent.getY());
            FG.recenterAndZoomRange(R2D, mouseX, mouseY, zoom);
            drawFractal();
        }
        public void mousePressed(MouseEvent mouseEvent) { }
        public void mouseReleased(MouseEvent mouseEvent) { }
        public void mouseEntered(MouseEvent mouseEvent) { }
        public void mouseExited(MouseEvent mouseEvent) { }
    }

    public static void main(String[] args){
        FractalExplorer A = new FractalExplorer(800);
        A.createAndShowGUI(); // // вызов метода для создания пользовательского интерфейса
    }
}
