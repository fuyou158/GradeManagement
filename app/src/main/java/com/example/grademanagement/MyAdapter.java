package com.example.grademanagement;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
//列表适配器
public class MyAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private List<StudentInfo> list;
	private Context context;

	private MyOpenHelper myHelper;
	private SQLiteDatabase db;

	public static final String DB_NAME="my_contact";
//	public static final String TABLE_NAME="personInfo";

	public MyAdapter(Context context, List<StudentInfo> list) {
		inflater = LayoutInflater.from(context);
		this.list = list;
		this.context=context;
		myHelper=new MyOpenHelper(context, DB_NAME, null, 1);
		db=myHelper.getWritableDatabase();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}



	@Override
	/*ViewHolder通常出现在适配器里，为的是listview滚动的时候快速设置值，
	而不必每次都重新创建很多对象，从而提升性能。*/
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.person_item, null);
			viewHolder = new ViewHolder();

			viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			viewHolder.tv_score = (TextView) convertView.findViewById(R.id.tv_score);
			viewHolder.btn_edit = (Button) convertView.findViewById(R.id.btn_edit);
			viewHolder.tv_sno=(TextView)convertView.findViewById(R.id.tv_sno);
//			viewHolder.btn_del = (Button) convertView.findViewById(R.id.btn_del);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv_name.setText(list.get(position).getName());
		viewHolder.tv_sno.setText("["+list.get(position).getGRADE()+"]");
		viewHolder.tv_score.setText("成绩:  "+list.get(position).getSCORE()+"分");
		final int id=list.get(position).getID();
		viewHolder.btn_edit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//带参跳转
				Intent intent=new Intent((MainActivity)context,EditAcitivity.class);
				intent.putExtra("id", id);
				((MainActivity)context).startActivity(intent);

			}
		});
		//删除
		/*viewHolder.btn_del.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				db.delete(TABLE_NAME, "ID="+id, null);
				Intent intent=new Intent((MainActivity)context,MainActivity.class);
				((MainActivity)context).startActivity(intent);
			}
		});*/
		return convertView;
	}
	public static class ViewHolder {
		TextView tv_score;
		TextView tv_name;
		TextView tv_sno;
		Button btn_edit,btn_del;
	}

}

