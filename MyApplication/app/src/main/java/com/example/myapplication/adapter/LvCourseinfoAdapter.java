package com.example.myapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.Dao.ApplyDao;
import com.example.myapplication.Dao.StudentDao;
import com.example.myapplication.R;
import com.example.myapplication.entity.Applyinfo;
import com.example.myapplication.entity.Courseinfo;
import com.example.myapplication.entity.Studentinfo;
import com.example.myapplication.utils.CommonUtils;

import java.util.List;
import java.util.logging.LogRecord;

public class LvCourseinfoAdapter extends BaseAdapter{
    private Context context; //上下文信息
    private List<Courseinfo> courseinfoList; //课程信息

    private ApplyDao applyDao;

    private Applyinfo applyinfo;

    private StudentDao studentDao;

    private Studentinfo studentinfo;
    private int id;
    private volatile int getid;

    private android.os.Handler handler;

    public LvCourseinfoAdapter() {
    }

    public LvCourseinfoAdapter(Context context, List<Courseinfo> courseinfoList) {
        this.context = context;
        this.courseinfoList = courseinfoList;
    }

    public void setCourseinfoList(List<Courseinfo> courseinfoList) {
        this.courseinfoList = courseinfoList;
    }

    @Override
    public int getCount() {
        return courseinfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return courseinfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        applyinfo = new Applyinfo();
        applyDao = new ApplyDao();
        studentDao = new StudentDao();
        handler = new Handler(Looper.getMainLooper());
        SharedPreferences sharedPreferences_id = context.getSharedPreferences("id",Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences_identity = context.getSharedPreferences("identity",Context.MODE_PRIVATE);
        id = Integer.parseInt(sharedPreferences_id.getString("id",null));
        String identity = sharedPreferences_identity.getString("identity",null);
        new Thread(new Runnable() {
            @Override
            public void run() {
               studentinfo = studentDao.getStudentBySid(id);
            }
        }).start();
        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.fragment_course_item,null);
            viewHolder = new ViewHolder();

            viewHolder.tv_Cid = convertView.findViewById(R.id.qtv_cid);
            viewHolder.tv_Stname = convertView.findViewById(R.id.qtv_Stname);
            viewHolder.tv_Cname = convertView.findViewById(R.id.qtv_Cname);
            viewHolder.tv_Score = convertView.findViewById(R.id.qtv_score);

            viewHolder.btn_choose = convertView.findViewById(R.id.qbtn_access);

            if (!identity.equals("学生")){
                viewHolder.btn_choose.setVisibility(View.INVISIBLE);
            }


            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Courseinfo item = courseinfoList.get(position);
        viewHolder.tv_Cname.setText(item.getCname());
        viewHolder.tv_Cid.setText(item.getCid()+".");
        viewHolder.tv_Stname.setText("主讲教师： "+item.getStname());
        viewHolder.tv_Score.setText(item.getScore()+"学分");


        viewHolder.btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(item.getCname());
                builder.setMessage("你要选择这门课程吗？");
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Looper.prepare();
                                applyinfo = applyDao.getApplyBySidAndCid(id,item.getCid());
                                if (applyinfo != null){
                                    final EditText text_fail = new EditText(v.getContext());
                                    AlertDialog.Builder builder_fail = new AlertDialog.Builder(v.getContext());
                                    builder_fail.setTitle("选课失败");
                                    builder_fail.setMessage("你已选择过此课程");
                                    builder_fail.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    AlertDialog faildialog = builder_fail.create();
                                    faildialog.show();
                                }else {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {

                                            final EditText text = new EditText(v.getContext());
                                            AlertDialog.Builder builder_yes = new AlertDialog.Builder(v.getContext());
                                            builder_yes.setTitle("选择课程: "+item.getCname());
                                            builder_yes.setMessage("请输入选择课程的原因:");
                                            builder_yes.setView(text);
                                            builder_yes.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                }
                                            });
                                            builder_yes.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    applyinfo = new Applyinfo();
                                                    applyinfo.setSid(id);
                                                    applyinfo.setCid(item.getCid());
                                                    applyinfo.setProcess("主讲教师审批中");
                                                    applyinfo.setReason(text.getText().toString().trim());
                                                    applyinfo.setCname(item.getCname());
                                                    applyinfo.setStname(item.getStname());
                                                    applyinfo.setSname(studentinfo.getSname());
                                                    applyinfo.setScore(item.getScore());
                                                    new Thread(new Runnable() {
                                                        @Override
                                                        public void run() {Looper.prepare();
                                                            int iRow = applyDao.addApply(applyinfo);
                                                            CommonUtils.showShortMsg(v.getContext(),"操作成功");
                                                            Looper.loop();
                                                        }
                                                    }).start();

                                                }
                                            });
                                            AlertDialog alert = builder_yes.create();
                                            alert.show();

                                        }
                                    });

                                }
                                Looper.loop();
                            }
                        }).start();

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        ViewHolder finalViewHolder = viewHolder;
        return convertView;
    }

    //自定义内部类
    private class ViewHolder{
        private TextView tv_Cid, tv_Cname, tv_Stname, tv_Score;
        private Button btn_choose;
    }
}
