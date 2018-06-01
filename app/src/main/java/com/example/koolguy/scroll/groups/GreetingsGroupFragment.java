package com.example.koolguy.scroll.groups;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.example.koolguy.scroll.MainActivity;
import com.example.koolguy.scroll.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GreetingsGroupFragment extends Fragment {


    public GreetingsGroupFragment() {
        // Required empty public constructor
    }
    EditText name;
    SharedPreferences resources;
    EditText second_name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_greetings_group, container, false);
        // Inflate the layout for this fragment
       // ViewGroup viewGroup = new ViewGroup();
        //viewGroup.e
        resources =view.getContext().getSharedPreferences(MainActivity.GROUP_PREFERENCES, Context.MODE_PRIVATE);
        name = view.findViewById(R.id.greetingf_name);
        second_name=view.findViewById(R.id.family);
        Button apply=view.findViewById(R.id.greetings_button);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name.getText().toString().isEmpty()&&!second_name.getText().toString().isEmpty()){
                resources.edit().putString("name",name.getText().toString());
                resources.edit().putString("second_name",second_name.getText().toString()).apply();
                getFragmentManager().beginTransaction().replace(R.id.frames,new ChooseToDo()).disallowAddToBackStack().commit();}
            }
        });


        return view;
    }

}
