package com.example.myapplication.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.Dao.ApplyDao;
import com.example.myapplication.R;
import com.example.myapplication.adapter.queryApplyAdapter;
import com.example.myapplication.entity.Applyinfo;

import java.util.ArrayList;
import java.util.List;

public class QueryActivity extends Activity implements View.OnClickListener {

    private ListView qlv_course;

    private TextView qtv_noq;
    private ApplyDao applyDao;
    
    private List<Applyinfo> applyinfoList;
    
    private Handler handler;
    
    private SharedPreferences idaata;
    private SharedPreferences identitydata;
    
    private com.example.myapplication.adapter.queryApplyAdapter lvApplyinfoAdapter;
    private int id;
    private String identity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        qtv_noq = findViewById(R.id.qtv_noq);
        qlv_course = findViewById(R.id.qlv_course);

        idaata = getApplicationContext().getSharedPreferences("id", Context.MODE_PRIVATE);
        identitydata =getApplicationContext().getSharedPreferences("identity",Context.MODE_PRIVATE);
        id = Integer.parseInt(idaata.getString("id","20211001"));
        identity = identitydata.getString("identity","学生");

        applyDao = new ApplyDao();
        applyinfoList = new ArrayList<>();
        handler = new Handler(Looper.getMainLooper());

        qlv_course.setEmptyView(qtv_noq);

        lvApplyinfoAdapter = null;
        switch (identity){
            case "学生":
                loadStuApplyDb();
                break;
            case "主讲教师":
                loadStcApplyDb();
                break;
            case "主管教师":
                loadMtcApplyDb();
                break;
        }

        qlv_course.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                switch (identity){
                    case "学生":
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Looper.prepare();
                                applyinfoList = applyDao.getApplyBySid(id);
                                AlertDialog.Builder builder_stu = new AlertDialog.Builder(QueryActivity.this);
                                builder_stu.setTitle("申请/驳回理由");
                                builder_stu.setMessage(applyinfoList.get(position).getReason());
                                AlertDialog alertDialog_stu = builder_stu.create();
                                alertDialog_stu.show();
                                Looper.loop();
                            }
                        }).start();
                        break;
                    case "主讲教师":
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Looper.prepare();
                                    applyinfoList = applyDao.getApplyByStid(id);
                                    AlertDialog.Builder builder_stc = new AlertDialog.Builder(QueryActivity.this);
                                    builder_stc.setTitle("申请/驳回理由");
                                    builder_stc.setMessage(applyinfoList.get(position).getReason());
                                    AlertDialog alertDialog_stc = builder_stc.create();
                                    alertDialog_stc.show();
                                    Looper.loop();
                                }
                            }).start();
                        break;
                    case "主管教师":
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Looper.prepare();
                                applyinfoList = applyDao.getApplyByMtid(id);
                                AlertDialog.Builder builder_mtc = new AlertDialog.Builder(QueryActivity.this);
                                builder_mtc.setTitle("申请/驳回理由");
                                builder_mtc.setMessage(applyinfoList.get(position).getReason());
                                AlertDialog alertDialog_mtc = builder_mtc.create();
                                alertDialog_mtc.show();
                                Looper.loop();
                            }
                        }).start();
                        break;
                }
            }
        });
        
    }

    private void loadStuApplyDb(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                qtv_noq.setText("无选课信息");
                applyinfoList = applyDao.getApplyBySid(id);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        showLvData();
                    }
                });
            }
        }).start();
    }

    private void loadStcApplyDb(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                qtv_noq.setText("无需要审批的申请");
                applyinfoList = applyDao.getApplyByStid(id);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        showLvData();
                    }
                });
            }
        }).start();
    }

    private void loadMtcApplyDb(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                qtv_noq.setText("无需要审批的申请");
                applyinfoList = applyDao.getApplyByMtid(id);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            showLvData();
                        }
                    });
            }
        }).start();
    }

    private void showLvData(){
        if (lvApplyinfoAdapter == null){
            lvApplyinfoAdapter = new queryApplyAdapter(getApplicationContext(),applyinfoList);
            qlv_course.setAdapter(lvApplyinfoAdapter);
        }else {
            lvApplyinfoAdapter.setApplyinfoList(applyinfoList);
            lvApplyinfoAdapter.notifyDataSetChanged();
        }
    }
    
    @Override
    public void onClick(View v) {

    }
}