package by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.exception;

public final class EntityModifyingException extends Exception
{
    public EntityModifyingException()
    {
        super();
    }

    public EntityModifyingException(final String description)
    {
        super(description);
    }

    public EntityModifyingException(final Exception cause)
    {
        super(cause);
    }

    public EntityModifyingException(final String description, final Exception cause)
    {
        super(description, cause);
    }
}
