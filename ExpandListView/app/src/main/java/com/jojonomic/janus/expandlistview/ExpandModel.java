package com.jojonomic.janus.expandlistview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wira on 4/29/16.
 */
public class ExpandModel {

    private String name;
    private List<ExpandModelItem> productList = new ArrayList<>();

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<ExpandModelItem> getProductList() {
        return productList;
    }

    public void setProductList(List<ExpandModelItem> productList) {
        this.productList = productList;
    }
}
