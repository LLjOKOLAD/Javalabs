/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import functions.Function;

/**
 *
 * @author vlad
 */
public class Task {

    public Function func;
    public double leftX, rigthX, step;
    private int tasks;

    public Task(int t) {
        if (t<=0) {
            throw new IllegalArgumentException();
        } 
        this.tasks = t;
    }
    
    public int getTasks(){
        return this.tasks;
    }

}
