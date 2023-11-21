
import functions.*;
import functions.basic.*;
import threads.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author vlad
 */
public class main {

    public static void simpleThreads() {
        Task t = new Task(100);
        Thread generator = new Thread(new SimpleGenerator(t));
        //  generator.setPriority(Thread.MAX_PRIORITY);
        generator.start();
        Thread integrator = new Thread(new SimpleIntegrator(t));
        //   integrator.setPriority(Thread.MIN_PRIORITY);
        integrator.start();
    }

    public static void complicatedThreads() throws InterruptedException {
        Task t = new Task(100);
        Semaphore semaphore = new Semaphore();
        Generator generator = new Generator(t, semaphore);
        Integrator integrator = new Integrator(t, semaphore);

        integrator.setPriority(10);

        generator.start();
        integrator.start();
        Thread.sleep(50);
        generator.interrupt();
        integrator.interrupt();

    }

    public static void nonThread() {
        Task t = new Task(100);
        for (int i = 0; i < t.getTasks(); i++) {
            t.func = new Log(1 + (Math.random() * 9));
            t.leftX = Math.random() * 100;
            t.rigthX = Math.random() * 100 + 100;
            t.step = Math.random();
            System.out.println("Source leftX = " + t.leftX + " rightX = " + t.rigthX + " step = " + t.step);
            double res = Functions.integrate(t.func, t.leftX, t.rigthX, t.step);
            System.out.println("Result leftX = " + t.leftX + " rightX = " + t.rigthX + " step = " + t.step + " integrate = " + res);
        }
    }

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
//        System.out.println("Задание 1");
//        TabulatedFunction f = new LinkedListTabulatedFunction(-1, 1, 5);
//        for (FunctionPoint p : f) {
//            System.out.println(p);
//        }
//        System.out.println("Задание 2");
//        Function f = new Cos();
//        TabulatedFunction tf;
//        tf = TabulatedFunctions.tabulate(f, 0, Math.PI, 11);
//        System.out.println(tf.getClass());
//        TabulatedFunctions.setTabulatedFunctionFactory(new LinkedListTabulatedFunction.LinkedListTabulatedFunctionFactory());
//        tf = TabulatedFunctions.tabulate(f, 0, Math.PI, 11);
//        System.out.println(tf.getClass());
//        TabulatedFunctions.setTabulatedFunctionFactory(new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory());
//        tf = TabulatedFunctions.tabulate(f, 0, Math.PI, 11);
//        System.out.println(tf.getClass());
        System.out.println("Задание 3");
        TabulatedFunction f;
        f = TabulatedFunctions.createTabulatedFunction(
                ArrayTabulatedFunction.class, 0, 10, 3);
        System.out.println(f.getClass());
        System.out.println(f);

        f = TabulatedFunctions.createTabulatedFunction(
                ArrayTabulatedFunction.class, 0, 10, new double[]{0, 10});
        System.out.println(f.getClass());
        System.out.println(f);

        f = TabulatedFunctions.createTabulatedFunction(
                LinkedListTabulatedFunction.class,
                new FunctionPoint[]{
                    new FunctionPoint(0, 0),
                    new FunctionPoint(10, 10)
                }
        );
        System.out.println(f.getClass());
        System.out.println(f);

        f = TabulatedFunctions.tabulate(
                LinkedListTabulatedFunction.class, new Sin(), 0, Math.PI, 11);
        System.out.println(f.getClass());
        System.out.println(f);

    }

}
