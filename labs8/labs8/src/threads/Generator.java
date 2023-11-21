/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import functions.basic.Log;

/**
 *
 * @author vlad
 */
public class Generator extends Thread {

    private Task tasks;
    private Semaphore semaphore;
    private boolean isRun = false;

    public Generator(Task t, Semaphore sem) {
        this.tasks = t;
        this.semaphore = sem;
    }

    /**
     *
     */
    @Override
    public void run() {
        isRun = true;
        for (int i = 0; i < tasks.getTasks() && isRun; i++) {
            try {
                Log l = new Log(1 + (Math.random() * 9));
                semaphore.beginWrite();
                tasks.func = l;
                tasks.leftX = Math.random() * 100;
                tasks.rigthX = Math.random() * 100 + 100;
                tasks.step = Math.random();
                semaphore.endWrite();
                System.out.println("Source leftX = " + tasks.leftX + " rightX = " + tasks.rigthX + " step = " + tasks.step);
            } catch (InterruptedException ex) {
                System.out.println("Генератор прервали во время сна, он корректно завершил свою работу");
            }
        }
    }

    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
        isRun = false;
    }

}
