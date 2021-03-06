package com.moberd.koolguy.scroll;


import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.TreeMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class DictionaryFragment extends Fragment {
    View view;
    TreeMap<String,String[]> dictionar;
    String[] phrases;
    private DictionaryFragment.DictionaryListener list;
    ListView listView;
    private SoundPool mySoundPool;
    private AssetManager myAssetManager;
    private int myButtonSound;
    private int myStreamID;

    public static interface DictionaryListener{
        void DictionaryClick(String[]phrase);
    }


    public void onAttach(Activity activity) {
        super.onAttach(activity);
        list= (DictionaryListener) activity;
    }

    public DictionaryFragment() {
        // Required empty public constructor
    }

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_dictionary, container, false);
        createSoundPool();
        myAssetManager = view.getContext().getAssets();
        myButtonSound=createSound("button_16.mp3");
        Resources res=getResources();
        dictionar = new TreeMap<String,String[]>();
        int id=1;
        phrases = res.getStringArray(R.array.dictionary);
        for (int i = 0;i<phrases.length;i++)
        {
            int phrase_id = res.getIdentifier("dictionary_"+Integer.toString(i),"array",view.getContext().getPackageName());
            dictionar.put(phrases[i],res.getStringArray(phrase_id));
        }
        listView=(ListView)view.findViewById(R.id.list);
        ArrayAdapter<String> dictAdapter=new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, phrases);
        listView.setAdapter(dictAdapter);
//        Arrays.sort(phrases);
//        List<String> list = Arrays.asList(phrases);
//        Set<String> set = new TreeSet<String>(list);
//        Toolbar toolbar= (Toolbar)view.findViewById(R.id.toolbar);
//        final EditText editText=(EditText)view.findViewById(R.id.searchEditText);
//        ImageButton search=(ImageButton)view.findViewById(R.id.search);
//        search.setOnClickListener((new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String s=editText.getText().toString();
//                Arrays.sort(phrases);
//                ArrayAdapter<String> dictAdapter=new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, phrases);
//                setListAdapter(dictAdapter);
//            }
//        }));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                try {
                    list.DictionaryClick(dictionar.get(phrases[i]));
                }catch (Exception e){}

            }
        });
        return view;
    }

    private void createSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mySoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    private int createSound(String fileName) {
        AssetFileDescriptor AsFileDesc;
        try {
            AsFileDesc = myAssetManager.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(view.getContext(), "Не смог загрузить звук " + fileName,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
        return mySoundPool.load(AsFileDesc, 1);
    }

    private int playSound(int sound) {
        if (sound > 0) {
            myStreamID = mySoundPool.play(sound, 1, 1, 1, 0, 1);
        }
        return myStreamID;
    }


}
