package com.example.myapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.Dao.ApplyDao;
import com.example.myapplication.R;
import com.example.myapplication.entity.Applyinfo;
import com.example.myapplication.entity.Courseinfo;
import com.example.myapplication.utils.CommonUtils;

import java.util.List;

public class queryApplyAdapter extends BaseAdapter{
    private Context context; //上下文信息
    private List<Courseinfo> courseinfoList; //课程信息
    private List<Applyinfo> applyinfoList;
    
    private SharedPreferences identitydata;

    private ApplyDao applyDao;

    private Applyinfo applyinfo;

    private int stu_id;

    private String stname,identity;
    private android.os.Handler handler;

    private TextView qtv_noq;

    public queryApplyAdapter() {
    }

    public queryApplyAdapter(Context context, List<Applyinfo> applyinfoList) {
        this.context = context;
        this.applyinfoList = applyinfoList;
    }

    public void setApplyinfoList(List<Applyinfo> applyinfoList) {
        this.applyinfoList = applyinfoList;
    }

    @Override
    public int getCount() {
        return applyinfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return applyinfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        applyinfo = new Applyinfo();
        applyDao = new ApplyDao();
        SharedPreferences sharedPreferences = context.getSharedPreferences("id",Context.MODE_PRIVATE);
        stu_id = Integer.parseInt(sharedPreferences.getString("id",null));

        identitydata = context.getSharedPreferences("identity",Context.MODE_PRIVATE);
        identity = identitydata.getString("identity","学生");

        handler = new Handler(Looper.getMainLooper());

        switch (identity){
            case "学生":
                ViewHolder viewHolder_stu = null;
                if (convertView == null){
                    convertView = LayoutInflater.from(context)
                            .inflate(R.layout.query_course_process,null);
                    viewHolder_stu = new ViewHolder();

                    viewHolder_stu.qtv_cid = convertView.findViewById(R.id.qtv_cid);
                    viewHolder_stu.qtv_Stname = convertView.findViewById(R.id.qtv_Stname);
                    viewHolder_stu.qtv_Cname = convertView.findViewById(R.id.qtv_Cname);
                    viewHolder_stu.qtv_Score = convertView.findViewById(R.id.qtv_score);
                    viewHolder_stu.qtv_Sid = convertView.findViewById(R.id.qtv_Sid);
                    viewHolder_stu.qtv_Sname = convertView.findViewById(R.id.qtv_Sname);
                    viewHolder_stu.qtv_process = convertView.findViewById(R.id.qtv_process);

                    convertView.setTag(viewHolder_stu);
                }else {
                    viewHolder_stu = (ViewHolder) convertView.getTag();
                }

                Applyinfo item_stu = applyinfoList.get(position);
                viewHolder_stu.qtv_Cname.setText(item_stu.getCname());
                viewHolder_stu.qtv_cid.setText(item_stu.getCid()+".");
                viewHolder_stu.qtv_Stname.setText("主讲教师： "+item_stu.getStname());
                viewHolder_stu.qtv_Score.setText(item_stu.getScore()+"学分");
                viewHolder_stu.qtv_Sname.setText("学生："+item_stu.getSname());
                viewHolder_stu.qtv_Sid.setText("学号："+item_stu.getSid());
                viewHolder_stu.qtv_process.setText("审批进度：" + item_stu.getProcess());

                if (applyinfoList.isEmpty()) qtv_noq.setText("无已选课程");

                ViewHolder finalViewHolder_stu = viewHolder_stu;
                break;
            case "主讲教师":
                ViewHolder viewHolder_stc = null;
                Applyinfo item_stc = applyinfoList.get(position);
                if (applyinfoList.isEmpty()) {
                    CommonUtils.showShortMsg(parent.getContext(), "无需要审批的申请");
                }
                if (convertView == null){
                    convertView = LayoutInflater.from(context)
                            .inflate(R.layout.query_course_item,null);
                    viewHolder_stc = new ViewHolder();

                    viewHolder_stc.qtv_cid = convertView.findViewById(R.id.qtv_cid);
                    viewHolder_stc.qtv_Stname = convertView.findViewById(R.id.qtv_Stname);
                    viewHolder_stc.qtv_Cname = convertView.findViewById(R.id.qtv_Cname);
                    viewHolder_stc.qtv_Score = convertView.findViewById(R.id.qtv_score);
                    viewHolder_stc.qtv_Sid = convertView.findViewById(R.id.qtv_Sid);
                    viewHolder_stc.qtv_Sname = convertView.findViewById(R.id.qtv_Sname);
                    viewHolder_stc.btn_access = convertView.findViewById(R.id.qbtn_access);
                    viewHolder_stc.btn_refuse = convertView.findViewById(R.id.qbtn_refuse);

                    convertView.setTag(viewHolder_stc);
                }else {
                    viewHolder_stc = (ViewHolder) convertView.getTag();
                }


                viewHolder_stc.qtv_Cname.setText(item_stc.getCname());
                viewHolder_stc.qtv_cid.setText(item_stc.getCid()+".");
                viewHolder_stc.qtv_Stname.setText("主讲教师： "+item_stc.getStname());
                viewHolder_stc.qtv_Score.setText(item_stc.getScore()+"学分");
                viewHolder_stc.qtv_Sname.setText("学生："+item_stc.getSname());
                viewHolder_stc.qtv_Sid.setText("学号："+item_stc.getSid());

                viewHolder_stc.btn_access.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        AlertDialog.Builder builder_stac = new AlertDialog.Builder(parent.getContext());
                                        builder_stac.setTitle(item_stc.getCname());
                                        builder_stac.setMessage("您是否确定通过此申请？\n"+"学生姓名："+item_stc.getSname()+"\n学号："+item_stc.getSid());
                                        builder_stac.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                        builder_stac.setNegativeButton("确认", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Looper.prepare();
                                                        item_stc.setProcess("主管教师审批中");
                                                        applyDao.editApply(item_stc);
                                                        AlertDialog.Builder builder_stacs = new AlertDialog.Builder(parent.getContext());
                                                        builder_stacs.setTitle("审批信息");
                                                        builder_stacs.setMessage("操作成功!");
                                                        AlertDialog alert_mtacs = builder_stacs.create();
                                                        alert_mtacs.show();
                                                        Looper.loop();
                                                    }
                                                }).start();
                                                applyinfoList.remove(position);
                                                queryApplyAdapter.this.notifyDataSetChanged();
                                            }
                                        });
                                        AlertDialog alertDialog_stac = builder_stac.create();
                                        alertDialog_stac.show();
                                    }
                                });
                    }
                });

                viewHolder_stc.btn_refuse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder builder_strf = new AlertDialog.Builder(parent.getContext());
                                builder_strf.setTitle(item_stc.getCname());
                                builder_strf.setMessage("您是否确定驳回此申请？\n"+"学生姓名："+item_stc.getSname()+"\n学号："+item_stc.getSid());
                                builder_strf.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                builder_strf.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        AlertDialog.Builder builder_strfs = new AlertDialog.Builder(parent.getContext());
                                        AlertDialog.Builder builder_fill = new AlertDialog.Builder(parent.getContext());
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                final EditText text = new EditText(parent.getContext());
                                                builder_fill.setTitle("驳回申请");
                                                builder_fill.setMessage("请输入驳回申请的原因:");
                                                builder_fill.setView(text);
                                                builder_fill.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.cancel();
                                                    }
                                                });
                                                builder_fill.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        item_stc.setProcess("主讲教师驳回");
                                                        item_stc.setReason(text.getText().toString().trim());
                                                        new Thread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Looper.prepare();
                                                                applyDao.editApply(item_stc);
                                                                Looper.loop();
                                                            }
                                                        }).start();
                                                        builder_strfs.setTitle("审批信息");
                                                        builder_strfs.setMessage("操作成功!");
                                                        AlertDialog alert_mtacs = builder_strfs.create();
                                                        alert_mtacs.show();

                                                        alert_mtacs.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                                            @Override
                                                            public void onDismiss(DialogInterface dialog) {
                                                                applyinfoList.remove(position);
                                                                queryApplyAdapter.this.notifyDataSetChanged();
                                                            }
                                                        });
                                                    }
                                                });
                                                AlertDialog alert_fill = builder_fill.create();
                                                alert_fill.show();
                                            }
                                        });
                                    }
                                });
                                AlertDialog alertDialog_strf = builder_strf.create();
                                alertDialog_strf.show();
                            }
                        });
                    }
                });

                ViewHolder finalViewHolder_stc = viewHolder_stc;
                break;
            case "主管教师":
                ViewHolder viewHolder_mtc = null;
                Applyinfo item_mtc = applyinfoList.get(position);
                if (item_mtc.getProcess().equals("")) {
                    System.out.println("2");
                    CommonUtils.showShortMsg(parent.getContext(), "无需要审批的申请");
                }
                System.out.println(applyinfoList.size());
                if (convertView == null){
                    convertView = LayoutInflater.from(context)
                            .inflate(R.layout.query_course_item,null);
                    viewHolder_mtc = new ViewHolder();

                    viewHolder_mtc.qtv_cid = convertView.findViewById(R.id.qtv_cid);
                    viewHolder_mtc.qtv_Stname = convertView.findViewById(R.id.qtv_Stname);
                    viewHolder_mtc.qtv_Cname = convertView.findViewById(R.id.qtv_Cname);
                    viewHolder_mtc.qtv_Score = convertView.findViewById(R.id.qtv_score);
                    viewHolder_mtc.qtv_Sid = convertView.findViewById(R.id.qtv_Sid);
                    viewHolder_mtc.qtv_Sname = convertView.findViewById(R.id.qtv_Sname);
                    viewHolder_mtc.btn_access = convertView.findViewById(R.id.qbtn_access);
                    viewHolder_mtc.btn_refuse = convertView.findViewById(R.id.qbtn_refuse);

                    convertView.setTag(viewHolder_mtc);
                }else {
                    viewHolder_mtc = (ViewHolder) convertView.getTag();
                }


                viewHolder_mtc.qtv_Cname.setText(item_mtc.getCname());
                viewHolder_mtc.qtv_cid.setText(item_mtc.getCid()+".");
                viewHolder_mtc.qtv_Stname.setText("主讲教师： "+item_mtc.getStname());
                viewHolder_mtc.qtv_Score.setText(item_mtc.getScore()+"学分");
                viewHolder_mtc.qtv_Sname.setText("学生："+item_mtc.getSname());
                viewHolder_mtc.qtv_Sid.setText("学号："+item_mtc.getSid());

                viewHolder_mtc.btn_access.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder builder_mtac = new AlertDialog.Builder(parent.getContext());
                                builder_mtac.setTitle(item_mtc.getCname());
                                builder_mtac.setMessage("您是否确定通过此课程？\n"+"学生姓名："+item_mtc.getSname()+"\n学号："+item_mtc.getSid());
                                builder_mtac.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                builder_mtac.setNegativeButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Looper.prepare();
                                                item_mtc.setProcess("审批通过");
                                                applyDao.editApply(item_mtc);
                                                AlertDialog.Builder builder_mtacs = new AlertDialog.Builder(parent.getContext());
                                                builder_mtacs.setTitle("审批信息");
                                                builder_mtacs.setMessage("操作成功!");
                                                AlertDialog alert_mtacs = builder_mtacs.create();
                                                alert_mtacs.show();
                                                Looper.loop();
                                            }
                                        }).start();
                                        applyinfoList.remove(position);
                                        queryApplyAdapter.this.notifyDataSetChanged();
                                    }
                                });
                                AlertDialog alertDialog_stac = builder_mtac.create();
                                alertDialog_stac.show();
                            }
                        });
                    }
                });

                viewHolder_mtc.btn_refuse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder builder_mtrf = new AlertDialog.Builder(parent.getContext());
                                builder_mtrf.setTitle(item_mtc.getCname());
                                builder_mtrf.setMessage("您是否确定驳回此申请？\n"+"学生姓名："+item_mtc.getSname()+"\n学号："+item_mtc.getSid());
                                builder_mtrf.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                builder_mtrf.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        AlertDialog.Builder builder_mtrfs = new AlertDialog.Builder(parent.getContext());
                                        AlertDialog.Builder builder_fill = new AlertDialog.Builder(parent.getContext());
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                final EditText text = new EditText(parent.getContext());
                                                builder_fill.setTitle("驳回申请");
                                                builder_fill.setMessage("请输入驳回申请的原因:");
                                                builder_fill.setView(text);
                                                builder_fill.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.cancel();
                                                    }
                                                });
                                                builder_fill.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        item_mtc.setProcess("主讲教师驳回");
                                                        item_mtc.setReason(text.getText().toString().trim());
                                                        new Thread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Looper.prepare();
                                                                applyDao.editApply(item_mtc);
                                                                Looper.loop();
                                                            }
                                                        }).start();
                                                        builder_mtrfs.setTitle("审批信息");
                                                        builder_mtrfs.setMessage("操作成功!");
                                                        AlertDialog alert_mtrfs = builder_mtrfs.create();
                                                        alert_mtrfs.show();

                                                        alert_mtrfs.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                                            @Override
                                                            public void onDismiss(DialogInterface dialog) {
                                                                applyinfoList.remove(position);
                                                                queryApplyAdapter.this.notifyDataSetChanged();
                                                            }
                                                        });
                                                    }
                                                });
                                                AlertDialog alert_fill = builder_fill.create();
                                                alert_fill.show();
                                            }
                                        });
                                    }
                                });
                                AlertDialog alertDialog_stac = builder_mtrf.create();
                                alertDialog_stac.show();
                            }
                        });
                    }
                });

                ViewHolder finalViewHolder_mtc = viewHolder_mtc;
                break;
        }
        return convertView;
    }
    

    //自定义内部类
    private class ViewHolder{
        private TextView qtv_cid, qtv_Cname, qtv_Stname, qtv_Score, qtv_Sname, qtv_Sid, qtv_process;
        private Button btn_access, btn_refuse;
    }
}
