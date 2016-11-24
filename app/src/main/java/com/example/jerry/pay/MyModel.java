package com.example.jerry.pay;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
@Table(database = AppDatabase.class)
public class MyModel extends BaseModel {

    @PrimaryKey(autoincrement = true)
    public long id;
    @Column
    public String pic;
    @Column
    public String name;
}
