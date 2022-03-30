<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.administrator.NameOfRequestParameterToAddAdministrator.*" %>
<%@ page import="by.itacademy.zuevvlad.cardpaymentproject.entity.Administrator" %>
<!DOCTYPE html>
<html>

<body>

<form accept-charset="UTF-8" action="${pageContext.request.contextPath}/add_administrator" method="post">

    <div>
        <label for="input_field_of_email_of_added_administrator">Email: </label>
        <input id="input_field_of_email_of_added_administrator" type="email" name="<%= NAME_OF_REQUEST_PARAMETER_OF_EMAIL_OF_ADDED_ADMINISTRATOR.getValue() %>" required="required" />
    </div>

    <div>
        <label for="input_field_of_password_of_added_administrator">Password: </label>
        <input id="input_field_of_password_of_added_administrator" type="password" name="<%= NAME_OF_REQUEST_PARAMETER_OF_PASSWORD_OF_ADDED_ADMINISTRATOR.getValue() %>" required="required" />
    </div>

    <div>
        <label for="select_list_of_level_of_added_administrator">Level: </label>
        <select id="select_list_of_level_of_added_administrator" size="1" name="<%= NAME_OF_REQUEST_PARAMETER_OF_LEVEL_OF_ADDED_ADMINISTRATOR.getValue() %>" required="required">
            <option value="<%= Administrator.Level.SUPPORTER.name() %>">Supporter</option>
            <option value="<%= Administrator.Level.MODIFIER.name() %>">Modifier</option>
            <option value="<%= Administrator.Level.MAIN.name() %>">Main</option>
        </select>
    </div>

    <input type="submit" value="add" />

    <input type="reset" value="reset" />

</form>

</body>

</html>