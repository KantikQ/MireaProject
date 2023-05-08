package ru.mirea.zhurin.d.r.mireaproject;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainProfile extends AppCompatActivity {
    private static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("Задание", MODE_PRIVATE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ProfileFragment profileFragment = new ProfileFragment();
        fragmentTransaction.add(R.id.fragment_container, profileFragment);
        fragmentTransaction.commit();
    }

    public static void saveProfile(float weight, float headHeight, int age) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("weight", weight);
        editor.putFloat("head_height", headHeight);
        editor.putInt("age", age);
        editor.apply();
    }

    public static Profile loadProfile() {
        float weight = sharedPreferences.getFloat("weight", 0);
        float headHeight = sharedPreferences.getFloat("head_height", 0);
        int age = sharedPreferences.getInt("age", 0);
        return new Profile(weight, headHeight, age);
    }
}
