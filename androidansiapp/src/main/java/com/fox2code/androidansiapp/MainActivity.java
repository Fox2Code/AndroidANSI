package com.fox2code.androidansiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.fox2code.androidansi.AnsiParser;
import com.fox2code.androidansi.AnsiTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.ansiView);
        AnsiParser.setAnsiText(textView, // It's "AndroidANSI!" but with color & style
                "\\e[1;38;2;164;198;57mAndroid\\e[0;35mAN\u001B[2mSI\u001B[0;73m!",
                AnsiParser.FLAG_PARSE_DISABLE_SUBSCRIPT); // Also disable superscript
    }
}