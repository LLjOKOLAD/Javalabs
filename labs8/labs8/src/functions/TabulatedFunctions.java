/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functions;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javax.xml.crypto.NoSuchMechanismException;

/**
 *
 * @author vlad
 */
public class TabulatedFunctions {

    private static TabulatedFunctionFactory factory = new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory();

    private TabulatedFunctions() {
    }

    /**
     *
     * @param factory
     */
    public static void setTabulatedFunctionFactory(TabulatedFunctionFactory factory) {
        TabulatedFunctions.factory = factory;
    }

    /**
     *
     * @param points
     * @return
     */
    public static TabulatedFunction createTabulatedFunction(FunctionPoint[] points) {
        return factory.createTabulatedFunction(points);
    }

    /**
     *
     * @param leftX
     * @param rightX
     * @param pointsCount
     * @return
     */
    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount) {
        return factory.createTabulatedFunction(leftX, rightX, pointsCount);
    }

    /**
     *
     * @param leftX
     * @param rightX
     * @param values
     * @return
     */
    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values) {
        return factory.createTabulatedFunction(leftX, rightX, values);
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> functionClass, FunctionPoint[] points) {
        Constructor constructors[] = functionClass.getConstructors();
        for (Constructor constructor : constructors) {
            Class types[] = constructor.getParameterTypes();
            if (types[0].equals(points.getClass())) {
                try {

                    return (TabulatedFunction) constructor.newInstance(new Object[]{points});
                } catch (Exception e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }

        throw new NoSuchMechanismException();
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> functionClass, double leftX, double rightX, int pointsCount) {
        Constructor constructors[] = functionClass.getConstructors();
        for (Constructor constructor : constructors) {
            Class types[] = constructor.getParameterTypes();
            if (types.length == 3 && types[0].equals(Double.TYPE) && types[1].equals(Double.TYPE) && types[2].equals(Integer.TYPE)) {
                try {
                    return (TabulatedFunction) constructor.newInstance(leftX, rightX, pointsCount);
                } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }

        throw new NoSuchMechanismException();
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> functionClass, double leftX, double rightX, double[] values) {
        Constructor constructors[] = functionClass.getConstructors();
        for (Constructor constructor : constructors) {
            Class types[] = constructor.getParameterTypes();
            if (types.length == 3 && types[0].equals(Double.TYPE) && types[1].equals(Double.TYPE) && types[2].equals(values.getClass())) {
                try {
                    return (TabulatedFunction) constructor.newInstance(leftX, rightX, values);
                } catch (Exception e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }

        throw new NoSuchMechanismException();
    }

    public static TabulatedFunction tabulate(Class<? extends TabulatedFunction> functionClass, Function function, double leftX, double rightX, int pointsCount) {

        if (leftX < function.getLeftDomainBorder() || rightX > function.getRightDomainBorder()) {
            throw new IllegalArgumentException();
        }

        double step = (rightX - leftX) / (pointsCount - 1);
        double values[] = new double[pointsCount];
        double argument = leftX;

        for (int i = 0; i < pointsCount; i++) {
            values[i] = function.getFunctionValue(argument);
            argument += step;
        }

        return createTabulatedFunction(functionClass, leftX, rightX, values);

    }

    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount) {
        if (leftX < function.getLeftDomainBorder() || rightX > function.getRightDomainBorder()) {
            throw new IllegalArgumentException();
        }

        FunctionPoint[] points = new FunctionPoint[pointsCount];
        points[0] = new FunctionPoint(leftX, function.getFunctionValue(leftX));

        double step = (rightX - leftX) / (pointsCount - 1);
        for (int i = 1; i < pointsCount; i++) {

            points[i] = new FunctionPoint(points[i - 1].x + step, function.getFunctionValue(points[i - 1].x + step));

        }

        return TabulatedFunctions.createTabulatedFunction(points);
    }

    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) throws IOException {
        int pointCount = function.getPointsCount();
        DataOutputStream stream = new DataOutputStream(out);
        stream.writeInt(pointCount);

        for (int i = 0; i < pointCount; i++) {
            stream.writeDouble(function.getPointX(i));
            stream.writeDouble(function.getPointY(i));
        }
        stream.flush();
    }

    public static TabulatedFunction inputTabulatedFunction(InputStream in) throws IOException {
        DataInputStream stream = new DataInputStream(in);
        int pointCount = stream.readInt();

        FunctionPoint points[] = new FunctionPoint[pointCount];

        for (int i = 0; i < pointCount; i++) {
            points[i] = new FunctionPoint(stream.readDouble(), stream.readDouble());
        }

        return TabulatedFunctions.createTabulatedFunction(points);
    }
    
    public static TabulatedFunction inputTabulatedFunction(Class<? extends TabulatedFunction> functionClass, InputStream in) throws IOException {
        DataInputStream stream = new DataInputStream(in);
        int pointCount = stream.readInt();

        FunctionPoint points[] = new FunctionPoint[pointCount];

        for (int i = 0; i < pointCount; i++) {
            points[i] = new FunctionPoint(stream.readDouble(), stream.readDouble());
        }

        return TabulatedFunctions.createTabulatedFunction(functionClass, points);
    }

    public static void writeTabulatedFunction(TabulatedFunction function, Writer out) {
        PrintWriter writer = new PrintWriter(out);
        int pointCount = function.getPointsCount();

        writer.println(pointCount);

        for (int i = 0; i < pointCount; i++) {
            writer.println(function.getPointX(i));
            writer.println(function.getPointY(i));
        }
    }

    public static TabulatedFunction readTabulatedFunction(Reader in) throws IOException {
        StreamTokenizer tokenizer = new StreamTokenizer(in);
        tokenizer.nextToken();

        int pointCount = (int) tokenizer.nval;

        FunctionPoint points[] = new FunctionPoint[pointCount];
        double x, y;
        for (int i = 0; i < pointCount; i++) {
            tokenizer.nextToken();
            x = tokenizer.nval;
            tokenizer.nextToken();
            y = tokenizer.nval;

            points[i] = new FunctionPoint(x, y);
        }

        return TabulatedFunctions.createTabulatedFunction(points);

    }
    
    public static TabulatedFunction readTabulatedFunction(Class<? extends TabulatedFunction> functionClass, Reader in) throws IOException {
        StreamTokenizer tokenizer = new StreamTokenizer(in);
        tokenizer.nextToken();

        int pointCount = (int) tokenizer.nval;

        FunctionPoint points[] = new FunctionPoint[pointCount];
        double x, y;
        for (int i = 0; i < pointCount; i++) {
            tokenizer.nextToken();
            x = tokenizer.nval;
            tokenizer.nextToken();
            y = tokenizer.nval;

            points[i] = new FunctionPoint(x, y);
        }

        return TabulatedFunctions.createTabulatedFunction(functionClass, points);

    }
}
