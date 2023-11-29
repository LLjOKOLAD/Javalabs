package functions;

public class LinkedListTabulatedFunction implements TabulatedFunction {

    // Вложенный класс для представления узла списка
    private class FunctionNode {

        private FunctionPoint point = null;
        private FunctionNode prev = null, next = null;

    }

    private int length, currentInd;
    private FunctionNode headMain = new FunctionNode(), head, tail, current;

    {
        // Инициализация sentinel-узла
        headMain.next = headMain;
        headMain.prev = headMain;
        head = headMain;
        tail = headMain;
        current = headMain;
    }

    // Конструктор для создания функции на основе левой и правой границ, а также количества точек
    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount) {
        if (leftX >= rightX || pointsCount < 2) {
            throw new IllegalArgumentException();
        }

        length = pointsCount;
        head = new FunctionNode();
        headMain.next = head;
        current = head;
        head.prev = headMain;

        // Инициализация первой точки и последующих точек
        current.point = new FunctionPoint(leftX, 0);
        current.next = new FunctionNode();
        current.next.prev = current;
        current = current.next;
        currentInd++;
        double step = (rightX - leftX) / (pointsCount - 1);
        for (int i = 1; i < length; i++) {
            current.point = new FunctionPoint(current.prev.point.x + step, 0);
            current.next = new FunctionNode();
            current.next.prev = current;
            current = current.next;
            currentInd++;
        }
        tail = current.prev;
        tail.next = headMain;
        headMain.prev = tail;

    }

    // Конструктор для создания функции на основе границ и массива значений
    public LinkedListTabulatedFunction(double leftX, double rightX, double values[]) {
        if (leftX >= rightX && values.length < 2) {
            throw new IllegalArgumentException();
        }

        length = values.length;
        head = new FunctionNode();
        headMain.next = head;
        current = head;
        head.prev = null;

        // Инициализация первой точки и последующих точек на основе массива значений
        current.point = new FunctionPoint(leftX, values[0]);
        current.next = new FunctionNode();
        current.next.prev = current;
        current = current.next;
        currentInd++;

        double step = (rightX - leftX) / (values.length - 1);

        for (int i = 1; i < length; i++) {
            current.point = new FunctionPoint(current.prev.point.x + step, values[i]);
            current.next = new FunctionNode();
            current.next.prev = current;
            current = current.next;
            currentInd++;
        }
        tail = current.prev;
        tail.next = headMain;
    }

    // Получение узла списка по индексу
    FunctionNode getNodeByIndex(int index) {
        int fromTail = length - index - 1;
        int fromHead = index;
        int fromCurrent = Math.abs(currentInd - index);

        if (fromTail < fromHead) {
            if (fromTail < fromCurrent) {
                current = tail;
                currentInd = length - 1;
            }
        } else {
            if (fromHead < fromCurrent) {
                current = head;
                currentInd = 0;
            }
        }

        if (index < currentInd) {
            while (currentInd != index) {
                current = current.prev;
                currentInd--;
            }
        } else {
            while (currentInd != index) {
                current = current.next;
                currentInd++;
            }
        }
        return current;
    }

    // Добавление узла в конец списка
    FunctionNode addNodeToTail() {
        tail.next = new FunctionNode();
        tail.next.prev = tail;
        tail.next.next = headMain;
        tail = tail.next;
        length++;
        headMain.prev = tail;
        return tail;
    }

    // Добавление узла в указанное место в списке
    FunctionNode addNodeByIndex(int index) {
        if (index < 0 || index > length) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        if (index == length) {
            return addNodeToTail();
        }
        getNodeByIndex(index);
        FunctionNode node = new FunctionNode();
        node.next = current;
        node.prev = current.prev;
        current.prev.next = node;
        current.prev = node;

        current = node;
        length++;

        return current;
    }

    // Удаление узла по индексу
    FunctionNode deleteNodeByIndex(int index) {
        if (index < 0 || index >= length) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        getNodeByIndex(index);
        FunctionNode node = current;
        current.prev.next = current.next;
        current.next.prev = current.prev;
        current = current.prev;
        --currentInd;

        --length;
        head = headMain.next;
        tail = headMain.prev;
        return node;
    }

    // Получение левой границы области определения функции
    @Override
    public double getLeftDomainBorder() {
        if (length == 0) {
            throw new IllegalStateException();
        }
        return head.point.x;
    }

    // Получение правой границы области определения функции
    @Override
    public double getRightDomainBorder() {
        if (length == 0) {
            throw new IllegalStateException();
        }
        return tail.point.x;
    }

    // Получение количества точек в функции
    @Override
    public int getPointsCount() {
        return length;
    }

    // Установка точки по индексу
    @Override
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        if (length == 0) {
            throw new IllegalStateException();
        }
        if (index < 0 || index >= length) {
            throw new FunctionPointIndexOutOfBoundsException();
        }

        double left = Double.MIN_VALUE;
        double right = Double.MAX_VALUE;

        FunctionNode node = getNodeByIndex(index);

        if (node.prev != null) {
            left = node.prev.point.x;
        }
        if (node.next != null) {
            right = node.next.point.x;
        }

        if (left > point.x || right < point.x) {
            throw new InappropriateFunctionPointException();
        }

        node.point = new FunctionPoint(point);
    }

    // Получение x-координаты точки по индексу
    @Override
    public double getPointX(int index) {
        if (length == 0) {
            throw new IllegalStateException();
        }
        if (index < 0 || index >= length) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        return getNodeByIndex(index).point.x;
    }

    // Установка x-координаты точки по индексу
    @Override
    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        if (length == 0) {
            throw new IllegalStateException();
        }
        if (index < 0 || index >= length) {
            throw new FunctionPointIndexOutOfBoundsException();
        }

        double left = Double.MIN_VALUE, right = Double.MAX_VALUE;

        FunctionNode node = getNodeByIndex(index);

        if (node.prev != null) {
            left = node.prev.point.x;
        }
        if (node.next != null) {
            right = node.next.point.x;
        }

        if (left > x || right < x) {
            throw new InappropriateFunctionPointException();
        }

        node.point.x = x;
    }

    // Получение y-координаты точки по индексу
    @Override
    public double getPointY(int index) {
        if (length == 0) {
            throw new IllegalStateException();
        }
        if (index < 0 || index >= length) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        return getNodeByIndex(index).point.y;
    }

    // Установка y-координаты точки по индексу
    @Override
    public void setPointY(int index, double y) {
        if (length == 0) {
            throw new IllegalStateException();
        }
        if (index < 0 || index >= length) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        getNodeByIndex(index).point.y = y;
    }

    // Получение значения функции в заданной точке
    @Override
    public double getFunctionValue(double x) {
        if (length == 0) {
            throw new IllegalStateException();
        }
        if (x < head.point.x || x > tail.point.x) {
            throw new FunctionPointIndexOutOfBoundsException();
        }

        current = head;
        currentInd = 0;
        while (current.point.x > x) {
            current = current.next;
            currentInd++;
        }

        if (current.point.x == x) {
            return current.point.y;
        }

        return current.point.y + (x - current.point.x) * (current.next.point.y - current.point.y) / (current.next.point.x - current.point.x);
    }

    // Удаление точки по индексу
    @Override
    public void deletePoint(int index) {
        if (length < 3) {
            throw new IllegalStateException();
        }
        if (index < 0 || index >= length) {
            throw new FunctionPointIndexOutOfBoundsException();
        }

        deleteNodeByIndex(index);
    }

    // Добавление точки в функцию
    @Override
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        if (length != 0 && (point.x < head.point.x || point.x > tail.point.x)) {
            throw new InappropriateFunctionPointException();
        }

        if (length == 0) {
            head = new FunctionNode();
            head.point = new FunctionPoint(point);
            length++;
            tail = head;
            current = head;
            currentInd = 0;
            return;
        }

        current = head;
        currentInd = 0;
        while (current.point.x < point.x) {
            current = current.next;
            currentInd++;
        }

        if (current.point.x == point.x) {
            throw new InappropriateFunctionPointException();
        }

        addNodeByIndex(currentInd).point = point;
    }

}
