package com.example.appquanlysinhvien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Lop> lopList;
    LopAdapter lopAdapter;
    ListView lopLVAX;
    int position = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Delete_Database();
        CreateOrOpenDatabase();
//        CreateDataBase();
        lopList = new ArrayList<>();
        SetDataLopList();
        lopAdapter = new LopAdapter(MainActivity.this,  lopList);
        lopLVAX = findViewById(R.id.lopLV);
        lopLVAX.setAdapter(lopAdapter);
        lopLVAX.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                Lop lop = lopList.get(position);
                Intent intent = new Intent(MainActivity.this, SinhVienActivity.class);
                intent.putExtra("maLop", lop.getMaLop());
                startActivity(intent);
            }
        });
    }
    public void SetDataLopList(){
        Cursor c = database.query("Lop", null ,null ,null ,null ,null ,null);
        c.moveToFirst();
        while (c.isAfterLast()== false){
            lopList.add(new Lop(c.getString(0), c.getString(1)));
            c.moveToNext();
        }
    }
    public void CreateDataBase(){
        CreateLopTable();
        CreateSinhVienTable();
        InsertLopTable( "202","20T2");
        InsertLopTable( "222","22T2");
        InsertLopTable( "201","20T1");
        InsertLopTable( "203","20T3");

        InsertSinhVienTable("2050531200226","Vo Minh Loi", "202", "2002", 7, 8 ,9);
        InsertSinhVienTable("2050531200222","Le Van Long", "202", "2002", 8, 9 ,10);
        InsertSinhVienTable("2050531200122","Le Duc Duy", "201", "2002", 8, 9 ,10);
        InsertSinhVienTable("2050531200322","Le Thuan Phuc", "203", "2002", 7, 9 ,8);
        InsertSinhVienTable("2250531200222","Phan Duc Duy", "222", "2002", 7, 8 ,10);
    }
    public SQLiteDatabase database;
    public void CreateOrOpenDatabase(){
        database = openOrCreateDatabase("QuanLySinhVien.db",MODE_PRIVATE,null);
        System.out.println("Create database successful");
    }

    public void Delete_Database(){
        deleteDatabase("QuanLySinhVien.db");
        System.out.println("Delete database successful");
    }

    public void CreateLopTable(){
        String sql =    "Create table Lop("
                +       "maLop TEXT primary key,"
                +       "tenLop TEXT)";
        database.execSQL(sql);
        System.out.println("Create Lop table successful");
    }

    public void CreateSinhVienTable(){
        String sql =    "Create table SinhVien("
                +       "maSV TEXT primary key,"
                +       "tenSV TEXT,"
                +       "maLop TEXT not null constraint maLop references Lop(maLop) on update cascade on delete cascade,"
                +       "namSinh TEXT,"
                +       "toan float,"
                +       "ly float,"
                +       "hoa float)";
        database.execSQL(sql);
        System.out.println("Create Sinh Vien table successful");

    }
    public void InsertLopTable(String maLop, String tenLop){
        ContentValues values = new ContentValues();
        values.put("maLop", maLop);
        values.put("tenLop", tenLop);
        database.insert("Lop", null, values);
        System.out.println("Insert Lop Table");
    }
    public void InsertSinhVienTable(String maSV, String tenSV, String maLop, String namSinh, float toan, float ly, float hoa){
        ContentValues values = new ContentValues();
        values.put("maSV", maSV);
        values.put("tenSV", tenSV);
        values.put("maLop", maLop);
        values.put("namSinh", namSinh);
        values.put("toan",toan);
        values.put("ly",ly);
        values.put("hoa",hoa);
        database.insert("SinhVien", null, values);
        System.out.println("Insert Sinh Vien Table");
    }

}