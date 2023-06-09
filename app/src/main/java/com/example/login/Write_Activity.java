package com.example.login;

import androidx.annotation.AnimatorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Write_Activity extends AppCompatActivity {

    private com.google.firebase.auth.FirebaseAuth FirebaseAuth;
    private DatabaseReference DatabaseRef;

    TessBaseAPI tess;
    String dataPath ="";
    Button btnCamera;
    ImageView imageView;
    final static int TAKE_PICTURE = 1;
    final static int CROP_PICTURE = 2;
    final static int TAKE_PICTURE2 = 3;
    final static int CROP_PICTURE2 = 4;
    private Uri pictureUri;
    Bitmap photo;

    List<UserAccount> userList = new ArrayList<>();
    List<Userpost> userpostList = new ArrayList<>();
    List<String> datelist = new ArrayList<String>();

    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        dataPath = getFilesDir()+"/tesseract/";

        checkFile(new File(dataPath + "tessdata/"), "kor");
        checkFile(new File(dataPath + "tessdata/"), "eng");

        String lang = "kor+eng";
        tess = new TessBaseAPI();
        tess.init(dataPath,lang);

        Button btnCamera = findViewById(R.id.btn_namecamera);
        Button btnCamera2 = findViewById(R.id.btn_reviewcamera);
        Button Home = findViewById(R.id.btn_home);
        Button insert = findViewById(R.id.btn_insert);
        TextView OCRTextView1 = (TextView) findViewById(R.id.edit_bookname);
        TextView OCRTextView2 = (TextView) findViewById(R.id.edit_bookreview);



        DatabaseRef = FirebaseDatabase.getInstance().getReference(
                "software"
        );

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth = FirebaseAuth.getInstance();
                DatabaseRef = FirebaseDatabase.getInstance().getReference(
                        "software"
                );
                FirebaseUser firebaseUser = FirebaseAuth.getCurrentUser();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                //Date 객체 사용
                Date date = new Date();
                String time = simpleDateFormat.format(date);
                String strtime = time.toString();

                String strbookname = OCRTextView1.getText().toString();
                String strbookreview = OCRTextView2.getText().toString();


                Userpost post = new Userpost();
                post.setBookname(strbookname);
                post.setBookreview(strbookreview);

                if (strbookname.length() == 0 || strbookreview.length() == 0) {
                    Toast.makeText(Write_Activity.this, "입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    //DatabaseRef.child("Review").child(firebaseUser.getUid()).child(strtime)).setValue(post);
                    DatabaseRef.child("Review").child(firebaseUser.getUid()).child(strbookname).setValue(post);
                    //datelist.add(strtime);
                    userpostList.add(post);
                    Toast.makeText(Write_Activity.this, "입력완료", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Write_Activity.this, MainActivity.class);
                    startActivity(intent);
                }


//                DatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                        for(DataSnapshot datasnapshot:snapshot.getChildren()){
//
//                            FirebaseUser firebaseUser = FirebaseAuth.getCurrentUser();
//
//                            String strcount = Integer.toString(count);
//
//                            String strbookname = OCRTextView1.getText().toString();
//                            String strbookreview = OCRTextView2.getText().toString();
//
//
//                            FirebaseDatabase database = FirebaseDatabase.getInstance();
//                            DatabaseRef = database.getInstance().getReference(
//                                    "software"
//                            );
//
//                            Userpost post = new Userpost();
//                            post.setBookname(strbookname);
//                            post.setBookreview(strbookreview);
//
//                            Userpost v = datasnapshot.getValue(Userpost.class);
//                            userpostList.add(v);
//                            count += 1;
//                            DatabaseRef.child("Review").child(firebaseUser.getUid()).child(strcount).setValue(post);
//
//                            Toast.makeText(Write_Activity.this, "입력완료", Toast.LENGTH_SHORT).show();
//
//                            Intent intent = new Intent(Write_Activity.this, MainActivity.class);
//                            startActivity(intent);
//
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
            }
        });



        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Write_Activity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    // 버튼 눌렀을 때, 카메라에서 이미지 가져오기

                    case R.id.btn_namecamera:
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // 임시로 사용할 파일의 경로를 생성
                        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
                        pictureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

                        cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, pictureUri);
                        cameraIntent.putExtra("return-data", true);
                        startActivityForResult(cameraIntent, CROP_PICTURE);
                        break;
                }
            }
        });

        btnCamera2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    // 버튼 눌렀을 때, 카메라에서 이미지 가져오기

                    case R.id.btn_reviewcamera:
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // 임시로 사용할 파일의 경로를 생성
                        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
                        pictureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

                        cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, pictureUri);
                        cameraIntent.putExtra("return-data", true);
                        startActivityForResult(cameraIntent, CROP_PICTURE2);
                        break;
                }
            }
        });

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build()); //권한 요청
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //안드로이드 버전확인 //권한 허용이 됐는지 확인
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            } else { //권한 허용 요청
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
        //processImage(photo);
        //processImage(BitmapFactory.decodeResource(getResources(),CROP_PICTURE));

    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED){

        }
    }


    @Override protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //크롭된 이미지 가져와서 이미지뷰에 보여주기

            case TAKE_PICTURE2:
                if (resultCode == RESULT_OK && data.hasExtra("data")) { //데이터를 가지고 있는지 확인
                    final Bundle extras = data.getExtras();

                    if(extras != null) {
                        photo = extras.getParcelable("data");
                        //크롭한 이미지 가져오기
                        processImage2(photo);
                        //imageView.setImageBitmap(photo); //이미지뷰에 넣기
                    } // 임시 파일 삭제

                    File f = new File(pictureUri.getPath());
                    if(f.exists())
                        f.delete();

                    break;
                }
                break; // 이미지 크롭

            case CROP_PICTURE2: {
                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다. // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(pictureUri, "image/*");

                intent.putExtra("outputX", 600);
                //크롭한 이미지 x축 크기
                intent.putExtra("outputY", 200); //크롭한 이미지 y축 크기
                intent.putExtra("aspectX", 3); //크롭 박스의 x축 비율
                intent.putExtra("aspectY", 1); //크롭 박스의 y축 비율
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, TAKE_PICTURE2);

                break;
            }

            case TAKE_PICTURE:
                if (resultCode == RESULT_OK && data.hasExtra("data")) { //데이터를 가지고 있는지 확인
                    final Bundle extras = data.getExtras();

                    if(extras != null) {
                        photo = extras.getParcelable("data");
                        //크롭한 이미지 가져오기
                        processImage(photo);
                        //imageView.setImageBitmap(photo); //이미지뷰에 넣기
                    } // 임시 파일 삭제

                    File f = new File(pictureUri.getPath());
                    if(f.exists())
                        f.delete();

                    break;
                }
                break; // 이미지 크롭


            case CROP_PICTURE: {
                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다. // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(pictureUri, "image/*");

                intent.putExtra("outputX", 600);
                //크롭한 이미지 x축 크기
                intent.putExtra("outputY", 200); //크롭한 이미지 y축 크기
                intent.putExtra("aspectX", 3); //크롭 박스의 x축 비율
                intent.putExtra("aspectY", 1); //크롭 박스의 y축 비율
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, TAKE_PICTURE);

                break;
            }
        }
    }
    public void processImage(Bitmap bitmap){
        //Toast.makeText(getApplicationContext(),"image complext -> long time", Toast.LENGTH_LONG).show();
        String OCRresult = null;
        tess.setImage(bitmap);
        OCRresult = tess.getUTF8Text();
        TextView OCRTextView1 = (TextView) findViewById(R.id.edit_bookname);

        OCRTextView1.setText(OCRresult);
    }
    public void processImage2(Bitmap bitmap){
        //Toast.makeText(getApplicationContext(),"image complext -> long time", Toast.LENGTH_LONG).show();
        String OCRresult = null;
        tess.setImage(bitmap);
        OCRresult = tess.getUTF8Text();
        TextView OCRTextView2 = (TextView) findViewById(R.id.edit_bookreview);

        OCRTextView2.setText(OCRresult);
    }



    private void copyFile(String lang) {
        try {
            String filepath = dataPath + "/tessdata/" + lang + ".traineddata";

            AssetManager assetManager = getAssets();

            InputStream inStream = assetManager.open("tessdata/" + lang + ".traineddata");
            OutputStream outStream = new FileOutputStream(filepath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, read);
            }
            outStream.flush();
            outStream.close();
            inStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkFile(File dir, String lang){
        if(!dir.exists()&&dir.mkdirs()){
            copyFile(lang);
        }
        if(dir.exists()){
            String datafilePath = dataPath+"/tessdata/"+lang+".traineddata";
            File datafile = new File(datafilePath);
            if(!datafile.exists()){
                copyFile(lang);
            }
        }
    }
}