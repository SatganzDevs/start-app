package com.example.clipboarddetector;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private ClipboardManager clipboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        clipboardManager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                if (clipboardManager.hasPrimaryClip() && clipboardManager.getPrimaryClip().getItemCount() > 0) {
                    CharSequence copiedText = clipboardManager.getPrimaryClip().getItemAt(0).getText();
                    if (copiedText != null) {
                        String copiedString = copiedText.toString();
                        if (isValidUrl(copiedString)) {
                            Toast.makeText(MainActivity.this, "Link detected: " + copiedString, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    private boolean isValidUrl(String url) {
        String urlPattern = "^(http|https)://[a-zA-Z0-9\\-._]+\\.[a-zA-Z]{2,}(/\\S*)?$";
        Pattern pattern = Pattern.compile(urlPattern);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }
}
