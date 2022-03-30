package by.itacademy.zuevvlad.cardpaymentproject.dao.sqloperation;

import static by.itacademy.zuevvlad.cardpaymentproject.dao.tableproperty.BankAccountTableProperty.*;
import static by.itacademy.zuevvlad.cardpaymentproject.dao.tableproperty.BankAccountTableProperty.NAME_OF_COLUMN_OF_ID;

public enum BankAccountSqlOperation
{
    /*
        INSERT INTO bank_accounts (money, is_blocked, number)
        VALUES(?, ?, ?)
     */
    PREPARED_STATEMENT_TO_INSERT_BANK_ACCOUNT("INSERT INTO " + NAME_OF_TABLE.getValue()
            + " (" + NAME_OF_COLUMN_OF_MONEY.getValue() + ", " + NAME_OF_COLUMN_OF_BLOCKED.getValue() + ", "
            + NAME_OF_COLUMN_OF_NUMBER.getValue() + ")"
            + "VALUES(?, ?, ?)"),
    /*
        SELECT id, money, is_blocked, number
        FROM bank_accounts
     */
    OFFLOAD_ALL_BANK_ACCOUNTS("SELECT " + NAME_OF_COLUMN_OF_ID.getValue() + ", "
            + NAME_OF_COLUMN_OF_MONEY.getValue() + ", " + NAME_OF_COLUMN_OF_BLOCKED.getValue() + ", "
            + NAME_OF_COLUMN_OF_NUMBER.getValue() + " FROM " + NAME_OF_TABLE.getValue()),
    /*
        SELECT id, money, is_blocked, number
        FROM bank_accounts
        WHERE id = ?
     */
    PREPARED_STATEMENT_TO_SELECT_BANK_ACCOUNT_BY_ID("SELECT " + NAME_OF_COLUMN_OF_ID.getValue() + ", "
            + NAME_OF_COLUMN_OF_MONEY.getValue() + ", " + NAME_OF_COLUMN_OF_BLOCKED.getValue() + ", "
            + NAME_OF_COLUMN_OF_NUMBER.getValue() + " FROM " + NAME_OF_TABLE.getValue()
            + " WHERE " + NAME_OF_COLUMN_OF_ID.getValue() + " = ?"),
    /*
        UPDATE bank_accounts SET money = ?, is_blocked = ?, number = ?
        WHERE id = ?
     */
    PREPARED_STATEMENT_TO_UPDATE_BANK_ACCOUNT("UPDATE " + NAME_OF_TABLE.getValue() + " SET "
            + NAME_OF_COLUMN_OF_MONEY.getValue() + " = ?, " + NAME_OF_COLUMN_OF_BLOCKED.getValue() + " = ?, "
            + NAME_OF_COLUMN_OF_NUMBER.getValue() + " = ? " + "WHERE " + NAME_OF_COLUMN_OF_ID.getValue() + " = ?"),
    /*
        DELETE FROM bank_accounts
        WHERE id = ?
     */
    PREPARED_STATEMENT_TO_DELETE_BANK_ACCOUNT_BY_ID("DELETE FROM " + NAME_OF_TABLE.getValue()
            + " WHERE " + NAME_OF_COLUMN_OF_ID.getValue() + " = ?"),
    /*
        SELECT 1 FROM bank_accounts
        WHERE id = ?
     */
    PREPARED_STATEMENT_TO_DEFINE_EXISTING_BANK_ACCOUNT_BY_ID("SELECT 1 FROM " + NAME_OF_TABLE.getValue()
            + " WHERE " + NAME_OF_COLUMN_OF_ID.getValue() + " = ?"),
    /*
        SELECT id, money, is_blocked, number
        FROM bank_accounts
        WHERE number = ?
     */
    PREPARED_STATEMENT_TO_FIND_BANK_ACCOUNT_BY_NUMBER("SELECT " + NAME_OF_COLUMN_OF_ID.getValue() + ", "
            + NAME_OF_COLUMN_OF_MONEY.getValue() + ", " + NAME_OF_COLUMN_OF_BLOCKED.getValue() + ", "
            + NAME_OF_COLUMN_OF_NUMBER.getValue() + " FROM " + NAME_OF_TABLE.getValue() + " WHERE "
            + NAME_OF_COLUMN_OF_NUMBER.getValue() + " = ?");

    private final String sqlQuery;

    private BankAccountSqlOperation(final String sqlQuery)
    {
        this.sqlQuery = sqlQuery;
    }

    public final String getSqlQuery()
    {
        return this.sqlQuery;
    }

    public static final int PARAMETER_INDEX_OF_MONEY_IN_PREPARED_STATEMENT_TO_INSERT = 1;
    public static final int PARAMETER_INDEX_OF_BLOCKED_IN_PREPARED_STATEMENT_TO_INSERT = 2;
    public static final int PARAMETER_INDEX_OF_NUMBER_IN_PREPARED_STATEMENT_TO_INSERT = 3;

    public static final int PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_SELECT_BY_ID = 1;

    public static final int PARAMETER_INDEX_OF_MONEY_IN_PREPARED_STATEMENT_TO_UPDATE = 1;
    public static final int PARAMETER_INDEX_OF_BLOCKED_IN_PREPARED_STATEMENT_TO_UPDATE = 2;
    public static final int PARAMETER_INDEX_OF_NUMBER_IN_PREPARED_STATEMENT_TO_UPDATE = 3;
    public static final int PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_UPDATE = 4;

    public static final int PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_DELETE_BY_ID = 1;

    public static final int PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_DEFINE_EXISTING = 1;

    public static final int PARAMETER_INDEX_OF_NUMBER_IN_PREPARED_STATEMENT_TO_SELECT_BY_NUMBER = 1;
}
