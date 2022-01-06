package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
public class MainActivity extends AppCompatActivity {


    Random rd = new Random();

    int[] diceArray = {R.drawable.dice1,
            R.drawable.dice2,
            R.drawable.dice3,
            R.drawable.dice4,
            R.drawable.dice5,
            R.drawable.dice6};
    Toast toast;
    Button btn_shake;
    TextView tv_left, tv_right;
    ImageView dice1, dice2;
    int left, right, cnt_left, cnt_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_shake = findViewById(R.id.btn_shake);
        tv_left = findViewById(R.id.tv_left);
        tv_right = findViewById(R.id.tv_right);
        dice1 = findViewById(R.id.dice1);
        dice2 = findViewById(R.id.dice2);


        btn_shake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("shake", "버튼 눌림");
                left = rd.nextInt(diceArray.length);
                right = rd.nextInt(diceArray.length);

                dice1.setImageResource(diceArray[left]);
                dice2.setImageResource(diceArray[right]);

                if (left > right) {
                    Log.d("왼쪽", "승리");
                    cnt_left += 1;
                    tv_left.setText(cnt_left+"");
                } else if (left < right) {
                    Log.d("오른쪽", "승리");
                    cnt_right += 1;
                    tv_right.setText(cnt_right+"");
                } else {
                    Log.d("무승부", "무승부");
                    toast.makeText(getApplicationContext(),"무승부", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }//onCreate
}

