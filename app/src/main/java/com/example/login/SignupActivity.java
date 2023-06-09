package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity {

    private com.google.firebase.auth.FirebaseAuth FirebaseAuth;
    private DatabaseReference DatabaseRef;
    private EditText email, pwd, name;
    private Button btnsignup, btnnamecheck,btngologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        FirebaseAuth = FirebaseAuth.getInstance();
        DatabaseRef = FirebaseDatabase.getInstance().getReference(
                "software"
        );

        email = findViewById(R.id.email);
        pwd = findViewById(R.id.pwd);
        name = findViewById(R.id.name);

        btngologin = findViewById(R.id.btn_gologin);
        btnsignup = findViewById(R.id.btn_signup);
        btnnamecheck = findViewById(R.id.btn_namecheck);

        ArrayList<String>usernamelist = new ArrayList<String>();

        final int[] checkcount = {0};

        DatabaseRef.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot uid : snapshot.getChildren()){
                    usernamelist.add(uid.child("username").getValue().toString());
                    System.out.println(usernamelist);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail = email.getText().toString();
                String strPwd = pwd.getText().toString();
                String strName = name.getText().toString();

                if (email.getText().length()==0 || pwd.getText().length()==0||name.getText().length()==0){
                    Toast.makeText(SignupActivity.this, "빈칸을입력해주세요",Toast.LENGTH_SHORT).show();
                }
                else if(checkcount[0] == 0){
                    Toast.makeText(SignupActivity.this, "닉네임 중복을 체크해주세요", Toast.LENGTH_SHORT).show();

                }
                else{
                    FirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                FirebaseUser firebaseUser = FirebaseAuth.getCurrentUser();

                                UserAccount account = new UserAccount();
                                account.setEmailId(firebaseUser.getEmail());
                                account.setPassword(strPwd);
                                account.setUsername(strName);
                                DatabaseRef.child("User").child(firebaseUser.getUid()).setValue(account);
                                System.out.println(account);
                                System.out.println(firebaseUser.getUid());
                                System.out.println(DatabaseRef.child("User"));
                                Toast.makeText(SignupActivity.this, "회원가입완료", Toast.LENGTH_SHORT).show();
                            }
                            else if(checkcount[0] == 0){
                                Toast.makeText(SignupActivity.this, "닉네임 중복을 체크해주세요", Toast.LENGTH_SHORT).show();
                            }
                            else if((strPwd).length() < 6){
                                Toast.makeText(SignupActivity.this, "비밀번호는 6자리 이상이여야 합니다.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(SignupActivity.this, "회원가입실패", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        btnnamecheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(name.getText());

                if (usernamelist.contains(name.getText().toString())){
                    Toast.makeText(SignupActivity.this, "사용할 수 없는 이름입니다.",Toast.LENGTH_SHORT).show();
                }
                else if(name.getText().toString() == ""){
                    Toast.makeText(SignupActivity.this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(SignupActivity.this, "사용할 수 있는 이름입니다.",Toast.LENGTH_SHORT).show();
                }
                checkcount[0]++;
            }
        });

        // 로그인 화면으로 돌아가기
        btngologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}