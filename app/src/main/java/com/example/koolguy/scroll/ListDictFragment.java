package com.example.koolguy.scroll;


import android.app.ListFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListDictFragment extends ListFragment {


    public ListDictFragment() {
        // Required empty public constructor
    }
    String[]phrase;
    View view;
    private TextToSpeech TTS;
    
    public void setI(String[]phrase){this.phrase = phrase;}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_dict, container, false);
        ArrayAdapter<String>adapter=new ArrayAdapter<>(inflater.getContext(),android.R.layout.simple_list_item_1,phrase);
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (position==0){
            String speak=phrase[0];
            textToSpeechRus(speak);
        }


        if(position==1){
            String needToSpeak=phrase[1];
            String[] makeSpeak=needToSpeak.split(":");
            String speak=makeSpeak[1];
            textToSpeechEng(speak);
        }

        if(position==2){
            String needToSpeak=phrase[2];
            String[] makeSpeak=needToSpeak.split(":");
            String speak=makeSpeak[1];
            textToSpeechGer(speak);
        }

        if(position==3){
            String needToSpeak=phrase[3];
            String[] makeSpeak=needToSpeak.split(":");
            String speak=makeSpeak[1];
            textToSpeechIsln(speak);
        }
        if(position==4){
            String needToSpeak=phrase[4];
            String[] makeSpeak=needToSpeak.split(":");
            String speak=makeSpeak[1];
            textToSpeechCroat(speak);
        }
        if(position==5){
            String needToSpeak=phrase[5];
            String[] makeSpeak=needToSpeak.split(":");
            String speak=makeSpeak[1];
            textToSpeechSpain(speak);
        }
        if (position==6){
            String needToSpeak=phrase[6];
            String[] makeSpeak=needToSpeak.split(":");
            String speak=makeSpeak[1];
            textToSpeechPortug(speak);
        }




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
                    Locale localeIsln=new Locale("island");
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


}
