/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functions;

import functions.meta.*;

/**
 *
 * @author vlad
 */
public class Functions {

    private Functions() {
    }

    public static Function shift(Function f, double shiftX, double shiftY) {
        return new Shift(f, shiftX, shiftY);
        
    }
    
    public static Function scale(Function f, double scaleX, double scaleY) {
        return new Scale(f, scaleX, scaleY);
    }
    
    public static Function power(Function f, double power) {
        return new Power(f, power);
    }
    
    public static Function sum(Function f1, Function f2) {
        return new Sum(f1, f2);
    }

    public static Function mult(Function f1, Function f2) {
        return new Mult(f1,f2);
    } 
    
    public static Function composition(Function f1, Function f2) {
        return new Composition(f1, f2);
    } 

}

