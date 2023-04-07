package seedu.duke;

import command.CommandDelete;
import command.CommandAdd;
import command.CommandCategory;
import command.CommandList;
import command.CommandTotal;
import command.CommandSort;
import command.CommandHelp;
import command.CommandFind;

import common.WelcomeMessage;
import command.overview.CommandOverview;

import data.Account;
import data.ExpenseList;
import data.Currency;

import parser.Parser;
import storage.Storage;

import java.util.Scanner;

//import static data.Account.account;
import static common.MessageList.MESSAGE_CANCEL;
import static common.MessageList.MESSAGE_DIVIDER;
import static common.MessageList.SAVING_EXIT_MESSAGE;
import static common.MessageList.SAVING_QUESTION_MESSAGE;
import static data.ExpenseList.showToUser;
import static parser.ParserAccount.caseLogOut;
import static parser.ParserAccount.initialize;
import static parser.ParserAccount.caseExit;


public class Duke {
    protected static Storage storage;
    protected Parser parser;
    protected ExpenseList expenseList;
    protected Currency currency;
    protected Account currentUser;


    //TODO: arbitrary filePath
    //protected String filePath = "test.json";

    /**
     * Initialize Duke and instantiate parser and account objects.
     */
    public Duke() {
        parser = new Parser();
        expenseList = new ExpenseList();
        currency = new Currency();
        storage = new Storage(expenseList);
        //account.setExpenseList(storage.loadExpenses(filePath));
    }

    public void run() {
        WelcomeMessage.printWelcomeLogo();
        System.out.println("Hello! What is your name?");
        Scanner in = new Scanner(System.in);
        if (in.hasNextLine()) {
            System.out.println("Hello " + in.nextLine());
        }
        initialize(in);
        WelcomeMessage.welcomeHelper();
        String input = "";
        while (in.hasNextLine()) {
            input = in.nextLine();
            if (input.equals("exit")) {
                showToUser(MESSAGE_DIVIDER, SAVING_EXIT_MESSAGE, MESSAGE_DIVIDER);
                String res = caseExit();
                if (res.equals("yes") || res.equals("no")) {
                    break;
                }
            }
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
                showToUser(MESSAGE_DIVIDER, SAVING_QUESTION_MESSAGE, MESSAGE_DIVIDER);
                String res = caseLogOut();
                if (!res.equals("cancel")) {
                    initialize(in);
                    break;
                } else {
                    showToUser(MESSAGE_DIVIDER, MESSAGE_CANCEL, MESSAGE_DIVIDER);
                }
                break;
            case "overview":
                new CommandOverview(expenseList.getExpenseList(),
                        parser.extractMonth(input), parser.extractYear(input)).execute();
                break;
            case "find":
                // Use the same parser function as category as it also need the input string from user
                new CommandFind(expenseList.getExpenseList(), parser.extractCategory(input)).execute();
                break;
            case "help":
                new CommandHelp().execute();
                break;
            default:
                System.out.println("Unknown command.");
            }
            //storage.saveExpenses(filePath);
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
