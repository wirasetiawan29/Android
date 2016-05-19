package com.wirasetiawan.networthlistview;

/**
 * Created by wira on 3/30/16.
 */
public class SubItem {

    private String title;
    private String amount;

    public SubItem(String title, String amount) {
        this.title = title;
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
