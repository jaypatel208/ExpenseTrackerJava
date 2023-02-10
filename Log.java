package BudgetTracker;

public class Log {
    private String date;
    private String item;
    private int cost;

    public void setDate(String date) { this.date = date;}
    public String getItem() {
        return item;
    }
    public int getCost(){return cost;}
    public String getDate() {return date;}
    public void setItem(String item) {this.item=item;}
    public void setCost(int cost) {this.cost=cost;}
}
