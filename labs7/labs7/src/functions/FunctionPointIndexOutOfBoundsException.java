/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package functions;
//исключение выхода за границы набора точек при обращении к ним по номеру, наследует от класса 
/**
 *
 * @author vlad
 */
public class FunctionPointIndexOutOfBoundsException extends IndexOutOfBoundsException {

    @Override
    public String getMessage() {
        return "Выход за пределы количества точек";
        
    }
}
