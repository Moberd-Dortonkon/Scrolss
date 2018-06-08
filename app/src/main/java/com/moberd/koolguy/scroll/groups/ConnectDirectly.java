package com.moberd.koolguy.scroll.groups;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.moberd.koolguy.scroll.MainActivity;
import com.moberd.koolguy.scroll.R;
import com.moberd.koolguy.scroll.groups.ServerInterfaces.CreateVolonteer;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConnectDirectly extends Fragment {


    public ConnectDirectly() {
        // Required empty public constructor
    }
View view;
Button button;
EditText editText;
Retrofit retrofit;
FrameLayout frameLayout;
ShowAllGroups.DefineGroup listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener=(ShowAllGroups.DefineGroup)activity;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_connect_directly, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.TEST_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        frameLayout=(FrameLayout)view.findViewById(R.id.LoadingFrame);
        editText = (EditText)view.findViewById(R.id.connect_direcltly);
        button =(Button)view.findViewById(R.id.connect_directly_buton);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                button.setEnabled(false);
                button.setVisibility(View.INVISIBLE);
                frameLayout.addView(inflater.inflate(R.layout.progress_view,null));
               // Toast.makeText(getActivity(),"Connecting...",Toast.LENGTH_SHORT).show();
                Call<ResponseBody>call=retrofit.create(CreateVolonteer.class).createVolonteer(view.getContext().getSharedPreferences(MainActivity.GROUP_PREFERENCES, Context.MODE_PRIVATE).getString("leaderid",
                        ""),editText.getText().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            try {
                                if(response.body().string().equals("complete"))
                                {
                                    listener.defineGroup(editText.getText().toString());
                                }
                                else
                                    {

                                        Toast.makeText(getActivity(),"Try Again",Toast.LENGTH_SHORT);
                                        frameLayout.removeAllViews();
                                    }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
        return view;
    }

}
