package by.itacademy.zuevvlad.cardpaymentproject.servlet.addingentity.paymentcard;

public enum PropertyOfAddingPaymentCardServlet
{
    PATH_OF_PAGE_WITH_FORM_TO_ADD_NEW_PAYMENT_CARD("/administrator_page/page_with_form_to_add_entity/form_to_add_new_payment_card.jsp");

    private final String value;

    private PropertyOfAddingPaymentCardServlet(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return this.value;
    }
}
