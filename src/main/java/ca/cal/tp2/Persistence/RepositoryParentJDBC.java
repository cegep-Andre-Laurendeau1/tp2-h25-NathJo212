package ca.cal.tp2.Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class RepositoryParentJDBC {
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:mem:tp1-h25-NathJo212;DB_CLOSE_DELAY=-1";
    static final String USER = "sa";
    static final String PASS = "";
    static Connection conn;


    static {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement statementmt = conn.createStatement();
            String sql =  "CREATE TABLE Livre" +
                    "(id INTEGER NOT NULL, " +
                    " titre VARCHAR(255), " +
                    " anneePublication INTEGER, " +
                    " nbExemplaires INTEGER, " +
                    " auteur VARCHAR(255), " +
                    " editeur VARCHAR(255), " +
                    " nbPages INTEGER, " +
                    " PRIMARY KEY ( id ))";
            statementmt.executeUpdate(sql);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
