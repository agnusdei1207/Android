package com.example.a2021_12_18;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class itemAdapter extends BaseAdapter {
    // xml View객체로 만들면 사용할 수 있다. (inflate)
    // findViewById >> 대표적인 inflate
    // 하지만! inflate 는 Activity 클래스에서만 사용이 가능하다...
    // 따라서 Activity 한테서 추출해야 한다. inflate 시키는 Inflater 를!

    // Adapter 에서 사용할 데이터 변수(데이터 공간)를 만들어 놓기
    private int template;
    private ArrayList<itemVO> data;
    private Context context; //Activity 에서 보내 준 화면정보! Activity 의 권한과 고유능력!
    private LayoutInflater inflater; // 추출한 inflater 를 저장할 공간
    private Activity activity;
    private int cnt; // 제품 수량을 저장하기 위한 공간


    // 생성자! 사용하기 전에는 반드시 생성하도록 정해준다.
    public itemAdapter(int template, ArrayList<itemVO> data, Context context, Activity activity) {
        this.template = template;
        this.data = data;
        this.context = context;
        this.activity = activity;

        // 추출한 inflate 를 저장할 공간
        //getSystemService ( 블루투스, 마이크, 스피커, 센서, 진동, GPS 등등 )
        // 하드웨어 센서에 접근하는 명령어! 어떤 센서에 접근할지?
        // Object 형태로 리턴
        // 다운 캐스팅
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }//itemAdapter()

    //전체리스트 개수
    @Override
    public int getCount() {
        return data.size();
    }

    //꺼내려는 리스트 항목 순번
    @Override
    public Object getItem(int i) {
        
        return data.get(i);
    }

    //아예 안 쓰는 애.... 그냥 i
    @Override
    public long getItemId(int i) {
        return i;
    }

    // 데이터의 사이즈만큼 getView()는 실행된다. i만큼~
    // 템플릿을 하나하나 꾸며주자!
    // 아이템 하나를 그린다.
    // 2번째 파라미터 >> infalte 된 친구 view
    // i : 순번
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {  // inflate 가 처음이라면, 이전에 만들어 놓은 view가 없다면
            // template 을 객체 View로 만들거야(flate) , 어디에 넣을거야? (listview), 속성을 따라갈거니 따로 새로 지정할거니? true하면 튕김
            view = inflater.inflate(template, viewGroup, false);
        }
        // view 안에는 4개 요소 다 (버튼 포함) 가지고 오기!
        ImageView img = view.findViewById(R.id.img);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_stock = view.findViewById(R.id.tv_stock);
        Button btn_show = view.findViewById(R.id.btn_show);

        // 이미지 꾸미기! setImageResource
        img.setImageResource(data.get(i).getImgID());
        tv_title.setText(data.get(i).getTitle());
        tv_stock.setText("상품개수: " + data.get(i).getStock() + "개");

        //상세보기 버튼 클릭 시 이벤트
        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Activity처럼 활동할 수 있도록 context를 넘겨준 것!
                //Activity가 아니니까 this 가 아닌 권한이 있는 context 를 사용
                Toast.makeText(context.getApplicationContext(), "상품명 : " + data.get(i).getTitle() + "\n" + data.get(i).getStock(), Toast.LENGTH_SHORT).show();
                // 토스트 커스텀 하는 방법 : Toast toast = new Toast(context); toast.set~

                //root : 어디에 종속될 것인지?
                ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.cusdialog, null, false);
                // inflater 와 setView 사이에 꾸미기가 들어가야 한다. **
                // ConstraintLayout 형태의 layout 을 가지고 와야지 R.id.~ 에 접근할 수 있다.
                // 왜냐하면 cusdialog 를 만들 때 root 를 Constlayout으로 했기 때문!!
                ImageView img_detail = layout.findViewById(R.id.img_detail);
                EditText et_cnt = layout.findViewById(R.id.et_cnt);

                //꾸미기 시작!
                img_detail.setImageResource(data.get(i).getImgID());
                et_cnt.setText(String.valueOf(data.get(i).getStock()));
                // =============================customDialog꾸민 부분
                
                
                // 빌드시켜주는 객체를 빌더라고 칭한다.
                // 정말 삭제하시겠습니까? 처럼 화면 위에 뜨는 알림들은 AlertDialog
                // 파라미터에 Context 타입을 넣으라고 되어 있지만, 업데이트가 됐는지 Activity 형태를 넣어야 작동한다.
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                builder.setTitle(data.get(i).getTitle()); // 머릿글
                builder.setMessage(data.get(i).getStock() + ""); // 몸통글
                // builder.setMessage(String.valueOf(data.get(i).getStock()));

                // Dialog OK버튼을 눌렀을 때
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int kkk) {

                        Toast.makeText(context.getApplicationContext(), "OK누름!", Toast.LENGTH_SHORT).show();

                        //동적인 이미지뷰 : 처음부터 생기지 않고 이벤트를 실행했을 때 새로 생성됨 (동적 프로그래밍)
                        //ImageView dialog_img = new ImageView(context);
                        //dialog_img.setImageResource(data.get(i).getImgID());

                        cnt = data.get(i).getStock() - Integer.parseInt(et_cnt.getText().toString());

                        data.get(i).setStock(cnt);
                        notifyDataSetChanged(); // 새로고침 ! 값 반영하기 ! ***

                    }
                });
                
                builder.setView(layout); // 꾸민 customDialog 를 화면에 착! 붙이는 부분
                // Dialog Cancel 버튼을 눌렀을 때
                builder.setPositiveButton("Cancel", null); //버튼 설치 , null 아무일도 안 일어난다.
                builder.show(); // dialog 를 보여줌


            }//onClick
        });//btn_show setOnClickListener

        // 다음 사람을 위해 view 리턴
        return view;
    }//getView****
}
