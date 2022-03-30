package by.itacademy.zuevvlad.cardpaymentproject.dao;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.*;
import by.itacademy.zuevvlad.cardpaymentproject.dao.databaseconnectionpool.DataBaseConnectionPool;
import by.itacademy.zuevvlad.cardpaymentproject.dao.databaseconnectionpool.exception.DataBaseConnectionPoolAccessConnectionException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.foundergeneratedidbydatabase.FounderGeneratedIdByDataBase;
import by.itacademy.zuevvlad.cardpaymentproject.dao.foundergeneratedidbydatabase.exception.FindingGeneratedIdByDataBaseException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.resultsetmappertocollection.ResultSetMapperToCollection;
import by.itacademy.zuevvlad.cardpaymentproject.dao.resultsetmappertocollection.ResultSetMapperToAdministrators;
import by.itacademy.zuevvlad.cardpaymentproject.dao.resultsetmappertocollection.exception.ResultSetMappingToCollectionException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.resultsetmappertocollection.exception.ResultSetRowMappingToEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.sqloperation.AdministratorSqlOperation;
import by.itacademy.zuevvlad.cardpaymentproject.dao.sqloperation.UserSqlOperation;
import by.itacademy.zuevvlad.cardpaymentproject.dao.tableproperty.UserTableProperty;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Administrator;
import by.itacademy.zuevvlad.cardpaymentproject.dao.cryptographer.Cryptographer;
import by.itacademy.zuevvlad.cardpaymentproject.dao.cryptographer.StringToStringCryptographer;
import by.itacademy.zuevvlad.cardpaymentproject.entity.User;

import java.sql.*;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static by.itacademy.zuevvlad.cardpaymentproject.dao.sqloperation.AdministratorSqlOperation.*;
import static by.itacademy.zuevvlad.cardpaymentproject.dao.sqloperation.UserSqlOperation.*;

public final class AdministratorDAO implements DAO<Administrator>
{
    private final DataBaseConnectionPool dataBaseConnectionPool;
    private final ResultSetMapperToCollection<Administrator, Set<Administrator>> resultSetMapperToAdministrators;
    private final ResultSetMapperToCollection.ResultSetRowMapperToEntity<Administrator> resultSetRowMapperToAdministrator;
    private final Cryptographer<String, String> cryptographer;
    private final FounderGeneratedIdByDataBase founderGeneratedIdByDataBase;
    private final DAO<User> userDAO;

    public static AdministratorDAO createAdministratorDAO()
    {
        if(AdministratorDAO.administratorDAO == null)
        {
            synchronized(AdministratorDAO.class)
            {
                if(AdministratorDAO.administratorDAO == null)
                {
                    AdministratorDAO.administratorDAO = new AdministratorDAO();
                }
            }
        }
        return AdministratorDAO.administratorDAO;
    }

    private static AdministratorDAO administratorDAO = null;

    private AdministratorDAO()
    {
        super();

        this.dataBaseConnectionPool = DataBaseConnectionPool.createDataBaseConnectionPool();
        this.resultSetMapperToAdministrators = ResultSetMapperToAdministrators.createResultSetMapperToAdministrators();
        this.resultSetRowMapperToAdministrator = this.resultSetMapperToAdministrators.getResultSetRowMapperToEntity();
        this.cryptographer = StringToStringCryptographer.createCryptographer();
        this.founderGeneratedIdByDataBase = FounderGeneratedIdByDataBase.createFounderGeneratedIdByDataBase();
        this.userDAO = UserDAO.createUserDAO();
    }

    @Override
    public final void addEntity(final Administrator addedAdministrator)
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
                    this.addInTableOfUsers(addedAdministrator, connectionToDataBase);
                    this.addInTableOfAdministrators(addedAdministrator, connectionToDataBase);
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

    private void addInTableOfUsers(final Administrator addedAdministrator, final Connection connectionToDataBase)
            throws AddingEntityException
    {
        try(final PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(
                PREPARED_STATEMENT_TO_INSERT_USER.getSqlQuery(), Statement.RETURN_GENERATED_KEYS))
        {
            preparedStatement.setString(PARAMETER_INDEX_OF_EMAIL_IN_PREPARED_STATEMENT_TO_INSERT,
                    addedAdministrator.getEmail());

            final String encryptedPassword = this.cryptographer.encrypt(addedAdministrator.getPassword());
            preparedStatement.setString(PARAMETER_INDEX_OF_ENCRYPTED_PASSWORD_IN_PREPARED_STATEMENT_TO_INSERT,
                    encryptedPassword);

            preparedStatement.executeUpdate();

            final long generatedId = this.founderGeneratedIdByDataBase.findGeneratedIdInLastInserting(preparedStatement,
                    UserTableProperty.NAME_OF_COLUMN_OF_ID.getValue());
            addedAdministrator.setId(generatedId);
        }
        catch(final SQLException | FindingGeneratedIdByDataBaseException cause)
        {
            throw new AddingEntityException(cause);
        }
    }

