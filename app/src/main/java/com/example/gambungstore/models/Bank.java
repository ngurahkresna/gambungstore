package com.example.gambungstore.models;

public class Bank {
    int id;
    int bank_code;
    String bank_name;

    public Bank(int bank_id, int bank_code, String bank_name) {
        this.id = bank_id;
        this.bank_code = bank_code;
        this.bank_name = bank_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBank_code() {
        return bank_code;
    }

    public void setBank_code(int bank_code) {
        this.bank_code = bank_code;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }
}
