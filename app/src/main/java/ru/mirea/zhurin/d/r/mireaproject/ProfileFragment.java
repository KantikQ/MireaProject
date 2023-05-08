package ru.mirea.zhurin.d.r.mireaproject;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ProfileFragment extends Fragment {

    private EditText weightEditText;
    private EditText headHeightEditText;
    private EditText ageEditText;
    private Button saveButton;

    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        weightEditText = view.findViewById(R.id.weight_edit_text);
        headHeightEditText = view.findViewById(R.id.head_height_edit_text);
        ageEditText = view.findViewById(R.id.age_edit_text);
        saveButton = view.findViewById(R.id.save_button);

        mainActivity = (MainActivity) getActivity();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float weight = Float.parseFloat(weightEditText.getText().toString());
                float headHeight = Float.parseFloat(headHeightEditText.getText().toString());
                int age = Integer.parseInt(ageEditText.getText().toString());
                MainProfile.saveProfile(weight, headHeight, age);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = MainProfile.loadProfile();
        weightEditText.setText(Float.toString(profile.getWeight()));
        headHeightEditText.setText(Float.toString(profile.getHeadHeight()));
        ageEditText.setText(Integer.toString(profile.getAge()));
    }
}