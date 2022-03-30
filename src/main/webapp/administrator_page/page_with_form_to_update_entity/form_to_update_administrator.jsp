<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.administrator.NameOfRequestParameterToUpdateAdministrator.*" %>
<%@ page import="by.itacademy.zuevvlad.cardpaymentproject.entity.Administrator" %>
<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.servlet.updatingentity.administrator.PropertyOfUpdatingAdministratorServlet.NAME_OF_REQUEST_ATTRIBUTE_OF_UPDATED_ADMINISTRATOR" %>
<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.servlet.updatingentity.administrator.PropertyOfUpdatingAdministratorServlet.NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_ADMINISTRATOR" %>

<!DOCTYPE html>
<html>

<body>

    <%
        final Administrator updatedAdministrator = (Administrator)request.getAttribute(
                NAME_OF_REQUEST_ATTRIBUTE_OF_UPDATED_ADMINISTRATOR.getValue());
    %>

    <form accept-charset="UTF-8" action="${pageContext.request.contextPath}/update_administrator" method="post">

        <input type="hidden" name="<%= NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_ADMINISTRATOR.getValue() %>"
                value="<%= updatedAdministrator.getId() %>" />

        <div>
            <label for="input_field_of_email_of_updated_administrator">New email: </label>
            <input id="input_field_of_email_of_updated_administrator" type="email"
                    name="<%= NAME_OF_REQUEST_PARAMETER_OF_EMAIL_OF_UPDATED_ADMINISTRATOR.getValue() %>"
                    required="required" value="<%= updatedAdministrator.getEmail() %>" />
        </div>

        <div>
            <label for="input_field_of_password_of_updated_administrator">New password: </label>
            <input id="input_field_of_password_of_updated_administrator" type="password"
                    name="<%= NAME_OF_REQUEST_PARAMETER_OF_PASSWORD_OF_UPDATED_ADMINISTRATOR.getValue() %>"
                    required="required" value="<%= updatedAdministrator.getPassword() %>" />
        </div>

        <div>
            <label for="select_list_of_level_of_updated_administrator">New level: </label>
            <select id="select_list_of_level_of_updated_administrator" size="1"
                    name="<%= NAME_OF_REQUEST_PARAMETER_OF_LEVEL_OF_UPDATED_ADMINISTRATOR.getValue() %>"
                    required="required">
                <option value="<%= Administrator.Level.SUPPORTER.name() %>">Supporter</option>
                <option value="<%= Administrator.Level.MODIFIER.name() %>">Modifier</option>
                <option value="<%= Administrator.Level.MAIN.name() %>">Main</option>
            </select>
        </div>

        <input type="submit" value="update" />

        <input type="reset" value="reset" />

</form>

</body>

</html>