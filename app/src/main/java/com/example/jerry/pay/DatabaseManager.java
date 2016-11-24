package com.example.jerry.pay;

import com.raizlabs.android.dbflow.sql.language.SQLite;

public class DatabaseManager {
    public void a() {
        MyModel myModel = new MyModel();
        myModel.name = "陶蒲";
        myModel.pic = "aaa";
        myModel.save();

    }
    public MyModel  b(){
        return new SQLite().select().from(MyModel.class).where(MyModel_Table.name.eq("陶蒲")).querySingle();
    }
}
