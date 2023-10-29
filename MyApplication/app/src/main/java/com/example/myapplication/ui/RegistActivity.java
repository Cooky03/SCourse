package com.example.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Dao.MteacherDao;
import com.example.myapplication.Dao.SteacherDao;
import com.example.myapplication.Dao.StudentDao;
import com.example.myapplication.R;
import com.example.myapplication.entity.Mteacherinfo;
import com.example.myapplication.entity.Steacherinfo;
import com.example.myapplication.entity.Studentinfo;
import com.example.myapplication.utils.CommonUtils;

public class RegistActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_yes;
    private Button btn_cancel;
    private TextView rtv_id,etv_oldpwd,etv_newpwd,etv_checkpwd;

    private StudentDao studentDao;
    private SteacherDao steacherDao;
    private MteacherDao mteacherDao;
    private String id,identity;

    private Studentinfo studentinfo;
    private Steacherinfo steacherinfo;
    private Mteacherinfo mteacherinfo;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        SharedPreferences iddata = getApplicationContext().getSharedPreferences("id", Context.MODE_PRIVATE);
        SharedPreferences identitydata = getApplicationContext().getSharedPreferences("identity",Context.MODE_PRIVATE);
        id = iddata.getString("id",null);
        identity = identitydata.getString("identity",null);

        btn_yes = findViewById(R.id.btn_yes);
        btn_cancel = findViewById(R.id.btn_cancel);

        rtv_id = findViewById(R.id.rtv_id);
        etv_oldpwd = findViewById(R.id.etv_oldpwd);
        etv_newpwd = findViewById(R.id.etv_newpwd);
        etv_checkpwd = findViewById(R.id.etv_checkpwd);
        handler = new Handler(Looper.getMainLooper());
        rtv_id.setText("当前用户ID：" + id);

        studentDao = new StudentDao();
        steacherDao = new SteacherDao();
        mteacherDao = new MteacherDao();

        btn_yes.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_yes){
            final String oldpwd,newpwd,checkpwd;
            oldpwd = etv_oldpwd.getText().toString().trim();
            newpwd = etv_newpwd.getText().toString().trim();
            checkpwd = etv_checkpwd.getText().toString().trim();
            if (TextUtils.isEmpty(oldpwd)){
                CommonUtils.showShortMsg(this,"请输入原密码");
                etv_oldpwd.requestFocus();
            }else if (TextUtils.isEmpty(newpwd)){
                CommonUtils.showShortMsg(this,"请输入新密码");
                etv_newpwd.requestFocus();
            }else if (TextUtils.isEmpty(checkpwd)){
                CommonUtils.showShortMsg(this,"确认密码未输入");
                etv_checkpwd.requestFocus();
            }
            else {
            switch (identity) {
                case "学生":
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            studentinfo = studentDao.getStudentBySidAndSpassword(Integer.parseInt(id), oldpwd);
                            if (studentinfo == null)
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        CommonUtils.showShortMsg(RegistActivity.this, "原密码错误");
                                    }
                                });
                            else if (!newpwd.equals(checkpwd))
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        CommonUtils.showShortMsg(RegistActivity.this, "两次输入新密码不一致");
                                    }
                                });
                            else if(newpwd.equals(oldpwd))
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        CommonUtils.showShortMsg(RegistActivity.this, "不能和原密码一致");
                                    }
                                });
                            else {
                                studentinfo.setSpassword(newpwd);
                                studentDao.editStudent(studentinfo);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        CommonUtils.showShortMsg(RegistActivity.this, "操作成功!");
                                        finish();
                                    }
                                });
                            }
                        }
                    }).start();
                    break;
                case "主讲教师":
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            steacherinfo = steacherDao.getSteacherByStidAndStpassword(Integer.parseInt(id), oldpwd);
                            if (steacherinfo == null)
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        CommonUtils.showShortMsg(RegistActivity.this, "原密码错误");
                                    }
                                });
                            else if (!newpwd.equals(checkpwd))
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        System.out.println(newpwd + "   " + checkpwd);
                                        CommonUtils.showShortMsg(RegistActivity.this, "两次输入新密码不一致");
                                    }
                                });
                            else if(newpwd.equals(oldpwd))
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        CommonUtils.showShortMsg(RegistActivity.this, "不能和原密码一致");
                                    }
                                });
                            else {
                                steacherinfo.setStpassword(newpwd);
                                steacherDao.editSteacher(steacherinfo);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        CommonUtils.showShortMsg(RegistActivity.this, "操作成功!");
                                        finish();
                                    }
                                });
                            }
                        }
                    }).start();
                    break;
                case "主管教师":
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mteacherinfo = mteacherDao.getMteacherByMtidAndMtpassword(Integer.parseInt(id), oldpwd);
                            if (mteacherinfo == null)
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        CommonUtils.showShortMsg(RegistActivity.this, "原密码错误");
                                    }
                                });
                            else if (!newpwd.equals(checkpwd))
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        System.out.println(newpwd + "   " + checkpwd);
                                        CommonUtils.showShortMsg(RegistActivity.this, "两次输入新密码不一致");
                                    }
                                });
                            else if(newpwd.equals(oldpwd))
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        CommonUtils.showShortMsg(RegistActivity.this, "不能和原密码一致");
                                    }
                                });
                            else {
                                mteacherinfo.setMtpassword(newpwd);
                                mteacherDao.editMteacher(mteacherinfo);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        CommonUtils.showShortMsg(RegistActivity.this, "操作成功!");
                                        finish();
                                    }
                                });
                            }
                        }
                    }).start();
                    break;
            }
            }
        }
        if (v.getId() == R.id.btn_cancel){
            finish();
        }
    }
}