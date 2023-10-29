package com.example.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button LoginButton;

    private TextView IDText;
    private TextView PasswordText;
    private StudentDao studentDao;
    private SteacherDao steacherDao;

    private MteacherDao mteacherDao;

    private Handler mainHandler; //主线程

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        LoginButton = findViewById(R.id.LoginButton);

        IDText = findViewById(R.id.IDText);
        PasswordText = findViewById(R.id.PasswordText);

        LoginButton.setOnClickListener(this);

        studentDao = new StudentDao();
        steacherDao = new SteacherDao();
        mteacherDao = new MteacherDao();

        mainHandler = new Handler(getMainLooper()); //获取主线程
    }

    public void onClick(View v) {
        if (v.getId() == R.id.LoginButton) {
            doLogin();
        }
    }

    private void doLogin() {
        final String id = IDText.getText().toString().trim();
        final String password = PasswordText.getText().toString().trim();
        SharedPreferences idsharedPreferences = getSharedPreferences("id", Context.MODE_PRIVATE);
        SharedPreferences identity = getSharedPreferences("identity", Context.MODE_PRIVATE);
        SharedPreferences.Editor ideditor = idsharedPreferences.edit();
        SharedPreferences.Editor identityeditor = identity.edit();
        ideditor.putString("id", IDText.getText().toString().trim());
        ideditor.commit();
        if (TextUtils.isEmpty(id)) {
            CommonUtils.showShortMsg(this, "请输入账号");
            IDText.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            CommonUtils.showShortMsg(this, "请输入密码");
            PasswordText.requestFocus();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final Studentinfo item1 = studentDao.getStudentBySidAndSpassword(Integer.parseInt(id), password);
                    final Steacherinfo item2 = steacherDao.getSteacherByStidAndStpassword(Integer.parseInt(id), password);
                    final Mteacherinfo item3 = mteacherDao.getMteacherByMtidAndMtpassword(Integer.parseInt(id), password);
                    //判断用户身份并登录
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (item1 == null && item2 == null && item3 == null) {
                                CommonUtils.showShortMsg(LoginActivity.this, "用户名或密码错误");
                            } else if (item1 != null) {
                                Intent intent_student = new Intent(LoginActivity.this, Main_Activity.class);
                                identityeditor.putString("identity", "学生");
                                identityeditor.commit();
                                IDText.setText("");
                                PasswordText.setText("");
                                startActivity(intent_student);
                            } else if (item2 != null) {
                                Intent intent_steacher = new Intent(LoginActivity.this, Main_Activity.class);
                                identityeditor.putString("identity", "主讲教师");
                                identityeditor.commit();
                                IDText.setText("");
                                PasswordText.setText("");
                                startActivity(intent_steacher);
                            } else {
                                Intent intent_mteacher = new Intent(LoginActivity.this, Main_Activity.class);
                                identityeditor.putString("identity", "主管教师");
                                identityeditor.commit();
                                IDText.setText("");
                                PasswordText.setText("");
                                startActivity(intent_mteacher);
                            }
                        }
                    });
                }
            }).start();
        }
    }
}
