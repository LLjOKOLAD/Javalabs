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
public class Power implements Function {

    private Function func;
    private double pok;

    public Power(Function f, double p) {
        this.func = f;
        this.pok = p;
    }

    @Override
    public double getLeftDomainBorder() {
        return func.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return func.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return Math.pow(func.getFunctionValue(x), pok);
    }

}
