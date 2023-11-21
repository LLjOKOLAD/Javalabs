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
public class Shift implements Function {

    private double multX, multY;
    private Function func;

    public Shift(Function f, double x, double y) {
        this.func = f;
        this.multX = x;
        this.multY = y;
    }

    @Override
    public double getLeftDomainBorder() {

        return multX + func.getLeftDomainBorder();

    }

    @Override
    public double getRightDomainBorder() {

        return multX + func.getRightDomainBorder();

    }

    @Override
    public double getFunctionValue(double x) {

        return multY + func.getFunctionValue(x);

    }

}
