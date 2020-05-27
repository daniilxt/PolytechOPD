package com.university.scan;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    ImageView mainImageView;
    TableRow TRPhone2;
    TableRow TRPhone3;
    TableRow TRAddress2;
    TableRow TRAddress3;
    TableRow TREmail2;
    TableRow TREmail3;
    TableRow TRSite2;
    TableRow TRSite3;
    Button BAddPhone;
    Button BDelPhone;
    Button BAddAddress;
    Button BDelAddress;
    Button BAddEmail;
    Button BDelEmail;
    Button BAddSite;
    Button BDelSite;
    int openedPhones;
    int openedAddresses;
    int openedEmails;
    int openedSites;
    private Uri outputFileUri;

    String data1, data2;
    int myImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mainImageView = findViewById(R.id.mainImageView);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            System.out.println("not null arguments!");
            outputFileUri = (Uri) arguments.get("outputFileUri");
            System.out.println(outputFileUri.getPath());
            mainImageView.setImageURI(outputFileUri);
        }

        openedPhones = 0;
        openedAddresses = 0;
        openedEmails = 0;
        openedSites = 0;

        TRPhone2 = findViewById(R.id.TRPhone2);
        TRPhone3 = findViewById(R.id.TRPhone3);
        TRAddress2 = findViewById(R.id.TRAddress2);
        TRAddress3 = findViewById(R.id.TRAddress3);
        TREmail2 = findViewById(R.id.TREmail2);
        TREmail3 = findViewById(R.id.TREmail3);
        TRSite2 = findViewById(R.id.TRSite2);
        TRSite3 = findViewById(R.id.TRSite3);
        BAddPhone = findViewById(R.id.BAddPhone);
        BDelPhone = findViewById(R.id.BDelPhone);
        BAddAddress = findViewById(R.id.BAddAddress);
        BDelAddress = findViewById(R.id.BDelAddress);
        BAddEmail = findViewById(R.id.BAddEmail);
        BDelEmail = findViewById(R.id.BDelEmail);
        BAddSite = findViewById(R.id.BAddSite);
        BDelSite = findViewById(R.id.BDelSite);

        TRPhone2.setEnabled(false);
        TRPhone2.setVisibility(View.GONE);
        TRPhone3.setEnabled(false);
        TRPhone3.setVisibility(View.GONE);
        TRAddress2.setEnabled(false);
        TRAddress2.setVisibility(View.GONE);
        TRAddress3.setEnabled(false);
        TRAddress3.setVisibility(View.GONE);
        TREmail2.setEnabled(false);
        TREmail2.setVisibility(View.GONE);
        TREmail3.setEnabled(false);
        TREmail3.setVisibility(View.GONE);
        TRSite2.setEnabled(false);
        TRSite2.setVisibility(View.GONE);
        TRSite3.setEnabled(false);
        TRSite3.setVisibility(View.GONE);

        BAddPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openedPhones++;
                if (openedPhones < 3) {
                    switch (openedPhones) {
                        case 0: {
                            TRPhone2.setEnabled(false);
                            TRPhone2.setVisibility(View.GONE);
                            TRPhone3.setEnabled(false);
                            TRPhone3.setVisibility(View.GONE);
                            break;
                        }
                        case 1: {
                            TRPhone2.setEnabled(true);
                            TRPhone2.setVisibility(View.VISIBLE);
                            TRPhone3.setEnabled(false);
                            TRPhone3.setVisibility(View.GONE);
                            break;
                        }
                        case 2: {
                            TRPhone2.setEnabled(true);
                            TRPhone2.setVisibility(View.VISIBLE);
                            TRPhone3.setEnabled(true);
                            TRPhone3.setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                }
            }
        });

        BDelPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openedPhones--;
                if (openedPhones >= 0) {
                    switch (openedPhones) {
                        case 0: {
                            TRPhone2.setEnabled(false);
                            TRPhone2.setVisibility(View.GONE);
                            TRPhone3.setEnabled(false);
                            TRPhone3.setVisibility(View.GONE);
                            break;
                        }
                        case 1: {
                            TRPhone2.setEnabled(true);
                            TRPhone2.setVisibility(View.VISIBLE);
                            TRPhone3.setEnabled(false);
                            TRPhone3.setVisibility(View.GONE);
                            break;
                        }
                        case 2: {
                            TRPhone2.setEnabled(true);
                            TRPhone2.setVisibility(View.VISIBLE);
                            TRPhone3.setEnabled(true);
                            TRPhone3.setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                }
            }
        });

        BAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openedAddresses++;
                if (openedAddresses < 3) {
                    switch (openedAddresses) {
                        case 0: {
                            TRAddress2.setEnabled(false);
                            TRAddress2.setVisibility(View.GONE);
                            TRAddress3.setEnabled(false);
                            TRAddress3.setVisibility(View.GONE);
                            break;
                        }
                        case 1: {
                            TRAddress2.setEnabled(true);
                            TRAddress2.setVisibility(View.VISIBLE);
                            TRAddress3.setEnabled(false);
                            TRAddress3.setVisibility(View.GONE);
                            break;
                        }
                        case 2: {
                            TRAddress2.setEnabled(true);
                            TRAddress2.setVisibility(View.VISIBLE);
                            TRAddress3.setEnabled(true);
                            TRAddress3.setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                }
            }
        });

        BDelAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openedAddresses--;
                if (openedAddresses >= 0) {
                    switch (openedAddresses) {
                        case 0: {
                            TRAddress2.setEnabled(false);
                            TRAddress2.setVisibility(View.GONE);
                            TRAddress3.setEnabled(false);
                            TRAddress3.setVisibility(View.GONE);
                            break;
                        }
                        case 1: {
                            TRAddress2.setEnabled(true);
                            TRAddress2.setVisibility(View.VISIBLE);
                            TRAddress3.setEnabled(false);
                            TRAddress3.setVisibility(View.GONE);
                            break;
                        }
                        case 2: {
                            TRAddress2.setEnabled(true);
                            TRAddress2.setVisibility(View.VISIBLE);
                            TRAddress3.setEnabled(true);
                            TRAddress3.setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                }
            }
        });

        BAddEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openedEmails++;
                if (openedEmails < 3) {
                    switch (openedEmails) {
                        case 0: {
                            TREmail2.setEnabled(false);
                            TREmail2.setVisibility(View.GONE);
                            TREmail3.setEnabled(false);
                            TREmail3.setVisibility(View.GONE);
                            break;
                        }
                        case 1: {
                            TREmail2.setEnabled(true);
                            TREmail2.setVisibility(View.VISIBLE);
                            TREmail3.setEnabled(false);
                            TREmail3.setVisibility(View.GONE);
                            break;
                        }
                        case 2: {
                            TREmail2.setEnabled(true);
                            TREmail2.setVisibility(View.VISIBLE);
                            TREmail3.setEnabled(true);
                            TREmail3.setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                }
            }
        });

        BDelEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openedEmails--;
                if (openedEmails >= 0) {
                    switch (openedEmails) {
                        case 0: {
                            TREmail2.setEnabled(false);
                            TREmail2.setVisibility(View.GONE);
                            TREmail3.setEnabled(false);
                            TREmail3.setVisibility(View.GONE);
                            break;
                        }
                        case 1: {
                            TREmail2.setEnabled(true);
                            TREmail2.setVisibility(View.VISIBLE);
                            TREmail3.setEnabled(false);
                            TREmail3.setVisibility(View.GONE);
                            break;
                        }
                        case 2: {
                            TREmail2.setEnabled(true);
                            TREmail2.setVisibility(View.VISIBLE);
                            TREmail3.setEnabled(true);
                            TREmail3.setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                }
            }
        });

        BAddSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openedSites++;
                if (openedSites < 3) {
                    switch (openedSites) {
                        case 0: {
                            TRSite2.setEnabled(false);
                            TRSite2.setVisibility(View.GONE);
                            TRSite3.setEnabled(false);
                            TRSite3.setVisibility(View.GONE);
                            break;
                        }
                        case 1: {
                            TRSite2.setEnabled(true);
                            TRSite2.setVisibility(View.VISIBLE);
                            TRSite3.setEnabled(false);
                            TRSite3.setVisibility(View.GONE);
                            break;
                        }
                        case 2: {
                            TRSite2.setEnabled(true);
                            TRSite2.setVisibility(View.VISIBLE);
                            TRSite3.setEnabled(true);
                            TRSite3.setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                }
            }
        });

        BDelSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openedSites--;
                if (openedSites >= 0) {
                    switch (openedSites) {
                        case 0: {
                            TRSite2.setEnabled(false);
                            TRSite2.setVisibility(View.GONE);
                            TRSite3.setEnabled(false);
                            TRSite3.setVisibility(View.GONE);
                            break;
                        }
                        case 1: {
                            TRSite2.setEnabled(true);
                            TRSite2.setVisibility(View.VISIBLE);
                            TRSite3.setEnabled(false);
                            TRSite3.setVisibility(View.GONE);
                            break;
                        }
                        case 2: {
                            TRSite2.setEnabled(true);
                            TRSite2.setVisibility(View.VISIBLE);
                            TRSite3.setEnabled(true);
                            TRSite3.setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                }
            }
        });

        getData();
        setData();

    }

    private void getData() {
        if (getIntent().hasExtra("myImage") && getIntent().hasExtra("data1") &&
                getIntent().hasExtra("data2")) {
            data1 = getIntent().getStringExtra("data1");
            data2 = getIntent().getStringExtra("data2");
            myImage = getIntent().getIntExtra("myImage", 1);
        }else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        mainImageView.setImageResource(myImage);
    }

}
