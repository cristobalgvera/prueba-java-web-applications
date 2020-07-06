package control.entities;

import java.util.List;

public class Visit {
    private int id, customerId, employeeId, summaryId, paymentId;
    private boolean ready;
    private String date;
    private List<String> activities;

    public Visit(int id, int customerId, int employeeId, int summaryId, int paymentId, boolean ready, String date, List<String> activities) {
        this.id = id;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.summaryId = summaryId;
        this.paymentId = paymentId;
        this.ready = ready;
        this.date = date;
        this.activities = activities;
    }

    public Visit() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getSummaryId() {
        return summaryId;
    }

    public void setSummaryId(int summaryId) {
        this.summaryId = summaryId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
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

    public List<String> getActivities() {
        return activities;
    }

    public String getActivity(int i) {
        return activities.get(i);
    }

    public void setActivities(List<String> activities) {
        this.activities = activities;
    }
}
