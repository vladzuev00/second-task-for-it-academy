<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.paymentcard.NameOfRequestParameterToAddPaymentCard.*" %>

<!DOCTYPE html>
<html>

<body>

    <form accept-charset="UTF-8" action="${pageContext.request.contextPath}/add_payment_card" method="post">

        <div>
            <label for="input_field_of_card_number_of_added_payment_card">Card number: </label>
            <input id="input_field_of_card_number_of_added_payment_card" type="text" name="<%= NAME_OF_REQUEST_PARAMETER_OF_CARD_NUMBER_OF_ADDED_PAYMENT_CARD.getValue() %>" required="required" />
        </div>

        <div>
            <label for="input_field_of_expiration_date_of_added_payment_card">Date of expiration: </label>
            <input id="input_field_of_expiration_date_of_added_payment_card" type="text" name="<%= NAME_OF_REQUEST_PARAMETER_OF_EXPIRATION_DATE_OF_ADDED_PAYMENT_CARD.getValue() %>" required="required" />
        </div>

        <div>
            <label for="input_field_of_payment_system_of_added_payment_card">Payment system: </label>
            <input id="input_field_of_payment_system_of_added_payment_card" type="text" name="<%= NAME_OF_REQUEST_PARAMETER_OF_PAYMENT_SYSTEM_OF_ADDED_PAYMENT_CARD.getValue() %>" required="required" />
        </div>

        <div>
            <label for="input_field_of_cvc_of_added_payment_card">Cvc: </label>
            <input id="input_field_of_cvc_of_added_payment_card" type="password" name="<%= NAME_OF_REQUEST_PARAMETER_OF_CVC_OF_ADDED_PAYMENT_CARD.getValue() %>" required="required" />
        </div>

        <div>
            <label for="input_field_of_phone_number_of_added_payment_card">Client's phone number: </label>
            <input id="input_field_of_phone_number_of_added_payment_card" type="text" name="<%= NAME_OF_REQUEST_PARAMETER_OF_PHONE_NUMBER_OF_CLIENT_OF_ADDED_PAYMENT_CARD.getValue() %>" required="required" />
        </div>

        <div>
            <label for="input_field_of_name_of_bank_of_added_payment_card">Name of bank: </label>
            <input id="input_field_of_name_of_bank_of_added_payment_card" type="text" name="<%= NAME_OF_REQUEST_PARAMETER_OF_NAME_OF_BANK_OF_ADDED_PAYMENT_CARD.getValue() %>" required="required" />
        </div>

        <div>
            <label for="input_field_of_password_of_added_payment_card">Password: </label>
            <input id="input_field_of_password_of_added_payment_card" type="password" name="<%= NAME_OF_REQUEST_PARAMETER_OF_PASSWORD_OF_ADDED_PAYMENT_CARD.getValue() %>" required="required" />
        </div>

        <input type="submit" value="add" />

        <input type="reset" value="reset" />

    </form>

</body>

</html>