<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.servlet.logon.LogOnServletProperty.*" %>
<%@ page import="java.io.PrintWriter" %>

<!DOCTYPE html>
<html>
<head>
    <title>Log on</title>
    <meta name="keywords" content="log on card payment"/>
    <meta name="description" content="log on page"/>
</head>
<body>

    <%
        final String errorMessage = (String)request.getAttribute(
                NAME_OF_ATTRIBUTE_OF_REQUEST_OF_ERROR_MESSAGE.getValue());
        if(errorMessage != null)
        {
            final PrintWriter printWriter = response.getWriter();
            printWriter.println(errorMessage);
        }
    %>

    <form accept-charset="UTF-8" action="${pageContext.request.contextPath}/log_on" method="get">
        <fieldset>

            <legend>Logging on</legend>

            <div>
                <label for="input_field_of_user_email">Email</label>
                <input id="input_field_of_user_email" type="email" name="<%= NAME_OF_REQUEST_PARAMETER_OF_INPUTTED_USER_EMAIL.getValue() %>" required="required" />
            </div>

            <div>
                <label for="input_field_of_user_password">Password</label>
                <input id="input_field_of_user_password" type="password" name="<%= NAME_OF_REQUEST_PARAMETER_OF_INPUTTED_USER_PASSWORD.getValue() %>" required="required" />
            </div>

            <input type="submit" value="log on" />

            <input type="reset" value="reset" />

        </fieldset>
    </form>
</body>
</html>
