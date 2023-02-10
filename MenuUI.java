package BudgetTracker;

import java.io.IOException;
import java.util.Scanner;

public class MenuUI {
    public static void main (String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        int ch;
        char userChoice = 'N';
        PassWord pass = new PassWord();

        if (pass.checkPassWord() == true) {
            do {
                System.out.print("\n Select Given Operations: \n1. Make an entry \n2. Get the expenses done for a particular date \n3. Get a log of particular month \n4. Check expense of particular month \n5. Set budget \n6. Delete Particular month's log \n7. Change Password \n Enter your choice: ");
                ch = input.nextInt();
                Manager manage = new Manager();
                switch (ch) {
                    case 1 -> manage.makeDailyLog();
                    case 2 -> manage.getDayExpenseDetails();
                    case 3 -> manage.getMonthExpenseDetails();
                    case 4 -> manage.displayMonthExpense();
                    case 5 -> manage.setBudget();
                    case 6 -> manage.deleteMonthLog();
                    case 7 -> pass.changePassword();
                }
                System.out.print("\nAnything Else?(y/n): ");
                userChoice = input.next().charAt(0);
            } while (userChoice == 'y' || userChoice == 'Y');
        }else
            System.out.print("The entered password is incorrect! ");
        input.close();

    }
}
