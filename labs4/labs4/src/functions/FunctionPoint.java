
package functions;

import java.io.Serializable;


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

}
