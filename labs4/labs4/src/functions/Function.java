
package functions;


public interface Function {

    /**
     *
     * @return
     */
    public double getLeftDomainBorder();

    /**
     *
     * @return
     */
    public double getRightDomainBorder();

    /**
     *
     * @param x
     * @return
     */
    public double getFunctionValue(double x);
}
