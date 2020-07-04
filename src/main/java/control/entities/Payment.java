package control.entities;

public class Payment {
    private int id, amount;
    private boolean ready;
    private String date;

    public Payment(int id, int amount, boolean ready, String date) {
        this.id = id;
        this.amount = amount;
        this.ready = ready;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
