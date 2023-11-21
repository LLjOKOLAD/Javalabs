
import functions.Function;
import functions.Functions;
import functions.basic.Exp;
import functions.basic.Log;
import threads.Generator;
import threads.Integrator;
import threads.Semaphore;
import threads.SimpleGenerator;
import threads.SimpleIntegrator;
import threads.Task;

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
//        Function exp = new Exp();
//        double theoreticValue = Math.E - 1;
//        double step = 0.00000005;
//        System.out.println("Значение полученное при помощи функции " + Functions.integrate(exp, 0, 1, step));
//        System.out.println("Теоретическое значение " + theoreticValue);
//        System.out.println("Шаг = " + step + "\n");
//        System.out.println("Задание 2");
//        main.nonThread();
//        System.out.println("Задание 3");
//        main.simpleThreads();
        System.out.println("Задание 4");
        main.complicatedThreads();
        
        
    }

}
