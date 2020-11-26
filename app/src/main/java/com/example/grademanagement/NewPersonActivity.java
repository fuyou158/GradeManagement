package com.example.grademanagement;

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

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class NewPersonActivity extends AppCompatActivity {
	private EditText et_name,et_score,et_class,et_grade;
	private Button btn_ok,btn_back;

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
		setContentView(R.layout.activity_new_person);

		ActionBar actionBar=getSupportActionBar();
		if(actionBar!=null){
			actionBar.hide();
		}

		myHelper=new MyOpenHelper(this, DB_NAME, null, 1);
		db=myHelper.getWritableDatabase();


		et_name=(EditText)findViewById(R.id.et_name);
		et_score=(EditText)findViewById(R.id.et_score);
		et_class=(EditText)findViewById(R.id.et_class);
		et_grade=(EditText)findViewById(R.id.et_grade);
		btn_ok=(Button)findViewById(R.id.button1);
		btn_back=(Button)findViewById(R.id.button2);


		if(getIntent().getIntExtra("id",0)>0)//判断是新增还是修改
		{
			id=getIntent().getIntExtra("id",0);
			setPersonInfo(id);
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
				else
					result=db.update(TABLE_NAME, value, "ID="+id, null);
				db.close();
				if(result>0)
				{
					Toast.makeText(NewPersonActivity.this, "操作成功！", Toast.LENGTH_LONG).show();
					Intent intent=new Intent(NewPersonActivity.this,MainActivity.class);
					startActivity(intent);
				}
			}
		});
		btn_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				startActivity(new Intent(NewPersonActivity.this,MainActivity.class));
				finish();
			}
		});
	}

	private void setPersonInfo(int id) {
		Cursor c=db.query(TABLE_NAME,new String[]{},"ID="+id,null,null,null,null);
		if(c.moveToFirst())
		{
			String name=c.getString(c.getColumnIndex(NAME));
			String Score=c.getString(c.getColumnIndex(SCORE));
			String Class=c.getString(c.getColumnIndex(CLASS));
			String Grade=c.getString(c.getColumnIndex(GRADE));
			StudentInfo d=new StudentInfo(name,Score, Class, Grade);
			d.setID(c.getInt(c.getColumnIndex("ID")));
			et_name.setText(d.getName());
			et_score.setText(d.getSCORE());
			et_class.setText(d.getCLASS());
			et_grade.setText(d.getGRADE());
		}
		c.close();
	}
}
