package MajorClasses;

import data.CommandRes;

import java.util.ArrayList;

import static data.MessageList.MESSAGE_DIVIDER_LIST;

public class ExpenseList {
    public ArrayList<Expense> expenseList = new ArrayList<>();
    
    public ArrayList<Expense> getExpenseList() {
        return expenseList;
    }

    public void deleteExpense (String userInput) {
        int expenseIndex = Integer.parseInt(userInput);
        expenseList.remove(expenseIndex-1); // change to 0-based indexing
    }
    
    //for list
    public static String getAllMessage() {
        int count = ExpenseList.expenseList.size();
        return "\t" + "Now you have " + count + " " + printExpensesOrExpense(count) + " in the list.";
    }

    private static String printExpensesOrExpense(int count) {
        return ((count == 1) ? "expense" : "expenses");
    }

    public CommandRes listExpense() {
        return new CommandRes(MESSAGE_DIVIDER_LIST, ExpenseList.expenseList, ExpenseList.getAllMessage());
    }
}
