/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functions;

import java.io.Serializable;

/**
 *
 * @author vlad
 */
public interface TabulatedFunction extends Function, Serializable, Cloneable, Iterable<FunctionPoint>{

    /**
     *
     * @return
     * @throws java.lang.CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException;

    /**
     *
     * @return
     */
    public int getPointsCount();

    /**
     *
     * @param index
     * @param point
     * @throws InappropriateFunctionPointException
     */
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException;

    /**
     *
     * @param index
     * @return
     */
    public double getPointX(int index);

    /**
     *
     * @param index
     * @param x
     * @throws InappropriateFunctionPointException
     */
    public void setPointX(int index, double x) throws InappropriateFunctionPointException;

    /**
     *
     * @param index
     * @return
     */
    public double getPointY(int index);

    /**
     *
     * @param index
     * @param y
     */
    public void setPointY(int index, double y);

    

    /**
     *
     * @param index
     */
    public void deletePoint(int index);

    /**
     *
     * @param point
     * @throws InappropriateFunctionPointException
     */
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;
    
    /**
     *
     * @param index
     * @return
     */
    public FunctionPoint getPoint(int index);
}
