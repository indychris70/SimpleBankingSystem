package banking;

public enum Messages {
    WELCOME_MENU_OPTIONS("1. Create an account\n" +
            "2. Log into account\n" +
            "0. Exit\n"),
    LOGGED_IN_MENU_OPTIONS("1. Balance\n" +
            "2. Add income\n" +
            "3. Do transfer\n" +
            "4. Close account\n" +
            "5. Log out\n" +
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
    PROMPT_ENTER_TRANSFER_CARD_NUMBER("Transfer\nEnter card number:"),
    PROMPT_ENTER_TRANSFER_AMOUNT("Enter how much money you want to transfer:"),
    CONFIRM_TRANSFER("Transfer %s!"),
    PROMPT_ENTER_PIN("Enter your PIN:"),
    INVALID_OPTION("The option you entered is not valid"),
    BALANCE_OPTION("Balance: %s"),
    ADD_INCOME("Enter income:"),
    CONFIRM_INCOME_ADDED("Income was added!"),
    CONFIRM_ACCOUNT_CLOSED("The account has been closed!"),
    WRONG_NUMBER_OR_PIN("Wrong card number or PIN!"),
    EXCEPTION("Exception caught: %s"),
    ERROR_TRANSFER_TO_SAME_ACCOUNT("You can't transfer money to the same account!"),
    ERROR_TRANSFER_CARD_NUMBER_NOT_VALID("Probably you made a mistake in the card number. Please try again!"),
    ERROR_TRANSFER_CARD_NOT_FOUND("Such a card does not exist."),
    INSUFFICIENT_FUNDS("Not enough money!"),
    ERROR_CARD_CREATION_FAILED("Failed to create a new card!"),
    ERROR_CLOSE_ACCOUNT_FAILED("Failed to close account!"),
    FAILED_TO_ADD_INCOME("Failed to add income!");

    String text;

    Messages(String text) {
        this.text = text;
    }

    public void print(String... strings) {
        System.out.println(String.format(text, strings));
    }
}
