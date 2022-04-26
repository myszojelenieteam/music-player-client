package eu.myszojelenie.music.player.client;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private final StreamingService streamingService = new StreamingService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityResultLauncher<String[]> launcher = registerForActivityResult(new ActivityResultContracts.OpenDocument(), this::intentResultHandler);
        findViewById(R.id.picker).setOnClickListener(l -> launcher.launch(new String[]{"audio/x-wav"}));
    }

    private void intentResultHandler(final Uri uri) {
        Thread thread = new Thread(() -> {
            try  {
                Log.i(Consts.loggerTag, "Obtained Uri: " + uri);
                try {
                    final InputStream stream = getContentResolver().openInputStream(uri);
                    streamingService.stream(stream);
                }
                catch (final IOException e) {
                    Log.w(Consts.loggerTag, "Error occurred during streaming");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
}