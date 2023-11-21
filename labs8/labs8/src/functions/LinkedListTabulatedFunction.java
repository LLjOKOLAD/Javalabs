/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functions;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author vlad
 */
public class LinkedListTabulatedFunction implements TabulatedFunction, Serializable, Cloneable {

    @Override

    public Iterator<FunctionPoint> iterator() {
        return new Iterator<FunctionPoint>() {
            private FunctionNode node = head;

            @Override
            public boolean hasNext() {
                return node != headMain;
            }

            @Override
            public FunctionPoint next() {
                if (node == headMain) {
                    throw new NoSuchElementException();
                }
                FunctionNode result = node;
                node = node.next;
                return (FunctionPoint) result.point.clone();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public static class LinkedListTabulatedFunctionFactory implements TabulatedFunctionFactory {

        @Override
        public TabulatedFunction createTabulatedFunction(FunctionPoint[] points) {
            return new LinkedListTabulatedFunction(points);
        }

        @Override
        public TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount) {
            return new LinkedListTabulatedFunction(leftX, rightX, pointsCount);
        }

        @Override
        public TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values) {
            return new LinkedListTabulatedFunction(leftX, rightX, values);
        }
    }

    private class FunctionNode {

        private FunctionPoint point = null;
        private FunctionNode prev = null, next = null;

    }

    private int length, currentInd;
    private FunctionNode headMain = new FunctionNode(), head, tail, current;

    {
        headMain.next = headMain;
        headMain.prev = headMain;
        head = headMain;
        tail = headMain;
        current = headMain;
    }

    public LinkedListTabulatedFunction(FunctionPoint[] array) {
        if (array.length < 2) {
            throw new IllegalArgumentException();
        }

        for (int i = 1; i < array.length; i++) {

            if (array[i - 1].x >= array[i].x) {

                headMain.next = headMain;
                headMain.prev = headMain;
                head = headMain;
                tail = headMain;
                current = headMain;

                throw new IllegalArgumentException();

            } else {
                current.next = new FunctionNode();
                current.next.prev = current;
                current.next.point = new FunctionPoint(array[i - 1].x, array[i - 1].y);
                current = current.next;
            }
        }
        current.next = new FunctionNode();
        current.next.prev = current;
        current.next.point = new FunctionPoint(array[array.length - 1].x, array[array.length - 1].y);
        current = current.next;
        length = array.length;
        head = headMain.next;
        headMain.prev = current;
        tail = current;
        tail.next = headMain;

    }

    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount) {
        if (leftX >= rightX || pointsCount < 2) {
            throw new IllegalArgumentException();
        }

        length = pointsCount;
        head = new FunctionNode();
        headMain.next = head;
        current = head;
        head.prev = headMain;

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

    public LinkedListTabulatedFunction(double leftX, double rightX, double values[]) {
        if (leftX >= rightX && values.length < 2) {
            throw new IllegalArgumentException();
        }

        length = values.length;
        head = new FunctionNode();
        headMain.next = head;
        current = head;
        head.prev = null;

        current.point = new FunctionPoint(leftX, values[0]);
        current.next = new FunctionNode();
        head.prev = headMain;
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

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder("{");

        currentInd = 0;
        current = head;

        while (current != headMain) {
            buffer.append(current.point.toString()).append(", ");
            currentInd++;
            current = current.next;
        }

        buffer.deleteCharAt(buffer.length() - 1).deleteCharAt(buffer.length() - 1);
        buffer.append("}");
        return buffer.toString();
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof TabulatedFunction) {
            if (o instanceof LinkedListTabulatedFunction) {
                LinkedListTabulatedFunction func = (LinkedListTabulatedFunction) o;
                if (func.length != this.length) {
                    return false;
                }
                currentInd = 0;
                this.current = this.head;
                FunctionNode funcCurrent = func.head;
                while (this.current != this.headMain) {
                    if (this.current.point.x != funcCurrent.point.x) {
                        return false;
                    }
                    if (this.current.point.y != funcCurrent.point.y) {
                        return false;
                    }
                    this.current = this.current.next;
                    funcCurrent = funcCurrent.next;
                    ++currentInd;
                }
                return true;
            } else {
                TabulatedFunction func = (TabulatedFunction) o;
                if (func.getPointsCount() != this.length) {
                    return false;
                }
                for (int i = 0; i < this.length; i++) {
                    if (!(this.getPoint(i).equals(func.getPoint(i)))) {
                        return false;
                    }
                }
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        currentInd = 0;
        current = head;

        int result = 0;

        while (current != headMain) {
            result += current.point.hashCode();
            currentInd++;
            current = current.next;
        }

        return length + result;
    }

    private FunctionNode getNodeByIndex(int index) { //получаем элемент списка с текущим индексом

        int fromTail = length - index - 1;
        int fromHead = index;
        int fromCurrent = Math.abs(currentInd - index);

        if (fromTail < fromHead) { //ищем от куда быстрее добраться до нужного индекса
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

        if (index < currentInd) { //нашли индекс от куда быстрее добраться, теперь выбираем вперед двигаться или назад
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

    private FunctionNode addNodeToTail() { //добавить элемент в конец
        tail.next = new FunctionNode();
        tail.next.prev = tail;
        tail.next.next = headMain;
        tail = tail.next;
        length++;
        headMain.prev = tail;
        return tail;
    }

    private FunctionNode addNodeByIndex(int index) {//добавить точку в указанное место
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

    private FunctionNode deleteNodeByIndex(int index) { //удалить точку с указанным индексом
        if (index < 0 || index >= length) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        getNodeByIndex(index);
        FunctionNode node = current;
        // currentInd=index;
        current.prev.next = current.next;
        current.next.prev = current.prev;
        current = current.prev;
        --currentInd;

        --length;
        head = headMain.next;
        tail = headMain.prev;
        return node;
    }

    /**
     *
     * @return
     */
    @Override
    public double getLeftDomainBorder() {
        if (length == 0) {
            throw new IllegalStateException();
        }
        return head.point.x;
    }

    /**
     *
     * @return
     */
    @Override
    public double getRightDomainBorder() {
        if (length == 0) {
            throw new IllegalStateException();
        }
        return tail.point.x;
    }

    /**
     *
     * @return
     */
    @Override
    public int getPointsCount() {
        return length;
    }

    /**
     *
     * @param index
     * @param point
     * @throws InappropriateFunctionPointException
     */
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

    /**
     *
     * @param index
     * @return
     */
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

    /**
     *
     * @param index
     * @param x
     * @throws InappropriateFunctionPointException
     */
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

    /**
     *
     * @param index
     * @return
     */
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

    /**
     *
     * @param index
     * @param y
     */
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

    /**
     *
     * @param x
     * @return
     */
    @Override
    public double getFunctionValue(double x) {
        if (length == 0) {
            return Double.NaN;
        }
        if (x < head.point.x || x > tail.point.x) {
            return Double.NaN;
        }

        current = head;
        currentInd = 0;
        while (current.point.x < x) {
            current = current.next;
            currentInd++;
        }

        if (current.point.x == x) {
            return current.point.y;
        }

        double k = (current.point.y - current.prev.point.y) / (current.point.x - current.prev.point.x);
        double b = current.point.y - k * current.point.x;
        return k * x + b;
    }

    /**
     *
     * @param index
     */
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

    /**
     *
     * @param point
     * @throws InappropriateFunctionPointException
     */
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

    @Override
    public FunctionPoint getPoint(int index) {
        getNodeByIndex(index);
        return new FunctionPoint(current.point);
    }

    /**
     *
     * @return @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        FunctionPoint points[] = new FunctionPoint[length];

        currentInd = 0;
        current = head;

        while (current != headMain) {
            points[currentInd] = current.point;
            currentInd++;
            current = current.next;
        }

        return new LinkedListTabulatedFunction(points);

    }

}
