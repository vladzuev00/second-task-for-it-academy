package by.itacademy.zuevvlad.cardpaymentproject.dao.sqloperation;

import static by.itacademy.zuevvlad.cardpaymentproject.dao.tableproperty.PaymentTableProperty.*;

public enum PaymentSqlOperation
{
    PREPARED_STATEMENT_TO_INSERT_PAYMENT("INSERT INTO " + NAME_OF_TABLE.getValue() + " ("
            + NAME_OF_COLUMN_OF_CARD_ID_OF_SENDER.getValue() + ", " + NAME_OF_COLUMN_OF_CARD_ID_OF_RECEIVER.getValue()
            + ", " + NAME_OF_COLUMN_OF_MONEY.getValue() + ", " + NAME_OF_COLUMN_OF_DATE.getValue()
            + ") VALUES (?, ?, ?, ?)"),
    OFFLOAD_ALL_PAYMENTS("SELECT " + NAME_OF_COLUMN_OF_ID.getValue() + ", "
            + NAME_OF_COLUMN_OF_CARD_ID_OF_SENDER.getValue() + ", " + NAME_OF_COLUMN_OF_CARD_ID_OF_RECEIVER.getValue()
            + ", " + NAME_OF_COLUMN_OF_MONEY.getValue() + ", " + NAME_OF_COLUMN_OF_DATE.getValue() + " FROM "
            + NAME_OF_TABLE.getValue()),
    PREPARED_STATEMENT_TO_SELECT_PAYMENT_BY_ID("SELECT " + NAME_OF_COLUMN_OF_ID.getValue() + ", "
            + NAME_OF_COLUMN_OF_CARD_ID_OF_SENDER.getValue() + ", " + NAME_OF_COLUMN_OF_CARD_ID_OF_RECEIVER.getValue()
            + ", " + NAME_OF_COLUMN_OF_MONEY.getValue() + ", " + NAME_OF_COLUMN_OF_DATE.getValue() + " FROM "
            + NAME_OF_TABLE.getValue() + " WHERE " + NAME_OF_COLUMN_OF_ID.getValue() + " = ?"),
    PREPARED_STATEMENT_TO_UPDATE_PAYMENT("UPDATE " + NAME_OF_TABLE.getValue() + " SET "
            + NAME_OF_COLUMN_OF_CARD_ID_OF_SENDER.getValue() + " = ?, "
            + NAME_OF_COLUMN_OF_CARD_ID_OF_RECEIVER.getValue() + " = ?, "
            + NAME_OF_COLUMN_OF_MONEY.getValue() + " = ?, " + NAME_OF_COLUMN_OF_DATE.getValue() + " = ? WHERE "
            + NAME_OF_COLUMN_OF_ID.getValue() + " = ?"),
    PREPARED_STATEMENT_TO_DELETE_PAYMENT_BY_ID("DELETE FROM " + NAME_OF_TABLE.getValue() + " WHERE "
            + NAME_OF_COLUMN_OF_ID.getValue() + " = ?"),
    PREPARED_STATEMENT_TO_DEFINE_EXISTING_PAYMENT_BY_ID("SELECT 1 FROM " + NAME_OF_TABLE.getValue() + " WHERE "
            + NAME_OF_COLUMN_OF_ID.getValue() + " = ?");

    private final String sqlQuery;

    private PaymentSqlOperation(final String sqlQuery)
    {
        this.sqlQuery = sqlQuery;
    }

    public final String getSqlQuery()
    {
        return this.sqlQuery;
    }

    public static final int PARAMETER_INDEX_OF_CARD_ID_OF_SENDER_IN_PREPARED_STATEMENT_TO_INSERT = 1;
    public static final int PARAMETER_INDEX_OF_CARD_ID_OF_RECEIVER_IN_PREPARED_STATEMENT_TO_INSERT = 2;
    public static final int PARAMETER_INDEX_OF_MONEY_IN_PREPARED_STATEMENT_TO_INSERT = 3;
    public static final int PARAMETER_INDEX_OF_DATE_IN_PREPARED_STATEMENT_TO_INSERT = 4;

    public static final int PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_SELECT_BY_ID = 1;

    public static final int PARAMETER_INDEX_OF_CARD_ID_OF_SENDER_IN_PREPARED_STATEMENT_TO_UPDATE = 1;
    public static final int PARAMETER_INDEX_OF_CARD_ID_OF_RECEIVER_IN_PREPARED_STATEMENT_TO_UPDATE = 2;
    public static final int PARAMETER_INDEX_OF_MONEY_IN_PREPARED_STATEMENT_TO_UPDATE = 3;
    public static final int PARAMETER_INDEX_OF_DATE_IN_PREPARED_STATEMENT_TO_UPDATE = 4;
    public static final int PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_UPDATE = 5;

    public static final int PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_DELETE = 1;

    public static final int PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_DEFINE_EXISTING = 1;
}
