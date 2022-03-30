<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.client.NameOfRequestParameterToUpdateClient.*" %>
<%@ page import="by.itacademy.zuevvlad.cardpaymentproject.entity.Client" %>
<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.servlet.updatingentity.client.PropertyOfUpdatingClientServlet.NAME_OF_REQUEST_ATTRIBUTE_OF_UPDATED_CLIENT" %>
<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.servlet.updatingentity.client.PropertyOfUpdatingClientServlet.NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_CLIENT" %>

<!DOCTYPE html>
<html>

<body>

    <%
        final Client updatedClient = (Client)request.getAttribute(
                NAME_OF_REQUEST_ATTRIBUTE_OF_UPDATED_CLIENT.getValue());
    %>

    <form accept-charset="UTF-8" action="${pageContext.request.contextPath}/update_client" method="post">

        <input type="hidden" name="<%= NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_CLIENT.getValue() %>"
               value="<%= updatedClient.getId() %>" />

        <div>
            <label for="input_field_of_email_of_updated_client">New email: </label>
            <input id="input_field_of_email_of_updated_client" type="email"
                   name="<%= NAME_OF_REQUEST_PARAMETER_OF_EMAIL_OF_UPDATED_CLIENT.getValue() %>" required="required"
                   value="<%= updatedClient.getEmail() %>" />
        </div>

        <div>
            <label for="input_field_of_password_of_updated_client">New password: </label>
            <input id="input_field_of_password_of_updated_client" type="password"
                   name="<%= NAME_OF_REQUEST_PARAMETER_OF_PASSWORD_OF_UPDATED_CLIENT.getValue() %>"
                   required="required" value="<%= updatedClient.getPassword() %>" />
        </div>

        <div>
            <label for="input_field_of_name_of_updated_client">New name: </label>
            <input id="input_field_of_name_of_updated_client" type="text"
                   name="<%= NAME_OF_REQUEST_PARAMETER_OF_NAME_OF_UPDATED_CLIENT.getValue() %>" required="required"
                   value="<%= updatedClient.getName() %>" />
        </div>

        <div>
            <label for="input_field_of_surname_of_updated_client">New surname: </label>
            <input id="input_field_of_surname_of_updated_client" type="text"
                   name="<%= NAME_OF_REQUEST_PARAMETER_OF_SURNAME_OF_UPDATED_CLIENT.getValue() %>"
                   required="required" value="<%= updatedClient.getSurname() %>" />
        </div>

        <div>
            <label for="input_field_of_patronymic_of_updated_client">New patronymic: </label>
            <input id="input_field_of_patronymic_of_updated_client" type="text"
                   name="<%= NAME_OF_REQUEST_PARAMETER_OF_PATRONYMIC_OF_UPDATED_CLIENT.getValue() %>"
                   required="required" value="<%= updatedClient.getPatronymic() %>" />
        </div>

        <div>
            <label for="input_field_of_phone_number_of_updated_client">New phone number: </label>
            <input id="input_field_of_phone_number_of_updated_client" type="text"
                   name="<%= NAME_OF_REQUEST_PARAMETER_OF_PHONE_NUMBER_OF_UPDATED_CLIENT.getValue() %>"
                   required="required" value="<%= updatedClient.getPhoneNumber() %>" />
        </div>

        <div>
            <label for="input_field_of_bank_account_number_of_added_client">New bank account's number: </label>
            <input id="input_field_of_bank_account_number_of_added_client" type="text"
                   name="<%= NAME_OF_REQUEST_PARAMETER_OF_BANK_ACCOUNT_NUMBER_OF_UPDATED_CLIENT.getValue() %>"
                   required="required" value="<%= updatedClient.getBankAccount().getNumber() %>" />
        </div>

        <input type="submit" value="update" />

        <input type="reset" value="reset" />

    </form>

</body>

</html>