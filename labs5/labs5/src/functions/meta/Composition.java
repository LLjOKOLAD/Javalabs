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
public class Composition implements Function {

    private Function func1, func2;

    public Composition(Function one, Function two) {
        this.func1 = one;
        this.func2 = two;
    }

    @Override
    public double getLeftDomainBorder() {
        return func1.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return func1.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return func1.getFunctionValue(func2.getFunctionValue(x));
    }

}
