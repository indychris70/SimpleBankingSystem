package banking;

public enum Messages {
    WELCOME_MENU_OPTIONS("1. Create an account\n" +
            "2. Log into account\n" +
            "0. Exit\n"),
    LOGGED_IN_MENU_OPTIONS("1. Create an account\n" +
            "2. Log into account\n" +
            "0. Exit"),
    CARD_CREATED_SUCCESS("Your card has been created\n" +
            "Your card number:\n" +
            "%s\n" +
            "Your card PIN:\n" +
            "%s\n"),
    LOGIN_SUCCESS("You have successfully logged in!"),
    LOGGED_OUT_SUCCESS("You have successfully logged out!"),
    EXIT_OPTION("Bye!"),
    PROMPT_ENTER_CARD_NUMBER("Enter your card number:"),
    PROMPT_ENTER_PIN("Enter your PIN:"),
    INVALID_OPTION("The option you entered is not valid"),
    BALANCE_OPTION("Balance: %s"),
    WRONG_NUMBER_OR_PIN("Wrong card number or PIN!"),
    EXCEPTION("Exception caught: %s");

    String text;

    Messages(String text) {
        this.text = text;
    }

    public void print(String... strings) {
        System.out.println(String.format(text, strings));
    }
}
