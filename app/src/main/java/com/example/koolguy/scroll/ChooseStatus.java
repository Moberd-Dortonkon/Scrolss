package com.example.koolguy.scroll;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseStatus extends Fragment {
    interface ChooseStatusClick
    {
        void chooseStatusClick(int id);

    }
    View view;
    Button volonteer,leader;
    ScrollView scrollView;
    TextView textView;


    public ChooseStatus() {
        // Required empty public constructor
    }
    ChooseStatusClick click;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        click=(ChooseStatusClick) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_choose_status, container, false);
        scrollView=view.findViewById(R.id.scrollView);
        textView = (TextView)view.findViewById(R.id.chooseStatus);
        volonteer=(Button)view.findViewById(R.id.Volonteer);
        leader=(Button) view.findViewById(R.id.Leader);
        volonteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("Присоединиться к Группе");
                getChildFragmentManager().beginTransaction().replace(R.id.childChooseFragment,new CreateVolonteer()).commit();
            }
        });
        leader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = view.getContext().getSharedPreferences(MainActivity.APP_PREFERENCES,Context.MODE_PRIVATE);
                preferences.edit().clear().commit();

                textView.setText("Создать Группу");
                getChildFragmentManager().beginTransaction().replace(R.id.childChooseFragment,new LeaderCreateGroup()).commit();
            }
        });

        return view;
    }

}
