/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import functions.Functions;

/**
 *
 * @author vlad
 */
public class Integrator extends Thread {

    private Task tasks;
    private Semaphore semaphore;
    private boolean isRun = false;

    public Integrator(Task t, Semaphore sem) {
        this.tasks = t;
        this.semaphore = sem;
    }

    @Override
    public void run() {
        isRun = true;
        for (int i = 0; i < tasks.getTasks() && isRun; i++) {
            try {
                semaphore.beginRead();
                double res = Functions.integrate(tasks.func, tasks.leftX, tasks.rigthX, tasks.step);
                System.out.println("Result leftX = " + tasks.leftX + " rightX = " + tasks.rigthX + " step = " + tasks.step + " integrate = " + res);
                semaphore.endRead();
            } catch (InterruptedException e) {
                System.out.println("Интегратор прервали во время сна, он корректно завершил свою работу");
            }
        }
    }

    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
        isRun = false;
    }

}
