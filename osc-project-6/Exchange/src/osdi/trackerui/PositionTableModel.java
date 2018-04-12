/*
 * File     : ExecutionTableModel.java
 *
 * Author   : Zoltan Feledy
 * 
 * Contents : This class is the TableModel for the Execution Table.
 * 
 */

package osdi.trackerui;

import javax.swing.table.AbstractTableModel;
import osdi.tracker.FIXTracker;
import osdi.tracker.Execution;
import osdi.tracker.ExecutionSet;
import osdi.tracker.Order;

public class PositionTableModel extends AbstractTableModel {
    private static ExecutionSet executions = 
            FIXTracker.getApplication().getExecutions();
    private static String[] columns = 
        {"ID", "ClOrdID", "Side", "Symbol", "LastQty", "LastPx", 
         "CumQty", "AvgPx", "Open", "ExecType", "ExecTranType", "RefID", "DKd"}; 
    

    public PositionTableModel(){
        FIXTracker.getApplication().getExecutions().addCallback(this);
    }
    
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Class getColumnClass(int column) {
        if (column == 4) return Double.class;
        if (column == 5) return Double.class;
        if (column == 6) return Double.class;
        if (column == 7) return Double.class;
        if (column == 8) return Double.class;
        return String.class;
    }
        
    public int getRowCount() {
        return executions.getCount();
    }

    public Object getValueAt(int row, int column) {
        Execution execution = executions.getExecution(row);
        Order order = execution.getOrder();
        if (column == 0) return execution.getID();        
        if (column == 1) return order.getClientID();
        if (column == 2) return order.getSide();
        if (column == 3) return order.getSymbol();
        if (column == 4) return execution.getLastShares();
        if (column == 5) return execution.getLastPx();
        if (column == 6) return execution.getCumQty();
        if (column == 7) return execution.getAvgPx();
        if (column == 8) return order.getOpen();
        if (column == 9) return execution.getExecType();
        if (column == 10) return execution.getExecTranType();
        if (column == 11) return execution.getRefID();
        if (column == 12) return execution.isDKd();
        return "";
    }
    
    public void update() {
        fireTableDataChanged();
    }
}
