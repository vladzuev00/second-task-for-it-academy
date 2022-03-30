package by.itacademy.zuevvlad.cardpaymentproject.dao.resultsetmappertocollection;

import by.itacademy.zuevvlad.cardpaymentproject.dao.ClientDAO;
import by.itacademy.zuevvlad.cardpaymentproject.dao.DAO;
import by.itacademy.zuevvlad.cardpaymentproject.dao.cryptographer.Cryptographer;
import by.itacademy.zuevvlad.cardpaymentproject.dao.cryptographer.StringToStringCryptographer;
import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.FindingEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.resultsetmappertocollection.exception.ResultSetMappingToCollectionException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.resultsetmappertocollection.exception.ResultSetRowMappingToEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Client;
import by.itacademy.zuevvlad.cardpaymentproject.entity.PaymentCard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;
import java.time.Year;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import static by.itacademy.zuevvlad.cardpaymentproject.dao.tableproperty.PaymentCardTableProperty.*;

public final class ResultSetMapperToPaymentCards implements ResultSetMapperToCollection<PaymentCard, Set<PaymentCard>>
{
    private final ResultSetMapperToCollection.ResultSetRowMapperToEntity<PaymentCard> resultSetRowMapperToPaymentCard;

    public static ResultSetMapperToPaymentCards createResultSetMapperToPaymentCards()
    {
        if(ResultSetMapperToPaymentCards.resultSetMapperToPaymentCards == null)
        {
            synchronized(ResultSetMapperToPaymentCards.class)
            {
                if(ResultSetMapperToPaymentCards.resultSetMapperToPaymentCards == null)
                {
                    ResultSetMapperToPaymentCards.resultSetMapperToPaymentCards = new ResultSetMapperToPaymentCards();
                }
            }
        }
        return ResultSetMapperToPaymentCards.resultSetMapperToPaymentCards;
    }

    private static ResultSetMapperToPaymentCards resultSetMapperToPaymentCards;

    private ResultSetMapperToPaymentCards()
    {
        super();
        this.resultSetRowMapperToPaymentCard = new ResultSetMapperToPaymentCards.ResultSetRowMapperToPaymentCard();
    }

    @Override
    public final ResultSetMapperToCollection.ResultSetRowMapperToEntity<PaymentCard> getResultSetRowMapperToEntity()
    {
        return this.resultSetRowMapperToPaymentCard;
    }

    @Override
    public final Set<PaymentCard> map(final ResultSet mappedResultSet)
            throws ResultSetMappingToCollectionException
    {
        try
        {
            final Set<PaymentCard> mappedPaymentCards = new LinkedHashSet<PaymentCard>();   //TODO: если будет можно заменить на HashSet
            PaymentCard currentMappedPaymentCard;
            while(mappedResultSet.next())
            {
                currentMappedPaymentCard = this.resultSetRowMapperToPaymentCard.mapCurrentRow(mappedResultSet);
                mappedPaymentCards.add(currentMappedPaymentCard);
            }
            return mappedPaymentCards;
        }
        catch(final SQLException | ResultSetRowMappingToEntityException cause)
        {
            throw new ResultSetMappingToCollectionException(cause);
        }
    }

    private static final class ResultSetRowMapperToPaymentCard
            implements ResultSetRowMapperToEntity<PaymentCard>
    {
        private final Cryptographer<String, String> cryptographer;
        private final DAO<Client> clientDAO;

        public ResultSetRowMapperToPaymentCard()
        {
            super();
            this.cryptographer = StringToStringCryptographer.createCryptographer();
            this.clientDAO = ClientDAO.createClientDAO();
        }

        @Override
        public final PaymentCard mapCurrentRow(final ResultSet resultSetOfMappedRow)
                throws ResultSetRowMappingToEntityException
        {
            try
            {
                final long id = resultSetOfMappedRow.getLong(NAME_OF_COLUMN_OF_ID.getValue());
                final String cardNumber = resultSetOfMappedRow.getString(NAME_OF_COLUMN_OF_CARD_NUMBER.getValue());

                final int numberOfMonthOfExpirationDate = resultSetOfMappedRow.getInt(
                        NAME_OF_COLUMN_OF_NUMBER_OF_MONTH_OF_EXPIRATION_DATE.getValue());
                final Month monthOfExpirationDate = Month.of(numberOfMonthOfExpirationDate);
                final int valueOfYearOfExpirationDate = resultSetOfMappedRow.getInt(
                        NAME_OF_COLUMN_OF_YEAR_OF_EXPIRATION_DATE.getValue());
                final Year yearOfExpirationDate = Year.of(valueOfYearOfExpirationDate);
                final PaymentCard.ExpirationDate expirationDate = new PaymentCard.ExpirationDate(
                        monthOfExpirationDate, yearOfExpirationDate);

                final String paymentSystem = resultSetOfMappedRow.getString(NAME_OF_COLUMN_OF_PAYMENT_SYSTEM.getValue());

                final String encryptedCvc = resultSetOfMappedRow.getString(NAME_OF_COLUMN_OF_ENCRYPTED_CVC.getValue());
                final String cvc = this.cryptographer.decrypt(encryptedCvc);

                final long idOfClient = resultSetOfMappedRow.getLong(NAME_OF_COLUMN_OF_CLIENT_ID.getValue());
                final Optional<Client> optionalOfClient = this.clientDAO.findEntityById(idOfClient);
                if(optionalOfClient.isEmpty())
                {
                    throw new ResultSetRowMappingToEntityException("Client with id = " + idOfClient
                            + " doesn't exist.");
                }
                final Client client = optionalOfClient.get();

                final String nameOfBank = resultSetOfMappedRow.getString(NAME_OF_COLUMN_OF_NAME_OF_BANK.getValue());

                final String encryptedPassword = resultSetOfMappedRow.getString(
                        NAME_OF_COLUMN_OF_ENCRYPTED_PASSWORD.getValue());
                final String password = this.cryptographer.decrypt(encryptedPassword);

                return new PaymentCard(id, cardNumber, expirationDate, paymentSystem, cvc, client, nameOfBank, password);
            }
            catch(final SQLException | FindingEntityException cause)
            {
                throw new ResultSetRowMappingToEntityException(cause);
            }
        }
    }
}
