<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.paymentcard.NameOfRequestParameterToUpdatePaymentCard.*" %>
<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.servlet.updatingentity.paymentcard.PropertyOfUpdatingPaymentCardServlet.NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_PAYMENT_CARD" %>
<%@ page import="by.itacademy.zuevvlad.cardpaymentproject.entity.PaymentCard" %>
<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.servlet.updatingentity.paymentcard.PropertyOfUpdatingPaymentCardServlet.NAME_OF_REQUEST_ATTRIBUTE_OF_UPDATED_PAYMENT_CARD" %>
<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.service.parser.paymentcardexpirationdate.ParserOfExpirationDateOfPaymentCard.DELIMITER_OF_MONTH_AND_YEAR" %>

<!DOCTYPE html>
<html>

<body>

    <%
        final PaymentCard updatedPaymentCard = (PaymentCard)request.getAttribute(
                NAME_OF_REQUEST_ATTRIBUTE_OF_UPDATED_PAYMENT_CARD.getValue());
    %>

    <form accept-charset="UTF-8" action="${pageContext.request.contextPath}/update_payment_card" method="post">

        <input type="hidden" name="<%= NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_PAYMENT_CARD.getValue() %>"
               value="<%= updatedPaymentCard.getId() %>" />

        <div>
            <label for="input_field_of_card_number_of_updated_payment_card">New card number: </label>
            <input id="input_field_of_card_number_of_updated_payment_card" type="text"
                    name="<%= NAME_OF_REQUEST_PARAMETER_OF_CARD_NUMBER_OF_UPDATED_PAYMENT_CARD.getValue() %>"
                    required="required" value="<%= updatedPaymentCard.getCardNumber() %>" />
        </div>

        <div>
            <label for="input_field_of_expiration_date_of_updated_payment_card">New date of expiration: </label>
            <input id="input_field_of_expiration_date_of_updated_payment_card" type="text"
                   name="<%= NAME_OF_REQUEST_PARAMETER_OF_EXPIRATION_DATE_OF_UPDATED_PAYMENT_CARD.getValue() %>"
                   required="required"
                   value="<%= updatedPaymentCard.getExpirationDate().getMonth().getValue()
                        + DELIMITER_OF_MONTH_AND_YEAR + updatedPaymentCard.getExpirationDate().getYear() %>" />
        </div>

        <div>
            <label for="input_field_of_payment_system_of_updated_payment_card">New payment system: </label>
            <input id="input_field_of_payment_system_of_updated_payment_card" type="text"
                   name="<%= NAME_OF_REQUEST_PARAMETER_OF_PAYMENT_SYSTEM_OF_UPDATED_PAYMENT_CARD.getValue() %>"
                   required="required" value="<%= updatedPaymentCard.getPaymentSystem() %>" />
        </div>

        <div>
            <label for="input_field_of_cvc_of_updated_payment_card">New cvc: </label>
            <input id="input_field_of_cvc_of_updated_payment_card" type="password"
                   name="<%= NAME_OF_REQUEST_PARAMETER_OF_CVC_OF_UPDATED_PAYMENT_CARD.getValue() %>" required="required"
                   value="<%= updatedPaymentCard.getPaymentSystem() %>" />
        </div>

        <div>
            <label for="input_field_of_phone_number_of_updated_payment_card">Phone number of new client: </label>
            <input id="input_field_of_phone_number_of_updated_payment_card" type="text"
                   name="<%= NAME_OF_REQUEST_PARAMETER_OF_PHONE_NUMBER_OF_CLIENT_OF_UPDATED_PAYMENT_CARD.getValue() %>"
                   required="required" value="<%= updatedPaymentCard.getClient().getPhoneNumber() %>" />
        </div>

        <div>
            <label for="input_field_of_name_of_bank_of_updated_payment_card">New name of bank: </label>
            <input id="input_field_of_name_of_bank_of_updated_payment_card" type="text"
                   name="<%= NAME_OF_REQUEST_PARAMETER_OF_NAME_OF_BANK_OF_UPDATED_PAYMENT_CARD.getValue() %>"
                   required="required" value="<%= updatedPaymentCard.getNameOfBank() %>" />
        </div>

        <div>
            <label for="input_field_of_password_of_updated_payment_card">Password: </label>
            <input id="input_field_of_password_of_updated_payment_card" type="password"
                   name="<%= NAME_OF_REQUEST_PARAMETER_OF_PASSWORD_OF_UPDATED_PAYMENT_CARD.getValue() %>"
                   required="required" value="<%= updatedPaymentCard.getPassword() %>" />
        </div>

        <input type="submit" value="update" />

        <input type="reset" value="reset" />

    </form>

</body>

</html>