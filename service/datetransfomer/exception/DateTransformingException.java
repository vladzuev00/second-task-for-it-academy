package by.itacademy.zuevvlad.cardpaymentproject.service.datetransfomer.exception;

public final class DateTransformingException extends Exception
{
    public DateTransformingException()
    {
        super();
    }

    public DateTransformingException(final String description)
    {
        super(description);
    }

    public DateTransformingException(final Exception cause)
    {
        super(cause);
    }

    public DateTransformingException(final String description, final Exception cause)
    {
        super(description, cause);
    }
}
