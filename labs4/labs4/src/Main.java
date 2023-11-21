
import functions.*;
import functions.basic.*;
import java.io.*;



public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // TODO code application logic here
        TabulatedFunction tabCos, tabSin;
        Sin sin;
        Cos cos;
        sin = new Sin();
        System.out.println("Синус");
        for (double i = 0; i < 2 * Math.PI; i += 0.1) {
            System.out.println("Значение в точке x=" + i + ": " + sin.getFunctionValue(i));
        }

        System.out.println("Косинус");
        cos = new Cos();
        for (double i = 0; i < 2 * Math.PI; i += 0.1) {
            System.out.println("Значение в точке x=" + i + ": " + cos.getFunctionValue(i));
        }

        System.out.println("Табулированный синус");
        tabSin = TabulatedFunctions.tabulate(sin, 0, 2 * Math.PI, 10);
        for (double i = 0; i < 2 * Math.PI; i += 0.1) {
            System.out.println("Значение в точке x=" + i + ": " + tabSin.getFunctionValue(i));
        }

        System.out.println("Табулированный косинус");
        tabCos = TabulatedFunctions.tabulate(cos, 0, 2 * Math.PI, 10);
        for (double i = 0; i < 2 * Math.PI; i += 0.1) {
            System.out.println("Значение в точке x=" + i + ": " + tabCos.getFunctionValue(i));
        }

        Function cos2 = Functions.power(tabCos, 2);
        Function sin2 = Functions.power(tabSin, 2);
        Function Sum = Functions.sum(sin2, cos2);

        System.out.println("Табулированная сумма");
        for (double i = 0; i < 2 * Math.PI; i += 0.1) {
            System.out.println("Значение в точке x=" + i + ": " + Sum.getFunctionValue(i));
        }

        System.out.println("Табулированная экспонента");
        Exp e = new Exp();
        TabulatedFunction expo = TabulatedFunctions.tabulate(e, 0, 10, 11);
        FileWriter writer = new FileWriter("exp.txt");
        TabulatedFunctions.writeTabulatedFunction(expo, writer);
        writer.flush();
        writer.close();
        FileReader reader = new FileReader("exp.txt");
        TabulatedFunction expi = TabulatedFunctions.readTabulatedFunction(reader);
        reader.close();

        System.out.println("Табулированный логарифм");
        Log l = new Log(Math.E);
        TabulatedFunction logo = TabulatedFunctions.tabulate(l, 0, 10, 11);
        OutputStream output = new FileOutputStream("log.txt");
        TabulatedFunctions.outputTabulatedFunction(logo, output);
        output.flush();
        output.close();
        InputStream input = new FileInputStream("log.txt");
        TabulatedFunction logi = TabulatedFunctions.inputTabulatedFunction(input);
        input.close();

        System.out.println("Логарифм от экспоненты");
        Log ln = new Log(Math.E);
        Exp nl = new Exp();
        Function comp = Functions.composition(ln, nl);
        TabulatedFunction tabLn = TabulatedFunctions.tabulate(comp, 0, 10, 11);
        for (double i = 0; i < 11; i++) {
            System.out.println("Значение в точке x=" + i + ": " + tabLn.getFunctionValue(i));
        }
        FileOutputStream fos = new FileOutputStream("temp.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
        objectOutputStream.writeObject(tabLn);
        objectOutputStream.close();
        
        FileInputStream fos1 = new FileInputStream("temp.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fos1);
        TabulatedFunction comp1 = (TabulatedFunction) objectInputStream.readObject();
        objectInputStream.close();
        for (double i = 0; i < 11; i++) {
            System.out.println("Значение в точке x=" + i + ": " + comp1.getFunctionValue(i));
        }
        
    }

}
