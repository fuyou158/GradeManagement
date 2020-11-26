package com.example.grademanagement;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    //全局变量
    private MyOpenHelper myHelper;
    public static final String DB_NAME="my_contact";
    public static final String TABLE_NAME="personInfo";
    public static final String ID="ID";
    public static final String NAME="Name";
    public static final String SCORE="Score";
    public static final String CLASS="Class";
    public static final String GRADE="Grade";

    private SQLiteDatabase db;
    private MyAdapter adapter;
    private ImageButton btn_add,btn_clear,btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }

        lv=(ListView)findViewById(R.id.listView1);
        btn_add=(ImageButton) findViewById(R.id.IB_add);
        btn_clear=(ImageButton) findViewById(R.id.IB_clear);
        btn_back=(ImageButton)findViewById(R.id.IB_back) ;
        myHelper=new MyOpenHelper(this, DB_NAME, null, 1);
        db=myHelper.getWritableDatabase();


        List<StudentInfo> list=this.getBasicInfo();
        adapter=new MyAdapter(this, list);
        lv.setAdapter(adapter);

        //跳转到添加页面
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,NewPersonActivity.class);
                startActivity(intent);
            }
        });
        //清空事件监听
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("清空列表");
                    dialog.setMessage("确定要清空列表吗?");
                    dialog.setCancelable(true);
                    dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(deleteAll()>0)
                            {
                                Toast.makeText(MainActivity.this,
                                        "清空成功！", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(MainActivity.this,MainActivity.class));
                            }
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(MainActivity.this,"操作已取消!",Toast.LENGTH_LONG).show();
                        }
                    });
                    dialog.show();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    //将数据库信息调出来,并添加到列表
    private List<StudentInfo> getBasicInfo() {
        Cursor c=db.query(TABLE_NAME,new String[]{},null,null,null,null,ID);
        List<StudentInfo> list=new ArrayList<StudentInfo>();
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            //获取数据库对应列名的列项
            String name=c.getString(c.getColumnIndex(NAME));
            String Score=c.getString(c.getColumnIndex(SCORE));
            String Class=c.getString(c.getColumnIndex(CLASS));
            String Grade=c.getString(c.getColumnIndex(GRADE));
            StudentInfo d=new StudentInfo(name,Score, Class, Grade);

            System.out.println("MainActivity:要添加的列表子项数据有:"+name+","+Score+","+Class+","+Grade);
            d.setID(c.getInt(c.getColumnIndex(ID)));
            //添加到列表
            list.add(d);
        }
        c.close();
        return list;
    }
    //清空
    public int deleteAll()
    {
        return db.delete(TABLE_NAME, null, null);
    }
    @Override
    protected void onPause() {
        super.onPause();
        db.close();
    }
}
