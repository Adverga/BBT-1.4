package com.example.bbt;


import com.example.bbt.Task.TaskFinishedListener;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.content.Intent;
import android.os.Bundle;

public class Splashbe extends AppCompatActivity implements TaskFinishedListener  {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Show the splash screen
        setContentView(R.layout.activity_splashbe);
        // Find the progress bar
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.activity_splash_progress_bar);
        // Start your loading
        new Task(progressBar, this).execute("www.google.co.uk"); // Pass in whatever you need a url is just an example we don't use it in this tutorial
    }

    // This is the callback for when your async task has finished
    @Override
    public void onTaskFinished() {
        completeSplash();
    }

    private void completeSplash(){
        startApp();
        finish(); // Don't forget to finish this Splash Activity so the user can't return to it!
    }

    private void startApp() {
        Intent intent = new Intent(Splashbe.this, MainActivity.class);
        startActivity(intent);
    }
}