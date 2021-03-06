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
    private static final String TABLE_ARRAY[] = {"CREATE TABLE [Address] (\n" +
            "  [id] INTEGER primary key autoincrement not null unique, \n" +
            "  [address] NVARCHAR, \n" +
            "  [country] NVARCHAR default '" + DEFAULT_COUNTRY + "', \n" +
            "  [city] NVARCHAR, \n" +
            "  [street] NVARCHAR, \n" +
            "  [house] NVARCHAR, \n" +
            "  [room] NVARCHAR, \n" +
            "  [postIndex] INTEGER);\n",
            "CREATE TABLE [Company](\n" +
                    "  [id] INTEGER primary key autoincrement not null unique, \n" +
                    "  [name] NVARCHAR,\n" +
                    "  [type] NVARCHAR\n" +
                    ");\n",
            "CREATE TABLE [Name](\n" +
                    "  [id] INTEGER primary key autoincrement not null unique, \n" +
                    "  [first_name] NVARCHAR, \n" +
                    "  [last_name] NVARCHAR, \n" +
                    "  [patronymic] NVARCHAR\n" +
                    ");\n",
            "CREATE TABLE [Phone](\n" +
                    "  [id] INTEGER primary key autoincrement not null unique, \n" +
                    "  [phone] NVARCHAR, \n" +
                    "  [name_ru] NVARCHAR, \n" +
                    "  [name_en] NVARCHAR\n" +
                    ");\n",
            "CREATE TABLE [Email](\n" +
                    "  id INTEGER primary key autoincrement not null unique, \n" +
                    "  email NVARCHAR \n" +
                    ");\n",
            "CREATE TABLE [WebSite](\n" +
                    "  id INTEGER primary key autoincrement not null unique, \n" +
                    "  site NVARCHAR, \n" +
                    "  name_ru NVARCHAR, \n" +
                    "  name_en NVARCHAR\n" +
                    ");\n",
            "CREATE TABLE [Card](\n" +
                    "  id INTEGER primary key autoincrement not null unique, \n" +
                    "  name_id INTEGER, \n" +
                    "  company_id INTEGER, \n" +
                    "  photo NVARCHAR, \n" +
                    "  FOREIGN KEY (name_id) REFERENCES [Name]([id]) ON DELETE CASCADE ON UPDATE CASCADE, \n" +
                    "  FOREIGN KEY (company_id) REFERENCES [Company]([id]) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                    ");\n",
            "CREATE TABLE [CardAddress](\n" +
                    "  card_id INTEGER,\n" +
                    "  address_id INTEGER,\n" +
                    "  FOREIGN KEY (card_id) REFERENCES [Card](id) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                    "  FOREIGN KEY (address_id) REFERENCES [Address](id) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                    ");\n",
            "CREATE TABLE [CardEmail](\n" +
                    "  card_id INTEGER,\n" +
                    "  email_id INTEGER,\n" +
                    "  FOREIGN KEY (card_id) REFERENCES [Card](id) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                    "  FOREIGN KEY (email_id) REFERENCES [Email](id) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                    ");\n",
            "CREATE TABLE [CardPhone](\n" +
                    "  card_id INTEGER,\n" +
                    "  phone_id INTEGER,\n" +
                    "  FOREIGN KEY (card_id) REFERENCES [Card](id) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                    "  FOREIGN KEY (phone_id) REFERENCES [Phone](id) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                    ");\n",
            "CREATE TABLE [CardWebSite](\n" +
                    "  card_id INTEGER,\n" +
                    "  website_id INTEGER,\n" +
                    "  FOREIGN KEY (card_id) REFERENCES [Card](id) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                    "  FOREIGN KEY (website_id) REFERENCES [WebSite](id) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                    ");"};

    //private SQLiteDatabase dbaseR;
    //private SQLiteDatabase dbaseW;

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
        for (String table : TABLE_NAME_ARRAY) {
            db.execSQL("DROP TABLE IF EXISTS " + table);
        }
        for (String s : TABLE_ARRAY) {
            db.execSQL(s);
        }
    }

    private void addPhonesAddress(Card card, long cardId) {
        SQLiteDatabase dbaseW = this.getWritableDatabase();

        ContentValues contentValuesList = new ContentValues();
        for (String phone : card.getPhoneNumbers()) {
            contentValuesList.clear();
            contentValuesList.put("phone", phone);
            long phoneId = dbaseW.insert("Phone", null, contentValuesList);

            contentValuesList.clear();
            contentValuesList.put("card_id", cardId);
            contentValuesList.put("phone_id", phoneId);
            dbaseW.insert("CardPhone", null, contentValuesList);
        }

        for (String email : card.getEmails()) {
            contentValuesList.clear();
            contentValuesList.put("email", email);
            long emailId = dbaseW.insert("Email", null, contentValuesList);

            contentValuesList.clear();
            contentValuesList.put("card_id", cardId);
            contentValuesList.put("email_id", emailId);
            dbaseW.insert("CardEmail", null, contentValuesList);
        }

        for (String site : card.getWebsite()) {
            contentValuesList.clear();
            contentValuesList.put("site", site);
            long webSiteId = dbaseW.insert("WebSite", null, contentValuesList);

            contentValuesList.clear();
            contentValuesList.put("card_id", cardId);
            contentValuesList.put("website_id", webSiteId);
            dbaseW.insert("CardWebSite", null, contentValuesList);
        }

        for (String adress : card.getOfficeAddress()) {
            contentValuesList.clear();
            contentValuesList.put("address", adress);
            long addressId = dbaseW.insert("Address", null, contentValuesList);

            contentValuesList.clear();
            contentValuesList.put("card_id", cardId);
            contentValuesList.put("address_id", addressId);
            dbaseW.insert("CardAddress", null, contentValuesList);
        }
        //  dbaseW.close();
    }

    public long addCard(Card card) {
        SQLiteDatabase dbaseW = this.getWritableDatabase();

        if (dbaseW != null) {

            System.out.println(dbaseW != null);
            long id = card.getId();
            long cardId = id;
            long nameId;
            long companyId;

            dbaseW.delete("CardWebSite", "card_id = " + id, null);
            dbaseW.delete("CardAddress", "card_id = " + id, null);
            dbaseW.delete("CardPhone", "card_id = " + id, null);
            dbaseW.delete("CardEmail", "card_id = " + id, null);

            Long nnameId;
            Long ccompanyId;

            String rawQuery = "SELECT name_id, company_id FROM Card " +
                    " WHERE Card.id = " + id + ";";
            Cursor cursor = dbaseW.rawQuery(rawQuery, null);

            if (cursor.moveToFirst()) {

                nnameId = cursor.getLong(cursor.getColumnIndex("name_id"));
                ccompanyId = cursor.getLong(cursor.getColumnIndex("company_id"));

                ContentValues cv = new ContentValues();
                cv.put("photo", card.getImage());

                dbaseW.update("Card", cv, "id = ?", new String[]{Long.toString(id)});
                cv.clear();

                cv = new ContentValues();
                cv.put("first_name", card.getFirstName());
                cv.put("last_name", card.getLastName());
                cv.put("patronymic", card.getFatherName());

                dbaseW.update("Name", cv, "id = ?", new String[]{nnameId.toString()});
                cv.clear();

                cv.put("name", card.getCompanyName());
                cv.put("type", card.getTypeOfOrganization());
                dbaseW.update("Company", cv, "id = ?", new String[]{ccompanyId.toString()});
            } else {

                ContentValues contentValuesList = new ContentValues();
                contentValuesList.put("first_name", card.getFirstName());
                contentValuesList.put("last_name", card.getLastName());
                contentValuesList.put("patronymic", card.getFatherName());
                nameId = dbaseW.insert("Name", null, contentValuesList);

                contentValuesList.clear();
                contentValuesList.put("name", card.getCompanyName());
                contentValuesList.put("type", card.getTypeOfOrganization());
                companyId = dbaseW.insert("Company", null, contentValuesList);

                contentValuesList.clear();
                contentValuesList.put("name_id", nameId);
                contentValuesList.put("company_id", companyId);
                contentValuesList.put("photo", card.getImage());
                cardId = dbaseW.insert("Card", null, contentValuesList);
            }

            addPhonesAddress(card, cardId);
            dbaseW.close();
            return cardId;
        }
        dbaseW.close();
        return -2;
    }

    public Card getCard(long id) {
        SQLiteDatabase dbaseR = this.getReadableDatabase();
        Card card = new Card();

        if (dbaseR != null) {
            card.setId(id);
            System.out.println("\ninnner in getCard");

            String rawQuery = "SELECT site FROM WebSite INNER JOIN CardWebSite " +
                    "ON WebSite.id = CardWebSite.website_id WHERE CardWebSite.card_id = " + id + ";";
            Cursor cursor = dbaseR.rawQuery(rawQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    card.setWebsite(cursor.getString(cursor.getColumnIndex("site")));
                } while (cursor.moveToNext());
            }

            String rawQueryPhone = "SELECT phone FROM Phone INNER JOIN CardPhone " +
                    "ON Phone.id = CardPhone.phone_id WHERE CardPhone.card_id = " + id + ";";
            cursor = dbaseR.rawQuery(rawQueryPhone, null);

            if (cursor.moveToFirst()) {
                do {
                    card.setPhoneNumber(cursor.getString(cursor.
                            getColumnIndex("phone")));
                } while (cursor.moveToNext());
            }

            String rawQueryAddress = "SELECT address FROM Address INNER JOIN CardAddress " +
                    "ON Address.id = CardAddress.address_id WHERE CardAddress.card_id = " + id + ";";
            cursor = dbaseR.rawQuery(rawQueryAddress, null);

            if (cursor.moveToFirst()) {
                do {
                    card.setOfficeAddress(cursor.getString(cursor.
                            getColumnIndex("address")));
                } while (cursor.moveToNext());
            }

            String rawQueryEmail = "SELECT email FROM Email INNER JOIN CardEmail " +
                    "ON Email.id = CardEmail.email_id WHERE CardEmail.card_id = " + id + ";";
            cursor = dbaseR.rawQuery(rawQueryEmail, null);

            if (cursor.moveToFirst()) {
                do {
                    card.setEmails(cursor.getString(cursor.
                            getColumnIndex("email")));
                } while (cursor.moveToNext());
            }

            String rawQueryName = "SELECT first_name, last_name, patronymic FROM Name INNER JOIN Card" +
                    " ON Card.name_id = Name.id WHERE Card.id = " + id + ";";
            cursor = dbaseR.rawQuery(rawQueryName, null);

            if (cursor.moveToFirst()) {
                card.setFirstName(cursor.getString(cursor.getColumnIndex("first_name")));
                card.setLastName(cursor.getString(cursor.getColumnIndex("last_name")));
                card.setFatherName(cursor.getString(cursor.getColumnIndex("patronymic")));
            }

            String rawQueryPhoto = "SELECT photo FROM Card" +
                   " WHERE Card.id = " + id + ";";
            cursor = dbaseR.rawQuery(rawQueryPhoto, null);

            if (cursor.moveToFirst()) {
                card.setImage(cursor.getString(cursor.
                        getColumnIndex("photo")));
            }

            String rawQueryCompany = "SELECT name FROM Company INNER JOIN Card" +
                    " ON Card.company_id = Company.id WHERE Card.id = " + id + ";";
            cursor = dbaseR.rawQuery(rawQueryCompany, null);

            if (cursor.moveToFirst()) {
                card.setCompanyName(cursor.getString(cursor.
                        getColumnIndex("name")));
            }

            cursor.close();
        }
        //dbaseR.close();
        return card;
    }

    public List<Record> getRecords() {
        SQLiteDatabase dbaseR = getReadableDatabase();

        List<Record> records = new ArrayList<>();

        if (dbaseR != null) {
            String rawQuery = "SELECT id FROM Card;";
            Cursor cursor = dbaseR.rawQuery(rawQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    long id = cursor.getLong(0);
                    if (id != -1) {
                        records.add(cardToRecord(getCard(id)));
                    }
                } while (cursor.moveToNext());
            }
        }
        dbaseR.close();
        return records;
    }

    public void addPhone(long cardId, String phone) {
        SQLiteDatabase dbaseW = this.getWritableDatabase();
        ContentValues contentValuesList = new ContentValues();

        contentValuesList.clear();
        contentValuesList.put("phone", phone);
        long phoneId = dbaseW.insert("Phone", null, contentValuesList);

        contentValuesList.clear();
        contentValuesList.put("card_id", cardId);
        contentValuesList.put("phone_id", phoneId);
        dbaseW.insert("CardPhone", null, contentValuesList);
        dbaseW.close();
    }

    private void updatePhone(long cardId, String prevPhone, String nextPhone) {
        SQLiteDatabase dbaseR = this.getReadableDatabase();

        if (dbaseR != null) {

            String rawQuery = "UPDATE Phone SET phone = " + nextPhone + " WHERE " + prevPhone + "" +
                    " = (SELECT phone FROM Phone INNER JOIN CardPhone ON Phone.id = " +
                    "CardPhone.phone_id WHERE CardPhone.card_id = " + cardId + ";";

            Cursor cursor = dbaseR.rawQuery(rawQuery, null);
        }
        dbaseR.close();
    }

    public void addEmail(long cardId, String email) {
        SQLiteDatabase dbaseW = this.getWritableDatabase();
        ContentValues contentValuesList = new ContentValues();

        contentValuesList.clear();
        contentValuesList.put("email", email);
        long phoneId = dbaseW.insert("Email", null, contentValuesList);

        contentValuesList.clear();
        contentValuesList.put("card_id", cardId);
        contentValuesList.put("email_id", phoneId);
        dbaseW.insert("CardEmail", null, contentValuesList);
        dbaseW.close();
    }

    private void updateEmail(long cardId, String prevPhone, String nextPhone) {
        SQLiteDatabase dbaseR = this.getReadableDatabase();

        if (dbaseR != null) {

            String rawQuery = "UPDATE Email SET email = " + nextPhone + " WHERE " + prevPhone +
                    " = (SELECT email FROM Email INNER JOIN CardEmail ON Email.id = " +
                    "CardEmail.email_id WHERE CardEmail.card_id = " + cardId + ";";

            Cursor cursor = dbaseR.rawQuery(rawQuery, null);
        }
        dbaseR.close();
    }

    public void addAddress(long cardId, String address) {
        SQLiteDatabase dbaseW = this.getWritableDatabase();
        ContentValues contentValuesList = new ContentValues();

        contentValuesList.clear();
        contentValuesList.put("address", address);
        long phoneId = dbaseW.insert("Address", null, contentValuesList);

        contentValuesList.clear();
        contentValuesList.put("card_id", cardId);
        contentValuesList.put("address_id", phoneId);
        dbaseW.insert("CardAddress", null, contentValuesList);
        dbaseW.close();
    }

    private void updateAddress(long cardId, String prevPhone, String nextPhone) {
        SQLiteDatabase dbaseR = this.getReadableDatabase();

        if (dbaseR != null) {

            String rawQuery = "UPDATE Address SET address = " + nextPhone + " WHERE " + prevPhone +
                    " = (SELECT address FROM Address INNER JOIN CardAddress ON Address.id = " +
                    "CardAddress.address_id WHERE CardAddress.card_id = " + cardId + ";";

            Cursor cursor = dbaseR.rawQuery(rawQuery, null);
        }

        dbaseR.close();
    }

    public void addWebsite(long cardId, String address) {
        SQLiteDatabase dbaseW = this.getWritableDatabase();
        ContentValues contentValuesList = new ContentValues();

        contentValuesList.clear();
        contentValuesList.put("site", address);
        long phoneId = dbaseW.insert("WebSite", null, contentValuesList);

        contentValuesList.clear();
        contentValuesList.put("card_id", cardId);
        contentValuesList.put("website_id", phoneId);
        dbaseW.insert("CardWebSite", null, contentValuesList);

        dbaseW.close();
    }

    private void updateWebsite(long cardId, String prevPhone, String nextPhone) {
        SQLiteDatabase dbaseR = this.getReadableDatabase();

        if (dbaseR != null) {

            String rawQuery = "UPDATE WebSite SET site = " + nextPhone + " WHERE " + prevPhone +
                    " = (SELECT site FROM WebSite INNER JOIN CardWebSite ON WebSite.id = " +
                    "CardWebSite.website_id WHERE CardWebSite.card_id = " + cardId + ";";

            Cursor cursor = dbaseR.rawQuery(rawQuery, null);
        }
        dbaseR.close();
    }

    private void updateName(long cardId, String name) {
        SQLiteDatabase dbaseR = this.getReadableDatabase();

        if (dbaseR != null) {

            String rawQuery = "UPDATE Name SET first_name = " + name + "INNER JOIN Card" +
                    " ON Name.id = Card.name_id WHERE Card.id = " + cardId + ";";

            Cursor cursor = dbaseR.rawQuery(rawQuery, null);
        }
        dbaseR.close();
    }

    private void updateCompany(long cardId, String company) {
        SQLiteDatabase dbaseR = this.getReadableDatabase();

        if (dbaseR != null) {

            String rawQuery = "UPDATE Company SET name = " + company + "INNER JOIN Card" +
                    " ON Company.id = Card.company_id WHERE Card.id = " + cardId + ";";

            Cursor cursor = dbaseR.rawQuery(rawQuery, null);
        }
        dbaseR.close();
    }

    private static Record cardToRecord(Card card) {
        return new Record(card.getId(),
                card.getCompanyName(),
                card.getFirstName(),
                card.getLastName(),
                card.getFatherName(),
                getFirstElemFromHashSet(card.getPhoneNumbers()),
                getFirstElemFromHashSet(card.getEmails()),
                getFirstElemFromHashSet(card.getWebsite()),
                getFirstElemFromHashSet(card.getOfficeAddress()),
                card.getImage());
    }

    private static String getFirstElemFromHashSet(HashSet<String> hashSet) {
        Iterator<String> iterator = hashSet.iterator();

        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            return "";
        }
    }

    public Card deleteCard(long id) {
        SQLiteDatabase dbaseW = this.getWritableDatabase();

        Card card = getCard(id);

        int delCount = dbaseW.delete("Card", "id = " + id, null);
        System.out.println("delete from db = " + delCount);
        dbaseW.close();
        return card;
    }

    public void deleteAll() {
        SQLiteDatabase dbaseW = this.getWritableDatabase();
        String rawQuery = "DELETE FROM Card;";
        Cursor cursor = dbaseW.rawQuery(rawQuery, null);

        cursor.close();
        dbaseW.close();
    }
}