/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

/**
 *
 * @author vlad
 */
public class Semaphore {

    private boolean canWrite = true;

    public synchronized void beginRead()
            throws InterruptedException {
        while (canWrite) {
            wait();
        }
    }

    public synchronized void endRead() {
        canWrite = true;
        notifyAll();
    }

    public synchronized void beginWrite()
            throws InterruptedException {
        while (!canWrite) {
            wait();
        }
    }

    public synchronized void endWrite() {
        canWrite = false;
        notifyAll();
    }
}
