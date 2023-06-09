package com.example.login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

public class GoalDialog extends AppCompatDialogFragment {
    GoalDialog.GoalDialogInterface dialogInterface1;
    EditText bookname;
    @NonNull
    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState){


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.activity_goal_dialog, null);


        builder.setView(view)
                .setTitle("목표설정")
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //돌아가기
                    }
                })
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //확인
                        String goalcount = bookname.getText().toString();
                        dialogInterface1.applyInteger(goalcount);
                    }
                });

        bookname = view.findViewById(R.id.goal_bookname);


        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);

        dialogInterface1 = (GoalDialog.GoalDialogInterface) context;
    }

    public interface GoalDialogInterface {
        void applyInteger(String stringone);
    }
}
