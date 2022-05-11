package ru.levprav.orionnitrogenerator;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class SettingsFragment extends Fragment {

    EditText tokenField;
    Button btnSave;
    Button btnPolicy;
    SharedPreferences sPref;

    public SettingsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        tokenField = view.findViewById(R.id.tokenField);
        btnSave = view.findViewById(R.id.btn_save);
        btnPolicy = view.findViewById(R.id.btn_policy);

        if(!loadToken().equals(""))
            tokenField.setText(loadToken());

        btnSave.setOnClickListener(click -> {
            saveToken(tokenField.getText().toString());
            Toast.makeText(getContext(), "Saved!", Toast.LENGTH_SHORT).show();
            loadFragment(new MainFragment());
        });

        btnPolicy.setOnClickListener(click->loadFragment(new PolicyFragment()));

        return view;
    }

    public void loadFragment(Fragment f) {
        FragmentManager fm = getChildFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.body,f);
        ft.commit();
        btnSave.setVisibility(View.GONE);
        btnPolicy.setVisibility(View.GONE);
        tokenField.setVisibility(View.GONE);

    }


    private void saveToken(String token) {
        sPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("token", token);
        ed.apply();
    }

    private String loadToken() {
        sPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        return sPref.getString("token", "");
    }

}