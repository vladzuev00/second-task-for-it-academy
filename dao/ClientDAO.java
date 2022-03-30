package by.itacademy.zuevvlad.cardpaymentproject.dao;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.*;
import by.itacademy.zuevvlad.cardpaymentproject.dao.databaseconnectionpool.DataBaseConnectionPool;
import by.itacademy.zuevvlad.cardpaymentproject.dao.databaseconnectionpool.exception.DataBaseConnectionPoolAccessConnectionException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.foundergeneratedidbydatabase.FounderGeneratedIdByDataBase;
import by.itacademy.zuevvlad.cardpaymentproject.dao.foundergeneratedidbydatabase.exception.FindingGeneratedIdByDataBaseException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.resultsetmappertocollection.ResultSetMapperToCollection;
import by.itacademy.zuevvlad.cardpaymentproject.dao.resultsetmappertocollection.ResultSetMapperToClients;
import by.itacademy.zuevvlad.cardpaymentproject.dao.resultsetmappertocollection.exception.ResultSetMappingToCollectionException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.resultsetmappertocollection.exception.ResultSetRowMappingToEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.sqloperation.ClientSqlOperation;
import by.itacademy.zuevvlad.cardpaymentproject.dao.sqloperation.UserSqlOperation;
import by.itacademy.zuevvlad.cardpaymentproject.dao.tableproperty.UserTableProperty;
import by.itacademy.zuevvlad.cardpaymentproject.entity.BankAccount;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Client;
import by.itacademy.zuevvlad.cardpaymentproject.dao.cryptographer.Cryptographer;
import by.itacademy.zuevvlad.cardpaymentproject.dao.cryptographer.StringToStringCryptographer;
import by.itacademy.zuevvlad.cardpaymentproject.entity.User;

import java.sql.*;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static by.itacademy.zuevvlad.cardpaymentproject.dao.sqloperation.ClientSqlOperation.*;
import static by.itacademy.zuevvlad.cardpaymentproject.dao.sqloperation.UserSqlOperation.PREPARED_STATEMENT_TO_UPDATE_USER;
import static by.itacademy.zuevvlad.cardpaymentproject.dao.sqloperation.UserSqlOperation.PARAMETER_INDEX_OF_EMAIL_IN_PREPARED_STATEMENT_TO_UPDATE;
import static by.itacademy.zuevvlad.cardpaymentproject.dao.sqloperation.UserSqlOperation.PARAMETER_INDEX_OF_ENCRYPTED_PASSWORD_IN_PREPARED_STATEMENT_TO_UPDATE;
import static by.itacademy.zuevvlad.cardpaymentproject.dao.sqloperation.UserSqlOperation.PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_UPDATE;

public final class ClientDAO implements DAO<Client>
{
    private final DataBaseConnectionPool dataBaseConnectionPool;
    private final Cryptographer<String, String> cryptographer;
    private final ResultSetMapperToCollection<Client, Set<Client>> resultSetMapperToClients;
    private final ResultSetMapperToCollection.ResultSetRowMapperToEntity<Client> resultSetRowMapperToClient;
    private final DAO<User> userDAO;

    public static ClientDAO createClientDAO()
    {
        if(ClientDAO.clientDAO == null)
        {
            synchronized(ClientDAO.class)
            {
                if(ClientDAO.clientDAO == null)
                {
                    ClientDAO.clientDAO = new ClientDAO();
                }
            }
        }
        return ClientDAO.clientDAO;
    }

    private static ClientDAO clientDAO = null;

    private ClientDAO()
    {
        super();
        this.dataBaseConnectionPool = DataBaseConnectionPool.createDataBaseConnectionPool();
        this.cryptographer = StringToStringCryptographer.createCryptographer();
        this.resultSetMapperToClients = ResultSetMapperToClients.createResultSetMapperToClients();
        this.resultSetRowMapperToClient = this.resultSetMapperToClients.getResultSetRowMapperToEntity();
        this.userDAO = UserDAO.createUserDAO();
    }

