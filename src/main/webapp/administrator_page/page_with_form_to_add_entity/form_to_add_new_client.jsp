<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.client.NameOfRequestParameterToAddClient.*" %>
<!DOCTYPE html>
<html>

<body>

    <form accept-charset="UTF-8" action="${pageContext.request.contextPath}/add_client" method="post">

        <div>
            <label for="input_field_of_email_of_added_client">Email: </label>
            <input id="input_field_of_email_of_added_client" type="email" name="<%= NAME_OF_REQUEST_PARAMETER_OF_EMAIL_OF_ADDED_CLIENT.getValue() %>" required="required" />
        </div>

        <div>
            <label for="input_field_of_password_of_added_client">Password: </label>
            <input id="input_field_of_password_of_added_client" type="password" name="<%= NAME_OF_REQUEST_PARAMETER_OF_PASSWORD_OF_ADDED_CLIENT.getValue() %>" required="required" />
        </div>

        <div>
            <label for="input_field_of_name_of_added_client">Name: </label>
            <input id="input_field_of_name_of_added_client" type="text" name="<%= NAME_OF_REQUEST_PARAMETER_OF_NAME_OF_ADDED_CLIENT.getValue() %>" required="required" />
        </div>

        <div>
            <label for="input_field_of_surname_of_added_client">Surname: </label>
            <input id="input_field_of_surname_of_added_client" type="text" name="<%= NAME_OF_REQUEST_PARAMETER_OF_SURNAME_OF_ADDED_CLIENT.getValue() %>" required="required" />
        </div>

        <div>
            <label for="input_field_of_patronymic_of_added_client">Patronymic: </label>
            <input id="input_field_of_patronymic_of_added_client" type="text" name="<%= NAME_OF_REQUEST_PARAMETER_OF_PATRONYMIC_OF_ADDED_CLIENT.getValue() %>" required="required" />
        </div>

        <div>
            <label for="input_field_of_phone_number_of_added_client">Phone number: </label>
            <input id="input_field_of_phone_number_of_added_client" type="text" name="<%= NAME_OF_REQUEST_PARAMETER_OF_PHONE_NUMBER_OF_ADDED_CLIENT.getValue() %>" required="required" />
        </div>

        <div>
            <label for="input_field_of_bank_account_number_of_added_client">Bank account's number: </label>
            <input id="input_field_of_bank_account_number_of_added_client" type="text" name="<%= NAME_OF_REQUEST_PARAMETER_OF_BANK_ACCOUNT_NUMBER_OF_ADDED_CLIENT.getValue() %>" required="required" />
        </div>

        <input type="submit" value="add" />

        <input type="reset" value="reset" />

    </form>

</body>

</html>