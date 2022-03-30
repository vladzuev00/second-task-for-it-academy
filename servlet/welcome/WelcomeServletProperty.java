package by.itacademy.zuevvlad.cardpaymentproject.servlet.welcome;

public enum WelcomeServletProperty
{
    PATH_OF_WELCOME_PAGE("/welcome_page/welcome_page.html");

    private final String value;

    private WelcomeServletProperty(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return this.value;
    }
}
