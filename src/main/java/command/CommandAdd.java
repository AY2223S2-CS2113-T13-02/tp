package command;

import data.Currency;
import data.Expense;
import data.ExpenseList;
import data.Time;
import parser.ParserAdd;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


import static common.MessageList.SUCCESSFUL_ADD;

public class CommandAdd extends Command {
    public static final String COMMAND_NAME = "add";
    protected ArrayList<Expense> expenseList;
    protected Currency currency;
    protected String[] parsedInput;
    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * Instantiates and references the expense list for the entry to be added to as well as the parsed input from the
     * parser.
     *
     * @param expenseList The expense list to add the entry to.
     * @param parsedInput The parsed input from the parser.
     */
    public CommandAdd(ArrayList<Expense> expenseList, String[] parsedInput, Currency currency) {
        super(COMMAND_NAME);
        this.currency = currency;
        this.expenseList = expenseList;
        this.parsedInput = parsedInput;
    }

    /**
     * Adds an entry into the ArrayList based on the parsed input provided. Currently, if the currency specified does
     * not exist, it is defaulted to SGD and a warning message is displayed.
     */
    @Override
    public CommandRes execute() {
        try {
            Time date = new Time(LocalDate.parse(parsedInput[ParserAdd.TIME_INDEX], formatter));
            Expense addedExpense = new Expense(currency.roundInput((parsedInput[ParserAdd.AMOUNT_INDEX])),
                    date, parsedInput[ParserAdd.CATEGORY_INDEX],
                    currency.convertCurrency(parsedInput[ParserAdd.CURRENCY_INDEX]));
            expenseList.add(addedExpense);
            if(!currency.checkCurrency(parsedInput[ParserAdd.CURRENCY_INDEX])) {
                System.out.println("The currency specified does not exist and is defaulted to SGD, please add a new " +
                        "currency before adding a new expense.\n");
            }
            return new CommandRes(SUCCESSFUL_ADD, addedExpense,
                    ExpenseList.getAllMessage(expenseList));
        } catch (NumberFormatException e) {
            System.out.println("Please input a valid amount.");
        } catch (NullPointerException e) {
            System.out.println("Please input both the amount and date with amt/ and t/ respectively.");
        } catch (DateTimeException e) {
            System.out.println("Invalid date. Please input the date in dd-MM-yyyy format.");
        }
        assert false;
        return null;
    }



}
