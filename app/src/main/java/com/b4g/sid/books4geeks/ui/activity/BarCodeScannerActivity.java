//* Source : https://github.com/dm77/barcodescanner */
package com.b4g.sid.books4geeks.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.b4g.sid.books4geeks.B4GAppClass;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;


public class BarCodeScannerActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler{

    private ZBarScannerView zBarScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        zBarScannerView = new ZBarScannerView(this);
        setContentView(zBarScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        zBarScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        zBarScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        zBarScannerView.stopCamera();           // Stop camera on pause
    }


    @Override
    public void handleResult(Result result) {
        Intent intent = new Intent(B4GAppClass.getAppContext(),SearchActivity.class);
        intent.putExtra(B4GAppClass.SEARCH_QUERY,"isbn:"+result.getContents());
        startActivity(intent);
        finish();
    }
}
