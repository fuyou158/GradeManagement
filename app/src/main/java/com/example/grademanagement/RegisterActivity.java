package com.example.grademanagement;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private MyOpenHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        //隐藏系统栏,以便使用自己的自定义栏
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        dbHelper = new MyOpenHelper(this,"my_contact",null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        logon();
    }
    //整个注册过程,由onCreate调用
    public void logon(){
       final EditText userName=(EditText)findViewById(R.id.et_rg_name);
       final EditText passWord=(EditText)findViewById(R.id.et_rg_pswd);


        Button rg_button=(Button)findViewById(R.id.button_rg);
        rg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取用户输入的值
                String newname =userName.getText().toString();
                String password=passWord.getText().toString();

                if (CheckIsDataAlreadyInDBorNot(newname)) {
                    Toast.makeText(RegisterActivity.this,"该用户名已被注册，注册失败", Toast.LENGTH_SHORT).show();
                }
                else {
                    //调用register方法,参数为获取用户输入的值
                    if (register(newname, password)) {
                        Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(RegisterActivity.this, "插入数据表成功", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        //返回
        Button bk_button=(Button)findViewById(R.id.button_bk);
        bk_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    //向数据库插入数据
    public boolean register(String username,String password){
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        /*String sql = "insert into userData(username,password) values(?,?)";
        Object obj[]={username,password};
        db.execSQL(sql,obj);*/
        ContentValues values=new ContentValues();
        //参数1为列名,参数2为值
        values.put("username",username);
        values.put("password",password);
        db.insert("userData",null,values);
        db.close();
        //db.execSQL("insert into userData (name,password) values (?,?)",new String[]{username,password});
        return true;
    }
    //检验用户名是否已存在
    public boolean CheckIsDataAlreadyInDBorNot(String value){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String Query = "Select * from userData where username =?";
        Cursor cursor = db.rawQuery(Query,new String[] { value });

        //如果getCount()方法得到结果大于0,
        // 表明已经存在对应的用户名密码,返回结果为真,否则返回值为假
        if (cursor.getCount()>0){
            cursor.close();
            return  true;
        }
        cursor.close();
        return false;
    }
}