package com.example.grademanagement;
//管理数据活动
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class EditAcitivity extends AppCompatActivity {
    //控件定义
    private EditText et_name,et_score,et_class,et_grade;
    private Button btn_ok,btn_back,btn_del;
    //全局变量
    private MyOpenHelper myHelper;
    public static final String DB_NAME="my_contact";
    public static final String TABLE_NAME="personInfo";
    public static final String NAME="Name";
    public static final String SCORE="Score";
    public static final String CLASS="Class";
    public static final String GRADE="Grade";
    private SQLiteDatabase db;
    private int flag=0;
    private int id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        myHelper=new MyOpenHelper(this, DB_NAME, null, 1);
        db=myHelper.getWritableDatabase();

        //获取控制权
        et_name=(EditText)findViewById(R.id.et_namee);
        et_score=(EditText)findViewById(R.id.et_scoree);
        et_class=(EditText)findViewById(R.id.et_classe);
        et_grade=(EditText)findViewById(R.id.et_gradee);
        btn_ok=(Button)findViewById(R.id.button2_confirm);
        btn_back=(Button)findViewById(R.id.button2_bk);
        btn_del=(Button)findViewById(R.id.button2_del);


        if(getIntent().getIntExtra("id",0)>0)//判断是新增还是修改
        {
            //获取来自MainActivity的id
            id=getIntent().getIntExtra("id",0);
            setPersonInfo(id);

            System.out.println("来自MainActivity的id:   "+id);
            flag=1;
        }
        btn_ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues value=new ContentValues();
                value.put(NAME, et_name.getText().toString());
                value.put(SCORE, et_score.getText().toString());
                value.put(CLASS, et_class.getText().toString());
                value.put(GRADE, et_grade.getText().toString());
                long result;
                if(flag==0)
                    result=db.insert(TABLE_NAME, null, value);
                else//flag不为零代表是update数据操作
                    result=db.update(TABLE_NAME, value, "ID="+id, null);
                db.close();

                if(result>0)
                {
                    Toast.makeText(EditAcitivity.this, "修改成功！", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(EditAcitivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });
        btn_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditAcitivity.this,MainActivity.class));
                finish();
            }
        });

        btn_del.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                db.delete(TABLE_NAME, "ID="+id, null);
                Toast.makeText(EditAcitivity.this,"删除成功!",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(EditAcitivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setPersonInfo(int id) {
        Cursor c=db.query(TABLE_NAME,new String[]{},"ID="+id,null,null,null,null);
        if(c.moveToFirst())
        {
            //从数据库获取数据并将数据填入相应的EditText控件
            String name=c.getString(c.getColumnIndex(NAME));
            String Score=c.getString(c.getColumnIndex(SCORE));
            String Class=c.getString(c.getColumnIndex(CLASS));
            String Grade=c.getString(c.getColumnIndex(GRADE));
            StudentInfo d=new StudentInfo(name,Score, Class, Grade);

            System.out.println("EditActivity:自动填入的值:  "+"name:  "+name+","+"score:  "+Score+","+"class:  "+Class+","+"sno:  "+Grade);

            d.setID(c.getInt(c.getColumnIndex("ID")));
            et_name.setText(d.getName());
            et_score.setText(d.getSCORE());
            et_class.setText(d.getCLASS());
            et_grade.setText(d.getGRADE());
        }
        c.close();
    }
}
