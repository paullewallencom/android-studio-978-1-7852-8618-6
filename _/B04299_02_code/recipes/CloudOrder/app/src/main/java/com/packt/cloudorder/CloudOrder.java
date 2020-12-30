package com.packt.cloudorder;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("CloudOrder")
public class CloudOrder extends ParseObject {
    public void setCustomer (String value) {
        put("customer", value);
    }

    public String getCustomer (){
        return getString("customer");
    }

    public void setAddress (String value) {
        put("address", value);
    }

    public String getAddress (){
        return getString("address");
    }


    // RECIPE 3
    public void setSignature(ParseFile file) {
        put("signature", file);
    }

    public void setStatus(int value) {
        put("status", value);
    }
    // ---


}
