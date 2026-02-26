import java.time.LocalDate;

public class Expense {
    private int id;
    private String date;
    private String description;
    private double amount;

    public Expense(String description, double amount) {
        this.id = Helper.generateId();
        this.date = LocalDate.now().toString();
        this.description = description;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + "," + date + "," + description + "," + amount + "\n";
    }
}
