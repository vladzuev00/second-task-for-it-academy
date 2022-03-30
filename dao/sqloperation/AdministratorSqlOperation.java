package by.itacademy.zuevvlad.cardpaymentproject.dao.sqloperation;

import by.itacademy.zuevvlad.cardpaymentproject.dao.tableproperty.AdministratorTableProperty;
import by.itacademy.zuevvlad.cardpaymentproject.dao.tableproperty.UserTableProperty;

import static by.itacademy.zuevvlad.cardpaymentproject.dao.tableproperty.AdministratorTableProperty.*;
import static by.itacademy.zuevvlad.cardpaymentproject.dao.tableproperty.UserTableProperty.*;

public enum AdministratorSqlOperation
{
    PREPARED_STATEMENT_TO_INSERT_ADMINISTRATOR("INSERT INTO " + AdministratorTableProperty.NAME_OF_TABLE.getValue()
            + " (" + AdministratorTableProperty.NAME_OF_COLUMN_OF_ID.getValue() + ", "
            + NAME_OF_COLUMN_OF_LEVEL.getValue() + ") VALUES (?, ?)"),
    /*
        SELECT users.id, email, encrypted_password, level
        FROM users INNER JOIN administrators
            ON users.id = administrators.id
     */
    OFFLOAD_ALL_ADMINISTRATORS("SELECT " + UserTableProperty.NAME_OF_TABLE.getValue() + "."
            + UserTableProperty.NAME_OF_COLUMN_OF_ID.getValue() + ", " + NAME_OF_COLUMN_OF_EMAIL.getValue() + ", "
            + NAME_OF_COLUMN_OF_ENCRYPTED_PASSWORD.getValue() + ", " + NAME_OF_COLUMN_OF_LEVEL.getValue() + " FROM "
            + UserTableProperty.NAME_OF_TABLE.getValue() + " INNER JOIN "
            + AdministratorTableProperty.NAME_OF_TABLE.getValue() + " ON "
            + UserTableProperty.NAME_OF_TABLE.getValue() + "." + UserTableProperty.NAME_OF_COLUMN_OF_ID.getValue()
            + " = " + AdministratorTableProperty.NAME_OF_TABLE.getValue() + "."
            + AdministratorTableProperty.NAME_OF_COLUMN_OF_ID.getValue()),
    /*
        SELECT users.id, email, encrypted_password, level
        FROM users INNER JOIN administrators
            ON users.id = administrators.id
        WHERE users.id = ?
     */
    PREPARED_STATEMENT_TO_SELECT_ADMINISTRATOR_BY_ID("SELECT " + UserTableProperty.NAME_OF_TABLE.getValue() + "."
            + UserTableProperty.NAME_OF_COLUMN_OF_ID.getValue() + ", " + NAME_OF_COLUMN_OF_EMAIL.getValue() + ", "
            + NAME_OF_COLUMN_OF_ENCRYPTED_PASSWORD.getValue() + ", " + NAME_OF_COLUMN_OF_LEVEL.getValue() + " FROM "
            + UserTableProperty.NAME_OF_TABLE.getValue() + " INNER JOIN "
            + AdministratorTableProperty.NAME_OF_TABLE.getValue() + " ON "
            + UserTableProperty.NAME_OF_TABLE.getValue() + "." + UserTableProperty.NAME_OF_COLUMN_OF_ID.getValue()
            + " = " + AdministratorTableProperty.NAME_OF_TABLE.getValue() + "."
            + AdministratorTableProperty.NAME_OF_COLUMN_OF_ID.getValue()
            + " WHERE " + UserTableProperty.NAME_OF_TABLE.getValue() + "."
            + UserTableProperty.NAME_OF_COLUMN_OF_ID.getValue() + " = ?"),
    PREPARED_STATEMENT_TO_UPDATE_ADMINISTRATOR("UPDATE " + AdministratorTableProperty.NAME_OF_TABLE.getValue() + " SET "
            + NAME_OF_COLUMN_OF_LEVEL.getValue() + " = " + "? WHERE "
            + AdministratorTableProperty.NAME_OF_COLUMN_OF_ID.getValue() + " = ?"),
    PREPARED_STATEMENT_TO_DEFINE_EXISTING_BY_ID("SELECT 1 FROM " + AdministratorTableProperty.NAME_OF_TABLE.getValue()
            + " WHERE " + AdministratorTableProperty.NAME_OF_COLUMN_OF_ID.getValue() + " = ?"),
    /*
        SELECT users.id, email, encrypted_password, level
        FROM users INNER JOIN administrators
            ON users.id = administrators.id
        WHERE email = ?
     */
    PREPARED_STATEMENT_TO_SELECT_ADMINISTRATOR_BY_EMAIL("SELECT " + UserTableProperty.NAME_OF_TABLE.getValue() + "."
            + UserTableProperty.NAME_OF_COLUMN_OF_ID.getValue() + ", " + NAME_OF_COLUMN_OF_EMAIL.getValue() + ", "
            + NAME_OF_COLUMN_OF_ENCRYPTED_PASSWORD.getValue() + ", " + NAME_OF_COLUMN_OF_LEVEL.getValue() + " FROM "
            + UserTableProperty.NAME_OF_TABLE.getValue() + " INNER JOIN "
            + AdministratorTableProperty.NAME_OF_TABLE.getValue() + " ON "
            + UserTableProperty.NAME_OF_TABLE.getValue() + "." + UserTableProperty.NAME_OF_COLUMN_OF_ID.getValue()
            + " = " + AdministratorTableProperty.NAME_OF_TABLE.getValue() + "."
            + AdministratorTableProperty.NAME_OF_COLUMN_OF_ID.getValue()
            + " WHERE " + UserTableProperty.NAME_OF_TABLE.getValue() + "."
            + NAME_OF_COLUMN_OF_EMAIL.getValue() + " = ?");

    private final String sqlQuery;

    private AdministratorSqlOperation(final String sqlQuery)
    {
        this.sqlQuery = sqlQuery;
    }

    public final String getSqlQuery()
    {
        return this.sqlQuery;
    }

    public static final int PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_INSERT = 1;
    public static final int PARAMETER_INDEX_OF_LEVEL_IN_PREPARED_STATEMENT_TO_INSERT = 2;

    public static final int PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_SELECT_BY_ID = 1;

    public static final int PARAMETER_INDEX_OF_LEVEL_IN_PREPARED_STATEMENT_TO_UPDATE = 1;
    public static final int PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_UPDATE = 2;

    public static final int PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_DEFINE_EXISTING = 1;

    public static final int PARAMETER_INDEX_OF_EMAIL_IN_PREPARED_STATEMENT_TO_SELECT_BY_EMAIL = 1;
}