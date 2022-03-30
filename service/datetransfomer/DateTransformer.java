package by.itacademy.zuevvlad.cardpaymentproject.service.datetransfomer;

import by.itacademy.zuevvlad.cardpaymentproject.service.datetransfomer.exception.DateTransformingException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateTransformer
{
    private final SimpleDateFormat simpleDateFormat;

    public DateTransformer()
    {
        super();
        this.simpleDateFormat = new SimpleDateFormat(DateTransformer.PATTERN_OF_SIMPLE_DATE_FORMAT);
    }

    private static final String PATTERN_OF_SIMPLE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public final String findDescription(final Calendar date)
    {
        final java.util.Date utilDate = date.getTime();
        return this.simpleDateFormat.format(utilDate);
    }

    public final Calendar findDate(final String descriptionOfDate)
            throws DateTransformingException
    {
        try
        {
            final Calendar date = Calendar.getInstance();
            final Date utilDate = this.simpleDateFormat.parse(descriptionOfDate);
            date.setTime(utilDate);
            return date;
        }
        catch(final ParseException cause)
        {
            throw new DateTransformingException(cause);
        }
    }
}
