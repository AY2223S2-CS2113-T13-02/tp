package data;

import java.math.RoundingMode;
import java.util.Objects;
import java.math.BigDecimal;

public class Expense {
    protected BigDecimal expenseAmount;
    protected Time expenseTime;
    protected String description;
    protected String currencyType;
    protected BigDecimal rate;

    public Expense(BigDecimal expenseAmount, Time expenseTime, String description, String currencyType,
                   BigDecimal rate) {
        this.expenseAmount = formatExpenseAmount(expenseAmount);
        this.expenseTime = expenseTime;
        this.description = description;
        this.currencyType = currencyType;
        this.rate = rate;
    }

    public Expense(String description, BigDecimal expenseAmount) {
        this.expenseAmount = expenseAmount;
        this.description = description;
    }

    private BigDecimal formatExpenseAmount(BigDecimal originalExpenseAmount) {
        BigDecimal roundedExpense = originalExpenseAmount.setScale(2, RoundingMode.HALF_UP);
        return roundedExpense;
    }

    public BigDecimal getExpenseAmount() {
        return expenseAmount;
    }

    public String getExpenseTime() {
        return expenseTime.toString();
    }

    public String getDescription() {
        return description;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public BigDecimal getRate() {
        return this.rate;
    }

    // The setter method will be used if the User want to change some information in their previous expense
    public void setExpenseAmount(BigDecimal expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public void setExpenseTime(Time expenseTime) {
        this.expenseTime = expenseTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        return Objects.equals(this.getDescription(), ((Expense) obj).getDescription())
                && this.getExpenseAmount().equals(((Expense) obj).getExpenseAmount())
                && Objects.equals(this.getExpenseTime(), ((Expense) obj).getExpenseTime())
                && Objects.equals(this.getCurrencyType(), ((Expense) obj).getCurrencyType())
                && Objects.equals(this.getRate(), ((Expense) obj).getRate());
    }

    /**
     * Override print method to print the information of an expense in a standard format
     */
    @Override
    public String toString() {
        String currencyString = this.currencyType;
        String amountString = this.expenseAmount.toString();
        String descriptionString = this.description;
        String timeString = this.expenseTime.toString();
        return (currencyString + amountString + " cat:" + descriptionString + " date:" + timeString);
    }

    /**
     * Standardize the printing format of sorted expenses
     */

    public String sortedDisplay(String sortBy) {
        String currencyString = this.currencyType;
        String amountString = this.expenseAmount.toString();
        String descriptionString = this.description;
        String timeString = this.expenseTime.toString();
        if (sortBy.equals("C")) {
            return (currencyString + amountString + " date:" + timeString);
        } else {
            return (currencyString + amountString + " cat:" + descriptionString);
        }

    }

    public String toAdd() {
        return ("add amt/" + this.expenseAmount + " t/" + this.expenseTime.toStringSave() + " cat/" + this.description
            + " cur/" + this.currencyType);
    }
}

