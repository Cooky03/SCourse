package com.example.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Dao.ApplyDao;
import com.example.myapplication.Dao.CourseDao;
import com.example.myapplication.Dao.MteacherDao;
import com.example.myapplication.Dao.SteacherDao;
import com.example.myapplication.Dao.StudentDao;
import com.example.myapplication.R;
import com.example.myapplication.entity.Mteacherinfo;
import com.example.myapplication.entity.Steacherinfo;
import com.example.myapplication.entity.Studentinfo;
import com.example.myapplication.ui.LoginActivity;
import com.example.myapplication.ui.Main_Activity;
import com.example.myapplication.ui.QueryActivity;
import com.example.myapplication.ui.RegistActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link me_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class me_fragment extends Fragment implements View.OnClickListener{

    private TextView tv_name;
    private TextView tv_uid;
    private TextView tv_identity;

    private ApplyDao applyDao;
    private CourseDao courseDao;
    private StudentDao studentDao;
    private SteacherDao steacherDao;
    private MteacherDao mteacherDao;

    private Studentinfo studentinfo;
    private Steacherinfo steacherinfo;
    private Mteacherinfo mteacherinfo;
    private Button btn_apply, btn_setpwd, btn_quit;

    private Handler handler;

    private SharedPreferences iddata,identitydata;

    private int id;
    private String identity;

    public me_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment student_fragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static me_fragment newInstance(String param1, String param2) {
        me_fragment fragment = new me_fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View square = inflater.inflate(R.layout.fragment_me_fragment, container, false);
        tv_name = square.findViewById(R.id.tv_name);
        tv_uid = square.findViewById(R.id.tv_uid);
        tv_identity = square.findViewById(R.id.tv_identity);

        btn_apply = square.findViewById(R.id.btn_apply);
        btn_setpwd = square.findViewById(R.id.btn_setpwd);
        btn_quit = square.findViewById(R.id.btn_quit);

        courseDao = new CourseDao();
        applyDao = new ApplyDao();
        studentDao = new StudentDao();
        steacherDao = new SteacherDao();
        mteacherDao = new MteacherDao();

        iddata = getContext().getSharedPreferences("id", Context.MODE_PRIVATE);
        identitydata = getContext().getSharedPreferences("identity",Context.MODE_PRIVATE);
        id = Integer.parseInt(iddata.getString("id","20211001"));
        identity = identitydata.getString("identity","学生");

        handler = new Handler(Looper.getMainLooper());

        tv_identity.setText("身份:"+ identity);
         switch (identity){
            case "学生":
                loadStuDb();
                break;
           case "主讲教师":
                loadStcDb();
                break;
            case "主管教师":
                loadMtcDb();
                break;
        }

         btn_apply.setOnClickListener(this);
         btn_setpwd.setOnClickListener(this);
         btn_quit.setOnClickListener(this);

        return square;
    }


    private void loadStuDb(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                studentinfo = studentDao.getStudentBySid(id);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_name.setText(studentinfo.getSname()+"");
                        tv_uid.setText("学号:"+studentinfo.getSid()+"");
                        btn_apply.setText("选课进度查询");
                    }
                });
            }
        }).start();
    }

    private void loadStcDb(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                steacherinfo = steacherDao.getSteacherByStid(id);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_name.setText(steacherinfo.getStname()+"");
                        tv_uid.setText("教工号:"+steacherinfo.getStid()+"");
                        btn_apply.setText("审批记录查询");
                    }
                });
            }
        }).start();
    }

    private void loadMtcDb(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mteacherinfo = mteacherDao.getMteacherByMtid(id);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_name.setText(mteacherinfo.getMtname()+"");
                        tv_uid.setText("教工号:"+mteacherinfo.getMtid()+"");
                        btn_apply.setText("审批记录查询");
                    }
                });
            }
        }).start();
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_apply){
            Intent intent_q = new Intent(getActivity(), QueryActivity.class);
            startActivity(intent_q);
        }
        if (v.getId() == R.id.btn_setpwd){
            Intent intent_rst = new Intent(getActivity(), RegistActivity.class);
            startActivity(intent_rst);
        }
        if (v.getId() == R.id.btn_quit){
            requireActivity().finish();
        }
    }
}