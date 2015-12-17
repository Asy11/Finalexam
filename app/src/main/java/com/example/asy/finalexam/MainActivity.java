package com.example.asy.finalexam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Question> questions;
    int problemNumber;              //ArrayList의 index를 저장
    TextView textView;
    EditText editText;
    Intent intent;
    Button bt_result;
    Button bt_pre;
    Button bt_next;
    Button bt_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //problemNumber 초기화
        problemNumber = 0;

        //ArrayList 초기화
        questions = new ArrayList<Question>();

        questions.add(new Question("1. 보기 중 가장 큰 수를 고르세요\n 1)0   2)4   3)50", 3));
        questions.add(new Question("2. 보기 중 가장 작은 수를 고르세요\n 1)0   2)4   3)50", 1));
        questions.add(new Question("3. 보기 중 음수를 고르세요\n 1)-5   2)4   3)50", 1));
        questions.add(new Question("4. 보기 중 알파벳을 고르세요\n 1)0   2)A   3)50", 2));

        textView = (TextView) findViewById(R.id.tv_questions);
        textView.setText(questions.get(problemNumber).getQuestion());

        bt_next = (Button) findViewById(R.id.bt_right);
        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    problemNumber++;
                    textView.setText(questions.get(problemNumber).getQuestion());
                } catch (IndexOutOfBoundsException e) {
                    Toast.makeText(MainActivity.this.getApplicationContext(), "현재 마지막 문제입니다.", Toast.LENGTH_LONG).show();
                    problemNumber = 3;
                }
            }
        });

        bt_pre = (Button) findViewById(R.id.bt_left);
        bt_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    problemNumber--;
                    textView.setText(questions.get(problemNumber).getQuestion());
                } catch (ArrayIndexOutOfBoundsException e) {
                    Toast.makeText(MainActivity.this.getApplicationContext(), "현재 첫 번째 문제입니다.", Toast.LENGTH_LONG).show();
                    problemNumber = 0;
                }

            }
        });

        editText = (EditText) findViewById(R.id.et_input);

        bt_input = (Button) findViewById(R.id.bt_insert);
        bt_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int userans;
                try {
                    userans = Integer.parseInt(editText.getText().toString());
                    questions.get(problemNumber).setUserAnswer(userans);
                    Toast.makeText(MainActivity.this.getApplicationContext(), problemNumber + 1 + "번 문제 " + userans + "보기 선택", Toast.LENGTH_LONG).show();

                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this.getApplicationContext(), "입력값이 잘못되었습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });

        bt_result = (Button) findViewById(R.id.bt_result);
        bt_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String result = "";

                for (int i = 0; i < questions.size(); i++) {
                    if (questions.get(i).isCorrect() == true)
                        result += i + 1 + "번 문제 맞음\n";
                    else
                        result += i + 1 + "번 문제 틀림\n";
                }

                intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("TextIn", result);
                startActivity(intent);
            }
        });


    }

    public class Question {
        //질문을 담을 변수
        String question;
        //질문에 대한 답을 담는 변수
        int correctAnswer;
        //사용자가 입력한 정답을 담을 변수. 처음엔 -1로 초기화
        int userAnswer;

        //질문, 정답으로 초기화하는 생성자
        public Question(String q, int answer) {
            question = q;
            correctAnswer = answer;
            userAnswer = -1;//-1은 사용자 답 초기화 용도
        }

        //정답과 사용자의 답을 비교
        public boolean isCorrect() {
            return correctAnswer == userAnswer;
        }

        //질문을 리턴, 화면에 문제를 보여줄 때 사용한다
        public String getQuestion() {
            return question;
        }

        //사용자의 답을 세팅한다
        public void setUserAnswer(int answer) {
            userAnswer = answer;
        }

        //사용자의 답을 -1로 초기화한다
        public void resetAnswer() {
            userAnswer = -1;
        }
    }

}
