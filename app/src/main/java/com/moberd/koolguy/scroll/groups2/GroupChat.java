package com.moberd.koolguy.scroll.groups2;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moberd.koolguy.scroll.MainActivity;
import com.moberd.koolguy.scroll.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupChat extends Fragment {


    public GroupChat() {
        // Required empty public constructor
    }

    View view;
    long messageCount;
    EditText message;
    ImageButton sendMessage;
    LinearLayout displayMessages;
    DatabaseReference ref;
    SharedPreferences pref;
    LayoutInflater inflater;
    boolean ScreenTouched;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_group_chat, container, false);
        message = (EditText)view.findViewById(R.id.messageEditText);
        sendMessage=(ImageButton)view.findViewById(R.id.sendMesage);
        ScrollView scrollView  = view.findViewById(R.id.group_chat_scroll_view);
        messageCount=0;
        pref =view.getContext().getSharedPreferences(MainActivity.GROUP_PREFERENCES, Context.MODE_PRIVATE);
        displayMessages = (LinearLayout)view.findViewById(R.id.chatLayout);
        displayMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScreenTouched=true;
            }
        });
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbarId);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        this.inflater = inflater;
        ref = FirebaseDatabase.getInstance().getReference("Groups").child(pref.getString("groupid","")).child("Chat");
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // ref.push().setValue(message.getText().toString());
                DatabaseReference messageRef = ref.push();
             /*   messageRef.child("message").setValue(message.getText().toString());
                messageRef.child("name").setValue(pref.getString("name",""));*/
                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat format2 = new SimpleDateFormat("dd.MM.yyyy");
                String dateFormatted = format2.format(date);
                //messageRef.child("date").setValue(dateFormatted);
                HashMap<String ,String> putted= new HashMap<>();
                putted.put("message",message.getText().toString());
                putted.put("name",pref.getString("name",""));
                putted.put("date",dateFormatted);
                message.setText("");
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                messageRef.setValue(putted);

            }
        });
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String message=dataSnapshot.child("message").getValue(String.class);
                String date=dataSnapshot.child("date").getValue(String.class);
                String name=dataSnapshot.child("name").getValue(String.class);
                addMessage(message,date,name);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }
    private void addMessage(String message,String date,String name)
    {
        View messageView = inflater.inflate(R.layout.chat_message,null);
        TextView textView = (TextView)messageView.findViewById(R.id.messageText);
        TextView messageInfo=(TextView)messageView.findViewById(R.id.messageInfo);
        Space space = new Space(getActivity());

        //Toast.makeText(getActivity(),name,Toast.LENGTH_SHORT).show();
        if(pref.getString("name","").equals(name))
        {
            textView.setText(message);
            messageInfo.setText(date);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.RIGHT;
            params.setMargins(0,24,8,0);
            messageView.setLayoutParams(params);
            messageView.setBackgroundResource(R.drawable.mymessages);
            try {

                if(ScreenTouched)messageView.getParent().requestChildFocus(messageView,messageView);}catch (Exception e){}
            ScreenTouched =false;




        }
        else
            {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.LEFT;
                messageInfo.setText(name+"/"+date);
                params.setMargins(8,24,0,0);
                textView.setText(message);
                messageView.setLayoutParams(params);
                messageView.setBackgroundResource(R.drawable.others);
            }

            messageCount++;
            messageView.setTag(messageCount);
            displayMessages.addView(messageView);
        if(!ScreenTouched) messageView.getParent().requestChildFocus(messageView,messageView);
       // if(view.findViewWithTag(messageCount).isV)messageView.getParent().requestChildFocus(messageView,messageView);
      //  try
       // {if(displayMessages.getY())}catch (Exception e){}
    }


}
