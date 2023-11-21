/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functions.meta;

import functions.Function;

/**
 *
 * @author vlad
 */
public class Mult implements Function {

    Function one, two;

    public Mult(Function func1, Function func2) {
        this.one = func1;
        this.two = func2;
    }

    @Override
    public double getLeftDomainBorder() {
        return Math.max(one.getLeftDomainBorder(), two.getLeftDomainBorder());
    }

    @Override
    public double getRightDomainBorder() {
        return Math.min(one.getRightDomainBorder(), two.getRightDomainBorder());
    }

    @Override
    public double getFunctionValue(double x) {
        return one.getFunctionValue(x) * two.getFunctionValue(x);
    }

}
