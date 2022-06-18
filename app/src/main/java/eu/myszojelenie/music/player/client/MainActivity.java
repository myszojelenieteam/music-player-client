package eu.myszojelenie.music.player.client;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import eu.myszojelenie.music.player.client.streaming.StreamingService;
import eu.myszojelenie.music.player.client.utils.Listener;
import eu.myszojelenie.music.player.client.utils.NotificationType;

public class MainActivity extends AppCompatActivity implements Listener {

    private final StreamingService streamingService = new StreamingService();
    private Uri uri;
    private boolean isFileChosen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        streamingService.registerListener(this);

        ActivityResultLauncher<String[]> launcher = registerForActivityResult(new ActivityResultContracts.OpenDocument(), this::intentResultHandler);
        findViewById(R.id.picker).setOnClickListener(l -> launcher.launch(new String[]{"audio/x-wav"}));
        findViewById(R.id.startButton).setOnClickListener(this::startStreaming);
        findViewById(R.id.pauseButton).setOnClickListener(l -> handlePauseButtonClicked());
        findViewById(R.id.stopButton).setOnClickListener(l -> handleStopButtonClicked());
    }

    private void intentResultHandler(final Uri uri) {
        try {
            if (uri == null)
                return;

            streamingService.setToStream(getContentResolver().openInputStream(uri));
            setTextOfView(R.id.fileStatus, "File has been selected", "#00FF00");
            isFileChosen = true;
            findViewById(R.id.startButton).setEnabled(true);
            findViewById(R.id.pauseButton).setEnabled(false);
            findViewById(R.id.stopButton).setEnabled(false);
        } catch (IOException e) {
            e.printStackTrace();
            setTextOfView(R.id.fileStatus, "Error selecting a file", "#FF0000");
        }

    }

    private void startStreaming(View view) {
        String ip = ((EditText) findViewById(R.id.ipInput)).getText().toString();
        streamingService.setIp(ip);

        String message = "";
        String color = "";
        if(!isFileChosen) {
            message += "File has to be chosen! ";
            color = "#FF0000";
        }
        if(!lookLikeIp(ip)) {
            message += "Ip address is incorrect";
            color = "#FF0000";
        }

        if(isFileChosen && lookLikeIp(ip)) {
            message = "Connected";
            color = "#00FF00";
            findViewById(R.id.startButton).setEnabled(false);
            findViewById(R.id.pauseButton).setEnabled(true);
            findViewById(R.id.stopButton).setEnabled(true);
            findViewById(R.id.picker).setEnabled(false);
            new Thread(streamingService::startStreaming).start();
        }

        setTextOfView(R.id.statusText, message, color);
    }

    @Override
    public void onMessage(NotificationType notificationType, Object object) {
        switch (notificationType) {
            case CONNECTION_ERROR:
                runOnUiThread(() -> {
                    setTextOfView(R.id.statusText, "Connection error", "#FF0000");
                });
                break;
        }
    }

    private void setTextOfView(int viewId, String content, String color) {
        TextView view = (TextView) findViewById(viewId);
        view.setText(content);
        view.setTextColor(Color.parseColor(color));
    }

    private boolean lookLikeIp(String ip) {
        if(ip == null) {
            return false;
        }

        String[] parts = ip.split("\\.");
        for(String part: parts) {
            try {
                Integer.parseInt(part);
            }
            catch (NumberFormatException e) {
                return false;
            }
        }

        return parts.length == 4;
    }

    private void handlePauseButtonClicked() {
        Button button = findViewById(R.id.pauseButton);
        switch (button.getText().toString()) {
            case "Pause":
                button.setText("Resume");
                break;
            case "Resume":
                button.setText("Pause");
                break;
        }
        streamingService.pause();
    }

    private void handleStopButtonClicked() {
        Button button = findViewById(R.id.pauseButton);
        button.setText("Pause");
        findViewById(R.id.startButton).setEnabled(false);
        findViewById(R.id.pauseButton).setEnabled(false);
        findViewById(R.id.stopButton).setEnabled(false);
        findViewById(R.id.picker).setEnabled(true);
        streamingService.stop();
    }
}
