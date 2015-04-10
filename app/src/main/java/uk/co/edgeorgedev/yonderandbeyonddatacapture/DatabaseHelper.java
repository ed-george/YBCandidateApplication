package uk.co.edgeorgedev.yonderandbeyonddatacapture;

/**
 * Created by edgeorge on 05/02/15.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    //       .==.       .==.
    //      //`^\\     //^`\\
    //     // ^ ^\(\__/)/^ ^^\\
    //    //^ ^^ ^/6 6\ ^^ ^ \\
    //   //^ ^^ ^/( .. )\^ ^ ^ \\
    //  // ^^ ^/\| v""v |/\^ ^ ^\\
    // // ^^/\/ /  `~~`  \ \/\^ ^\\
    // -----------------------------
    // / HERE BE DRAGONS - Please update this properly!
    // / Screw-ups here would be bad...m'kay

    private static final String BASE_DATABASE_NAME = "candidates.db";
    public static final int DATABASE_VERSION = 1;
    private static DatabaseHelper instance;

    public DatabaseHelper(final Context context) {
        super(context, BASE_DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            createTables(connectionSource);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    public static DatabaseHelper getDatabaseHelperInstance(Context c) {

        if (instance == null) {
            instance = new DatabaseHelper(c);
        }
        return instance;
    }


    /**
     * @param connectionSource
     */
    private void createTables(ConnectionSource connectionSource) throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, Candidate.class);

    }

    private void dropTables(ConnectionSource connectionSource) throws SQLException {

        TableUtils.dropTable(connectionSource, Candidate.class, true);

    }

    public boolean resetAllData(){
        try{
            dropTables(getConnectionSource());
            createTables(getConnectionSource());
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void close() {
        super.close();
    }

    public static String getSQLiteVersion(){
        String sqliteVersion = "";
        if(instance != null){
            Cursor cursor = instance.getReadableDatabase().rawQuery("select sqlite_version() AS sqlite_version", null);
            while(cursor.moveToNext()){
                sqliteVersion += cursor.getString(0);
            }
        }else{
            sqliteVersion = "ERROR";
        }
        return sqliteVersion;
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {}
}
