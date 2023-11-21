/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functions;

/**
 *
 * @author vlad
 */
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
