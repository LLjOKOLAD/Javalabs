/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor;

import functions.InappropriateFunctionPointException;
import functions.TabulatedFunction;
import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author vlad
 */
public class TabulatedFunctionTableModel extends DefaultTableModel {

    private TabulatedFunction function;
    private Component parent;

    public TabulatedFunctionTableModel(TabulatedFunction function, Component parent) {
        this.function = function;
        this.parent = parent;
    }

    /**
     *
     * @return
     */
    @Override
    public int getRowCount() {
        if (function != null) {
            return function.getPointsCount();
        } else {
            return 0;
        }
    }

    /**
     *
     * @return
     */
    @Override
    public int getColumnCount() {
        return 2;
    }

    /**
     *
     * @param column
     * @return
     */
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "x";
            case 1:
                return "y";
            default:
                return "Undefined";
        }
    }

    /**
     *
     * @param columnIndex
     * @return
     */
    @Override
    public Class getColumnClass(int columnIndex) {
        return Double.class;
    }

    /**
     *
     * @param row
     * @param column
     * @return
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        return row <= this.getRowCount() && column <= this.getColumnCount();
    }

    @Override
    public Object getValueAt(int row, int column) {
        switch (column) {
            case 0:
                return function.getPointX(row);
            case 1:
                return function.getPointY(row);
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        switch (column) {
            case 0:
                try {
                    function.setPointX(row, (Double) aValue);
                } catch (InappropriateFunctionPointException e) {
                    JOptionPane.showMessageDialog(parent, e.getMessage());
                }
                break;
            case 1:
                function.setPointY(row, (Double) aValue);
        }
    }
}
