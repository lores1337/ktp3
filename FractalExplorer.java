import java.awt.geom.Rectangle2D;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class FractalExplorer {  // исследование разных областей фрактала
    private int size;          // размер экрана
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
        JD = new JImageDisplay(size,size);
        JF.add(JD, BorderLayout.CENTER);
        JF.setLocation(100,50);
        JButton B1 = new JButton("Discharge"); // кнопка для сброса отображения
        JButton B2 = new JButton("Save");    // кнопка для сохранения изображения
        JLabel L1 = new JLabel("Fractal");  // метка
        JComboBox JCB = new JComboBox();         //Создание бокса combo box
        FractalGenerator mandelbrotFractal = new Mandelbrot();  //Добавление разных фракталов в JCB
        JCB.addItem(mandelbrotFractal);          // добавление первого фрактала
        FractalGenerator tricornFractal = new Tricorn();
        JCB.addItem(tricornFractal);             // добавление второго фрактала
        FractalGenerator burningShipFractal = new BurningShip();
        JCB.addItem(burningShipFractal);         // добавление третьего фрактала
        Choice fractalChooser = new Choice();
        JCB.addActionListener(fractalChooser);   // присваивание обработчика
        JPanel P1 = new JPanel();
        JPanel P2 = new JPanel();
        P1.add(B1);   // добавление кнопки для сброса отображения
        P1.add(B2);   // добавление кнопки для сохранения изображения
        P2.add(L1);   // добавление метки
        P2.add(JCB);  // добавление combo-box
        JF.add(P1, BorderLayout.SOUTH);
        JF.add(P2, BorderLayout.NORTH);
        JF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // закрытие окна по умолчанию
        B1.addActionListener(new TestActionListener());
        B2.addActionListener(new Save());
        JD.addMouseListener(new TestMouseListener());
        JF.setVisible(true);    // окно становится видимым
        JF.setResizable(false); // размер окна нельзя менять
        JD.setVisible(true);
        drawFractal();
        JF.pack();
        JD.repaint(); // для обновления изображения на экране
        JD.validate();
    }

    private void drawFractal(){  // для вывода фрактала на экран
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
        }
    }

    public class TestMouseListener implements MouseListener {  // для обработки событий мыши
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

    public class Choice implements ActionListener {  // обработчик для выбора фрактала
        @Override
        public void actionPerformed(ActionEvent e) {
            //Получение фрактала находящегося в списке
            if (e.getSource() instanceof JComboBox) {
                JComboBox mySource = (JComboBox) e.getSource();
                FG = (FractalGenerator) mySource.getSelectedItem();
                FG.getInitialRange(R2D);
                drawFractal();
            }
        }
    }

    public class Save implements ActionListener{ // обработчик для сохранения фрактала
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser MF = new JFileChooser();
            FileFilter extensionFilter =new FileNameExtensionFilter("PNG Images", "png");
            MF.setFileFilter(extensionFilter);
            MF.setAcceptAllFileFilterUsed(false);
            //Выбор папки сохранения
            int userSelection = MF.showSaveDialog(JD);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                java.io.File file = MF.getSelectedFile(); // путь сохранения
                try {
                    BufferedImage displayImage = JD.getImage();
                    javax.imageio.ImageIO.write(displayImage, "png",file);
                }
                catch (Exception exception) {
                    JOptionPane.showMessageDialog(JD, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public static void main(String[] args){
        FractalExplorer A = new FractalExplorer(500);
        A.createAndShowGUI(); // // вызов метода для создания пользовательского интерфейса
    }
}
