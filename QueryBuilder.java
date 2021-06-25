package banking;

public enum QueryBuilder {
    CREATE_CARD_TABLE("CREATE TABLE IF NOT EXISTS card(\n" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "number TEXT,\n" +
            "pin TEXT,\n" +
            "balance INTEGER DEFAULT 0);"
    ),
    INSERT_CARD("INSERT INTO card (number, pin, balance) VALUES ('%s', '%s', %s);"),
    SELECT_CARD_BY_NUMBER("SELECT * FROM card WHERE number = '%s';");

    private String text;

    QueryBuilder(String text) {
        this.text = text;
    }

    public String build(String... strings) {
        return String.format(text, strings);
    }
}
