package com.university.scan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Parsers.Card;

public class ThirdActivity extends AppCompatActivity {

    private Button cameraButton;
    private ImageView imageView;
    private Uri outputFileUri;
    private Context context;
    private File saveDir;
    private String currentPhotoPath;
    private static int TAKE_PICTURE_REQUEST = 1;
    static final int REQUEST_TAKE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        context = this;
        saveDir = context.getCacheDir();
        cameraButton = findViewById(R.id.buttonCamera);
        imageView = findViewById(R.id.imageView);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PICTURE_REQUEST && resultCode == RESULT_OK) {
            // Проверяем, содержит ли результат маленькую картинку
            if (data != null) {
                if (data.hasExtra("data")) {
                    Bitmap thumbnailBitmap = data.getParcelableExtra("data");
                    // Какие-то действия с миниатюрой
                    imageView.setImageBitmap(thumbnailBitmap);
                }
            } else {
                // Какие-то действия с полноценным изображением,
                // сохраненным по адресу outputFileUri
                imageView.setImageURI(outputFileUri);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        System.out.println("1");
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            System.out.println("2");
            saveDir = null;
            try {
                saveDir = createImageFile();
                System.out.println(saveDir);
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            System.out.println("3");
            // Continue only if the File was successfully created
            if (saveDir != null) {
                System.out.println("4");
                outputFileUri = FileProvider.getUriForFile(this,
                        "com.university.scan.fileprovider",
                        saveDir);
                System.out.println("5");
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                System.out.println("6");
            }
        }
    }

}
