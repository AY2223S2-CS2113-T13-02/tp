package storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.MalformedJsonException;
import data.Expense;
import data.ExpenseList;
import utils.GsonLocalDateAdaptor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Objects;

import static common.MessageList.PERIOD;
import static common.MessageList.WHITESPACE;

public class Storage {

    private static final String READ_STORAGE_SUCCESSFUL = "All past expenses retrieved successfully!";
    private static final String READ_EXPENSELIST_ERROR = "Error reading expense list.";
    private static final String WRITE_TO_EXPENSELIST_ERROR = "Error writing to expense list.";
    private static final String NEW_EXPENSE = "New expense list created.";
    private static final String WRITING_TO_FILE_ERROR = "Error writing to account file";
    private static final String INITIAL_WELCOME_MESSAGE = "Welcome to Duke!";
    private static final String SUBSEQUENT_WELCOME_MESSAGE = "Welcome back!";
    private static final String DATA_CORRUPTED_ERROR = "Data file corrupted. " +
            "Save the remaining data to another location before deleting the current data file. " +
            "Restart the programme after deleting the corrupted data file to proceed.";
    private static final String CREATE_FILE_ERROR = "Error creating file.";
    private static final String MORE_DP_ERROR = "More than 2 decimal places detected for Expense ";
    private static final String LESS_DP_ERROR = "Less than 2 decimal places detected for Expense ";
    private static final String ROUND_UP_WARNING = "Expense amount is rounded back to 2 decimal points by default.";

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new GsonLocalDateAdaptor())
            .create();

    private ExpenseList expenseList;


    public Storage(ExpenseList expenseList) {
        this.expenseList = expenseList;
    }

    /**
     * Saves expenses to json file as json object.
     *
     * @param filePath Path at which the json file is stored.
     */
    public void saveExpenses(String filePath) {
        try {
            File file = new File(filePath);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(GSON.toJson(expenseList.getExpenseList()));
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(WRITING_TO_FILE_ERROR);
        }
    }


    /**
     * Creates JSON file if it does not exist with user specified file path.
     *
     * @param filePath Path at which the json file is stored.
     */
    public void createFile(String filePath) {
        try {
            File f = new File(filePath);
            if (f.createNewFile()) {
                // first time that the programme is being run, update welcome message later on
                //System.out.println(INITIAL_WELCOME_MESSAGE);
            } else {
                //System.out.println(SUBSEQUENT_WELCOME_MESSAGE);
            }
        } catch (IOException e) {
            System.out.println(CREATE_FILE_ERROR);
        }
    }

    //TODO: JSON

    /**
     * Loads expenses from json file and save as an ArrayList of Expenses
     *
     * @param filePath Path at which the json file is stored.
     * @return An arraylist of expenses.
     * @throws NullPointerException if json file is empty.
     */

    public ExpenseList loadExpenses(String filePath) throws NullPointerException {
        ExpenseList expenseList = new ExpenseList();
        createFile(filePath);
        try {
            Reader reader = Files.newBufferedReader(Paths.get(filePath));
            Type typeOfObject = new TypeToken<ArrayList<Expense>>() {
            }.getType();
            ArrayList<Expense> expenses = GSON.fromJson(reader, typeOfObject);
            expenseList.setExpenseList(expenses);
        } catch (JsonSyntaxException | MalformedJsonException | DateTimeParseException e) {
            System.out.println(DATA_CORRUPTED_ERROR);
            System.exit(0);
        } catch (IOException e) {
            System.out.println(CREATE_FILE_ERROR);
        }
        if (expenseList.getExpenseList() != null) {
            checkValidExpenseList(expenseList.getExpenseList(), filePath);
        }
        return Objects.requireNonNullElseGet(expenseList, ExpenseList::new);
    }

    private void checkValidExpenseList(ArrayList<Expense> expenses, String filePath) {
        int index = 0;
        for (Expense expense : expenses) {
            try {
                index++;
                expense.toString();
                if (expense.getDescription() == null | expense.getCurrencyType() == null | expense.getRate() == null) {
                    throw new NullPointerException();
                }
                if (expense.getExpenseAmount().scale() > 2) {
                    System.out.println(MORE_DP_ERROR + index + PERIOD + WHITESPACE + ROUND_UP_WARNING);
                    expense.setExpenseAmount(expense.getExpenseAmount().setScale(2, RoundingMode.HALF_UP));
                    expenseList.setExpenseList(expenses);
                    saveExpenses(filePath);
                } else if (expense.getExpenseAmount().scale() < 2) {
                    System.out.println(LESS_DP_ERROR + index + PERIOD + WHITESPACE + ROUND_UP_WARNING);
                    expense.setExpenseAmount(expense.getExpenseAmount().setScale(2, RoundingMode.HALF_UP));
                    expenseList.setExpenseList(expenses);
                    saveExpenses(filePath);
                }
            } catch (NullPointerException e) {
                System.out.println("Expense " + index + " corrupted.");
                System.out.println("Edit Expense " + index + " in data file to fix this error. " +
                        "If problem persists, delete the current data file and restart the programme.");
                System.exit(0);
            }
        }
    }

    public ExpenseList getExpenseList() {
        return expenseList;
    }
}

