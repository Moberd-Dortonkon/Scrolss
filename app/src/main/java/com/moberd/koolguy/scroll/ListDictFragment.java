package com.moberd.koolguy.scroll;


import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Fragment;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListDictFragment extends Fragment {


    public ListDictFragment() {
        // Required empty public constructor
    }
    String[]phrase;
    View view;
    private TextToSpeech TTS;
    ListView listView;


    private SoundPool mySoundPool;
    private AssetManager myAssetManager;
    private int myButtonSound;
    private int myStreamID;
    
    public void setI(String[]phrase){this.phrase = phrase;}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_dict, container, false);
        listView=(ListView)view.findViewById(R.id.list);
        ArrayAdapter<String>adapter=new ArrayAdapter<>(inflater.getContext(),android.R.layout.simple_list_item_1,phrase);
        listView.setAdapter(adapter);
        createSoundPool();
        myAssetManager = getActivity().getAssets();
        myButtonSound=createSound("button_16.mp3");

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbarId);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(myButtonSound);
                getActivity().onBackPressed();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                playSound(myButtonSound);
                if (i==0){
                    String speak=phrase[0];
                    textToSpeechRus(speak);
                }


                if(i==1){
                    String needToSpeak=phrase[1];
                    String[] makeSpeak=needToSpeak.split(":");
                    String speak=makeSpeak[1];
                    textToSpeechEng(speak);
                }

                if(i==2){
                    String needToSpeak=phrase[2];
                    String[] makeSpeak=needToSpeak.split(":");
                    String speak=makeSpeak[1];
                    textToSpeechGer(speak);
                }

                if(i==3){
                    String needToSpeak=phrase[3];
                    String[] makeSpeak=needToSpeak.split(":");
                    String speak=makeSpeak[1];
                    textToSpeechIsln(speak);
                }
                if(i==4){
                    String needToSpeak=phrase[4];
                    String[] makeSpeak=needToSpeak.split(":");
                    String speak=makeSpeak[1];
                    textToSpeechCroat(speak);
                }
                if(i==5){
                    String needToSpeak=phrase[5];
                    String[] makeSpeak=needToSpeak.split(":");
                    String speak=makeSpeak[1];
                    textToSpeechSpain(speak);
                }
                if (i==6){
                    String needToSpeak=phrase[6];
                    String[] makeSpeak=needToSpeak.split(":");
                    String speak=makeSpeak[1];
                    textToSpeechPortug(speak);
                }
            }
        });

        return view;
    }



    public void textToSpeechEng(final String text){
        TTS = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override public void onInit(int initStatus) {
                if (initStatus == TextToSpeech.SUCCESS) {
                    TTS.setLanguage(Locale.US);
                    TTS.setPitch(1.3f);
                    TTS.setSpeechRate(0.7f);
                    String utteranceId = this.hashCode() + "";
                    TTS.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);

                } else if (initStatus == TextToSpeech.ERROR) {
                    Toast.makeText(getActivity(),"error" , Toast.LENGTH_LONG).show();
                }
            }
        });

        
    }
    public void textToSpeechRus(final String text){
        TTS = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override public void onInit(int initStatus) {
                if (initStatus == TextToSpeech.SUCCESS) {
                    Locale localeRu=new Locale("ru");
                    TTS.setLanguage(localeRu);
                    TTS.setPitch(1.3f);
                    TTS.setSpeechRate(0.7f);
                    String utteranceId = this.hashCode() + "";
                    TTS.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);

                } else if (initStatus == TextToSpeech.ERROR) {
                    Toast.makeText(getActivity(),"error" , Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    public void textToSpeechGer(final String text){
        TTS = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override public void onInit(int initStatus) {
                if (initStatus == TextToSpeech.SUCCESS) {
                    TTS.setLanguage(Locale.GERMAN);
                    TTS.setPitch(1.3f);
                    TTS.setSpeechRate(0.7f);
                    String utteranceId = this.hashCode() + "";
                    TTS.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);

                } else if (initStatus == TextToSpeech.ERROR) {
                    Toast.makeText(getActivity(),"error" , Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void textToSpeechIsln(final String text){
        TTS = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override public void onInit(int initStatus) {
                if (initStatus == TextToSpeech.SUCCESS) {
                    Locale localeIsln=new Locale("icelandic");
                    TTS.setLanguage(localeIsln);
                    TTS.setPitch(1.3f);
                    TTS.setSpeechRate(0.7f);
                    String utteranceId = this.hashCode() + "";
                    TTS.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);

                } else if (initStatus == TextToSpeech.ERROR) {
                    Toast.makeText(getActivity(),"error" , Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    public void textToSpeechCroat(final String text){
        TTS = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override public void onInit(int initStatus) {
                if (initStatus == TextToSpeech.SUCCESS) {
                    Locale localeCroat=new Locale("croatian");
                    TTS.setLanguage(localeCroat);
                    TTS.setPitch(1.3f);
                    TTS.setSpeechRate(0.7f);
                    String utteranceId = this.hashCode() + "";
                    TTS.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);

                } else if (initStatus == TextToSpeech.ERROR) {
                    Toast.makeText(getActivity(),"error" , Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    public void textToSpeechSpain(final String text){
        TTS = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override public void onInit(int initStatus) {
                if (initStatus == TextToSpeech.SUCCESS) {
                    Locale localeSpain=new Locale("spanish");
                    TTS.setLanguage(localeSpain);
                    TTS.setPitch(1.3f);
                    TTS.setSpeechRate(0.7f);
                    String utteranceId = this.hashCode() + "";
                    TTS.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);

                } else if (initStatus == TextToSpeech.ERROR) {
                    Toast.makeText(getActivity(),"error" , Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    public void textToSpeechPortug(final String text){
        TTS = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override public void onInit(int initStatus) {
                if (initStatus == TextToSpeech.SUCCESS) {
                    Locale localePortug=new Locale("portuguese");
                    TTS.setLanguage(localePortug);
                    TTS.setPitch(1.3f);
                    TTS.setSpeechRate(0.7f);
                    String utteranceId = this.hashCode() + "";
                    TTS.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);

                } else if (initStatus == TextToSpeech.ERROR) {
                    Toast.makeText(getActivity(),"error" , Toast.LENGTH_LONG).show();
                }
            }
        });


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
            Toast.makeText(getActivity(), "Не смог загрузить звук " + fileName,
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
