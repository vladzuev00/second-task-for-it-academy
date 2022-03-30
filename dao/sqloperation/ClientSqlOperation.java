package by.itacademy.zuevvlad.cardpaymentproject.dao.sqloperation;

import by.itacademy.zuevvlad.cardpaymentproject.dao.tableproperty.ClientTableProperty;
import by.itacademy.zuevvlad.cardpaymentproject.dao.tableproperty.UserTableProperty;

import static by.itacademy.zuevvlad.cardpaymentproject.dao.tableproperty.ClientTableProperty.*;
import static by.itacademy.zuevvlad.cardpaymentproject.dao.tableproperty.UserTableProperty.*;

public enum ClientSqlOperation
{
    /*
        INSERT INTO clients (name, surname, patronymic, phone_number, bank_account_id, id) VALUES(?, ?, ?, ?, ?, ?)
     */
    PREPARED_STATEMENT_TO_INSERT_CLIENT("INSERT INTO " + ClientTableProperty.NAME_OF_TABLE.getValue() + " ("
            + NAME_OF_COLUMN_OF_NAME.getValue() + ", " + NAME_OF_COLUMN_OF_SURNAME.getValue() + ", "
            + NAME_OF_COLUMN_OF_PATRONYMIC.getValue() + ", " + NAME_OF_COLUMN_OF_PHONE_NUMBER.getValue() + ", "
            + NAME_OF_COLUMN_OF_BANK_ACCOUNT_ID.getValue() + ", "
            + ClientTableProperty.NAME_OF_COLUMN_OF_ID.getValue() + ") VALUES(?, ?, ?, ?, ?, ?)"),
    /*
        SELECT users.id, email, encrypted_password, name, surname, patronymic, phone_number, bank_account_id
        FROM clients LEFT OUTER JOIN users
            ON users.id = clients.id
     */
    OFFLOAD_ALL_CLIENTS("SELECT " + UserTableProperty.NAME_OF_TABLE.getValue() + "."
            + ClientTableProperty.NAME_OF_COLUMN_OF_ID.getValue() + ", "
            + NAME_OF_COLUMN_OF_EMAIL.getValue() + ", " + NAME_OF_COLUMN_OF_ENCRYPTED_PASSWORD.getValue() + ", "
            + NAME_OF_COLUMN_OF_NAME.getValue() + ", " + NAME_OF_COLUMN_OF_SURNAME.getValue() + ", "
            + NAME_OF_COLUMN_OF_PATRONYMIC.getValue() + ", " + NAME_OF_COLUMN_OF_PHONE_NUMBER.getValue() + ", "
            + NAME_OF_COLUMN_OF_BANK_ACCOUNT_ID.getValue() + " FROM " + ClientTableProperty.NAME_OF_TABLE.getValue()
            + " LEFT OUTER JOIN " + UserTableProperty.NAME_OF_TABLE.getValue() + " ON "
            + ClientTableProperty.NAME_OF_TABLE.getValue() + "." + ClientTableProperty.NAME_OF_COLUMN_OF_ID.getValue()
            + " = " + UserTableProperty.NAME_OF_TABLE.getValue() + "." + UserTableProperty.NAME_OF_COLUMN_OF_ID.getValue()),
    /*
        SELECT users.id, email, encrypted_password, name, surname, patronymic, phone_number, bank_account_id
        FROM clients LEFT OUTER JOIN users
            ON users.id = clients.id
        WHERE clients.id = ?
     */
    PREPARED_STATEMENT_TO_SELECT_CLIENT_BY_ID("SELECT " + UserTableProperty.NAME_OF_TABLE.getValue() + "."
            + ClientTableProperty.NAME_OF_COLUMN_OF_ID.getValue() + ", "
            + NAME_OF_COLUMN_OF_EMAIL.getValue() + ", " + NAME_OF_COLUMN_OF_ENCRYPTED_PASSWORD.getValue() + ", "
            + NAME_OF_COLUMN_OF_NAME.getValue() + ", " + NAME_OF_COLUMN_OF_SURNAME.getValue() + ", "
            + NAME_OF_COLUMN_OF_PATRONYMIC.getValue() + ", " + NAME_OF_COLUMN_OF_PHONE_NUMBER.getValue() + ", "
            + NAME_OF_COLUMN_OF_BANK_ACCOUNT_ID.getValue() + " FROM " + ClientTableProperty.NAME_OF_TABLE.getValue()
            + " LEFT OUTER JOIN " + UserTableProperty.NAME_OF_TABLE.getValue() + " ON "
            + ClientTableProperty.NAME_OF_TABLE.getValue() + "." + ClientTableProperty.NAME_OF_COLUMN_OF_ID.getValue()
            + " = " + UserTableProperty.NAME_OF_TABLE.getValue() + "." + UserTableProperty.NAME_OF_COLUMN_OF_ID.getValue()
            + " WHERE " + ClientTableProperty.NAME_OF_TABLE.getValue() + "."
            + ClientTableProperty.NAME_OF_COLUMN_OF_ID.getValue() + " = ?"),
    PREPARED_STATEMENT_TO_UPDATE_CLIENT("UPDATE " + ClientTableProperty.NAME_OF_TABLE.getValue() + " SET "
            + NAME_OF_COLUMN_OF_NAME.getValue() + " = ?, " + NAME_OF_COLUMN_OF_SURNAME.getValue() + " = ?, "
            + NAME_OF_COLUMN_OF_PATRONYMIC.getValue() + " = ?, " + NAME_OF_COLUMN_OF_PHONE_NUMBER.getValue()
            + " = ?, " + NAME_OF_COLUMN_OF_BANK_ACCOUNT_ID.getValue() + " = ?"
            + " WHERE " + ClientTableProperty.NAME_OF_COLUMN_OF_ID.getValue() + " = ?"),
    PREPARED_STATEMENT_TO_DEFINE_EXISTING_BY_ID("SELECT 1 FROM " + ClientTableProperty.NAME_OF_TABLE.getValue()
            + " WHERE " + ClientTableProperty.NAME_OF_COLUMN_OF_ID.getValue() + " = ?"),
    /*
        SELECT users.id, email, encrypted_password, name, surname, patronymic, phone_number, bank_account_id
        FROM users INNER JOIN clients
            ON users.id = clients.id
        WHERE email = ?
     */
    PREPARED_STATEMENT_TO_FIND_CLIENT_BY_EMAIL("SELECT " + ClientTableProperty.NAME_OF_TABLE.getValue()
            + "." + ClientTableProperty.NAME_OF_COLUMN_OF_ID.getValue() + ", " + NAME_OF_COLUMN_OF_EMAIL.getValue()
            + ", " + NAME_OF_COLUMN_OF_ENCRYPTED_PASSWORD.getValue() + ", " + NAME_OF_COLUMN_OF_NAME.getValue() + ", "
            + NAME_OF_COLUMN_OF_SURNAME.getValue() + ", " + NAME_OF_COLUMN_OF_PATRONYMIC.getValue() + ", "
            + NAME_OF_COLUMN_OF_PHONE_NUMBER.getValue() + ", " + NAME_OF_COLUMN_OF_BANK_ACCOUNT_ID.getValue() + " FROM "
            + UserTableProperty.NAME_OF_TABLE.getValue() + " INNER JOIN " + ClientTableProperty.NAME_OF_TABLE.getValue()
            + " ON " + UserTableProperty.NAME_OF_TABLE.getValue() + "." + UserTableProperty.NAME_OF_COLUMN_OF_ID.getValue()
            + " = " + ClientTableProperty.NAME_OF_TABLE.getValue() + "." + ClientTableProperty.NAME_OF_COLUMN_OF_ID.getValue()
            + " WHERE " + NAME_OF_COLUMN_OF_EMAIL.getValue() + " = ?"),
    /*
        SELECT users.id, email, encrypted_password, name, surname, patronymic, phone_number, bank_account_id
        FROM users INNER JOIN clients
            ON users.id = clients.id
        WHERE phone_number = ?
     */
    PREPARED_STATEMENT_TO_FIND_CLIENT_BY_PHONE_NUMBER("SELECT " + ClientTableProperty.NAME_OF_TABLE.getValue()
            + "." + ClientTableProperty.NAME_OF_COLUMN_OF_ID.getValue() + ", " + NAME_OF_COLUMN_OF_EMAIL.getValue()
            + ", " + NAME_OF_COLUMN_OF_ENCRYPTED_PASSWORD.getValue() + ", " + NAME_OF_COLUMN_OF_NAME.getValue() + ", "
            + NAME_OF_COLUMN_OF_SURNAME.getValue() + ", " + NAME_OF_COLUMN_OF_PATRONYMIC.getValue() + ", "
            + NAME_OF_COLUMN_OF_PHONE_NUMBER.getValue() + ", " + NAME_OF_COLUMN_OF_BANK_ACCOUNT_ID.getValue() + " FROM "
            + UserTableProperty.NAME_OF_TABLE.getValue() + " INNER JOIN " + ClientTableProperty.NAME_OF_TABLE.getValue()
            + " ON " + UserTableProperty.NAME_OF_TABLE.getValue() + "." + UserTableProperty.NAME_OF_COLUMN_OF_ID.getValue()
            + " = " + ClientTableProperty.NAME_OF_TABLE.getValue() + "." + ClientTableProperty.NAME_OF_COLUMN_OF_ID.getValue()
            + " WHERE " + NAME_OF_COLUMN_OF_PHONE_NUMBER.getValue() + " = ?");

