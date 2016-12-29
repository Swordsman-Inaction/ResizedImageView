package com.inaction.share.resizedimageviewexample;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.inaction.share.resizedimageviewexample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mBinding.widthRatioTextView.setText("1");
        mBinding.heightRatioTextView.setText("1");
        mBinding.widthSeekBar.setProgress(0);
        mBinding.heightSeekBar.setProgress(0);

        mBinding.widthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mBinding.widthRatioTextView.setText(String.valueOf(i + 1));
                resizeImage();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mBinding.heightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mBinding.heightRatioTextView.setText(String.valueOf(i + 1));
                resizeImage();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mBinding.scaleTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.centerInsideButton:
                        mBinding.resizedImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        break;
                    case R.id.fixStartButton:
                        mBinding.resizedImageView.setScaleType(ImageView.ScaleType.FIT_START);
                        break;
                    case R.id.fixXYButton:
                        mBinding.resizedImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void resizeImage() {
        mBinding.resizedImageView.setAspectRatio(
                Integer.parseInt(mBinding.widthRatioTextView.getText().toString()),
                Integer.parseInt(mBinding.heightRatioTextView.getText().toString())
        );
    }
}
