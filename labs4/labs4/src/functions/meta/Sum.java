
package functions.meta;

import functions.Function;

public class Sum implements Function {

    Function one, two;

    public Sum(Function func1, Function func2) {
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
        return one.getFunctionValue(x) + two.getFunctionValue(x);
    }

}
