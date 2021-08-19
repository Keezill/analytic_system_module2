package utils;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.DriverManager;
import java.sql.SQLException;

public class LiquibaseUtil {
    private final String url = "jdbc:postgresql://ec2-18-211-41-246.compute-1.amazonaws.com:5432/d1toalp4e4fve8?password=c2c2c0f7a7010d82b43c2b255ebdfd8b84986c901d5f7f3bede3653f62352218";
    private final String user = "bxmpmdakspjxay";
    private final String password = "c2c2c0f7a7010d82b43c2b255ebdfd8b84986c901d5f7f3bede3653f62352218";

    public void liquibaseStart() {
        java.sql.Connection c = null;
        Database database = null;
        try {
            c = DriverManager.getConnection(url, user, password);
            database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(c));
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException | DatabaseException e) {
            e.printStackTrace();
        }
        Liquibase liquibase = null;

        try {
            liquibase = new Liquibase("liquibase/dbchangelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (LiquibaseException e) {
            try {
                throw new DatabaseException(e);
            } catch (DatabaseException databaseException) {
                databaseException.printStackTrace();
            }
        } finally {
            if (c != null) {
                try {
                    c.rollback();
                    c.close();
                } catch (SQLException e) {
                    //nothing to do
                }
            }
        }
    }
}