    @Override
    public final void addEntity(final Client addedClient)
            throws AddingEntityException
    {
        try
        {
            final Connection connectionToDataBase = this.dataBaseConnectionPool.findAvailableConnection();
            try
            {
                connectionToDataBase.setAutoCommit(false);
                try
                {
                    this.addInTableOfUsers(addedClient, connectionToDataBase);
                    this.addInTableOfClients(addedClient, connectionToDataBase);
                    connectionToDataBase.commit();
                }
                catch(final AddingEntityException exception)
                {
                    connectionToDataBase.rollback();
                    throw exception;
                }
            }
            finally
            {
                if(!connectionToDataBase.getAutoCommit())
                {
                    connectionToDataBase.setAutoCommit(true);
                }
                this.dataBaseConnectionPool.returnConnectionToPool(connectionToDataBase);
            }
        }
        catch(final DataBaseConnectionPoolAccessConnectionException | SQLException cause)
        {
            throw new AddingEntityException(cause);
        }
    }

    private void addInTableOfUsers(final Client addedClient, final Connection connectionToDataBase)
            throws AddingEntityException
    {
        try(final PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(
                UserSqlOperation.PREPARED_STATEMENT_TO_INSERT_USER.getSqlQuery(), Statement.RETURN_GENERATED_KEYS))
        {
            preparedStatement.setString(PARAMETER_INDEX_OF_EMAIL_IN_PREPARED_STATEMENT_TO_INSERT_USER,
                    addedClient.getEmail());

            final String encryptedPassword = this.cryptographer.encrypt(addedClient.getPassword());
            preparedStatement.setString(PARAMETER_INDEX_OF_ENCRYPTED_PASSWORD_IN_PREPARED_STATEMENT_TO_INSERT_USER,
                    encryptedPassword);

            preparedStatement.executeUpdate();

            final FounderGeneratedIdByDataBase founderGeneratedIdByDataBase
                    = FounderGeneratedIdByDataBase.createFounderGeneratedIdByDataBase();
            final long generatedId = founderGeneratedIdByDataBase.findGeneratedIdInLastInserting(preparedStatement,
                    UserTableProperty.NAME_OF_COLUMN_OF_ID.getValue());
            addedClient.setId(generatedId);
        }
        catch(final SQLException | FindingGeneratedIdByDataBaseException cause)
        {
            throw new AddingEntityException(cause);
        }
    }

    private void addInTableOfClients(final Client addedClient, final Connection connectionToDataBase)
            throws AddingEntityException
    {
        try(final PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(
                PREPARED_STATEMENT_TO_INSERT_CLIENT.getSqlQuery()))
        {
            preparedStatement.setString(PARAMETER_INDEX_OF_NAME_IN_PREPARED_STATEMENT_TO_INSERT_CLIENT,
                    addedClient.getName());
            preparedStatement.setString(PARAMETER_INDEX_OF_SURNAME_IN_PREPARED_STATEMENT_TO_INSERT_CLIENT,
                    addedClient.getSurname());
            preparedStatement.setString(PARAMETER_INDEX_OF_PATRONYMIC_IN_PREPARED_STATEMENT_TO_INSERT_CLIENT,
                    addedClient.getPatronymic());
            preparedStatement.setString(PARAMETER_INDEX_OF_PHONE_NUMBER_IN_PREPARED_STATEMENT_TO_INSERT_CLIENT,
                    addedClient.getPhoneNumber());
            preparedStatement.setLong(PARAMETER_INDEX_OF_BANK_ACCOUNT_ID_IN_PREPARED_STATEMENT_TO_INSERT_CLIENT,
                    addedClient.getBankAccount().getId());
            preparedStatement.setLong(PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_INSERT_CLIENT,
                    addedClient.getId());

            preparedStatement.executeUpdate();
        }
        catch(final SQLException cause)
        {
            throw new AddingEntityException(cause);
        }
    }

