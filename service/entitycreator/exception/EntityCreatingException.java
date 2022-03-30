package by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.exception;

public final class EntityCreatingException extends Exception
{
    public EntityCreatingException()
    {
        super();
    }

    public EntityCreatingException(final String description)
    {
        super(description);
    }

    public EntityCreatingException(final Exception cause)
    {
        super(cause);
    }

    public EntityCreatingException(final String description, final Exception cause)
    {
        super(description, cause);
    }
}
