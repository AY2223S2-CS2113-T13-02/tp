package command.overview;

import data.Expense;
import data.Time;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import static common.MessageList.TAB;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MonthlyOverviewTest {

    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;
    private static final String CATEGORY_DIVIDER = "----------------------------";
    private static final String CATEGORY_TITLE =
            "Breakdown of expenses by category in descending order by category sum:";

    @Test
    void printOverviewEmptyArrayList_successful() {
        ArrayList<Expense> expenses = new ArrayList<>();
        MonthlyOverview monthlyOverview = new MonthlyOverview(expenses, "March", "2021");
        monthlyOverview.printOverview();
        assertEquals("No expenses tracked in MARCH 2021.", outputStreamCaptor.toString().trim());
    }

    @Test
    void printOverview_successful() {
        ArrayList<Expense> testExpenses = new ArrayList<>();
        testExpenses.add(new Expense(new BigDecimal("2.5"),
                new Time(LocalDate.parse("01-03-2012", formatter)),
                "travel", "SGD", new BigDecimal(1)));
        testExpenses.add(new Expense(new BigDecimal("5"),
                new Time(LocalDate.parse("05-03-2012", formatter)),
                "travel", "SGD", new BigDecimal(1)));
        testExpenses.add(new Expense(new BigDecimal("10"),
                new Time(LocalDate.parse("12-03-2012", formatter)),
                "food", "SGD", new BigDecimal(1)));
        testExpenses.add(new Expense(new BigDecimal("2.5"),
                new Time(LocalDate.parse("10-03-2012", formatter)),
                "food", "SGD", new BigDecimal(1)));
        MonthlyOverview monthlyOverview = new MonthlyOverview(testExpenses, "March", "2012");
        monthlyOverview.printOverview();
        String expectedOutput = "Monthly Overview for MARCH 2012\n" + "\n" +
                TAB + "Total expenses: 20.00 SGD\n" + "\n" +
                TAB + CATEGORY_TITLE + "\n" +
                TAB + CATEGORY_DIVIDER + "\n" +
                TAB + " food 12.50 SGD\n" +
                TAB + CATEGORY_DIVIDER + "\n" +
                TAB + " travel 7.50 SGD\n" +
                TAB + CATEGORY_DIVIDER;
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }


    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}