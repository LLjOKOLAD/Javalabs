package functions;


public class TabulatedFunction {

    private FunctionPoint points[];         //массив хранит точки табулированной функции

    private int count;              //хранит кол-во точек табулированной функции

    public TabulatedFunction(double leftX, double rigthX, int pointsCount) {
        //создает объект класса табулированной функции
        //параметры, область определения и кол-во точек табуляции
        //точку создаются через равные промежутки, значения функции в точке равны 0
        points = new FunctionPoint[pointsCount + pointsCount / 2];

        this.points[0] = new FunctionPoint(leftX, 0);

        for (int i = 1; i < pointsCount; i++) {
            this.points[i] = new FunctionPoint(this.points[i - 1].x + (rigthX - leftX) / (pointsCount - 1), 0);

        }

        count = pointsCount;
    }

    public TabulatedFunction(double leftX, double rigthX, double[] values) {
        //создает объект класса табулированной функции
        //параметры, область определения и кол-во точек табуляции
        //точку создаются через равные промежутки
        //значения функции равны элементам массива values

        points = new FunctionPoint[values.length + values.length / 2];

        this.points[0] = new FunctionPoint(leftX, values[0]);

        for (int i = 1; i < values.length; i++) {
            this.points[i] = new FunctionPoint(this.points[i - 1].x + (rigthX - leftX) / (values.length - 1), values[i]);

        }

        count = values.length;

    }

    public double getLeftDomainBorder() {
        //возвращает левую границу области определения
        return this.points[0].x;

    }

    public int getPointsCount() {
        //возвращает кол-во точек табуляции
        return count;
    }

    public double getRightDomainBorder() {
        //возвращает правую границу области определения
        return this.points[this.getPointsCount() - 1].x;
    }

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
            return null;
        }
    }

    public void setPoint(int index, FunctionPoint point) {
        //устонавливает новые значения для точки с указанным индексом
        //значения берутся из объекта класса FunctionPoint
        if (point.x >= this.points[index - 1].x && point.x <= this.points[index + 1].x || index >= 0 && index < count) {
            this.points[index].x = point.x;
            this.points[index].y = point.y;
        }
    }

    public double getPointX(int index) {
        //возвращает значение Х для точки с указанным индексом
        if (index >= 0 && index < count) {
            return new FunctionPoint(this.points[index]).x;
        } else {
            return Double.NaN;
        }
    }

    public void setPointX(int index, double x) {
        //устанавливает новое значение Х, для точки с указанным индексом
        if (x >= this.points[index - 1].x && x <= this.points[index + 1].x || index >= 0 && index < count) {
            this.points[index].x = x;
        }
    }

    public double getPointY(int index) {
        //возвращает значение Y для точки с указанным индексом
        if (index >= 0 && index < count) {
            return new FunctionPoint(this.points[index]).y;
        } else {
            return Double.NaN;
        }
    }

    public void setPointY(int index, double y) {
        //устанавливает новое значение Y, для точки с указанным индексом
        if (index >= 0 && index < count) {
            this.points[index].y = y;
        }
    }

    public void deletePoint(int index) {
        //удаляет точки с указаным индексом
        if (index >= 0 && index < count) {
            System.arraycopy(this.points, index + 1, this.points, index, this.getPointsCount() - index);
            --count;
        }
    }

    public void addPoint(FunctionPoint point) {
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
        if (count < points.length) {
            System.arraycopy(this.points, last, this.points, last + 1, this.getPointsCount() - last);
            this.points[last] = point;
            ++count;
        } else {
            FunctionPoint pointsNew[] = new FunctionPoint[count + count / 2];
            System.arraycopy(this.points, 0, pointsNew, 0, count);
            this.points=pointsNew;
            System.arraycopy(this.points, last, this.points, last + 1, this.getPointsCount() - last);
            this.points[last] = point;
            ++count;

        }
    }

}
