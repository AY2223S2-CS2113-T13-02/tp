package seedu.duke;

import command.CommandAdd;
import command.CommandCategory;
import command.CommandDelete;
import command.CommandList;
import command.CommandSort;
import command.CommandTotal;
import command.overview.CommandOverview;
import command.CommandFind;

import data.ExpenseList;
import data.Currency;
import parser.Parser;
import storage.Storage;

import java.util.Scanner;
import static common.MessageList.HELLO_MESSAGE;
import static common.MessageList.COMMAND_LIST_MESSAGE;
import static common.MessageList.MESSAGE_DIVIDER;
import static common.MessageList.NAME_QUESTION;
import static data.Account.logout;
import static data.ExpenseList.showToUser;
import static parser.ParserPassword.initialize;



public class Duke {

    protected Parser parser;
    protected ExpenseList expenseList;
    protected Currency currency;
    protected Storage storage;
    /**
     * Initialize Duke and instantiate parser and expenseList objects.
     */
    public Duke() {
        parser = new Parser();
        expenseList = new ExpenseList();
        currency = new Currency();
        storage = new Storage(expenseList);
        expenseList = storage.initialiseExpenseList();
    }

    public void run() {
        showToUser(HELLO_MESSAGE, MESSAGE_DIVIDER, COMMAND_LIST_MESSAGE, MESSAGE_DIVIDER, NAME_QUESTION);
        Scanner in = new Scanner(System.in);
        if (in.hasNextLine()) {
            System.out.println("Hello " + in.nextLine());
        }
        initialize(in);
        String input = "";
        if (in.hasNextLine()) {
            input = in.nextLine();
        }
        while (!input.equals("exit")) {
            switch (parser.extractCommandKeyword(input)) {
            case "add":
                new CommandAdd(expenseList.getExpenseList(), parser.extractAddParameters(input), currency).execute();
                break;
            case "delete":
                new CommandDelete(expenseList.getExpenseList(), parser.extractIndex(input)).execute();
                break;
            case "list":
                new CommandList(expenseList.getExpenseList()).run();
                break;
            case "total":
                new CommandTotal(expenseList.getExpenseList()).execute();
                break;
            case "sort":
                new CommandSort(expenseList.getExpenseList(), parser.extractSortBy(input)).execute();
                break;
            case "category":
                new CommandCategory(expenseList.getExpenseList(), parser.extractCategory(input)).execute();
                break;
            case "logout":
                logout();
                initialize(in);
            case "overview":
                new CommandOverview(expenseList.getExpenseList(),
                        parser.extractMonth(input), parser.extractYear(input)).execute();
                break;
            case "find":
                // Use the same parser function as category as it also need the input string from user
                new CommandFind(expenseList.getExpenseList(), parser.extractCategory(input)).execute();
                break;
            default:
                System.out.println("Unknown command.");
            }
            storage.saveExpenseList();
            input = in.nextLine();
        }
        in.close();
    }

    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        new Duke().run();
    }


}
