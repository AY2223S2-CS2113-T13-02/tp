package command;

import data.Expense;
import data.Time;

import java.util.ArrayList;

import static common.MessageList.MESSAGE_DIVIDER;
import static common.MessageList.MESSAGE_DIVIDER_LIST;
import java.util.Scanner;
import java.io.InputStream;

public class CommandSort extends Command {
    public static final String COMMAND_NAME = "sort";
    protected ArrayList<Expense> expenseList;

    private ArrayList<Expense> expenseListDate = new ArrayList<>();
    private ArrayList<Expense> expenseListCategory = new ArrayList<>();

    public CommandSort(ArrayList<Expense> expenseList) {
        super(COMMAND_NAME);
        this.expenseList = expenseList;
        for (int i = 0; i < expenseList.size(); i++) {
            this.expenseListDate.add(expenseList.get(i));
            this.expenseListCategory.add(expenseList.get(i));
        }

    }

    /**
     * Method allows the user to sort their expenses list based on either categories or date
     */
    @Override
    public CommandRes execute() {
        Scanner input = new Scanner(System.in);
        if (expenseList.size() == 0) {
            System.out.println("Sorry, there are no expenses tracked currently.");
            System.out.println(MESSAGE_DIVIDER);
        } else {
            System.out.println("Please indicate you want your expenses sorted by Date/Category? ");
            System.out.println("Enter: D (represent Date); C (represent Category)");
            String sortBy = input.nextLine();

            while (!sortBy.equals("C") && !sortBy.equals("D")) {
                System.out.println(MESSAGE_DIVIDER_LIST);
                System.out.println("Please indicate the right sorted criteria.");
                System.out.println("Enter: D (represent Date); C (represent Category)");
                sortBy = input.nextLine();
            }
            System.out.println(MESSAGE_DIVIDER_LIST);
            if (sortBy.equals("C")) {
                sortByCategory();
                for (int i = 0; i < expenseListCategory.size(); i++) {
                    System.out.print((i + 1) + ".");
                    System.out.println(expenseListCategory.get(i).toString());
                }
            } else {
                sortByDate();
                for (int i = 0; i < expenseListDate.size(); i++) {
                    System.out.print((i + 1) + ".");
                    System.out.println(expenseListDate.get(i).toString());
                }
            }
            System.out.println(MESSAGE_DIVIDER);
        }
        assert false;
        return null;
    }

    private void sortByDate() {
        int size = expenseListDate.size();
        for (int i = 0; i < size; i++) {
            int minIndex = i;
            for (int j = i + 1; j < size; j++) {
                Time timeOfj = Time.toTime(expenseListDate.get(j).getExpenseTime());
                Time timeOfMinIndex = Time.toTime(expenseListDate.get(minIndex).getExpenseTime());
                if (timeOfj.compareTo(timeOfMinIndex) < 0) {
                    minIndex = j;
                }
            }
            Expense tempExpense = new Expense(expenseListDate.get(i).getExpenseAmount(),
                    Time.toTime(expenseListDate.get(i).getExpenseTime()),
                    expenseListDate.get(i).getDescription(),
                    expenseListDate.get(i).getCurrencyType());
            Expense minIndexExpense = new Expense(expenseListDate.get(minIndex).getExpenseAmount(),
                    Time.toTime(expenseListDate.get(minIndex).getExpenseTime()),
                    expenseListDate.get(minIndex).getDescription(),
                    expenseListDate.get(minIndex).getCurrencyType());

            expenseListDate.set(i, minIndexExpense);
            expenseListDate.set(minIndex, tempExpense);
        }
    }

    private void sortByCategory() {
        int size = expenseListCategory.size();
        for (int i = 0; i < size; i++) {
            int minIndex = i;
            for (int j = i + 1; j < size; j++) {
                String categoryOfj = expenseListCategory.get(j).getDescription();
                String categoryOfMinIndex = expenseListCategory.get(minIndex).getDescription();
                if (categoryOfj.compareTo(categoryOfMinIndex) < 0) {
                    minIndex = j;
                }
            }
            Expense tempExpense = new Expense(expenseListCategory.get(i).getExpenseAmount(),
                    Time.toTime(expenseListCategory.get(i).getExpenseTime()),
                    expenseListCategory.get(i).getDescription(),
                    expenseListCategory.get(i).getCurrencyType());
            Expense minIndexExpense = new Expense(expenseListCategory.get(minIndex).getExpenseAmount(),
                    Time.toTime(expenseListCategory.get(minIndex).getExpenseTime()),
                    expenseListCategory.get(minIndex).getDescription(),
                    expenseListCategory.get(minIndex).getCurrencyType());

            expenseListCategory.set(i, minIndexExpense);
            expenseListCategory.set(minIndex, tempExpense);
        }
    }


}