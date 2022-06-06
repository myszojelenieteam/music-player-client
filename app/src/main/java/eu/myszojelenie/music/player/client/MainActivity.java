package eu.myszojelenie.music.player.client;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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
    private boolean isFileChosen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        streamingService.registerListener(this);

        ActivityResultLauncher<String[]> launcher = registerForActivityResult(new ActivityResultContracts.OpenDocument(), this::intentResultHandler);
        findViewById(R.id.picker).setOnClickListener(l -> launcher.launch(new String[]{"audio/x-wav"}));
        findViewById(R.id.startButton).setOnClickListener(this::startStreaming);
    }

    private void intentResultHandler(final Uri uri) {
        try {
            streamingService.setToStream(getContentResolver().openInputStream(uri));
            setTextOfView(R.id.fileStatus, "File has been selected", "#00FF00");
            isFileChosen = true;
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
}