package com.university.scan.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import Parsers.Card;

public class LocalSQL extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PolytechOPD";
    private static final String DEFAULT_COUNTRY = "Россия";
    private static final String TABLE_NAME_ARRAY[] = {"Address", "Company", "Name", "Phone",
            "Email", "WebSite", "Card", "CardAddress", "CardEmail", "CardPhone", "CardWebSite"};
    private static final String TABLE_ARRAY[] = {"CREATE TABLE [Address](\n" +
            "  [id] INTEGER primary key autoincrement not null unique, \n" +
            "  [address] NVARCHAR, \n" +
            "  [country] NVARCHAR default '" + DEFAULT_COUNTRY + "', \n" +
            "  [city] NVARCHAR, \n" +
            "  [street] NVARCHAR, \n" +
            "  [house] NVARCHAR, \n" +
            "  [room] NVARCHAR, \n" +
            "  [postIndex] INTEGER);\n",
            "create table [Company](\n" +
                    "  [id] INTEGER primary key autoincrement not null unique, \n" +
                    "  [name] NVARCHAR not null\n" +
                    "  [type] NVARCHAR\n" +
                    ");\n",
            "create table [Name](\n" +
                    "  [id] INTEGER primary key autoincrement not null unique, \n" +
                    "  [first_name] NVARCHAR not null, \n" +
                    "  [last_name] NVARCHAR not null, \n" +
                    "  [patronymic] NVARCHAR\n" +
                    ");\n",
            "create table [Phone](\n" +
                    "  [id] INTEGER primary key autoincrement not null unique, \n" +
                    "  [phone] NVARCHAR not null, \n" +
                    "  [name_ru] NVARCHAR, \n" +
                    "  [name_en] NVARCHAR\n" +
                    ");\n",
            "create table [Email](\n" +
                    "  [id] INTEGER primary key autoincrement not null unique, \n" +
                    "  [email] NVARCHAR not null, \n" +
                    ");\n",
            "create table [WebSite](\n" +
                    "  [id] INTEGER primary key autoincrement not null unique, \n" +
                    "  [site] NVARCHAR not null, \n" +
                    "  [name_ru] NVARCHAR, \n" +
                    "  [name_en] NVARCHAR\n" +
                    ");\n",
            "create table [Card](\n" +
                    "  [id] INTEGER primary key autoincrement not null unique, \n" +
                    "  [name_id] INTEGER, \n" +
                    "  [company_id] INTEGER, \n" +
                    "  FOREIGN KEY [name_id] references [Name]([id]) ON DELETE CASCADE, \n" +
                    "  FOREIGN KEY [company_id] references [Company]([id]) ON DELETE CASCADE, \n" +
                    "  [photo] IMAGE, \n" +
                    "  [qr] IMAGE\n" +
                    ");\n",
            "create table [CardAddress](\n" +
                    "  card_id INTEGER NOT NULL,\n" +
                    "  adress_id INTEGER NOT NULL,\n" +
                    "  FOREIGN KEY (card_id) REFERENCES [Card](id) ON DELETE CASCADE,\n" +
                    "  FOREIGN KEY (adress_id) REFERENCES [Adress](id) ON DELETE CASCADE\n" +
                    ");\n",
            "create table [CardEmail](\n" +
                    "  card_id INTEGER NOT NULL,\n" +
                    "  email_id INTEGER NOT NULL,\n" +
                    "  FOREIGN KEY (card_id) REFERENCES [Card](id) ON DELETE CASCADE,\n" +
                    "  FOREIGN KEY (email_id) REFERENCES [Adress](id) ON DELETE CASCADE\n" +
                    ");\n",
            "create table [CardPhone](\n" +
                    "  card_id INTEGER NOT NULL,\n" +
                    "  phone_id INTEGER NOT NULL,\n" +
                    "  FOREIGN KEY (card_id) REFERENCES [Card](id) ON DELETE CASCADE,\n" +
                    "  FOREIGN KEY (phone_id) REFERENCES [Phone](id) ON DELETE CASCADE\n" +
                    ");\n",
            "create table [CardWebSite](\n" +
                    "  card_id INTEGER NOT NULL,\n" +
                    "  website_id INTEGER NOT NULL,\n" +
                    "  FOREIGN KEY (card_id) REFERENCES [Card](id) ON DELETE CASCADE,\n" +
                    "  FOREIGN KEY (website_id) REFERENCES [WebSite](id) ON DELETE CASCADE\n" +
                    ");"};

    public LocalSQL(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
        for (String s : TABLE_ARRAY) {
            db.execSQL(s);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (String table: TABLE_NAME_ARRAY) {
            db.execSQL("DROP TABLE IF EXISTS " + table);
        }

        onCreate(db);
    }

    public void addCard(Card card) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (db != null) {

            long cardId;
            long nameId;
            long companyId;

            card.getContactName();
            ContentValues contentValuesList = new ContentValues();
            contentValuesList.put("first_name", card.getContactName());
            nameId = db.insert("Name", null, contentValuesList);

            contentValuesList.clear();
            contentValuesList.put("name", card.getCompanyName());
            contentValuesList.put("type", card.getTypeOfOrganization());
            companyId = db.insert("Company", null, contentValuesList);

            contentValuesList.clear();
            contentValuesList.put("name_id", nameId);
            contentValuesList.put("company_id", companyId);
            cardId = db.insert("Card", null, contentValuesList);

            for (String phone : card.getPhoneNumber()) {
                contentValuesList.clear();
                contentValuesList.put("phone", phone);
                long phoneId = db.insert("Phone", null, contentValuesList);

                contentValuesList.clear();
                contentValuesList.put("card_id", cardId);
                contentValuesList.put("phone_id", phoneId);
                db.insert("CardPhone", null, contentValuesList);
            }

            for (String email : card.getEmails()) {
                contentValuesList.clear();
                contentValuesList.put("email", email);
                long emailId = db.insert("Email", null, contentValuesList);

                contentValuesList.clear();
                contentValuesList.put("card_id", cardId);
                contentValuesList.put("email_id", emailId);
                db.insert("CardEmail", null, contentValuesList);
            }

            for (String site : card.getWebsite()) {
                contentValuesList.clear();
                contentValuesList.put("site", site);
                long webSiteId = db.insert("Phone", null, contentValuesList);

                contentValuesList.clear();
                contentValuesList.put("card_id", cardId);
                contentValuesList.put("website_id", webSiteId);
                db.insert("CardWebSite", null, contentValuesList);
            }

            for (String adress : card.getOfficeAddress()) {
                contentValuesList.clear();
                contentValuesList.put("address", adress);
                long addressId = db.insert("Address", null, contentValuesList);

                contentValuesList.clear();
                contentValuesList.put("card_id", cardId);
                contentValuesList.put("website_id", addressId);
                db.insert("CardAddress", null, contentValuesList);
            }
        }
        db.close();
    }

    public Card getCard(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Card card = new Card();

        if (db != null) {
            String rawQuery = "SELECT site FROM WebSite INNER JOIN CardWebSite " +
                    "ON WebSite.id = CardWebSite.website_id WHERE CardWebSite.card_id = " + id + ";";
            Cursor cursor = db.rawQuery(rawQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    card.setWebsite(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            cursor.close();

            String rawQueryPhone = "SELECT phone FROM Phone INNER JOIN CardPhone " +
                    "ON Phone.id = CardPhone.phone_id WHERE CardPhone.card_id = " + id + ";";
            Cursor cursorPhone = db.rawQuery(rawQueryPhone, null);

            if (cursorPhone.moveToFirst()) {
                do {
                    card.setPhoneNumber(cursorPhone.getString(0));
                } while (cursorPhone.moveToNext());
            }
            cursorPhone.close();

            String rawQueryAddress = "SELECT address FROM Address INNER JOIN CardAddress " +
                    "ON Address.id = CardAddress.adress_id WHERE CardAddress.card_id = " + id + ";";
            Cursor cursorAddress = db.rawQuery(rawQueryAddress, null);

            if (cursorAddress.moveToFirst()) {
                do {
                    card.setOfficeAddress(cursorAddress.getString(0));
                } while (cursorAddress.moveToNext());
            }
            cursorAddress.close();

            String rawQueryName = "SELECT first_name FROM Name INNER JOIN Card" +
                    " ON Card.name_id = Name.id WHERE Card.id = " + id + ";";
            Cursor cursorName = db.rawQuery(rawQueryName, null);
            card.setContactName(cursorName.getString(0));
            cursorName.close();

            String rawQueryCompany = "SELECT name FROM Company INNER JOIN Card" +
                    " ON Card.company_id = Company.id WHERE Card.id = " + id + ";";
            Cursor cursorCompany = db.rawQuery(rawQueryCompany, null);
            card.setContactName(cursorCompany.getString(0));
            cursorCompany.close();
        }
        db.close();
        return card;
    }

    private List<Record> getRecords() {
        SQLiteDatabase db = this.getReadableDatabase();
        Card card;

        List<Record> records = new ArrayList<>();

        if (db != null) {
            String rawQuery = "SELECT id FROM Card;";
            Cursor cursor = db.rawQuery(rawQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    card = getCard(cursor.getLong(1));
                    records.add(cardToRecord(card));
                } while (cursor.moveToNext());
            }
        }
        db.close();
        return records;
    }

    public void addPhone(long cardId, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValuesList = new ContentValues();

        contentValuesList.clear();
        contentValuesList.put("phone", phone);
        long phoneId = db.insert("Phone", null, contentValuesList);

        contentValuesList.clear();
        contentValuesList.put("card_id", cardId);
        contentValuesList.put("phone_id", phoneId);
        db.insert("CardPhone", null, contentValuesList);
    }

    private void updatePhone(long cardId, String prevPhone, String nextPhone) {
        SQLiteDatabase db = this.getReadableDatabase();

        if (db != null) {

            String rawQuery = "UPDATE Phone SET phone = " + nextPhone + " WHERE " + prevPhone + " = (SELECT phone FROM Phone " +
                    "INNER JOIN Phone ON Phone.id = CardPhone.phone_id WHERE CardPhone.card_id = " + cardId + ";";

            Cursor cursor = db.rawQuery(rawQuery, null);
        }
    }

    private Record cardToRecord(Card card) {
        return new Record(card.getCompanyName(),
                card.getContactName(),
                getFirstElemFromHashSet(card.getPhoneNumber()),
                getFirstElemFromHashSet(card.getEmails()),
                getFirstElemFromHashSet(card.getWebsite()),
                getFirstElemFromHashSet(card.getOfficeAddress()));
    }

    private String getFirstElemFromHashSet(HashSet<String> hashSet) {
        Iterator<String> iterator = hashSet.iterator();

        if(iterator.hasNext()){
            return iterator.next();
        }else{
            return "";
        }
    }

    public void deleteCard(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String rawQuery = "DELETE FROM Card\n" +
                "        WHERE id = " + id + ";";
        Cursor cursor = db.rawQuery(rawQuery, null);

        cursor.close();
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        String rawQuery = "DELETE FROM Card;";
        Cursor cursor = db.rawQuery(rawQuery, null);

        cursor.close();
        db.close();
    }
}