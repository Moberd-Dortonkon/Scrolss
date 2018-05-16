package com.example.koolguy.scroll.Tools;

import com.example.koolguy.scroll.VolonteersInfo.Volonteer;

import java.util.HashMap;

public interface GroupListener {
    void listener(HashMap<String,Volonteer> vGroup);
}
