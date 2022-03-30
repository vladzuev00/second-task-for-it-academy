package by.itacademy.zuevvlad.cardpaymentproject.dao.sqloperation;

import static by.itacademy.zuevvlad.cardpaymentproject.dao.tableproperty.PaymentCardTableProperty.*;

public enum PaymentCardSqlOperation
{
    /*
        INSERT INTO payment_cards (card_number, number_of_month_of_expiration_date, year_of_expiration_date,
                                   payment_system, encrypted_cvc, client_id, name_of_bank, encrypted_password)
                    VALUES(?, ?, ?, ?, ?, ?, ?, ?)
     */
    PREPARED_STATEMENT_TO_INSERT_PAYMENT_CARD("INSERT INTO " + NAME_OF_TABLE.getValue() + " ("
            + NAME_OF_COLUMN_OF_CARD_NUMBER.getValue() + ", "
            + NAME_OF_COLUMN_OF_NUMBER_OF_MONTH_OF_EXPIRATION_DATE.getValue() + ", "
            + NAME_OF_COLUMN_OF_YEAR_OF_EXPIRATION_DATE.getValue() + ", " + NAME_OF_COLUMN_OF_PAYMENT_SYSTEM.getValue()
            + ", " + NAME_OF_COLUMN_OF_ENCRYPTED_CVC.getValue() + ", " + NAME_OF_COLUMN_OF_CLIENT_ID.getValue()
            + ", " + NAME_OF_COLUMN_OF_NAME_OF_BANK.getValue() + ", " + NAME_OF_COLUMN_OF_ENCRYPTED_PASSWORD.getValue()
            + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?)"),
    /*
        SELECT id, card_number, number_of_month_of_expiration_date, year_of_expiration_date, payment_system,
               encrypted_cvc, client_id, name_of_bank, encrypted_password
        FROM payment_cards
     */
    OFFLOAD_ALL_PAYMENT_CARDS("SELECT " + NAME_OF_COLUMN_OF_ID.getValue() + ", "
            + NAME_OF_COLUMN_OF_CARD_NUMBER.getValue() + ", "
            + NAME_OF_COLUMN_OF_NUMBER_OF_MONTH_OF_EXPIRATION_DATE.getValue() + ", "
            + NAME_OF_COLUMN_OF_YEAR_OF_EXPIRATION_DATE.getValue() + ", " + NAME_OF_COLUMN_OF_PAYMENT_SYSTEM.getValue()
            + ", " + NAME_OF_COLUMN_OF_ENCRYPTED_CVC.getValue() + ", " + NAME_OF_COLUMN_OF_CLIENT_ID.getValue() + ", "
            + NAME_OF_COLUMN_OF_NAME_OF_BANK.getValue() + ", " + NAME_OF_COLUMN_OF_ENCRYPTED_PASSWORD.getValue()
            + " FROM " + NAME_OF_TABLE.getValue()),
    /*
        SELECT id, card_number, number_of_month_of_expiration_date, year_of_expiration_date, payment_system,
               encrypted_cvc, client_id, name_of_bank, encrypted_password
        FROM payment_cards
        WHERE id = ?
     */
    PREPARED_STATEMENT_TO_SELECT_PAYMENT_CARD_BY_ID("SELECT " + NAME_OF_COLUMN_OF_ID.getValue() + ", "
            + NAME_OF_COLUMN_OF_CARD_NUMBER.getValue() + ", "
            + NAME_OF_COLUMN_OF_NUMBER_OF_MONTH_OF_EXPIRATION_DATE.getValue() + ", "
            + NAME_OF_COLUMN_OF_YEAR_OF_EXPIRATION_DATE.getValue() + ", " + NAME_OF_COLUMN_OF_PAYMENT_SYSTEM.getValue()
            + ", " + NAME_OF_COLUMN_OF_ENCRYPTED_CVC.getValue() + ", " + NAME_OF_COLUMN_OF_CLIENT_ID.getValue() + ", "
            + NAME_OF_COLUMN_OF_NAME_OF_BANK.getValue() + ", " + NAME_OF_COLUMN_OF_ENCRYPTED_PASSWORD.getValue()
            + " FROM " + NAME_OF_TABLE.getValue() + " WHERE " + NAME_OF_COLUMN_OF_ID.getValue() + " = ?"),
    /*
        UPDATE payment_cards SET card_number = ?, number_of_month_of_expiration_date = ?, year_of_expiration_date = ?,
                                 payment_system = ?, encrypted_cvc = ?, client_id = ?, name_of_bank = ?,
                                 encrypted_password = ?
        WHERE id = ?
     */
    PREPARED_STATEMENT_TO_UPDATE_PAYMENT_CARD("UPDATE " + NAME_OF_TABLE.getValue() + " SET "
            + NAME_OF_COLUMN_OF_CARD_NUMBER.getValue() + " = ?, "
            + NAME_OF_COLUMN_OF_NUMBER_OF_MONTH_OF_EXPIRATION_DATE.getValue() + " = ?, "
            + NAME_OF_COLUMN_OF_YEAR_OF_EXPIRATION_DATE.getValue() + " = ?, "
            + NAME_OF_COLUMN_OF_PAYMENT_SYSTEM.getValue() + " = ?, "
            + NAME_OF_COLUMN_OF_ENCRYPTED_CVC.getValue() + " = ?, " + NAME_OF_COLUMN_OF_CLIENT_ID.getValue() + " = ?, "
            + NAME_OF_COLUMN_OF_NAME_OF_BANK.getValue() + " = ?, "
            + NAME_OF_COLUMN_OF_ENCRYPTED_PASSWORD.getValue() + " = ? WHERE "
            + NAME_OF_COLUMN_OF_ID.getValue() + " = ?"),
    /*
        DELETE FROM payment_cards WHERE id = ?
     */
    PREPARED_STATEMENT_TO_DELETE_PAYMENT_CARD_BY_ID("DELETE FROM " + NAME_OF_TABLE.getValue() + " WHERE "
            + NAME_OF_COLUMN_OF_ID.getValue() + " = ?"),
    /*
        SELECT 1 FROM payment_cards WHERE id = ?
     */
    PREPARED_STATEMENT_TO_DEFINE_EXISTING_BY_ID("SELECT 1 FROM " + NAME_OF_TABLE.getValue() + " WHERE "
            + NAME_OF_COLUMN_OF_ID.getValue() + " = ?"),
    /*
        SELECT id, card_number, number_of_month_of_expiration_date, year_of_expiration_date, payment_system,
               encrypted_cvc, client_id, name_of_bank, encrypted_password
        FROM payment_cards
        WHERE card_number = ?
     */
    PREPARED_STATEMENT_TO_SELECT_PAYMENT_CARD_BY_CARD_NUMBER("SELECT " + NAME_OF_COLUMN_OF_ID.getValue() + ", "
            + NAME_OF_COLUMN_OF_CARD_NUMBER.getValue() + ", "
            + NAME_OF_COLUMN_OF_NUMBER_OF_MONTH_OF_EXPIRATION_DATE.getValue() + ", "
            + NAME_OF_COLUMN_OF_YEAR_OF_EXPIRATION_DATE.getValue() + ", " + NAME_OF_COLUMN_OF_PAYMENT_SYSTEM.getValue()
            + ", " + NAME_OF_COLUMN_OF_ENCRYPTED_CVC.getValue() + ", " + NAME_OF_COLUMN_OF_CLIENT_ID.getValue() + ", "
            + NAME_OF_COLUMN_OF_NAME_OF_BANK.getValue() + ", " + NAME_OF_COLUMN_OF_ENCRYPTED_PASSWORD.getValue()
            + " FROM " + NAME_OF_TABLE.getValue() + " WHERE " + NAME_OF_COLUMN_OF_CARD_NUMBER.getValue() + " = ?");

