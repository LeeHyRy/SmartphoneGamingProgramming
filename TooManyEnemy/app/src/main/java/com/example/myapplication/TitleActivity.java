package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityTitleBinding;

public class TitleActivity extends AppCompatActivity {

    private ActivityTitleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTitleBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Start Game 버튼 클릭 이벤트 처리
        binding.startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클릭 이벤트 처리 코드 작성
                Intent intent = new Intent(TitleActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
