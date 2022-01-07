package com.example.a2021_12_31;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class Fragment4 extends Fragment {

    EditText edt_url;
    Button btn_setting;

    //환경설정 페이지
    //앱을 껏다 켜도 환경설정 유지~
    //Cache URL 에 저장해서 아무때나 가져다 사용하기!
    //값을 공유할 때 사용
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_1, container, false);

        edt_url = v.findViewById(R.id.edt_url);
        btn_setting = v.findViewById(R.id.btn_setting);

        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fragment 이기에 getContext() 사용!
                //getSharedPreferences : mySPF 라는 이름의 메모리를 가져오는데~
                //없으면 새로 만들고 없으면 가져와라 : MODE_PRIVATE
                SharedPreferences spf = getContext().getSharedPreferences("mySPF", Context.MODE_PRIVATE);
                //Cache에 입력 및 저장할 때 꼭 edit 사용!!
                spf.edit().putString("url", edt_url.getText().toString()).commit();
                //데이터 2개 이상 넣고 싶으면 key값 다르게 해서 put~ 여러번 하기!
            }
        });

        return v;
    }
}
