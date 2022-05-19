package com.fox2code.androidansiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.fox2code.androidansi.AnsiTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnsiTextView textView = findViewById(R.id.ansiView); // It's "AndroidANSI!" but with color & style
        textView.setAnsiText("\\e[1;38;2;164;198;57mAndroid\\e[0;35mAN\u001B[2mSI\u001B[0;73m!",
                textView.FLAG_PARSE_DISABLE_SUBSCRIPT); // Also disable superscript
    }
}