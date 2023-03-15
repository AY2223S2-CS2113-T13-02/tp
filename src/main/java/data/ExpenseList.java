package data;


import parser.Parser;

import java.util.ArrayList;

public class ExpenseList {
    public ArrayList<Expense> expenseList = new ArrayList<>();
    protected Parser parser = new Parser();


    public ArrayList<Expense> getExpenseList() {
        return expenseList;
    }

    //for list
    public static String getAllMessage(ArrayList<Expense> expenseList) {
        int count = expenseList.size();
        // Align the output statement position with the start instead of using tab to indent
        return "Now you have " + count + " " + printExpensesOrExpense(count) + " in the list.";
    }

    private int getSize() {
        return expenseList.size();
    }

    private static String printExpensesOrExpense(int count) {
        return ((count == 1) ? "expense" : "expenses");
    }

    public static void showToUser(String... message) {
        for (String i : message) {
            System.out.println(i);
        }
    }

    // In order to clear the list for test
    public void clear() {
        expenseList.clear();
    }

}