    private void addInTableOfAdministrators(final Administrator addedAdministrator,
                                            final Connection connectionToDataBase)
            throws AddingEntityException
    {
        try(final PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(
                PREPARED_STATEMENT_TO_INSERT_ADMINISTRATOR.getSqlQuery()))
        {
            preparedStatement.setLong(PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_INSERT, addedAdministrator.getId());

            final Administrator.Level level = addedAdministrator.getLevel();
            preparedStatement.setString(PARAMETER_INDEX_OF_LEVEL_IN_PREPARED_STATEMENT_TO_INSERT, level.name());

            preparedStatement.executeUpdate();
        }
        catch(final SQLException cause)
        {
            throw new AddingEntityException(cause);
        }
    }

    @Override
    public final Collection<Administrator> offloadEntities()
            throws OffloadingEntitiesException
    {
        try
        {
            final Connection connectionToDataBase = this.dataBaseConnectionPool.findAvailableConnection();
            try(final Statement statement = connectionToDataBase.createStatement())
            {
                final ResultSet resultSet = statement.executeQuery(OFFLOAD_ALL_ADMINISTRATORS.getSqlQuery());
                return this.resultSetMapperToAdministrators.map(resultSet);
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
    public final Optional<Administrator> findEntityById(final long idOfFoundAdministrator)
            throws FindingEntityException
    {
        try
        {
            final Connection connectionToDataBase = this.dataBaseConnectionPool.findAvailableConnection();
            try(final PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(
                    PREPARED_STATEMENT_TO_SELECT_ADMINISTRATOR_BY_ID.getSqlQuery()))
            {
                preparedStatement.setLong(
                        AdministratorSqlOperation.PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_SELECT_BY_ID,
                        idOfFoundAdministrator);
                final ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next() ? Optional.of(this.resultSetRowMapperToAdministrator.mapCurrentRow(resultSet))
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
    public final void updateEntity(final Administrator updatedAdministrator)
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
                    this.updateInTableOfUsers(updatedAdministrator, connectionToDataBase);
                    this.updateInTableOfAdministrators(updatedAdministrator, connectionToDataBase);
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

    private void updateInTableOfUsers(final Administrator updatedAdministrator, final Connection connectionToDataBase)
            throws UpdatingEntityException
    {
        try(final PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(
                PREPARED_STATEMENT_TO_UPDATE_USER.getSqlQuery()))
        {
            preparedStatement.setString(PARAMETER_INDEX_OF_EMAIL_IN_PREPARED_STATEMENT_TO_UPDATE,
                    updatedAdministrator.getEmail());

            final String encryptedPassword = this.cryptographer.encrypt(updatedAdministrator.getPassword());
            preparedStatement.setString(PARAMETER_INDEX_OF_ENCRYPTED_PASSWORD_IN_PREPARED_STATEMENT_TO_UPDATE,
                    encryptedPassword);

            preparedStatement.setLong(UserSqlOperation.PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_UPDATE,
                    updatedAdministrator.getId());

            preparedStatement.executeUpdate();
        }
        catch(final SQLException cause)
        {
            throw new UpdatingEntityException(cause);
        }
    }

    private void updateInTableOfAdministrators(final Administrator updatedAdministrator,
                                               final Connection connectionToDataBase)
            throws UpdatingEntityException
    {
        try(final PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(
                AdministratorSqlOperation.PREPARED_STATEMENT_TO_UPDATE_ADMINISTRATOR.getSqlQuery()))
        {
            final Administrator.Level level = updatedAdministrator.getLevel();
            preparedStatement.setString(
                    AdministratorSqlOperation.PARAMETER_INDEX_OF_LEVEL_IN_PREPARED_STATEMENT_TO_UPDATE, level.name());

            preparedStatement.setLong(AdministratorSqlOperation.PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_UPDATE,
                    updatedAdministrator.getId());

            preparedStatement.executeUpdate();
        }
        catch(final SQLException cause)
        {
            throw new UpdatingEntityException(cause);
        }
    }

    @Override
    public final void deleteEntityById(final long idOfDeletedAdministrator)
            throws DeletingEntityException
    {
        this.userDAO.deleteEntityById(idOfDeletedAdministrator);   //cascade deleting
    }

    @Override
    public final boolean isEntityWithGivenIdExisting(final long idOfResearchAdministrator)
            throws DefiningExistingEntityException
    {
        try
        {
            final Connection connectionToDataBase = this.dataBaseConnectionPool.findAvailableConnection();
            try(final PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(
                    AdministratorSqlOperation.PREPARED_STATEMENT_TO_DEFINE_EXISTING_BY_ID.getSqlQuery()))
            {
                preparedStatement.setLong(
                        AdministratorSqlOperation.PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_DEFINE_EXISTING,
                        idOfResearchAdministrator);

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

    public final Optional<Administrator> findAdministratorByGivenEmail(final String emailOfFoundAdministrator)
            throws FindingEntityException
    {
        try
        {
            final Connection connectionToDataBase = this.dataBaseConnectionPool.findAvailableConnection();
            try(final PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(
                    PREPARED_STATEMENT_TO_SELECT_ADMINISTRATOR_BY_EMAIL.getSqlQuery()))
            {
                preparedStatement.setString(
                        UserSqlOperation.PARAMETER_INDEX_OF_EMAIL_IN_PREPARED_STATEMENT_TO_SELECT_BY_EMAIL,
                        emailOfFoundAdministrator);
                final ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next() ? Optional.of(this.resultSetRowMapperToAdministrator.mapCurrentRow(resultSet))
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
}
