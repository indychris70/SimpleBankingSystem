package banking;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import org.sqlite.SQLiteDataSource;

public class DbHelper {
    private final SQLiteDataSource dataSource = new SQLiteDataSource();

    DbHelper(String db) {
        String url = "jdbc:sqlite:" + db;
        this.dataSource.setUrl(url);
    }

    public Account getAccount(String sql) {
        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                try (ResultSet results = statement.executeQuery(sql)) {
                    if (results.next()) {
                        int id = results.getInt("id");
                        String accountNumber = results.getString("number");
                        String pin = results.getString("pin");
                        int balance = results.getInt("balance");
                        return new Account(accountNumber, pin, balance);
                    }
                } catch (SQLException e) {
                    System.out.println("ResultSet exception:");
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                System.out.println("Statement exception:");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Connection exception:");
            e.printStackTrace();
        }
        return null;
    }

    public void update(String sql) {
        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                System.out.println("Statement exception:");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Connection exception:");
            e.printStackTrace();
        }
    }
}
