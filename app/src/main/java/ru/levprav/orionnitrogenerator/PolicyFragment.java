package ru.levprav.orionnitrogenerator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class PolicyFragment extends Fragment {

    Button btnGoBack;

    public PolicyFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_policy, container, false);

        btnGoBack = view.findViewById(R.id.btn_go_back);

        btnGoBack.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                Log.d("ddd", "sss");
                loadFragment(new SettingsFragment());
            }
        });

        return view;
    }

    public void loadFragment(Fragment f) {
        FragmentManager fm = getChildFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.body,f);
        ft.commit();

    }
}