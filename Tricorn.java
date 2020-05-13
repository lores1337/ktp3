import java.awt.geom.Rectangle2D;

public class Tricorn extends FractalGenerator {    // вычисление фрактала Tricorn
    public static final int MAX_ITERATIONS = 2000; // максимальное число итераций

    @Override
    public void getInitialRange(Rectangle2D.Double range) { // функция позволяет генератору фракталов определить
        range.x = -2;                                       // наиболее "интересную область" комплексной плоскости
        range.y = -1.5;                                    // range - прямоугольный объект
        range.width = 4;  // ширина
        range.height = 4; // высота
    }

    @Override
    public int numIterations(double x, double y) {
        int k=0;
        double zr = x, zi = y;
        for(int i=0;i<MAX_ITERATIONS;i++){
            double x2=zr*zr-zi*zi+x;
            double y2= -2*zr*zi+y;
            zr=x2;
            zi=y2;
            if (zr*zr+zi*zi>4){
                return i;
            }
        }
        return -1; // если произошло 2000 итераций
    }
    public String toString(){
        return "Tricorn";
    }
}
