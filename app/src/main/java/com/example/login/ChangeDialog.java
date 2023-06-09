package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class ChangeDialog extends AppCompatDialogFragment {

    private com.google.firebase.auth.FirebaseAuth FirebaseAuth;
    private DatabaseReference DatabaseRef;

    ChageDialogInterface dialogInterface1;
    EditText changepwd, changename;
    @NonNull
    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.activity_change, null);

        builder.setView(view);
        builder.setTitle("개인정보변경");
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 돌아가기
            }
        });
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //setText
                //String new_pwd = changepwd.getText().toString();
                String new_name = changename.getText().toString();
                dialogInterface1.applyTexts(new_name);
            }
        });

        //changepwd = view.findViewById(R.id.change_pwd);
        changename = view.findViewById(R.id.change_name);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);

        dialogInterface1 = (ChageDialogInterface)context;
    }
    public interface ChageDialogInterface{
        void applyTexts(String stringone);
    }
}