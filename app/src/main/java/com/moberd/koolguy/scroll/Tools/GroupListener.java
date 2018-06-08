package com.moberd.koolguy.scroll.Tools;

import com.moberd.koolguy.scroll.VolonteersInfo.Volonteer;

import java.util.HashMap;

public interface GroupListener {
    void listener(HashMap<String,Volonteer> vGroup);
}
