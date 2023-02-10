package BudgetTracker;

import java.io.IOException;
import java.util.Scanner;

public class PassWord {
    FileOperations file = new FileOperations();
    Scanner input = new Scanner(System.in);

    public boolean checkPassWord() throws IOException {
        System.out.println("\nEnter the password: ");
        Scanner input = new Scanner(System.in);
        String pass = input.next();
        checkUtilPasswordIsCorrect(pass);
        return true;
    }
    private String getPassword() throws IOException{
        String pass = file.getPassWord();
        return pass;
    }
    private boolean checkUtilPasswordIsCorrect(String oldPass) throws IOException{
        while (oldPass.equals(getPassword()) == false){
            System.out.print("The entered password is incorrect! \n\nRe-enter the password: ");
            Scanner input = new Scanner(System.in);
            oldPass = input.next();
            if (oldPass.equals(getPassword()))
                break;
        }
        return true;
    }
    public void changePassword() throws IOException{
        System.out.print("\nEnter the current password: ");
        Scanner input = new Scanner(System.in);
        String oldPass = input.next();
        if (checkUtilPasswordIsCorrect(oldPass)){
            System.out.print("\nEnter the new Password: ");
            String newPass = input.next();
            file.setPassWord(newPass);
            System.out.print("\nThe password has been changed successfully!");
        }
    }
}
