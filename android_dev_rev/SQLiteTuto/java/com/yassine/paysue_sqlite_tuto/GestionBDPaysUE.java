package com.yassine.paysue_sqlite_tuto;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GestionBDPaysUE extends SQLiteOpenHelper {
    public static final String NOM_TABLE = "UnionEuropeenne";
    public static final String COL_ID = "_id";
    public static final String COL_INTITULE = "intitulé";
    public static final String COL_CAPITALE = "capitale";
    public static final String COL_ANNEE = "année";

    public static final String CREATE_TABLE = "CREATE TABLE " + NOM_TABLE + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                                                 + COL_INTITULE + " TEXT, "
                                                                                 + COL_CAPITALE + " TEXT, "
                                                                                 + COL_ANNEE + " INTEGER);";
    public static final String SELECT_ALL = "SELECT * FROM " + NOM_TABLE + " ORDER BY " + COL_ANNEE + " DESC";
    public static final String SUPPRIMER_TABLE = "DROP TABLE IF EXISTS " + NOM_TABLE;

    public GestionBDPaysUE(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE);
        db.execSQL("INSERT INTO " + NOM_TABLE + " (" + COL_INTITULE + ", " + COL_CAPITALE + ", " + COL_ANNEE + ") VALUES ('Allemagne', 'Berlin', 1990);");
        db.execSQL("INSERT INTO " + NOM_TABLE + " (" + COL_INTITULE + ", " + COL_CAPITALE + ", " + COL_ANNEE + ") VALUES ('Belgique', 'Bruxelles', 1990);");
        db.execSQL("INSERT INTO " + NOM_TABLE + " (" + COL_INTITULE + ", " + COL_CAPITALE + ", " + COL_ANNEE + ") VALUES ('France', 'Paris', 1990);");
        db.execSQL("INSERT INTO " + NOM_TABLE + " (" + COL_INTITULE + ", " + COL_CAPITALE + ", " + COL_ANNEE + ") VALUES ('Grèce', 'Athènes', 1990);");
        db.execSQL("INSERT INTO " + NOM_TABLE + " (" + COL_INTITULE + ", " + COL_CAPITALE + ", " + COL_ANNEE + ") VALUES ('Italie', 'Rome', 1990);");
        db.execSQL("INSERT INTO " + NOM_TABLE + " (" + COL_INTITULE + ", " + COL_CAPITALE + ", " + COL_ANNEE + ") VALUES ('Luxembourg', 'Luxembourg', 1990);");
        db.execSQL("INSERT INTO " + NOM_TABLE + " (" + COL_INTITULE + ", " + COL_CAPITALE + ", " + COL_ANNEE + ") VALUES ('Pays-Bas', 'Amsterdam', 1990);");
        ContentValues enregistrement = new ContentValues();
        enregistrement.put(COL_INTITULE, "Espagne");
        enregistrement.put(COL_CAPITALE, "Madrid");
        enregistrement.put(COL_ANNEE, 1990);
        db.insert(NOM_TABLE, null, enregistrement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(SUPPRIMER_TABLE);
        onCreate(db);
    }
}
