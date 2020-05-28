package com.university.scan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.university.scan.SQL.LocalSQL;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Parsers.Card;

public class SecondActivity extends AppCompatActivity {

    LocalSQL sql;

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
    Button floatingActionButton2;
    int openedPhones;
    int openedAddresses;
    int openedEmails;
    int openedSites;
    EditText ETLastName;
    EditText ETFirstName;
    EditText ETFatherName;
    EditText ETCompany;
    EditText ETPhone1;
    EditText ETPhone2;
    EditText ETPhone3;
    EditText ETEmail1;
    EditText ETEmail2;
    EditText ETEmail3;
    EditText ETAddress1;
    EditText ETAddress2;
    EditText ETAddress3;
    EditText ETSite1;
    EditText ETSite2;
    EditText ETSite3;

    String data1, data2;
    int myImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        openedPhones = 0;
        openedAddresses = 0;
        openedEmails = 0;
        openedSites = 0;

        sql = new LocalSQL(SecondActivity.this);

        //long id = getIntent().getLongExtra("id", 1);
        long id = -1;
        Card card = sql.getCard(id);
        if (card != null) {
            ETFirstName.setText(card.getFirstName());
            ETLastName.setText(card.getLastName());
            ETFatherName.setText(card.getFatherName());
            ETCompany.setText(card.getCompanyName());

            if (card.getPhoneNumbers().size() != 0) {
                openedPhones = card.getPhoneNumbers().size() - 1;
                checkPhones();

                Iterator<String> iterator = card.getPhoneNumbers().iterator();

                List<String> iterators = new ArrayList<>();
                while (iterator.hasNext()) {
                    iterators.add(iterator.next());
                }

                switch (openedPhones) {
                    case 0: {
                        ETPhone1.setText(iterators.get(0));
                        break;
                    }
                    case 1: {
                        ETPhone1.setText(iterators.get(0));
                        ETPhone2.setText(iterators.get(1));
                        break;
                    }
                    case 2: {
                        ETPhone1.setText(iterators.get(0));
                        ETPhone2.setText(iterators.get(1));
                        ETPhone3.setText(iterators.get(2));
                        break;
                    }
                }
            }

            if (card.getWebsite().size() != 0) {
                openedSites = card.getWebsite().size() - 1;
                checkSites();

                Iterator<String> iterator = card.getWebsite().iterator();

                List<String> iterators = new ArrayList<>();
                while (iterator.hasNext()) {
                    iterators.add(iterator.next());
                }

                switch (openedSites) {
                    case 0: {
                        ETSite1.setText(iterators.get(0));
                        break;
                    }
                    case 1: {
                        ETSite1.setText(iterators.get(0));
                        ETSite2.setText(iterators.get(1));
                        break;
                    }
                    case 2: {
                        ETSite1.setText(iterators.get(0));
                        ETSite2.setText(iterators.get(1));
                        ETSite3.setText(iterators.get(2));
                        break;
                    }
                }
            }

            if (card.getOfficeAddress().size() != 0) {
                openedAddresses = card.getOfficeAddress().size() - 1;
                checkAddress();

                Iterator<String> iterator = card.getEmails().iterator();

                List<String> iterators = new ArrayList<>();
                while (iterator.hasNext()) {
                    iterators.add(iterator.next());
                }

                switch (openedAddresses) {
                    case 0: {
                        ETAddress1.setText(iterators.get(0));
                        break;
                    }
                    case 1: {
                        ETAddress1.setText(iterators.get(0));
                        ETAddress2.setText(iterators.get(1));
                        break;
                    }
                    case 2: {
                        ETAddress1.setText(iterators.get(0));
                        ETAddress2.setText(iterators.get(1));
                        ETAddress3.setText(iterators.get(2));
                        break;
                    }
                }
            }

            if (card.getEmails().size() != 0) {
                openedEmails = card.getEmails().size() - 1;
                checkEmails();

                Iterator<String> iterator = card.getEmails().iterator();

                List<String> iterators = new ArrayList<>();
                while (iterator.hasNext()) {
                    iterators.add(iterator.next());
                }

                switch (openedEmails) {
                    case 0: {
                        ETEmail1.setText(iterators.get(0));
                        break;
                    }
                    case 1: {
                        ETEmail1.setText(iterators.get(0));
                        ETEmail2.setText(iterators.get(1));
                        break;
                    }
                    case 2: {
                        ETEmail1.setText(iterators.get(0));
                        ETEmail2.setText(iterators.get(1));
                        ETEmail3.setText(iterators.get(2));
                        break;
                    }
                }
            }
        }

        ETLastName= findViewById(R.id.ETLastName);
        ETFirstName= findViewById(R.id.ETFirstName);
        ETFatherName= findViewById(R.id.ETFatherName);
        ETCompany= findViewById(R.id.ETCompany);
        ETPhone1= findViewById(R.id.ETPhone1);
        ETPhone2= findViewById(R.id.ETPhone2);
        ETPhone3= findViewById(R.id.ETPhone3);
        ETEmail1= findViewById(R.id.ETEmail1);
        ETEmail2= findViewById(R.id.ETEmail2);
        ETEmail3= findViewById(R.id.ETEmail3);
        ETAddress1= findViewById(R.id.ETAddress1);
        ETAddress2= findViewById(R.id.ETAddress2);
        ETAddress3= findViewById(R.id.ETAddress3);
        ETSite1= findViewById(R.id.ETSite1);
        ETSite2= findViewById(R.id.ETSite2);
        ETSite3= findViewById(R.id.ETSite3);

        mainImageView = findViewById(R.id.mainImageView);
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
        floatingActionButton2= findViewById(R.id.floatingActionButton2);

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

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Card card = new Card();

                card.setFirstName(ETFirstName.getText().toString());
                card.setLastName(ETLastName.getText().toString());
                card.setFatherName(ETFatherName.getText().toString());
                card.setCompanyName(ETCompany.getText().toString());

                if(!isEmpty(ETAddress1)) {
                    card.setOfficeAddress(ETAddress1.getText().toString());
                }
                if(!isEmpty(ETAddress2)) {
                    card.setOfficeAddress(ETAddress2.getText().toString());
                }
                if(!isEmpty(ETAddress3)) {
                    card.setOfficeAddress(ETAddress3.getText().toString());
                }

                if(!isEmpty(ETEmail1)) {
                    card.setEmails(ETEmail1.getText().toString());
                }
                if(!isEmpty(ETEmail2)) {
                    card.setEmails(ETEmail2.getText().toString());
                }
                if(!isEmpty(ETEmail3)) {
                    card.setEmails(ETEmail3.getText().toString());
                }

                if(!isEmpty(ETPhone1)) {
                    card.setPhoneNumber(ETPhone1.getText().toString());
                }
                if(!isEmpty(ETPhone2)) {
                    card.setPhoneNumber(ETPhone2.getText().toString());
                }
                if(!isEmpty(ETPhone3)) {
                    card.setPhoneNumber(ETPhone3.getText().toString());
                }

                if(!isEmpty(ETSite1)) {
                    card.setOfficeAddress(ETSite1.getText().toString());
                }
                if(!isEmpty(ETSite2)) {
                    card.setOfficeAddress(ETSite2.getText().toString());
                }
                if(!isEmpty(ETSite3)) {
                    card.setOfficeAddress(ETSite3.getText().toString());
                }
            }
        });

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

    private void checkSites() {
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

    private void checkEmails() {
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

    private void checkPhones() {
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

    private void checkAddress() {
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

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

}
