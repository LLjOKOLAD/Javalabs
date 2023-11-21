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
public class Scale implements Function {

    private double multX, multY;
    private Function func;

    public Scale(Function f, double x, double y) {
        this.func = f;
        this.multX = x;
        this.multY = y;
    }

    @Override
    public double getLeftDomainBorder() {
        if (multX >= 0) {
            return multX * func.getLeftDomainBorder();
        } else {
            return func.getLeftDomainBorder() / multX;
        }
    }

    @Override
    public double getRightDomainBorder() {
        if (multX >= 0) {
            return multX * func.getRightDomainBorder();
        } else {
            return func.getRightDomainBorder() / multX;
        }
    }

    @Override
    public double getFunctionValue(double x) {
        if (multY >= 0) {
            return multY * func.getFunctionValue(x);
        } else {
            return func.getFunctionValue(x) / multY;
        }
    }

}
