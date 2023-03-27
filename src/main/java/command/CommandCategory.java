package command;

import data.Expense;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static common.MessageList.MESSAGE_DIVIDER;
import static common.MessageList.MESSAGE_DIVIDER_CATEGORY;

public class CommandCategory extends Command {
    public static final String COMMAND_NAME = "classify";
    public static final String ALL_CATEGORY = "Here are all your expense categories: ";

    protected ArrayList<Expense> expenseList;
    private String category;
    private Set<String> categorySet = new HashSet<>();

    public CommandCategory(ArrayList<Expense> expenseList) {
        super(COMMAND_NAME);
        this.expenseList = expenseList;
        for (Expense e : expenseList) {
            String categoryOfe = e.getDescription();
            if (!categorySet.contains(categoryOfe)) {
                categorySet.add(categoryOfe);
            }
        }
    }

    public CommandCategory(ArrayList<Expense> expenseList, String category) {
        super(COMMAND_NAME);
        this.expenseList = expenseList;
        this.category = category;
        for (Expense e : expenseList) {
            String categoryOfe = e.getDescription();
            if (!categorySet.contains(categoryOfe)) {
                categorySet.add(categoryOfe);
            }
        }
    }

    /**
     * Execution of to classify the expenses based on all categories.
     * It will display all the categories within it.
     * It will also display all the expenses with the specified category.
     *
     * @return printing the list of command
     */
    @Override
    public CommandRes execute() {
        if (expenseList.size() == 0) {
            System.out.println("Sorry, there are no expenses tracked currently.");
            System.out.println(MESSAGE_DIVIDER);
        } else if (!categorySet.contains(category)) {
            System.out.println("Sorry, none of your previous expenses corresponds to this category.");
            System.out.println(MESSAGE_DIVIDER);
        } else {
            displayAllCategories();
            System.out.println(MESSAGE_DIVIDER_CATEGORY);
            int index = 1;
            for (Expense e : expenseList) {
                if (e.getDescription().equals(category)) {
                    System.out.print((index) + ".");
                    System.out.println(e.sortedDisplay("C"));
                }
            }
            System.out.println(MESSAGE_DIVIDER);
        }
        return null;
    }

    private void displayAllCategories() {
        System.out.println(ALL_CATEGORY);
        for (String categoryStored : categorySet) {
            System.out.print(categoryStored + " ");
        }
        System.out.println();
        System.out.println("Totally there are " + categorySet.size() + " categories.");
    }

    public ArrayList<Expense> sumByCategory() {
        HashMap<String, ArrayList<Expense>> categoryGroup = new HashMap<>();
        ArrayList<Expense> expensesByCategorySum = new ArrayList<>();

        // instantiates a HashMap containing all the categories
        for (String categoryStored : categorySet) {
            categoryGroup.put(categoryStored, new ArrayList<>());
        }

        for (Expense e : expenseList) {
            String category = e.getDescription();
            ArrayList<Expense> currExpenseList = categoryGroup.get(category);
            currExpenseList.add(e);
            categoryGroup.put(category, currExpenseList);
        }

        for (String categoryStored : categorySet) {
            expensesByCategorySum.add(new Expense(categoryStored,
                    new CommandTotal(categoryGroup.get(categoryStored))
                            .calculateTotal().setScale(2, RoundingMode.HALF_UP)));
        }
        return expensesByCategorySum;
    }

}
