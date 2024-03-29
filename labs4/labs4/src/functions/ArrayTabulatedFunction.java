
package functions;

import java.io.Serializable;


public class ArrayTabulatedFunction implements TabulatedFunction, Serializable {

    private FunctionPoint points[];         //массив хранит точки табулированной функции

    private int count;              //хранит кол-во точек табулированной функции

    public ArrayTabulatedFunction(FunctionPoint[] array) {
        
        if (array.length < 2) {
            throw new IllegalArgumentException();
        }
        count = array.length;
        for (int i = 1; i<array.length; i++) {
            if (array[i-1].x >= array[i].x) {
                throw new IllegalArgumentException();
            }
        }
        points = new FunctionPoint[array.length + array.length / 2];
        System.arraycopy(array, 0, points, 0, array.length);
    }

    public ArrayTabulatedFunction(double leftX, double rigthX, int pointsCount) {
        //создает объект класса табулированной функции
        //параметры, область определения и кол-во точек табуляции
        //точку создаются через равные промежутки, значения функции в точке равны 0
        if ((leftX >= rigthX) || (pointsCount < 2)) {
            throw new IllegalArgumentException();
        }
        points = new FunctionPoint[pointsCount + pointsCount / 2];

        this.points[0] = new FunctionPoint(leftX, 0);
        double step = (rigthX - leftX) / (pointsCount - 1);
        for (int i = 1; i < pointsCount; i++) {
            this.points[i] = new FunctionPoint(this.points[i - 1].x + step, 0);

        }

        count = pointsCount;
    }

    public ArrayTabulatedFunction(double leftX, double rigthX, double[] values) {
        //создает объект класса табулированной функции
        //параметры, область определения и кол-во точек табуляции
        //точку создаются через равные промежутки
        //значения функции равны элементам массива values
        if ((leftX >= rigthX) || (values.length < 2)) {
            throw new IllegalArgumentException();
        }
        points = new FunctionPoint[values.length + values.length / 2];

        this.points[0] = new FunctionPoint(leftX, values[0]);
        double step = (rigthX - leftX) / (values.length - 1);
        for (int i = 1; i < values.length; i++) {
            this.points[i] = new FunctionPoint(this.points[i - 1].x + step, values[i]);

        }

        count = values.length;

    }

    /**
     *
     * @return
     */
    @Override
    public double getLeftDomainBorder() {
        //возвращает левую границу области определения
        return this.points[0].x;

    }

    /**
     *
     * @return
     */
    @Override
    public int getPointsCount() {
        //возвращает кол-во точек табуляции
        return count;
    }

    /**
     *
     * @return
     */
    @Override
    public double getRightDomainBorder() {
        //возвращает правую границу области определения
        return this.points[this.getPointsCount() - 1].x;
    }

    /**
     *
     * @param x
     * @return
     */
    @Override
    public double getFunctionValue(double x) {
        //возвращает значение функции в точке
        //если введенное значение находится между точек табуляции, то вычисление
        //значения функции высчитывается при помощи линейной интерполяции
        if (x >= this.getLeftDomainBorder() && x <= this.getRightDomainBorder()) {
            //для поиска, того где находится точка используется бинарный поиск
            int last = this.getPointsCount();
            int first = 0;
            int mid = first + (last - first) / 2;
            if (points.length == 0) {
                return Double.NaN;
            }
            if (points[0].x > x) {
                return Double.NaN;
            }
            if (points[this.getPointsCount() - 1].x < x) {
                return Double.NaN;
            }
            while (first < last) {
                if (x <= points[mid].x) {
                    last = mid;
                } else {
                    first = mid + 1;
                }
                mid = first + (last - first) / 2;
            }
            if (points[last].x == x) { //если введеный x равен найденому, то выводим значение функции из массива
                return points[last].y;
            } else {
                double k = (points[last].y - points[last - 1].y) / (points[last].x - points[last - 1].x);
                double b = points[last].y - k * points[last].x;
                return k * x + b; //находим уравнение прямой и возвращаем значение в точке
            }
        } else {
            return Double.NaN;
        }
    }