    private final String sqlQuery;

    private ClientSqlOperation(final String sqlQuery)
    {
        this.sqlQuery = sqlQuery;
    }

    public final String getSqlQuery()
    {
        return this.sqlQuery;
    }

    public static final int PARAMETER_INDEX_OF_EMAIL_IN_PREPARED_STATEMENT_TO_INSERT_USER = 1;
    public static final int PARAMETER_INDEX_OF_ENCRYPTED_PASSWORD_IN_PREPARED_STATEMENT_TO_INSERT_USER = 2;

    public static final int PARAMETER_INDEX_OF_NAME_IN_PREPARED_STATEMENT_TO_INSERT_CLIENT = 1;
    public static final int PARAMETER_INDEX_OF_SURNAME_IN_PREPARED_STATEMENT_TO_INSERT_CLIENT = 2;
    public static final int PARAMETER_INDEX_OF_PATRONYMIC_IN_PREPARED_STATEMENT_TO_INSERT_CLIENT = 3;
    public static final int PARAMETER_INDEX_OF_PHONE_NUMBER_IN_PREPARED_STATEMENT_TO_INSERT_CLIENT = 4;
    public static final int PARAMETER_INDEX_OF_BANK_ACCOUNT_ID_IN_PREPARED_STATEMENT_TO_INSERT_CLIENT = 5;
    public static final int PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_INSERT_CLIENT = 6;

    public static final int PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_SELECT_BY_ID = 1;

    public static final int PARAMETER_INDEX_OF_NAME_IN_PREPARED_STATEMENT_TO_UPDATE = 1;
    public static final int PARAMETER_INDEX_OF_SURNAME_IN_PREPARED_STATEMENT_TO_UPDATE = 2;
    public static final int PARAMETER_INDEX_OF_PATRONYMIC_IN_PREPARED_STATEMENT_TO_UPDATE = 3;
    public static final int PARAMETER_INDEX_OF_PHONE_NUMBER_IN_PREPARED_STATEMENT_TO_UPDATE = 4;
    public static final int PARAMETER_INDEX_OF_BANK_ACCOUNT_ID_IN_PREPARED_STATEMENT_TO_UPDATE = 5;
    public static final int PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_UPDATE = 6;

    public static final int PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_DEFINE_EXISTING = 1;

    public static final int PARAMETER_INDEX_OF_EMAIL_IN_PREPARED_STATEMENT_TO_SELECT_BY_EMAIL = 1;

    public static final int PARAMETER_INDEX_OF_PHONE_NUMBER_IN_PREPARED_STATEMENT_TO_SELECT_BY_PHONE_NUMBER = 1;
}
