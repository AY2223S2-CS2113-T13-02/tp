package parser;

import data.Account;
import java.util.Scanner;

import static common.MessageList.*;
import static data.Account.saveLogOut;
import static data.ExpenseList.showToUser;


public class ParserAccount {
    private static void caseLogIn() {
        System.out.println("username");
        Scanner scanner = new Scanner(System.in);
        String user = scanner.nextLine();
        System.out.println("Password");
        String password = scanner.nextLine();
        Account existingAccount = new Account(user, password);
        String res = existingAccount.login();
        System.out.println(res);
        if (res.equals("Invalid username or password.")
            || res.equals("Sorry, there is no username found")
            || res.equals("An error occurred while logging in.")
            || res.equals("Log In Failed. Invalid login credentials")) {
            initialize(scanner);
        } else {
            showToUser(MESSAGE_DIVIDER, COMMAND_LIST_MESSAGE, MESSAGE_DIVIDER);
        }
    }

    private static void caseSignUp() {
        System.out.println("username");
        Scanner scanner = new Scanner(System.in);
        String user = scanner.nextLine();
        System.out.println("Password");
        String password = scanner.nextLine();
        Account newAccount = new Account(user, password);
        newAccount.signup();
    }

    public static void caseLogOut() {
        Scanner scanner = new Scanner(System.in);
        saveLogOut();
        showToUser(LOGOUT_MESSAGE);
        initialize(scanner);
    }

    public static void caseExit() {
        Scanner scanner = new Scanner(System.in);
        saveLogOut();
        showToUser(EXIT_MESSAGE);
    }

    public static void initialize(Scanner in) {
        do {
            showToUser(MESSAGE_DIVIDER, ACCOUNT_MESSAGE, MESSAGE_DIVIDER);
            String input = in.nextLine();
            if (input.equals("login")) {
                // get login details
                caseLogIn();
                break;
            } else if (input.equals("signup")) {
                // get register details
                caseSignUp();
            } else {
                // invalid input, tell them to try again
                System.out.println("invalid option, chose login or signup!");
                initialize(in);
                break;
            }
        } while (true);
    }

}



