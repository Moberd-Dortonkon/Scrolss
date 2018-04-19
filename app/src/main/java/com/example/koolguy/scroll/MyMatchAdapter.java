package com.example.koolguy.scroll;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by User on 17.04.2018.
 */

public class MyMatchAdapter extends ArrayAdapter<MyMatch> {


    public MyMatchAdapter(@NonNull Context context, int resource, @NonNull MyMatch[] arr) {
        super(context, R.layout.adapter_item, arr);
    }



public View getView(int position, View convertView, ViewGroup parent){
    final MyMatch match = getItem(position);

    if (convertView == null) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_item, null);
    }

    ((TextView) convertView.findViewById(R.id.date)).setText(match.date);
    ((TextView) convertView.findViewById(R.id.text_team_1)).setText(String.valueOf(match.team1));
    ((TextView) convertView.findViewById(R.id.text_team_2)).setText(String.valueOf(match.team2));

    if (match.team1=="  Бразилия  ")
        ((ImageView) convertView.findViewById(R.id.image_team_1)).setImageResource(R.drawable.brazil);
    if (match.team2=="  Бразилия  ")
        ((ImageView) convertView.findViewById(R.id.image_team_2)).setImageResource(R.drawable.brazil);
    if (match.team1=="     Швейцария  ")
        ((ImageView) convertView.findViewById(R.id.image_team_1)).setImageResource(R.drawable.switzerland);
    if (match.team2=="     Швейцария  ")
        ((ImageView) convertView.findViewById(R.id.image_team_2)).setImageResource(R.drawable.switzerland);
    if (match.team1=="Уругвай")
        ((ImageView) convertView.findViewById(R.id.image_team_1)).setImageResource(R.drawable.uruguay);
    if (match.team2=="Уругвай")
        ((ImageView) convertView.findViewById(R.id.image_team_2)).setImageResource(R.drawable.uruguay);
    if (match.team1=="Саудовская Аравия")
        ((ImageView) convertView.findViewById(R.id.image_team_1)).setImageResource(R.drawable.saudi_arabia);
    if (match.team2=="Саудовская Аравия")
        ((ImageView) convertView.findViewById(R.id.image_team_2)).setImageResource(R.drawable.saudi_arabia);
    if (match.team1=="Республика Корея")
        ((ImageView) convertView.findViewById(R.id.image_team_1)).setImageResource(R.drawable.south_korea);
    if (match.team2=="Республика Корея")
        ((ImageView) convertView.findViewById(R.id.image_team_2)).setImageResource(R.drawable.south_korea);
    if (match.team1=="Мексика")
        ((ImageView) convertView.findViewById(R.id.image_team_1)).setImageResource(R.drawable.mexico);
    if (match.team2=="Мексика")
        ((ImageView) convertView.findViewById(R.id.image_team_2)).setImageResource(R.drawable.mexico);
    if (match.team1=="  Исландия    ")
        ((ImageView) convertView.findViewById(R.id.image_team_1)).setImageResource(R.drawable.iceland);
    if (match.team2=="  Исландия    ")
        ((ImageView) convertView.findViewById(R.id.image_team_2)).setImageResource(R.drawable.iceland);
    if (match.team1=="          Хорватия")
        ((ImageView) convertView.findViewById(R.id.image_team_1)).setImageResource(R.drawable.croatia);
    if (match.team2=="          Хорватия")
        ((ImageView) convertView.findViewById(R.id.image_team_2)).setImageResource(R.drawable.croatia);



    return  convertView;
}












}
