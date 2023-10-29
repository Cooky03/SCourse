package com.example.myapplication.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.fragment.course_fragment;
import com.example.myapplication.fragment.me_fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;




@RequiresApi(api = Build.VERSION_CODES.M)
public class Main_Activity extends AppCompatActivity {
    private FrameLayout mContainer;
    private BottomNavigationView mBottomNavigationView;
    private me_fragment me_fragment;

    private course_fragment course_fragment;

    private ListView list_course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        me_fragment = new me_fragment();
        course_fragment = new course_fragment();

        mContainer = findViewById(R.id.view_stu_main);
        mBottomNavigationView = findViewById(R.id.bottomNavigationView);
        switchFragment(course_fragment);
        mBottomNavigationView.setOnItemSelectedListener(onItemSelectedListener);

    }



    private NavigationBarView.OnItemSelectedListener onItemSelectedListener
            = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // 点击Tab, 切换对应的 Fragment
            if(item.getItemId() == R.id.choose){
                course_fragment = new course_fragment();
                switchFragment(course_fragment);
            }
            if (item.getItemId() == R.id.me)
                switchFragment(me_fragment);
            return true;
        }
    };

    public void switchFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.view_stu_main,fragment).commitNow();
    }
}

