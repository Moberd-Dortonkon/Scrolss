package com.example.koolguy.scroll.groups;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.koolguy.scroll.HandBook2;
import com.example.koolguy.scroll.MainActivity;
import com.example.koolguy.scroll.R;

public class ChooseToDoVolonteer extends Fragment {


    View view;
    Button show_groups;
    Button conntect_directly;
    public ChooseToDoVolonteer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_chooset_to_do__volonteer, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        show_groups=(Button)view.findViewById(R.id.choose_to_do_showgroups);
        conntect_directly=(Button)view.findViewById(R.id.chooset_to_do_coonnect_directly);
        ImageButton imageButton = (ImageButton)view.findViewById(R.id.settings);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        view.getContext().getSharedPreferences(MainActivity.GROUP_PREFERENCES,Context.MODE_PRIVATE).edit().clear().apply();
                        getFragmentManager().beginTransaction().replace(R.id.frames,new HandBook2()).disallowAddToBackStack().commit();
                    }
                }).setTitle("Пересосздать?").setCancelable(true).show();
            }
        });
        show_groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.choose_to_do_volonteer_framelayout,new ShowAllGroups()).disallowAddToBackStack().commit();
            }
        });
        conntect_directly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.choose_to_do_volonteer_framelayout,new ConnectDirectly()).disallowAddToBackStack().commit();
            }
        });
        return view;
    }

}
