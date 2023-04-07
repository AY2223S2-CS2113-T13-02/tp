package common;

public class MessageList {
    public static final String MESSAGE_DIVIDER =
            "______________________________________________________________________________________";
    public static final String MESSAGE_DIVIDER_LIST =
            "_________________________________________LIST_________________________________________";

    public static final String MESSAGE_DIVIDER_SORTEDLIST =
            "______________________________________SORTEDLIST______________________________________";

    public static final String MESSAGE_DIVIDER_CATEGORY =
            "_______________________________________CATEGORY_______________________________________";
    public static final String MESSAGE_DIVIDER_FIND =
            "_________________________________________FIND_________________________________________";
    public static final int OFFSET = 1;

    public static final String SUCCESSFUL_DELETE = "The following expense is successfully deleted:";
    public static final String SUCCESSFUL_ADD = "The following expense is successfully added:";

    public static final String TAB = "    ";
    public static final String ACCOUNT_MESSAGE = "> Type \"login\" to start manage your expense. \n" +
            "> Haven't got your account yet? Type \"signup\" to create your account.";
    public static final String COMMAND_LIST_MESSAGE =
            "> Type \"list\" to list all the expenses. \n" +
                    "> Type \"add amt/x t/y [cat/z] [cur/t]\" with x is the amount of expense, " +
                    "y is the date \n in the form DD-MM-YYYY, z is the category of the expense, t is the currency. \n" +
                    " * Note: cat/z, cur/t are optional. \n" +
                    "   + If no currency is specified, the default currency is SGD.\n" +
                    "   + If no category is specified, the default category is \"uncategorized\".\n" +
                    "> Type \"delete\" follow by a number z to delete task z in the the expense list. \n" +
                    "> Type \"sort X\" with X as the uppercase of the first letter of the criteria \n" +
                    "> Type \"total\" to calculate your total expense. \n" +
                    "> Type \"category x\" to find all expenses of category \"x\". \n" +
                    "> Type \"find k\" to find all expenses with information of \"k\". \n" +
                    "> Type \"overview MONTH YYYY\" to generate a monthly overview of all expenses " +
                    "of month \n MONTH in year YYYY. \n" +
                    "> Type \"logout\" to log out of your account. \n" +
                    "> Type \"exit\" to exit the program.";
    public static final String SAVING_QUESTION_MESSAGE = "You are logging out. Do you want to save your expense? \n" +
            "> Type \"yes\" to save your expense \n" +
            "> If you type \"no\", a recent copy of the expense list will be available. \n" +
            "> Type \"cancel\" to cancel the logging out process and continue your work.";

    public static final String SAVING_EXIT_MESSAGE = "You are logging out. Do you want to save your expense? \n" +
            "> Type \"yes\" to save your expense \n" +
            "> If you type \"no\", a recent copy of the expense list will be available.";
    public static final String MESSAGE_CANCEL = "Note. You can continue to experience our program!";
    public static String logo = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";

    public static final String HELLO_MESSAGE = "Hello from\n" + logo + "Please Type The Command As Follow:";
    public static final String NAME_QUESTION = "What is your name?";
    public static final String WHITESPACE = " ";
    public static final String PERIOD = ".";

}
