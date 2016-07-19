package com.example.arpita.expenses.db;

/**
 * Created by arpita on 13/07/16.
 */
public class ExpenseTable extends DbTable {

    public static final String TABLE_NAME="expenses";

    public interface Columns {
        String ID="id";
        String NAME="name";
        String AMOUNT="amount";
        String DATE="date";
    }
    public static final String DELETE="DELETE FROM "+TABLE_NAME+" WHERE "+Columns.AMOUNT+" = 44;";
    public static final String TABLE_SELECT_ONE="SELECT * FROM "+TABLE_NAME+" WHERE "+Columns.ID+" = ";
    public static final String DAY="SELECT SUBSTRING"+LBR+Columns.DATE+",1,2);";
    public static final String MONTH="SELECT SUBSTRING"+LBR+Columns.DATE+",4,5);";
    public static final String YEAR="SELECT SUBSTRING"+LBR+Columns.DATE+",7,10);";

    public static final String SEARCH="SELECT * FROM "+TABLE_NAME+" WHERE ";
    public static final String TABLE_ADD="SELECT SUM("+Columns.AMOUNT+") FROM "+TABLE_NAME+" ; ";
    public static final String TABLE_SELECT_ALL="SELECT * FROM "+TABLE_NAME+" ORDER BY "+Columns.ID+" DESC "+" ; ";
    public static final String TABLE_CREATE_CMD="CREATE TABLE IF NOT EXISTS "+TABLE_NAME
            + LBR
            +Columns.ID+TYPE_INT_PK+COMMA
            + Columns.NAME +TYPE_TEXT + COMMA
            + Columns.AMOUNT+TYPE_REAL+COMMA
            + Columns.DATE+TYPE_TEXT
            +RBR+ " ; ";


}
