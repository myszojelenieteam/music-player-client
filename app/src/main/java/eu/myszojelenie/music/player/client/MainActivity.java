package eu.myszojelenie.music.player.client;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

import eu.myszojelenie.music.player.client.streaming.StreamingService;
import eu.myszojelenie.music.player.client.utils.Consts;

public class MainActivity extends AppCompatActivity {

    private final StreamingService streamingService = new StreamingService();
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityResultLauncher<String[]> launcher = registerForActivityResult(new ActivityResultContracts.OpenDocument(), this::intentResultHandler);
        findViewById(R.id.picker).setOnClickListener(l -> launcher.launch(new String[]{"audio/x-wav"}));
        findViewById(R.id.play_button).setOnClickListener(l -> handlePlayButtonClicked());
        findViewById(R.id.pause_button).setOnClickListener(l -> handlePauseButtonClicked());
        findViewById(R.id.stop_button).setOnClickListener(l -> handleStopButtonClicked());
    }

    private void intentResultHandler(final Uri uri) {
        Log.i(Consts.loggerTag, "Obtained Uri: " + uri);
        this.uri = uri;
        findViewById(R.id.play_button).setEnabled(true);
    }

    private void handlePlayButtonClicked() {
        if (uri == null)
            return;
        findViewById(R.id.play_button).setEnabled(false);

        Thread thread = new Thread(() -> {
            try {
                final InputStream stream = getContentResolver().openInputStream(uri);
                streamingService.stream(stream);
            } catch (final IOException e) {
                Log.w(Consts.loggerTag, "Error occurred during streaming");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void handlePauseButtonClicked() {
        streamingService.pause();
    }

    private void handleStopButtonClicked() {
        findViewById(R.id.play_button).setEnabled(true);
        streamingService.stop();
    }
}