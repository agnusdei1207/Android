package com.example.a2021_12_31;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class Fragment2 extends Fragment {
    TextView tv_time;
    TextView tv_score;
    ImageView[] dodos = new ImageView[9]; // 두더지들 저장할 배열~ (순서대로)
    int score = 0;
    dodoThread[] dodoThreads = new dodoThread[dodos.length]; // 두더지의 개수만큼 Thread 저장공간 생성!

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // onCreateView의 하는 일?
        // xml코드로 디자인 한 fragment_2.xml 파일을 java 객체로 바꾼 (inflate) 다음에 리턴~~
        View v = inflater.inflate(R.layout.fragment_2, container, false);

        tv_time = v.findViewById(R.id.tv_time);
        tv_score = v.findViewById(R.id.tv_score);

        for (int i = 0; i < dodos.length; i++) {
            final int temp = i;
            // 변수명(String)으로 주소 or 아이디 (int) 찾기!
            int dodoId = getContext().getResources()
                    .getIdentifier("imageView" + (i + 1), "id", getContext().getPackageName());

            // 1) i번째 두더지 찾고
            dodos[i] = v.findViewById(dodoId);
            // 2) 모든 두더지 올라오게! setImageResource 해주세요!
            dodos[i].setImageResource(R.drawable.off);
            dodos[i].setTag(0);

            dodoThreads[i] = new dodoThread(dodos[i]);
            dodoThreads[i].start();


            // 3) 두더지 클릭하면 "클릭!" 이라고 Toast 띄우기
            dodos[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (v.getTag().equals(1)){
                        Toast.makeText(getActivity().getApplicationContext(), temp + "잡았다!", Toast.LENGTH_SHORT).show();
                        score++;
                        v.setTag(0);
                    }else{
                        score--;
                    }
                    tv_score.setText("잡은 갯수 : " + score);
                }
            });
        }



        // 2) Thread 실행(start)하는 법!!
        // 2-1) 객체를 생성한다.
        TimeThread time = new TimeThread(5);
        // 2-2) start 메소드를 호출한다.
        time.start();


        // Thread 멈추는 방법!!!
        // 1) interrupt 메소드를 호출한다.
        // -> Thread 내부에서 interrupt Exception 발생!
        // 2) Exception을 처리하는 catch 블럭에서 return 키워드를 사용하여 run메소드를 종료시킨다!
        //      time.interrupt();


        return v;
    }

    Handler aetuk = new Handler() {
        // 전달받은 메세지 처리하는 메소드!
        @Override
        public void handleMessage(@NonNull Message msg) {
            tv_time.setText(msg.arg1 + "");

            if (msg.arg1 == 0){
                for(int i = 0; i<dodoThreads.length; i++){
                    dodoThreads[i].interrupt();
                }
            }

        }
    };


    // 숫자 n 부터 1까지 세는 Thread 만들기~
    // 1) Thread 설계(Class)하기!
    // 2) Thread 실행(start)하기!

    class TimeThread extends Thread {
        private int start; // 1-1) 시작할 숫자

        public TimeThread(int start) {
            this.start = start;
        }

        // 1-2) Thread 실행시키면 동작할 메소드 만들기~
        @Override
        public void run() { // 실행하면 딱 한번 호출되는 메소드~~
            // start ~ 0까지 1씩 감소하면서 tv_time에다가 출력~~


            for (int i = start; i >= 0; i--) {
                // ★★★ subThread에서는 UI 손댈 수 있다? X
                // setText, setImageResourse X
                // Handler한테 도움을 요청해야해!
                // => Message (객체) 를 보내야해~

                Message msg = new Message();
                msg.arg1 = i;
                aetuk.sendMessage(msg);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    Handler dodoHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            // 각각의 Thread가 보내준 두더지 정보 꺼내다가
            // on으로 이미지 바꿔야함~~~
            ImageView dodo = (ImageView) msg.obj;
            dodo.setImageResource(msg.arg1);
            dodo.setTag(msg.arg2);
        }
    };


    // 두더지 올라갔다 내려갔다 하게 만드는 Thread 설계~~
    class dodoThread extends Thread {

        private ImageView dodo; // 담당두더지!

        public dodoThread(ImageView dodo){
            this.dodo = dodo;
        }

        @Override
        public void run() {
            // 두더지는 멈추지 않아~
            while(true){
                try {
                    int offTime = new Random().nextInt(5000) + 500; // 0.5초 ~ 5.5초 사이만큼 내려가 있음!

                    Thread.sleep(offTime); // 쉰다.

                    // offTime만큼 쉬었으니 사진을 on으로 바꿔달라!
                    Message msg = new Message();
                    msg.obj = dodo;
                    msg.arg1 = R.drawable.on;
                    msg.arg2 = 1;

                    // 핸들러한테 쏘기!
                    dodoHandler.sendMessage(msg);

                    // onTime만큼 쉬었으니 사진을 다시 off로 바꿔달라!
                    int onTime = new Random().nextInt(1000) + 500; // 0.5~1.5초 사이만큼 올라와 있음!

                    Thread.sleep(onTime);

                    msg = new Message();
                    msg.obj = dodo;
                    msg.arg1 = R.drawable.off;
                    msg.arg2 = 0;

                    dodoHandler.sendMessage(msg);


                } catch (InterruptedException e) {
                    return; // interrupt Exception 발생 시 run메소드 종료~~
                }
            }
        }
    }


}