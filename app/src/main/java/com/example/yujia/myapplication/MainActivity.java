package com.example.yujia.myapplication;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements CountDownAnimation.CountDownListener {
    private TextView textView;
    private EditText startCountEditText;
    private Spinner spinner;

    private CountDownAnimation countDownAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        startCountEditText = (EditText) findViewById(R.id.startCountEditText);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.animations_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button startButton = (Button) findViewById(R.id.button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCountDownAnimation();
            }
        });

        Button cancelButton = (Button) findViewById(R.id.buttonCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelCountDownAnimation();
            }
        });

        initCountDownAnimation();
    }


    private void initCountDownAnimation() {
        countDownAnimation = new CountDownAnimation(textView, getStartCount());
        countDownAnimation.setCountDownListener(this);
    }

    private void startCountDownAnimation() {
        // Customizable animation
        if (spinner.getSelectedItemPosition() == 1) { // Scale
            // Use scale animation
            Animation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f,
                    0.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            countDownAnimation.setAnimation(scaleAnimation);
        } else if (spinner.getSelectedItemPosition() == 2) { // Set (Scale +
            // Alpha)
            // Use a set of animations
            Animation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f,
                    0.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            Animation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            AnimationSet animationSet = new AnimationSet(false);
            animationSet.addAnimation(scaleAnimation);
            animationSet.addAnimation(alphaAnimation);
            countDownAnimation.setAnimation(animationSet);
        }

        // Customizable start count
        countDownAnimation.setStartCount(getStartCount());

        countDownAnimation.start();
    }

    private void cancelCountDownAnimation() {
        countDownAnimation.cancel();
    }

    private int getStartCount() {
        return Integer.parseInt(startCountEditText.getText().toString());
    }

    @Override
    public void onCountDownEnd(CountDownAnimation animation) {
        Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
    }
}