    @Override
    public final Collection<Client> offloadEntities()
            throws OffloadingEntitiesException
    {
        try
        {
            final Connection connectionToDataBase = this.dataBaseConnectionPool.findAvailableConnection();
            try(final Statement statement = connectionToDataBase.createStatement())
            {
                final ResultSet resultSet = statement.executeQuery(OFFLOAD_ALL_CLIENTS.getSqlQuery());
                return this.resultSetMapperToClients.map(resultSet);
            }
            finally
            {
                this.dataBaseConnectionPool.returnConnectionToPool(connectionToDataBase);
            }
        }
        catch(final DataBaseConnectionPoolAccessConnectionException | SQLException
                | ResultSetMappingToCollectionException cause)
        {
            throw new OffloadingEntitiesException(cause);
        }
    }

    @Override
    public final Optional<Client> findEntityById(final long idOfFoundClient)
            throws FindingEntityException
    {
        try
        {
            final Connection connectionToDataBase = this.dataBaseConnectionPool.findAvailableConnection();
            try(final PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(
                    PREPARED_STATEMENT_TO_SELECT_CLIENT_BY_ID.getSqlQuery()))
            {
                preparedStatement.setLong(PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_SELECT_BY_ID, idOfFoundClient);

                final ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next() ? Optional.of(this.resultSetRowMapperToClient.mapCurrentRow(resultSet))
                        : Optional.empty();
            }
            finally
            {
                this.dataBaseConnectionPool.returnConnectionToPool(connectionToDataBase);
            }
        }
        catch(final DataBaseConnectionPoolAccessConnectionException | SQLException
                | ResultSetRowMappingToEntityException cause)
        {
            throw new FindingEntityException(cause);
        }
    }

    @Override
    public final void updateEntity(final Client updatedClient)
            throws UpdatingEntityException
    {
        try
        {
            final Connection connectionToDataBase = this.dataBaseConnectionPool.findAvailableConnection();
            try
            {
                connectionToDataBase.setAutoCommit(false);
                try
                {
                    this.updateInTableOfUsers(updatedClient, connectionToDataBase);
                    this.updateInTableOfClients(updatedClient, connectionToDataBase);
                    connectionToDataBase.commit();
                }
                catch(final UpdatingEntityException exception)
                {
                    connectionToDataBase.rollback();
                    throw exception;
                }
            }
            finally
            {
                if(!connectionToDataBase.getAutoCommit())
                {
                    connectionToDataBase.setAutoCommit(true);
                }
                this.dataBaseConnectionPool.returnConnectionToPool(connectionToDataBase);
            }
        }
        catch(final DataBaseConnectionPoolAccessConnectionException | SQLException cause)
        {
            throw new UpdatingEntityException(cause);
        }
    }

    private void updateInTableOfUsers(final Client updatedClient, final Connection connectionToDataBase)
            throws UpdatingEntityException
    {
        try(final PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(
                PREPARED_STATEMENT_TO_UPDATE_USER.getSqlQuery()))
        {
            preparedStatement.setString(PARAMETER_INDEX_OF_EMAIL_IN_PREPARED_STATEMENT_TO_UPDATE,
                    updatedClient.getEmail());

            final String encryptedPassword = this.cryptographer.encrypt(updatedClient.getPassword());
            preparedStatement.setString(PARAMETER_INDEX_OF_ENCRYPTED_PASSWORD_IN_PREPARED_STATEMENT_TO_UPDATE,
                    encryptedPassword);

            preparedStatement.setLong(PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_UPDATE, updatedClient.getId());

            preparedStatement.executeUpdate();
        }
        catch(final SQLException cause)
        {
            throw new UpdatingEntityException(cause);
        }
    }

