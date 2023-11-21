/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functions;

import java.io.Serializable;

/**
 *
 * @author Vlad
 */
public class FunctionPoint implements Serializable{

    double x, y;

    public FunctionPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    FunctionPoint(FunctionPoint point) {
        this.x = point.x;
        this.y = point.y;
    }

    FunctionPoint() {
        this.x = 0;
        this.y = 0;
    }
    
    @Override
    public String toString(){
        return new String("("+this.x+"; "+this.y+")");
    }
    
    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof FunctionPoint) {
            FunctionPoint point = (FunctionPoint) o;
            if (this.x == point.x) {
                if (this.y == point.y) {
                    return true;   
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        return hash;
    }
    
    /**
     *
     * @return
     */
    @Override
    public Object clone() {
        return new FunctionPoint(this);
        
    }
}
