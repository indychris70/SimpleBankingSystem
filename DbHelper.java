package banking;

import java.sql.*;

import org.sqlite.SQLiteDataSource;

public class DbHelper {
    enum Queries {
        CREATE_CARD_TABLE("CREATE TABLE IF NOT EXISTS card(\n" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "number TEXT,\n" +
                "pin TEXT,\n" +
                "balance INTEGER DEFAULT 0);"
        ),
        ADD_CARD("INSERT INTO card (number, pin, balance) VALUES (?, ?, ?);"),
        SELECT_CARD_BY_NUMBER("SELECT * FROM card WHERE number = ?;"),
        DELETE_ACCOUNT("DELETE FROM card WHERE number = ?;"),
        UPDATE_BALANCE("UPDATE card SET balance = ? WHERE number = ?;");

        private String text;

        Queries(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    private final SQLiteDataSource dataSource = new SQLiteDataSource();

    DbHelper(String db) {
        String url = "jdbc:sqlite:" + db;
        this.dataSource.setUrl(url);
    }

    public Account selectCardByNumber(String accountNumber) {
        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement statement = con.prepareStatement(Queries.SELECT_CARD_BY_NUMBER.getText())) {
                Savepoint savepoint = con.setSavepoint();
                statement.setString(1, accountNumber);
                try (ResultSet results = statement.executeQuery()) {
                    if (results.next()) {
                        String number = results.getString("number");
                        String pin = results.getString("pin");
                        int balance = results.getInt("balance");
                        con.commit();
                        return new Account(number, pin, balance);
                    } else {
                        con.rollback(savepoint);
                    }
                } catch (SQLException e) {
                    System.out.println("selectCardByNumber ResultSet exception:");
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                System.out.println("selectCardByNumber Statement exception:");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("selectCardByNumber Connection exception:");
            e.printStackTrace();
        }
        return null;
    }

    public void addCard(String number, String pin, int balance) {
        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement statement = con.prepareStatement(Queries.ADD_CARD.getText())) {
                Savepoint savepoint = con.setSavepoint();

                statement.setString(1, number);
                statement.setString(2, pin);
                statement.setInt(3, balance);
                int result = statement.executeUpdate();

                System.out.println(String.format("addCard result = %d", result));
                if (result != 1) {
                    Messages.ERROR_CARD_CREATION_FAILED.print();
                    con.rollback(savepoint);
                }
                con.commit();
            } catch (SQLException e) {
                System.out.println("addCard PreparedStatement exception:");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("addCard Connection exception:");
            e.printStackTrace();
        }
    }

    public void closeAccount(Account account) {
        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement statement = con.prepareStatement(Queries.DELETE_ACCOUNT.getText())) {
                Savepoint savepoint = con.setSavepoint();

                statement.setString(1, account.getNumber());
                int result = statement.executeUpdate();

                System.out.println(String.format("addCard result = %d", result));
                if (result != 1) {
                    Messages.ERROR_CLOSE_ACCOUNT_FAILED.print();
                    con.rollback(savepoint);
                }
                con.commit();
            } catch (SQLException e) {
                System.out.println("closeAccount Statement exception:");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("closeAccount Connection exception:");
            e.printStackTrace();
        }
    }

    public void createCardTable() {
        update(Queries.CREATE_CARD_TABLE.getText());
    }

    public int updateBalance(Account account, long amount) {
        int result = 0;
        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement statement = con.prepareStatement(Queries.UPDATE_BALANCE.getText())) {
                Savepoint savepoint = con.setSavepoint();

                statement.setLong(1, amount);
                statement.setString(2, account.getNumber());
                result = statement.executeUpdate();

                System.out.println(String.format("updateBalance result = %d", result));
                if (result != 1) {
                    Messages.ERROR_CLOSE_ACCOUNT_FAILED.print();
                    con.rollback(savepoint);
                }
                con.commit();
            } catch (SQLException e) {
                System.out.println("updateBalance Statement exception:");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("updateBalance Connection exception:");
            e.printStackTrace();
        }
        return result;
    }

    public boolean transferFunds(Account fromAccount, Account toAccount, long amount) {
        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement fromStatement = con.prepareStatement(Queries.UPDATE_BALANCE.getText());
                PreparedStatement toStatement = con.prepareStatement(Queries.UPDATE_BALANCE.getText())) {
                Savepoint savepoint = con.setSavepoint();

                fromStatement.setLong(1, fromAccount.getBalance() - amount);
                fromStatement.setString(2, fromAccount.getNumber());
                int fromResult = fromStatement.executeUpdate();

                toStatement.setLong(1, toAccount.getBalance() + amount);
                toStatement.setString(2, toAccount.getNumber());
                int toResult = toStatement.executeUpdate();

                boolean successfulTransfer = fromResult == 1 && toResult == 1;
                if (!successfulTransfer) {
                    System.out.println(String.format("fromResult: %d toResult: %d", fromResult, toResult));
                    con.rollback(savepoint);
                }

                con.commit();
                return successfulTransfer;
            } catch (SQLException e) {
                System.out.println("transferFunds Statement exception:");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("transferFunds Connection exception:");
            e.printStackTrace();
        }
        return false;
    }

    private void update(String sql) {
        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                System.out.println(String.format("sql: %s", sql));
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                System.out.println("update Statement exception:");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("update Connection exception:");
            e.printStackTrace();
        }
    }
}
