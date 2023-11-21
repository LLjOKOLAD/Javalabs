/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor;

import functions.ArrayTabulatedFunction;
import functions.Function;
import functions.FunctionPoint;
import functions.FunctionPointIndexOutOfBoundsException;
import functions.InappropriateFunctionPointException;
import functions.TabulatedFunction;
import functions.TabulatedFunctions;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author vlad
 */
public class DocumentTabulatedFunction implements TabulatedFunction {

    private TabulatedFunction function;
    private String fileName = "";
    private boolean modified = false;
    private boolean fileNameAssigned = false;

    public void newFunction(double leftX, double rightX, int pointsCount) {
        function = new ArrayTabulatedFunction(leftX, rightX, pointsCount);
        modified = false;
    }

    public void saveFunction() throws IOException {

        FileWriter writer = new FileWriter(fileName);
        TabulatedFunctions.writeTabulatedFunction(function, writer);
        writer.close();
        modified = false;

    }

    public void saveFunctionAs(String fileName) throws IOException {
        this.fileName = fileName;
        fileNameAssigned = true;
        saveFunction();
        modified = false;
    }

    public void loadFunction(String fileName) throws FileNotFoundException, IOException {
        this.fileName = fileName;
        fileNameAssigned = true;

        FileReader reader = new FileReader(fileName);
        function = TabulatedFunctions.readTabulatedFunction(reader);
        modified = false;
    }

    public void tabulateFunction(Function function, double leftX, double rightX, int pointsCount) {
        this.function = TabulatedFunctions.tabulate(function, leftX, rightX, pointsCount);
        modified = false;
    }

    public boolean isModified() {
        return modified;
    }

    public boolean isFileNameAssigned() {
        return fileNameAssigned;
    }

    @Override
    public int getPointsCount() {
        return this.function.getPointsCount();
    }

    @Override
    public void setPointY(int index, double y) {
        this.function.setPointY(index, y);
        this.modified = true;
    }

    @Override
    public double getPointY(int index) {
        return this.function.getPointY(index);
    }

    @Override
    public double getPointX(int index) {
        return this.function.getPointX(index);
    }

    @Override
    public double getFunctionValue(double x) {
        return this.function.getFunctionValue(x);
    }

    @Override
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        this.modified = true;
        this.function.addPoint(point);
    }

    @Override
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        this.function.setPoint(index, point);
        this.modified = true;
    }

    @Override
    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        this.function.setPointX(index, x);
        this.modified = true;
    }

    @Override
    public void deletePoint(int index) {
        this.function.deletePoint(index);
        this.modified = true;
    }

    @Override
    public double getRightDomainBorder() {
        return this.function.getRightDomainBorder();
    }

    @Override
    public double getLeftDomainBorder() {
        return this.function.getLeftDomainBorder();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        DocumentTabulatedFunction clone = new DocumentTabulatedFunction();
        clone.function = (TabulatedFunction) function.clone();
        clone.fileName = fileName;
        clone.modified = modified;
        clone.fileNameAssigned = fileNameAssigned;
        return clone();
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder("{");
        for (int i = 0; i < this.function.getPointsCount(); i++) {
            buffer.append(this.function.getPoint(i).toString()).append(", ");
        }
        buffer.deleteCharAt(buffer.length() - 1).deleteCharAt(buffer.length() - 1);
        buffer.append("}");
        return buffer.toString();

    }

    @Override
    public FunctionPoint getPoint(int index) {
        if (index >= 0 && index < this.getPointsCount()) {
            return new FunctionPoint(this.function.getPoint(index));
        } else {
            throw new FunctionPointIndexOutOfBoundsException();
        }
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof TabulatedFunction) {

            TabulatedFunction func = (TabulatedFunction) o;
            if (func.getPointsCount() != this.getPointsCount()) {
                return false;
            }
            for (int i = 0; i < this.getPointsCount(); i++) {
                if (!(this.getPoint(i).equals(func.getPoint(i)))) {
                    return false;
                }
            }
            return true;
        }
        else return false;

    }
}