    private final String sqlQuery;

    private PaymentCardSqlOperation(final String sqlQuery)
    {
        this.sqlQuery = sqlQuery;
    }

    public final String getSqlQuery()
    {
        return this.sqlQuery;
    }

    public static final int PARAMETER_INDEX_OF_CARD_NUMBER_IN_PREPARED_STATEMENT_TO_INSERT = 1;
    public static final int PARAMETER_INDEX_OF_NUMBER_OF_MONTH_OF_EXPIRATION_DATE_IN_PREPARED_STATEMENT_TO_INSERT = 2;
    public static final int PARAMETER_INDEX_OF_YEAR_OF_EXPIRATION_DATE_IN_PREPARED_STATEMENT_TO_INSERT = 3;
    public static final int PARAMETER_INDEX_OF_PAYMENT_SYSTEM_IN_PREPARED_STATEMENT_TO_INSERT = 4;
    public static final int PARAMETER_INDEX_OF_ENCRYPTED_CVC_IN_PREPARED_STATEMENT_TO_INSERT = 5;
    public static final int PARAMETER_INDEX_OF_CLIENT_ID_IN_PREPARED_STATEMENT_TO_INSERT = 6;
    public static final int PARAMETER_INDEX_OF_NAME_OF_BANK_IN_PREPARED_STATEMENT_TO_INSERT = 7;
    public static final int PARAMETER_INDEX_OF_ENCRYPTED_PASSWORD_IN_PREPARED_STATEMENT_TO_INSERT = 8;

    public static final int PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_SELECT_BY_ID = 1;

    public static final int PARAMETER_INDEX_OF_CARD_NUMBER_IN_PREPARED_STATEMENT_TO_UPDATE = 1;
    public static final int PARAMETER_INDEX_OF_NUMBER_OF_MONTH_OF_EXPIRATION_DATE_IN_PREPARED_STATEMENT_TO_UPDATE = 2;
    public static final int PARAMETER_INDEX_OF_YEAR_OF_EXPIRATION_DATE_IN_PREPARED_STATEMENT_TO_UPDATE = 3;
    public static final int PARAMETER_INDEX_OF_PAYMENT_SYSTEM_IN_PREPARED_STATEMENT_TO_UPDATE = 4;
    public static final int PARAMETER_INDEX_OF_ENCRYPTED_CVC_IN_PREPARED_STATEMENT_TO_UPDATE = 5;
    public static final int PARAMETER_INDEX_OF_CLIENT_ID_IN_PREPARED_STATEMENT_TO_UPDATE = 6;
    public static final int PARAMETER_INDEX_OF_NAME_OF_BANK_IN_PREPARED_STATEMENT_TO_UPDATE = 7;
    public static final int PARAMETER_INDEX_OF_ENCRYPTED_PASSWORD_IN_PREPARED_STATEMENT_TO_UPDATE = 8;
    public static final int PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_UPDATE = 9;

    public static final int PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_DELETE = 1;

    public static final int PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_DEFINE_EXISTING = 1;

    public static final int PARAMETER_INDEX_OF_CARD_NUMBER_IN_PREPARED_STATEMENT_TO_SELECT_BY_CARD_NUMBER = 1;
}
