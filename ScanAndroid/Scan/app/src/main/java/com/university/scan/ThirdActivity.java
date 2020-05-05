package com.university.scan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class ThirdActivity extends AppCompatActivity {

    private Button cameraButton;
    private Uri mUri;
    private Context context;
    private File saveDir;

    private static final int CAMERA_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        context = this;
        saveDir = context.getCacheDir();
        cameraButton = findViewById(R.id.buttonCamera);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUri = generateFileUri();
                if (mUri == null) {
                    Toast.makeText(context, "not enough space", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //intent.setAction(Intent.ACTION_CAMERA_BUTTON);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
                startActivityForResult(intent, CAMERA_REQUEST);
            }
        });


    }

    private Uri generateFileUri() {
        /*Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (!Environment.getExternalStorageState().equals(Environment.DIRECTORY_DCIM)) {
            System.out.println("null 1");
            return null;
        }*/

        /*if (! path.exists()){
            if (! path.mkdirs()){
                System.out.println("null 2");
                return null;
            }
        }*/

        String timeStamp = String.valueOf(System.currentTimeMillis());
        File newFile = new File(saveDir + File.separator + timeStamp + ".jpg");
        return Uri.fromFile(newFile);
    }

}
