package com.example.login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ChangeBookInfoDialog extends AppCompatDialogFragment {

    String[] items = {"철학", "종교", "사회과학", "자연과학", "기술과학", "예술", "언어", "문학", "역사", "자기계발", "여행", "기타"};

    EditText change_bookname, change_author, change_punisher, change_punishdate, change_bookreview;
    Spinner change_genre;
    String change_bookgenre;

    ChangeBookInfoDialogInterface changebookdialogInterface1;

    @NonNull
    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.activity_changebookinfo_dialog, null);

        change_bookname= view.findViewById(R.id.et_change_bookname);
        change_author =view.findViewById(R.id.et_change_author);
        change_punisher = view.findViewById(R.id.et_change_punisher);
        change_punishdate = view.findViewById(R.id.et_change_punishdate);
        change_genre = view.findViewById(R.id.spinner_change_genre);
        change_bookreview = view.findViewById(R.id.et_change_bookreview);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, items);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        change_genre.setAdapter(adapter);

        change_genre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                change_bookgenre = items[i].toString();
                //Toast.makeText(BookRegisterActivity.this, items[i]+"선택", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //
            }
        });

        builder.setView(view)
                .setTitle("책정보변경")
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //
                    }
                })
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //
                        String str_bookname = change_bookname.getText().toString();
                        String str_author = change_author.getText().toString();
                        String str_punisher = change_punisher.getText().toString();
                        String str_punishdate = change_punishdate.getText().toString();
                        String str_genre = change_bookgenre.toString();
                        String str_bookreview = change_bookreview.getText().toString();

                        changebookdialogInterface1.applyTexts(str_bookname, str_author, str_punisher, str_punishdate, str_genre, str_bookreview);
                    }
                });


        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);

        changebookdialogInterface1 = (ChangeBookInfoDialogInterface)context;
    }
    public interface ChangeBookInfoDialogInterface{
        void applyTexts(String str_bookname, String str_author, String str_punisher, String str_punishdate, String str_genre, String str_bookreview);

    }
}