    public FunctionPoint getPoint(int index) {
        //возвращает ссылку на точку с указанным индексом
        if (index >= 0 && index < count) {
            return new FunctionPoint(this.points[index]);
        } else {
            throw new FunctionPointIndexOutOfBoundsException();
        }
    }

    /**
     *
     * @param index
     * @param point
     * @throws InappropriateFunctionPointException
     */
    @Override
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        //устонавливает новые значения для точки с указанным индексом
        //значения берутся из объекта класса FunctionPoint
        if (!(index >= 0 && index < count)) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        if ((!(point.x >= this.points[index - 1].x && point.x <= this.points[index + 1].x)) || (point.x == this.points[index].x)) {
            throw new InappropriateFunctionPointException();
        }
        this.points[index].x = point.x;
        this.points[index].y = point.y;
    }

    /**
     *
     * @param index
     * @return
     */
    @Override
    public double getPointX(int index) {
        //возвращает значение Х для точки с указанным индексом
        if (index >= 0 && index < count) {
            return new FunctionPoint(this.points[index]).x;
        } else {
            throw new FunctionPointIndexOutOfBoundsException();
            //return Double.NaN;
        }
    }

    /**
     *
     * @param index
     * @param x
     * @throws InappropriateFunctionPointException
     */
    @Override
    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        //устанавливает новое значение Х, для точки с указанным индексом
        if (!(index >= 0 && index < count)) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        if (!(x >= this.points[index - 1].x && x <= this.points[index + 1].x)) {
            throw new InappropriateFunctionPointException();
        } else {
            this.points[index].x = x;
        }
    }

    /**
     *
     * @param index
     * @return
     */
    @Override
    public double getPointY(int index) {
        //возвращает значение Y для точки с указанным индексом
        if (index >= 0 && index < count) {
            return new FunctionPoint(this.points[index]).y;
        } else {
            throw new FunctionPointIndexOutOfBoundsException();
            //   return Double.NaN;
        }
    }

    /**
     *
     * @param index
     * @param y
     */
    @Override
    public void setPointY(int index, double y) {
        //устанавливает новое значение Y, для точки с указанным индексом
        if (index >= 0 && index < count) {
            this.points[index].y = y;
        } else {
            throw new FunctionPointIndexOutOfBoundsException();
        }
    }

    /**
     *
     * @param index
     */
    @Override
    public void deletePoint(int index) {
        //удаляет точки с указаным индексом
        if (count < 3) {
            throw new IllegalStateException();
        }
        if (index >= 0 && index < count) {
            System.arraycopy(this.points, index + 1, this.points, index, this.getPointsCount() - index);
            --count;
        } else {
            throw new FunctionPointIndexOutOfBoundsException();
        }
    }

    /**
     *
     * @param point
     * @throws InappropriateFunctionPointException
     */
    @Override
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        //добавляет новую точку в массив точек
        //для поиска, того где находится точка используется бинарный поиск
        int last = this.getPointsCount();
        int first = 0;
        int mid = first + (last - first) / 2;

        while (first < last) {
            if (point.x <= points[mid].x) {
                last = mid;
            } else {
                first = mid + 1;
            }
            mid = first + (last - first) / 2;
        }
        if (point.x == this.points[last].x) {
            throw new InappropriateFunctionPointException();
        }
        if (count < points.length) {
            System.arraycopy(this.points, last, this.points, last + 1, this.getPointsCount() - last);
            this.points[last] = point;
            ++count;
        } else {
            FunctionPoint pointsNew[] = new FunctionPoint[count + count / 2];
            System.arraycopy(this.points, 0, pointsNew, 0, count);
            this.points = pointsNew;
            System.arraycopy(this.points, last, this.points, last + 1, this.getPointsCount() - last);
            this.points[last] = point;
            ++count;

        }
    }

}
