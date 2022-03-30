package by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities;

public enum PropertyOfListingEntitiesServlet
{
    NAME_OF_REQUEST_ATTRIBUTE_OF_LISTED_BANK_ACCOUNTS("listed_bank_accounts"),
    PATH_OF_PAGE_WITH_LISTED_BANK_ACCOUNTS("/administrator_page/list_of_entities_page/list_of_bank_accounts.jsp"),

    NAME_OF_REQUEST_ATTRIBUTE_OF_LISTED_ADMINISTRATORS("listed_administrators"),
    PATH_OF_PAGE_WITH_LISTED_ADMINISTRATORS("/administrator_page/list_of_entities_page/list_of_administrators.jsp"),

    NAME_OF_REQUEST_ATTRIBUTE_OF_LISTED_CLIENTS("listed_clients"),
    PATH_OF_PAGE_WITH_LISTED_CLIENTS("/administrator_page/list_of_entities_page/list_of_clients.jsp"),
    NAME_OF_REQUEST_ATTRIBUTE_OF_CLIENTS_AND_ASSOCIATED_NUMBERS_OF_PAYMENT_CARDS("clients_and_associated_numbers_of_payment_cards"),

    NAME_OF_REQUEST_ATTRIBUTE_OF_LISTED_PAYMENT_CARDS("listed_payment_cards"),
    PATH_OF_PAGE_WITH_LISTED_PAYMENT_CARDS("/administrator_page/list_of_entities_page/list_of_payment_cards.jsp"),

    NAME_OF_REQUEST_ATTRIBUTE_OF_LISTED_PAYMENTS("listed_payments"),
    NAME_OF_REQUEST_ATTRIBUTE_OF_PAYMENTS_AND_ASSOCIATED_DESCRIPTION_OF_DATES("payments_and_associated_description_of_dates"),
    PATH_OF_PAGE_WITH_LISTED_PAYMENTS("/administrator_page/list_of_entities_page/list_of_payments.jsp"),

    DELIMITER_OF_ACTIONS(" | ");


    private final String value;

    private PropertyOfListingEntitiesServlet(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return this.value;
    }
}
