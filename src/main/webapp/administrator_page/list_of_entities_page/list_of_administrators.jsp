<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl-core" %>
<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.servlet.updatingentity.administrator.PropertyOfUpdatingAdministratorServlet.*" %>
<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.servlet.deletingentity.administrator.PropertyOfDeletingAdministratorServlet.NAME_OF_REQUEST_PARAMETER_OF_ID_OF_DELETED_ADMINISTRATOR" %>
<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.DELIMITER_OF_ACTIONS" %>

<html>

<body>

    <table border="1">
        <tr>
            <th>Email</th>
            <th>Level</th>
            <th>Action</th>
        </tr>

        <jsp:useBean id="listed_administrators" scope="request" type="java.util.Collection"/>
        <jstl-core:forEach var="listed_administrator" items="${listed_administrators}">

            <jstl-core:url var="link_to_update_administrator" value="/update_administrator">
                <jstl-core:param name="<%= NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_ADMINISTRATOR.getValue() %>"
                                 value="${listed_administrator.id}" />
            </jstl-core:url>

            <jstl-core:url var="link_to_delete_administrator" value="/delete_administrator">
                <jstl-core:param name="<%= NAME_OF_REQUEST_PARAMETER_OF_ID_OF_DELETED_ADMINISTRATOR.getValue() %>"
                                 value="${listed_administrator.id}" />
            </jstl-core:url>

            <tr>
                <td>${listed_administrator.email}</td>
                <td>${listed_administrator.level}</td>
                <td>
                    <a href="${link_to_update_administrator}">Update</a>

                    <%= DELIMITER_OF_ACTIONS.getValue() %>

                    <a href="${link_to_delete_administrator}"
                       onclick="return (confirm('Are you sure you want to delete this administrator?'))">
                        Delete
                    </a>
                </td>
            </tr>
        </jstl-core:forEach>

    </table>

    <input type="button" value="Add new administrator" onclick="window.location.href='/add_administrator'" />

</body>

</html>