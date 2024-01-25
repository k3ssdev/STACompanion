package io.github.k3ssdev.stacompanion;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;

public class LegalActivity extends AppCompatActivity {

    // TODO: implementar la actividad de visualización de los documentos legales
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal);

        WebView webView = findViewById(R.id.webview);
        String filename = getIntent().getStringExtra("filename");

        webView.loadUrl("file:///android_asset/" + filename);
    }
}