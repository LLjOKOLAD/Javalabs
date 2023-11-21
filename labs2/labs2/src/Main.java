
import functions.FunctionPoint;
import functions.TabulatedFunction;


public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TabulatedFunction Parabola;
        double[] arr = {1, 0, 1};
        Parabola = new TabulatedFunction(-3, 3, arr);
        FunctionPoint p = new FunctionPoint(20.5, 5);
        System.out.println("Левая граница " + Parabola.getLeftDomainBorder());
        System.out.println("Правая граница " + Parabola.getRightDomainBorder());
        for (int i = 0; i < Parabola.getPointsCount(); i++) {

            System.out.print("Точка " + (i + 1) + " x = " + Parabola.getPointX(i));
            System.out.println("\n       " + " y = " + Parabola.getPointY(i));

        }
        System.out.println();
        System.out.println(" Y = " + Parabola.getFunctionValue(2.5));
        System.out.println("Кол-во точек " + Parabola.getPointsCount());
        System.out.println("Добавляем новую точку c координатами (2.5 , 5)");
        Parabola.addPoint(p);
        System.out.println("Кол-во точек " + Parabola.getPointsCount());
        for (int i = 0; i < Parabola.getPointsCount(); i++) {

            System.out.print("Точка " + (i + 1) + " x = " + Parabola.getPointX(i));
            System.out.println("\n       " + " y = " + Parabola.getPointY(i));

        }
        System.out.println();
        FunctionPoint p1 = new FunctionPoint(1.5, 5);
        System.out.println("Добавляем новую точку c координатами (1.5 , 5)");
        Parabola.addPoint(p1);
        System.out.println("Кол-во точек " + Parabola.getPointsCount());
        for (int i = 0; i < Parabola.getPointsCount(); i++) {

            System.out.print("Точка " + (i + 1) + " x = " + Parabola.getPointX(i));
            System.out.println("\n       " + " y = " + Parabola.getPointY(i));

        }
        
        
        Parabola.deletePoint(400);
        System.out.println();
        System.out.println("Удаляем точку с индексом 5");
        System.out.println("Кол-во точек " + Parabola.getPointsCount());
        for (int i = 0; i < 10; i++) {

            System.out.print("Точка " + (i + 1) + " x = " + Parabola.getPointX(i));
            System.out.println("\n       " + " y = " + Parabola.getPointY(i));

        }
    }

}
