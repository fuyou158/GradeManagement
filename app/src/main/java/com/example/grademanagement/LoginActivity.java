package com.example.grademanagement;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    //定义控件
    private Button btnRegister;
    private Button btnLogIn;
    private EditText etUsername;
    private EditText etPassword;
    //数据存储
    private SQLiteDatabase db;
    private MyOpenHelper myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        //获取连接
        myHelper=new MyOpenHelper(this, "my_contact", null, 1);
        db=myHelper.getWritableDatabase();
        initView();

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
    }


    //登陆方法
    public boolean login(String userName,String passWord){
        db=myHelper.getReadableDatabase();
        //查询
        String sql="select * from UserData where username=? and password=?";
        Cursor cursor=db.rawQuery(sql, new String[]{userName,passWord});//参数一为指令语句内容,参数二为字符串数组
        if(cursor.moveToFirst()==true){
            cursor.close();
            return true;
        }
        return false;
    }


    private void initView() {
        //获取控件的控制权
        etUsername=(EditText)findViewById(R.id.et_login_username) ;
        etPassword = (EditText) findViewById(R.id.et_login_password);

        //跳转到注册界面
        btnRegister=(Button)findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        //登陆并跳转到MainActivity
        btnLogIn = (Button) findViewById(R.id.btn_login_log_in);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName=etUsername.getText().toString();
                String passWord=etPassword.getText().toString();
                //判断用户名密码是否正确
                if (login(userName,passWord)) {
                    Toast.makeText(LoginActivity.this, "欢迎你!"+userName, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(LoginActivity.this, "用户不存在或用户名密码不正确!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}