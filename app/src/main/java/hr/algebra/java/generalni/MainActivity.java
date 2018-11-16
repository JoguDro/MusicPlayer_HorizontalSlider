package hr.algebra.java.generalni;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


/*
Napravite aplikaciju koja ima akcijsku traku (action bar). Akcijska traka ima samo jednu opciju
 izlaz iz aplikacije gdje se odabirom pokazuje dialog da li stvarno zelite izaci.

 Aplickacija treba imati jedan gumb za play mp3 datoteke i korisnicki horizontal slider za volume.
 */

public class MainActivity extends AppCompatActivity {

    private Button btnPlay;
    private MediaPlayer mediaPlayer;


    private HorizontalSlider hSlider;
    private HorizontalSlider horSlide;

    private Handler hSliderUpdateHandler;
    private Runnable hSliderUpdate;

    private AudioManager audioManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.show();


        init();
        setupListeners();

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        initControls();


    }

    private void initControls() {
        horSlide = findViewById(R.id.horSlide);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        horSlide.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        horSlide.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        horSlide.setOnProgressChangeListener(new OnProgressChangeListener() {
            @Override
            public void onProgressChange(View view, int progress) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }
        });
        {


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.action_bar_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.Iexit:
                Toast.makeText(this, "exit", Toast.LENGTH_SHORT).show();
                finish();
                break;

        }
        return true;
    }


    private void init() {

        btnPlay = findViewById(R.id.btnPlay);
        mediaPlayer = MediaPlayer.create(this, R.raw.staticx);

        horSlide = findViewById(R.id.horSlide);

        hSlider = findViewById(R.id.hsSlider);
        hSlider.setMax(mediaPlayer.getDuration());

        hSliderUpdateHandler = new Handler();
        hSliderUpdate = new Runnable() {
            @Override
            public void run() {
                hSlider.setProgress(mediaPlayer.getCurrentPosition());
                hSliderUpdateHandler.postDelayed(this, 50);
            }
        };
    }

    private void setupListeners() {
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    hSliderUpdateHandler.postDelayed(hSliderUpdate, 0);
                    btnPlay.setText("pause");
                } else {
                    mediaPlayer.pause();
                    btnPlay.setText("play");
                }
            }
        });

        hSlider.setOnProgressChangeListener(new OnProgressChangeListener() {
            @Override
            public void onProgressChange(View view, int progress) {
                mediaPlayer.seekTo(progress);
            }
        });
    }
}


