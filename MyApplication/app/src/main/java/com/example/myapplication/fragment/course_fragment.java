package com.example.myapplication.fragment;

import static android.os.Looper.getMainLooper;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.Dao.CourseDao;
import com.example.myapplication.R;
import com.example.myapplication.adapter.LvCourseinfoAdapter;
import com.example.myapplication.entity.Courseinfo;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link course_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class course_fragment extends Fragment implements View.OnClickListener{


    private CourseDao courseDao;

    private Button btn_search;
    private TextView edit_course,tv_noc;

    private List<Courseinfo> courseinfoList;

    private ListView lv_course;
    private LvCourseinfoAdapter lvCourseinfoAdapter;

    private Handler handler;
    private Handler subhandler;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public course_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment course_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static course_fragment newInstance(String param1, String param2) {
        course_fragment fragment = new course_fragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        iniView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View square = inflater.inflate(R.layout.fragment_course_fragment,container,false);
        lv_course = square.findViewById(R.id.qlv_course);
        btn_search = square.findViewById(R.id.btn_search);
        edit_course = square.findViewById(R.id.edit_course);
        tv_noc = square.findViewById(R.id.tv_noc);

        lv_course.setEmptyView(tv_noc);
        tv_noc.setText("无搜索结果");
        btn_search.setOnClickListener(this);

        iniView();
        loadCourseDb();
        return square;
    }

    private void iniView(){
        courseDao = new CourseDao();
        courseinfoList = new ArrayList<Courseinfo>();
        handler = new Handler(getMainLooper());
        subhandler = new Handler();
    }

    private void loadCourseDb(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                courseinfoList = courseDao.getcourseList();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        showLvData();
                    }
                });
            }
        }).start();
    }

    private void loadCourseDbFromCname(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                courseinfoList = courseDao.getCourseByCname(edit_course.getText().toString().trim());
                subhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showLvData();
                    }
                });
            }
        }).start();
    }

    private void showLvData(){
        if (lvCourseinfoAdapter == null){
            lvCourseinfoAdapter = new LvCourseinfoAdapter(getContext(),courseinfoList);
            lv_course.setAdapter(lvCourseinfoAdapter);
        }else {

            lvCourseinfoAdapter.setCourseinfoList(courseinfoList);
            lvCourseinfoAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_search){
            loadCourseDbFromCname();
        }
    }
}