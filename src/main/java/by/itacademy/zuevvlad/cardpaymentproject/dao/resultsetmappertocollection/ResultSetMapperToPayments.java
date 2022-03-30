package by.itacademy.zuevvlad.cardpaymentproject.dao.resultsetmappertocollection;

import by.itacademy.zuevvlad.cardpaymentproject.dao.DAO;
import by.itacademy.zuevvlad.cardpaymentproject.dao.PaymentCardDAO;
import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.FindingEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.resultsetmappertocollection.exception.ResultSetMappingToCollectionException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.resultsetmappertocollection.exception.ResultSetRowMappingToEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Payment;
import by.itacademy.zuevvlad.cardpaymentproject.entity.PaymentCard;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import static by.itacademy.zuevvlad.cardpaymentproject.dao.tableproperty.PaymentTableProperty.*;

public final class ResultSetMapperToPayments implements ResultSetMapperToCollection<Payment, Set<Payment>>
{
    private final ResultSetMapperToCollection.ResultSetRowMapperToEntity<Payment> resultSetRowMapperToPayment;

    public static ResultSetMapperToPayments createResultSetMapperToPayments()
    {
        if(ResultSetMapperToPayments.resultSetMapperToPayments == null)
        {
            synchronized(ResultSetMapperToPayments.class)
            {
                if(ResultSetMapperToPayments.resultSetMapperToPayments == null)
                {
                    ResultSetMapperToPayments.resultSetMapperToPayments = new ResultSetMapperToPayments();
                }
            }
        }
        return ResultSetMapperToPayments.resultSetMapperToPayments;
    }

    private static ResultSetMapperToPayments resultSetMapperToPayments = null;

    private ResultSetMapperToPayments()
    {
        super();
        this.resultSetRowMapperToPayment = new ResultSetRowMapperToPayment();
    }

    @Override
    public final ResultSetMapperToCollection.ResultSetRowMapperToEntity<Payment> getResultSetRowMapperToEntity()
    {
        return this.resultSetRowMapperToPayment;
    }

    @Override
    public final Set<Payment> map(final ResultSet mappedResultSet)
            throws ResultSetMappingToCollectionException
    {
        try
        {
            final Set<Payment> mappedPayments = new LinkedHashSet<Payment>();
            Payment currentMappedPayment;
            while(mappedResultSet.next())
            {
                currentMappedPayment = this.resultSetRowMapperToPayment.mapCurrentRow(mappedResultSet);   //TODO: если будет можно заменить на HashSet
                mappedPayments.add(currentMappedPayment);
            }
            return mappedPayments;
        }
        catch(final SQLException | ResultSetRowMappingToEntityException cause)
        {
            throw new ResultSetMappingToCollectionException(cause);
        }
    }

    private static final class ResultSetRowMapperToPayment
            implements ResultSetMapperToCollection.ResultSetRowMapperToEntity<Payment>
    {
        private final DAO<PaymentCard> paymentCardDAO;

        public ResultSetRowMapperToPayment()
        {
            super();
            this.paymentCardDAO = PaymentCardDAO.createPaymentCardDAO();
        }

        @Override
        public final Payment mapCurrentRow(final ResultSet resultSetOfMappedRow)
                throws ResultSetRowMappingToEntityException
        {
            try
            {
                final long id = resultSetOfMappedRow.getLong(NAME_OF_COLUMN_OF_ID.getValue());

                final long idOfPaymentCardOfSender = resultSetOfMappedRow.getLong(
                        NAME_OF_COLUMN_OF_CARD_ID_OF_SENDER.getValue());
                final Optional<PaymentCard> optionalOfPaymentCardOfSender = this.paymentCardDAO.findEntityById(
                        idOfPaymentCardOfSender);
                if(optionalOfPaymentCardOfSender.isEmpty())
                {
                    throw new ResultSetRowMappingToEntityException("Payment card with id = " + idOfPaymentCardOfSender
                            + " doesn't exist.");
                }
                final PaymentCard paymentCardOfSender = optionalOfPaymentCardOfSender.get();

                final long idOfPaymentCardOfReceiver = resultSetOfMappedRow.getLong(
                        NAME_OF_COLUMN_OF_CARD_ID_OF_RECEIVER.getValue());
                final Optional<PaymentCard> optionalOfPaymentCardOfReceiver = this.paymentCardDAO.findEntityById(
                        idOfPaymentCardOfReceiver);
                if(optionalOfPaymentCardOfReceiver.isEmpty())
                {
                    throw new ResultSetRowMappingToEntityException("Payment card with id = " + idOfPaymentCardOfReceiver
                            + " doesn't exist.");
                }
                final PaymentCard paymentCardOfReceiver = optionalOfPaymentCardOfReceiver.get();

                final BigDecimal money = resultSetOfMappedRow.getBigDecimal(NAME_OF_COLUMN_OF_MONEY.getValue());

                final Calendar date = Calendar.getInstance();
                final Timestamp timestamp = resultSetOfMappedRow.getTimestamp(NAME_OF_COLUMN_OF_DATE.getValue());
                date.setTimeInMillis(timestamp.getTime());

                return new Payment(id, paymentCardOfSender, paymentCardOfReceiver, money, date);
            }
            catch(final SQLException | FindingEntityException cause)
            {
                throw new ResultSetRowMappingToEntityException(cause);
            }
        }
    }
}