    private void updateInTableOfClients(final Client updatedClient, final Connection connectionToDataBase)
            throws UpdatingEntityException
    {
        try(final PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(
                PREPARED_STATEMENT_TO_UPDATE_CLIENT.getSqlQuery()))
        {
            preparedStatement.setString(PARAMETER_INDEX_OF_NAME_IN_PREPARED_STATEMENT_TO_UPDATE,
                    updatedClient.getName());
            preparedStatement.setString(PARAMETER_INDEX_OF_SURNAME_IN_PREPARED_STATEMENT_TO_UPDATE,
                    updatedClient.getSurname());
            preparedStatement.setString(PARAMETER_INDEX_OF_PATRONYMIC_IN_PREPARED_STATEMENT_TO_UPDATE,
                    updatedClient.getPatronymic());
            preparedStatement.setString(PARAMETER_INDEX_OF_PHONE_NUMBER_IN_PREPARED_STATEMENT_TO_UPDATE,
                    updatedClient.getPhoneNumber());

            final BankAccount bankAccount = updatedClient.getBankAccount();
            preparedStatement.setLong(PARAMETER_INDEX_OF_BANK_ACCOUNT_ID_IN_PREPARED_STATEMENT_TO_UPDATE,
                    bankAccount.getId());
            preparedStatement.setLong(ClientSqlOperation.PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_UPDATE,
                    updatedClient.getId());

            preparedStatement.executeUpdate();
        }
        catch(final SQLException cause)
        {
            throw new UpdatingEntityException(cause);
        }
    }

    @Override
    public final void deleteEntityById(final long idOfDeletedClient)
            throws DeletingEntityException
    {
        this.userDAO.deleteEntityById(idOfDeletedClient);     //cascade deleting
    }

    @Override
    public final boolean isEntityWithGivenIdExisting(final long idOfResearchClient)
            throws DefiningExistingEntityException
    {
        try
        {
            final Connection connectionToDataBase = this.dataBaseConnectionPool.findAvailableConnection();
            try(final PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(
                    PREPARED_STATEMENT_TO_DEFINE_EXISTING_BY_ID.getSqlQuery()))
            {
                preparedStatement.setLong(PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_DEFINE_EXISTING,
                        idOfResearchClient);
                final ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next();
            }
            finally
            {
                this.dataBaseConnectionPool.returnConnectionToPool(connectionToDataBase);
            }
        }
        catch(final DataBaseConnectionPoolAccessConnectionException | SQLException cause)
        {
            throw new DefiningExistingEntityException(cause);
        }
    }

    public final Optional<Client> findClientByGivenEmail(final String emailOfFoundClient)
            throws FindingEntityException
    {
        try
        {
            final Connection connectionToDataBase = this.dataBaseConnectionPool.findAvailableConnection();
            try(final PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(
                    PREPARED_STATEMENT_TO_FIND_CLIENT_BY_EMAIL.getSqlQuery()))
            {
                preparedStatement.setString(PARAMETER_INDEX_OF_EMAIL_IN_PREPARED_STATEMENT_TO_SELECT_BY_EMAIL,
                        emailOfFoundClient);
                final ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next() ? Optional.of(this.resultSetRowMapperToClient.mapCurrentRow(resultSet))
                        : Optional.empty();
            }
            finally
            {
                this.dataBaseConnectionPool.returnConnectionToPool(connectionToDataBase);
            }
        }
        catch(final DataBaseConnectionPoolAccessConnectionException | SQLException
                | ResultSetRowMappingToEntityException cause)
        {
            throw new FindingEntityException(cause);
        }
    }

    public final Optional<Client> findClientByGivenPhoneNumber(final String phoneNumberOfFoundClient)
            throws FindingEntityException
    {
        try
        {
            final Connection connectionToDateBase = this.dataBaseConnectionPool.findAvailableConnection();
            try(final PreparedStatement preparedStatement = connectionToDateBase.prepareStatement(
                    PREPARED_STATEMENT_TO_FIND_CLIENT_BY_PHONE_NUMBER.getSqlQuery()))
            {
                preparedStatement.setString(
                        PARAMETER_INDEX_OF_PHONE_NUMBER_IN_PREPARED_STATEMENT_TO_SELECT_BY_PHONE_NUMBER,
                        phoneNumberOfFoundClient);
                final ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next() ? Optional.of(this.resultSetRowMapperToClient.mapCurrentRow(resultSet))
                        : Optional.empty();
            }
            finally
            {
                this.dataBaseConnectionPool.returnConnectionToPool(connectionToDateBase);
            }
        }
        catch(final DataBaseConnectionPoolAccessConnectionException | SQLException
                | ResultSetRowMappingToEntityException cause)
        {
            throw new FindingEntityException(cause);
        }
    }
}
