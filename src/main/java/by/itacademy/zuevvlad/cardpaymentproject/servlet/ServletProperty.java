package by.itacademy.zuevvlad.cardpaymentproject.servlet;

public enum ServletProperty
{
    CONTENT_TYPE_OF_RESPONSE("text/html"), CHARACTER_ENCODING_OF_RESPONSE("UTF-8"), PATH_OF_MAIN_PAGE_OF_USER(""),
    PATH_OF_MAIN_PAGE_OF_ADMINISTRATOR("/administrator_page/main_page/administrator_main_page.jsp"),
    NAME_OF_SESSION_ATTRIBUTE_OF_LOGGED_ON_USER("logged_on_user"),
    PATH_OF_WELCOME_PAGE("/welcome_page/welcome_page.html"), PATH_OF_LOG_ON_PAGE("/log_on_page/log_on_page.jsp");

    private final String value;

    private ServletProperty(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return this.value;
    }
}
