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
public interface TabulatedFunctionFactory {
    
    /**
     *
     * @param leftX
     * @param rigthX
     * @param values
     * @return
     */
    public TabulatedFunction createTabulatedFunction(double leftX, double rigthX, double[] values);
    
    /**
     *
     * @param leftX
     * @param rigthX
     * @param pointsCount
     * @return
     */
    public TabulatedFunction createTabulatedFunction(double leftX, double rigthX, int pointsCount);
    
    /**
     *
     * @param array
     * @return
     */
    public TabulatedFunction createTabulatedFunction(FunctionPoint[] array);
}
