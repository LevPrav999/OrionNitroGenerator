package ru.levprav.orionnitrogenerator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

import ru.levprav.orionnitrogenerator.adapters.RecyclerAdapter;
import ru.levprav.orionnitrogenerator.retrofit.models.CodeRequestModel;

public class MainFragment extends Fragment {

    Button btnStart;
    RecyclerView recyclerView;
    SharedPreferences sPref;
    MutableLiveData<ArrayList<CodeRequestModel>> array = new MutableLiveData<>();
    Handler h;
    RecyclerAdapter recyclerAdapter;
    Generator generator;

    public MainFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        array.setValue(new ArrayList<>());
        btnStart = view.findViewById(R.id.btn_start);
        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerAdapter = new RecyclerAdapter(getContext(), array);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        btnStart.setOnClickListener(click -> {
            int statusCode = loadStatus();
            switch (statusCode){ // 0 - never used | 1 - active | 2 - idle
                case 1: {
                    saveStatus(2);
                    btnStart.setText(R.string.start_generating);
                    Toast.makeText(getContext(), "Generation stopped", Toast.LENGTH_LONG).show();
                    return;
                }
                case 0:
                case 2:{
                    saveStatus(1);
                    btnStart.setText(R.string.stop_generating);
                    Toast.makeText(getContext(), "Generation started", Toast.LENGTH_LONG).show();
                    generator = new Generator(getActivity(), array);
                    h = new Handler(Looper.getMainLooper()) {
                        @Override
                        public void handleMessage(@NonNull Message msg) {
                            array.setValue((ArrayList<CodeRequestModel>) msg.obj);
                        }
                    };
                    Thread th = new Thread(() -> {
                        int i = 0;
                        while (true){
                            if(loadStatus() != 2){
                                try {
                                    ArrayList<CodeRequestModel> a = generator.startGeneration();
                                    Message msg = Message.obtain();
                                    msg.obj = a;
                                    msg.setTarget(h);
                                    msg.sendToTarget();
                                } catch (IOException e) {e.printStackTrace();}
                                if(i < 10){
                                    try {
                                        Thread.sleep(20000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    i++;
                                }else{
                                    try {
                                        Thread.sleep(1336000);
                                    } catch (InterruptedException e) {e.printStackTrace();}
                                    i=0;
                                }
                            }
                        }
                    });
                    th.start();

                    array.observe(getViewLifecycleOwner(), codeRequestModels -> {
                        recyclerAdapter.notifyDataSetChanged();
                        recyclerView.smoothScrollToPosition(array.getValue().size());

                    });

                }
            }
        });


        return view;
    }



    private void saveStatus(int statusCode) {
        sPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt("statusCode", statusCode);
        ed.apply();
    }

    private int loadStatus() {
        sPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        return sPref.getInt("statusCode", 0);
    }
}