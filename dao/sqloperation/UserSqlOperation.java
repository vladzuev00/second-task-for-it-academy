package by.itacademy.zuevvlad.cardpaymentproject.dao.sqloperation;

import static by.itacademy.zuevvlad.cardpaymentproject.dao.tableproperty.UserTableProperty.*;
import static by.itacademy.zuevvlad.cardpaymentproject.dao.tableproperty.UserTableProperty.NAME_OF_COLUMN_OF_ID;

public enum UserSqlOperation
{
    PREPARED_STATEMENT_TO_INSERT_USER("INSERT INTO " + NAME_OF_TABLE.getValue() + " ("
            + NAME_OF_COLUMN_OF_EMAIL.getValue() + ", " + NAME_OF_COLUMN_OF_ENCRYPTED_PASSWORD.getValue()
            + ") VALUES (?, ?)"),
    OFFLOAD_ALL_USERS("SELECT " + NAME_OF_COLUMN_OF_ID.getValue() + ", "
            + NAME_OF_COLUMN_OF_EMAIL.getValue() + ", " + NAME_OF_COLUMN_OF_ENCRYPTED_PASSWORD.getValue()
            + " FROM " + NAME_OF_TABLE.getValue()),
    PREPARED_STATEMENT_TO_SELECT_USER_BY_ID("SELECT " + NAME_OF_COLUMN_OF_ID.getValue() + ", "
            + NAME_OF_COLUMN_OF_EMAIL.getValue() + ", " + NAME_OF_COLUMN_OF_ENCRYPTED_PASSWORD.getValue()
            + " FROM " + NAME_OF_TABLE.getValue() + " WHERE " + NAME_OF_COLUMN_OF_ID.getValue() + " = ?"),
    PREPARED_STATEMENT_TO_UPDATE_USER("UPDATE " + NAME_OF_TABLE.getValue() + " SET "
            + NAME_OF_COLUMN_OF_EMAIL.getValue() + " = ?, "
            + NAME_OF_COLUMN_OF_ENCRYPTED_PASSWORD.getValue() + " = ? WHERE "
            + NAME_OF_COLUMN_OF_ID.getValue() + " = ?"),
    PREPARED_STATEMENT_TO_DELETE_USER_BY_ID("DELETE FROM " + NAME_OF_TABLE.getValue() + " WHERE "
            + NAME_OF_COLUMN_OF_ID.getValue() + " = ?"),
    PREPARED_STATEMENT_TO_DEFINE_EXISTING_USER_BY_ID("SELECT 1 FROM " + NAME_OF_TABLE.getValue() + " WHERE "
            + NAME_OF_COLUMN_OF_ID.getValue() + " = ?"),
    PREPARED_STATEMENT_TO_DEFINE_EXISTING_USER_BY_EMAIL("SELECT 1 FROM " + NAME_OF_TABLE.getValue() + " WHERE "
            + NAME_OF_COLUMN_OF_EMAIL.getValue() + " = ?"),
    PREPARED_STATEMENT_TO_SELECT_USER_BY_EMAIL("SELECT " + NAME_OF_COLUMN_OF_ID.getValue() + ", "
            + NAME_OF_COLUMN_OF_EMAIL.getValue() + ", " + NAME_OF_COLUMN_OF_ENCRYPTED_PASSWORD.getValue() + " FROM "
            + NAME_OF_TABLE.getValue() + " WHERE " + NAME_OF_COLUMN_OF_EMAIL.getValue() + " = ?");

    private final String sqlQuery;

    private UserSqlOperation(final String sqlQuery)
    {
        this.sqlQuery = sqlQuery;
    }

    public final String getSqlQuery()
    {
        return this.sqlQuery;
    }

    public static final int PARAMETER_INDEX_OF_EMAIL_IN_PREPARED_STATEMENT_TO_INSERT = 1;
    public static final int PARAMETER_INDEX_OF_ENCRYPTED_PASSWORD_IN_PREPARED_STATEMENT_TO_INSERT = 2;

    public static final int PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_SELECT_BY_ID = 1;

    public static final int PARAMETER_INDEX_OF_EMAIL_IN_PREPARED_STATEMENT_TO_UPDATE = 1;
    public static final int PARAMETER_INDEX_OF_ENCRYPTED_PASSWORD_IN_PREPARED_STATEMENT_TO_UPDATE = 2;
    public static final int PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_UPDATE = 3;

    public static final int PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_DELETE = 1;

    public static final int PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_DEFINE_EXISTING = 1;

    public static final int PARAMETER_INDEX_OF_EMAIL_IN_PREPARED_STATEMENT_TO_DEFINE_EXISTING = 1;

    public static final int PARAMETER_INDEX_OF_EMAIL_IN_PREPARED_STATEMENT_TO_SELECT_BY_EMAIL = 1;
}
