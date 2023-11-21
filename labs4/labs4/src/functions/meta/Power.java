
package functions.meta;

import functions.Function;


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
