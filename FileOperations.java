package BudgetTracker;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class FileOperations {
    File logBookFile;
    File passWord = new File("C:\\Users\\Jay Patel\\IdeaProjects\\JavaLearn\\CompetitiveJava\\src\\BudgetTracker\\password.txt");
    public void updateLogBook(int budget, int expense, String date, String item, int cost) throws IOException {

        logBookFile = new File("C:\\Users\\Jay Patel\\IdeaProjects\\JavaLearn\\CompetitiveJava\\src\\BudgetTracker\\logbook.txt" + getMonthYearName(date) + ".txt");
        FileWriter writer = new FileWriter(logBookFile,true);
        BufferedWriter br = new BufferedWriter(writer);
        if(item == "nil")
            date = "nil";
        br.append(budget + "\t" + expense + "\t" + date + "\t" + item + "\t" + cost );
        br.newLine();
        br.close();
    }
    public String getPassWord() throws IOException {
        if (passWord.length() == 0)
            System.out.print("\nPlease check the Password file for errors");
        FileReader read = new FileReader(this.passWord);
        BufferedReader rd = new BufferedReader(read);
        //reading password and returning it
        String pass = rd.readLine().toString().trim();
        rd.close();
        return pass;
    }
    public ArrayList<Log> getLog(String date) throws IOException{
        logBookFile = new File("C:\\Users\\Jay Patel\\IdeaProjects\\JavaLearn\\CompetitiveJava\\src\\BudgetTracker\\logbook.txt" + getMonthYearName(date)+ ".txt");
        FileReader read = new FileReader(logBookFile);
        BufferedReader rd = new BufferedReader(read);
        String temp = new String();
        //data object to an array list
        ArrayList<Log> list1 = new ArrayList<Log>();
        while ((temp = rd.readLine()) != null){
            Log log = new Log();
            StringTokenizer st = new StringTokenizer(temp, "\t");
            String[] str = new String[5];
            int i =0;
            while (st.hasMoreTokens()){
                str[i] = st.nextToken();
                i++;
            }
            if (str[2].toString().equals(date)){
                log.setDate(str[2]);
                log.setItem(str[3]);
                //This code checks whether str[4] is null before trying to parse it as an integer. If it is null, the setCost method is not called, and the error is avoided.
                if (str[4] != null) {
                    log.setCost(Integer.parseInt(str[4]));
                }
                list1.add(log);
            }
        }
        rd.close();
        return list1;
    }
    private String getMonthYearName(String date){
        String name = "";
        if (date.length() == 7)
            date = "00/".concat(date);
        StringTokenizer st = new StringTokenizer(date, "/");
        int i = 0;
        String[] arr = new String[3];
        while (st.hasMoreTokens()){
            arr[i] = st.nextToken();
            i++;
        }
        if (date.length() == 10)
            name = arr[1].toString().concat(arr[2].toString());
        else if (date.length() == 8) {
            name = arr[0].toString().concat(arr[1].toString());
        }
        return name;
    }
    public ArrayList<Log> getMonthLog(String date) throws IOException{
        logBookFile = new File("C:\\Users\\Jay Patel\\IdeaProjects\\JavaLearn\\CompetitiveJava\\src\\BudgetTracker\\logbook.txt" + getMonthYearName(date) + ".txt");
        FileReader read = new FileReader(logBookFile);
        BufferedReader rd = new BufferedReader(read);
        String temp = new String();
        ArrayList<Log> list1 = new ArrayList<Log>();
        while ((temp = rd.readLine()) != null){
            Log log = new Log();
            StringTokenizer st = new StringTokenizer(temp,"\t");
            String[] str = new String[5];
            int i = 0;
            while (st.hasMoreTokens()){
                str[i] = st.nextToken();
                i++;
            }
            log.setDate(str[2]);
            log.setItem(str[3]);
            if (str[4] != null) {
                log.setCost(Integer.parseInt(str[4]));
            }
            list1.add(log);
        }
        rd.close();
        return list1;
    }
    public String getBudgetDate(String date) throws IOException{
        logBookFile = new File("C:\\Users\\Jay Patel\\IdeaProjects\\JavaLearn\\CompetitiveJava\\src\\BudgetTracker\\logbook.txt" + getMonthYearName(date) + ".txt");
        if (!logBookFile.exists()){
            logBookFile.createNewFile();
        }
        FileReader read = new FileReader(logBookFile);
        BufferedReader rd = new BufferedReader(read);
        String budgetData = null;
        String line, last = null;
        if (rd.readLine() == null){
            budgetData = null;
        }else {
            while ((line = rd.readLine()) != null){
                last = line;
            }
            String[] arr = new String[5];
            int i = 0;
            StringTokenizer st = new StringTokenizer(last, "\t");
            while (st.hasMoreTokens()){
                arr[i] = st.nextToken();
                i++;
            }
            budgetData = arr[0].toString().concat(" "+arr[1].toString());
        }
        rd.close();
        return budgetData;
    }
    public void resetBudget(String date,int budget) throws IOException{
        String last = "";
        String line = "";
        logBookFile = new File("C:\\Users\\Jay Patel\\IdeaProjects\\JavaLearn\\CompetitiveJava\\src\\BudgetTracker\\logbook.txt"+getMonthYearName(date)+".txt");
        FileReader read = new FileReader(logBookFile);
        BufferedReader rd = new BufferedReader(read);
        while ((line = rd.readLine()) != null){
            last = line;
        }
        StringTokenizer st = new StringTokenizer(last,"\t");
        int i=0;
        String[] arr = new String[5];
        while (st.hasMoreTokens()){
            arr[i] = st.nextToken();
            i++;
        }
        rd.close();
        RandomAccessFile f = new RandomAccessFile("logbook" + getMonthYearName(date) + ".txt","rw");
        long length = f.length();
        if (length >= 0) {
            f.seek(length);
            int b = f.read();

            if (b == 10) {
                f.setLength(length - 1);
            }
        }
//        byte b;
//        do {
//            length -= 1;
//            f.seek(length);
//            b = f.readByte();
//        }while (b !=10 && length>0);
//        if (length == 0){
//            f.setLength(length);
//        }else {
//            f.setLength(length + 1);
//        }
        f.close();
        if (arr[4] != null) {
            updateLogBook(budget, Integer.parseInt(arr[1].toString()), arr[2], arr[3], Integer.parseInt(arr[4].toString()));
        }
       // updateLogBook(budget, Integer.parseInt(arr[1].toString()),arr[2],arr[3],Integer.parseInt(arr[4].toString()));
    }
    public void deleteLog(String date) {
        logBookFile = new File("C:\\Users\\Jay Patel\\IdeaProjects\\JavaLearn\\CompetitiveJava\\src\\BudgetTracker\\logbook.txt" + getMonthYearName(date) + ".txt");
        boolean success = logBookFile.delete();
        if (success) {
            System.out.print("\nFile Deleted Successfully !");
        } else {
            System.out.print("\nFile for the particular month does not exist or could not be deleted !");
        }
    }
    /*public void deleteLog(String date){
        logBookFile = new File("C:\\Users\\Jay Patel\\IdeaProjects\\JavaLearn\\CompetitiveJava\\src\\BudgetTracker\\logbook.txt" + getMonthYearName(date) + ".txt");
        boolean bool = true;
        if (bool == true){
            System.out.print("\nFile Deleted Successfully !");
        }else
            System.out.print("\nFile for the particular month does not exist !");
    }*/
    public void setPassWord(String passWord) throws IOException {
        FileWriter writer = new FileWriter("C:\\Users\\Jay Patel\\IdeaProjects\\JavaLearn\\CompetitiveJava\\src\\BudgetTracker\\password.txt");
        BufferedWriter br = new BufferedWriter(writer);
        br.write(passWord);
        br.close();
        System.out.println("\nYour password has been changed!");
        /*RandomAccessFile f = new RandomAccessFile("password.txt","rw");long length = f.length()-1;
        byte b;
        do {
            length -= 1;
            f.seek(length);
            b = f.readByte();
        }while (b != 10 && length>0);
        if (length == 0){
            f.setLength(length);
        }else {
            f.setLength(length + 1);
        }
        f.close();
        // writing the new pass using buffer reader
        FileWriter writer = new FileWriter(this.passWord,true);
        BufferedWriter br = new BufferedWriter(writer);
        br.write(passWord);
        br.close();
        System.out.print("\nYour password has been changed!");*/
    }
    public String getBudgetData(String date) throws IOException{
        logBookFile = new File("C:\\Users\\Jay Patel\\IdeaProjects\\JavaLearn\\CompetitiveJava\\src\\BudgetTracker\\logBook.txt" + getMonthYearName(date) + ".txt");
        if (!logBookFile.exists()){
            logBookFile.createNewFile();
        }
        FileReader read = new FileReader(logBookFile);
        BufferedReader rd = new BufferedReader(read);
        String budgetData = null;
        String line, last = null;
        if (rd.readLine() == null){
            budgetData = null;
        }else {
            while ((line = rd.readLine()) != null){
                last = line;
            }
            String[] arr = new String[5];
            int i = 0;
            StringTokenizer st = new StringTokenizer(last, "\t");
            while (st.hasMoreTokens()){
                arr[i] = st.nextToken();
                i++;
            }
            budgetData = arr[0].toString().concat(" "+arr[1].toString());
        }
        rd.close();
        return budgetData;
    }
}

