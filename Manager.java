package BudgetTracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Manager {
    Scanner input = new Scanner(System.in);
    FileOperations file = new FileOperations();
    CostOperations cost = new CostOperations();
    PassWord pass = new PassWord();
    public void makeDailyLog() throws IOException{
        Log log = new Log();
        System.out.print("\nEnter the Date (dd/mm/yyyy): ");
        log.setDate(input.next());
        System.out.print("\nEnter the item: ");
        Scanner input = new Scanner(System.in);
        input.nextLine();
        log.setItem(input.nextLine());
        System.out.print("\nEnter Cost: ₹");
        log.setCost(input.nextInt());
        enterLog(log);
    }
    private void enterLog(Log log) throws IOException{
        int budget = 0, expense = 0;
        String budgetBookData = file.getBudgetData(log.getDate());
        if (budgetBookData == null){
            System.out.print("\nThe budget has not been set yet, please set the budget: ");
            System.out.print("\nThe budget for the month is ₹");
            Scanner input = new Scanner(System.in);
            budget = input.nextInt();
            file.updateLogBook(budget, 0 , log.getDate(),"nil" , 0);
        }else {
            budget = cost.getBudget(budgetBookData);
            expense = cost.getExpense(budgetBookData);
        }
        System.out.print("\nLog added successfully!");
        if (cost.budgetCheck(budget,expense,log.getCost()))
            System.out.print("\n!!!Your monthly budget has exceeded, Please take care !!!");
        file.updateLogBook(budget, (expense+log.getCost()), log.getDate(), log.getItem(), log.getCost());
    }
    public void getDayExpenseDetails() throws IOException{
        System.out.print("\nEnter the Date (dd/mm/yyyy): ");
        Scanner input = new Scanner(System.in);
        String date = input.next();
        getDetails(date);
    }
    private void getDetails(String date) throws IOException {
        ArrayList<Log> list1 = file.getLog(date);
        int n = list1.size();
        if (n == 0)
            System.out.print("\nNo entry has been made for the date entered! Please check the date");
        else {
            System.out.println("\nThe details of the expense done on " + date + " :");
            for (int i=0; i<n; i++){
                Log log = list1.get(i);
                System.out.println((i+1) + " : " + "\nExpense Item: " + log.getItem() + "\nExpenditure: Rs." + log.getCost());
            }
        }
    }
    public void getMonthExpenseDetails() throws NumberFormatException, IOException{
        System.out.print("\nEnter the month and the year (MM/YYYY): ");
        Scanner input = new Scanner(System.in);
        String date = input.next();
        ArrayList<Log> list1 = file.getMonthLog(date);
        int n = list1.size();
        if (n == 0)
            System.out.print("\nNo entry has been made for the date entered! Please Check the date");
        else {
            System.out.println("\nThe details of the expense done on " + date + " :");
            for (int i=1; i<n; i++){
                Log log = list1.get(i);
                System.out.println((i) + " : " + "\nDate: " + log.getDate() + " : " + "\nExpense Item: " + log.getItem() + "\nExpenditure: Rs." + log.getCost());
            }
        }
    }
    public void displayMonthExpense() throws IOException{
        System.out.print("Enter the month and year (MM/YYYY): ");
        Scanner input = new Scanner(System.in);
        String date = input.next();
        date = "00/".concat(date);
        String budgetdata = file.getBudgetData(date);
        int expense = cost.getExpense(budgetdata);
        int budget = cost.getBudget(budgetdata);
        System.out.print("\nThe expense done till date is: ₹" + expense);
        if (budget > expense)
            System.out.print("\nThe amount that can yet be spent is: ₹" + (budget - expense));
        else
            System.out.print("\nThe amount exceeding your budget is: ₹" + (expense - budget));
    }
    public void setBudget() throws IOException{
        if (pass.checkPassWord()){
            System.out.print("\nResetting the budget may create error in the calculations\nDo you still want to continue? (y/n): ");
            Scanner input = new Scanner(System.in);
            char ch = input.next().charAt(0);
            if (ch == 'y' || ch == 'Y'){
                System.out.print("\nEnter the month and the year(MM/YYYY): ");
                String date = input.next();
                System.out.print("\nEnter the budget: ₹");
                int budget = input.nextInt();
                file.resetBudget(date,budget);
                System.out.print("\nThe Budget has been Reset Successfully");
            }
            ch = 'n';
        }
        else
            System.out.print("The entered password is incorrect!");
    }
    public void deleteMonthLog(){
        System.out.print("Are you sure that you want to delete a whole month's log forever? (Y/N): ");
        Scanner input = new Scanner(System.in);
        char choice = input.next().charAt(0);
        if (choice == 'y' || choice == 'Y'){
            System.out.print("\nEnter the month and the year (MM/YYYY): ");
            String date = input.next();
            file.deleteLog(date);
        }
    }
}
