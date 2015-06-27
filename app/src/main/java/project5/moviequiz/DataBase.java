package project5.moviequiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DataBase extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "moviedb";
    private static final int DATABASE_VERSION = 1;
    private static final String MOVIE_TABLE_NAME = "movies";
    private static final String MOVIE_ID = "id";
    private static final String MOVIE_TITLE = "title";
    private static final String MOVIE_YEAR = "year";
    private static final String MOVIE_DIRECTOR = "director";
    private static final String MOVIE_FILE_NAME = "movies7.csv";
    private static final String MOVIE_CREATE_TABLE = "CREATE TABLE "+ MOVIE_TABLE_NAME + "("+MOVIE_ID+" integer primary key autoincrement, "
                                                                            +MOVIE_TITLE+" text, "
                                                                            +MOVIE_YEAR+" integer, "
                                                                            +MOVIE_DIRECTOR+" text);";
    //DATABASE ENTRY FOR STARS
    private static final String STAR_TABLE_NAME = "stars";
    private static final String STAR_ID = "s_id";
    private static final String STAR_FNAME = "first_name";
    private static final String STAR_LNAME = "last_name";
    private static final String STAR_DOB = "dob";
    private static final String STAR_FILE_NAME = "stars.csv";
    private static final String STAR_CREATE_TABLE = "CREATE TABLE "+ STAR_TABLE_NAME + "("+STAR_ID+" integer primary key autoincrement, "+STAR_FNAME+" text not null, " + STAR_LNAME + " text not null, " + STAR_DOB + " text not null);";

    //DATABASE ENTRY FOR STARS_IN_MOVIES
    private static final String SM_TABLE_NAME = "stars_in_movies";
    private static final String SM_MOVIE_ID = "movie_id";
    private static final String SM_STAR_ID = "star_id";
    private static final String SM_FILE_NAME = "stars_in_movies.csv";
    private static final String SM_CREATE_TABLE = "CREATE TABLE "+ SM_TABLE_NAME + "("+SM_STAR_ID+" integer, "+ SM_MOVIE_ID +" integer, FOREIGN KEY(" + SM_STAR_ID + ") REFERENCES stars(s_id), FOREIGN KEY (" + SM_MOVIE_ID + ") REFERENCES movies(s_id));";

    //DATABASE ENTRY FOR STARS_IN_MOVIES
    private static final String STATS = "stats";
    private static final String id = "stats_id";
    private static final String game = "games";
    private static final String cor = "correct";
    private static final String wrong = "wrong";
    private static final String avg = "avg";



    private static final String STATS_CREATE_TABLE = "CREATE TABLE "+ STATS + "("+id+" integer primary key, "
                                                                                 +game+" integer, "
                                                                                 + cor + " integer, "
                                                                                 + wrong + " integer, "
                                                                                 + avg +" integer);";
    private SQLiteDatabase mDb;
    private Context mContext;

    public DataBase(Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = ctx;
        this.mDb = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MOVIE_CREATE_TABLE);
        db.execSQL(STAR_CREATE_TABLE);
        db.execSQL(SM_CREATE_TABLE);
        db.execSQL(STATS_CREATE_TABLE);
        // populate database
        try {
            BufferedReader in_movies = new BufferedReader(new InputStreamReader(mContext.getAssets().open(MOVIE_FILE_NAME)));
            String line=null;

            while((line=in_movies.readLine())!=null) {
                String[] tokens = line.split(",");
                ContentValues movie_values = new ContentValues();
                movie_values.put(MOVIE_ID, tokens[0]);
                movie_values.put(MOVIE_TITLE, tokens[1]);
                movie_values.put(MOVIE_YEAR, tokens[2]);
                movie_values.put(MOVIE_DIRECTOR, tokens[3]);
                db.insert(MOVIE_TABLE_NAME, null, movie_values);
                line = null;
            }

            BufferedReader in_stars = new BufferedReader(new InputStreamReader(mContext.getAssets().open(STAR_FILE_NAME)));
            while((line=in_stars.readLine())!=null) {
                String[] tokens = line.split(",");
                ContentValues star_values = new ContentValues();
                star_values.put(STAR_ID, tokens[0]);
                star_values.put(STAR_FNAME, tokens[1]);
                star_values.put(STAR_LNAME, tokens[2]);
                star_values.put(STAR_DOB, tokens[3]);
                db.insert(STAR_TABLE_NAME, null, star_values);
                line = null;
            }

            BufferedReader in_sm = new BufferedReader(new InputStreamReader(mContext.getAssets().open(SM_FILE_NAME)));
            while((line=in_sm.readLine())!=null) {
                String[] tokens = line.split(",");
                ContentValues sm_values = new ContentValues();
                sm_values.put(SM_STAR_ID, tokens[0]);
                sm_values.put(SM_MOVIE_ID, tokens[1]);
                db.insert(SM_TABLE_NAME, null, sm_values);
                line = null;
            }

            ContentValues stats_values = new ContentValues();
            stats_values.put(id, 0);
            stats_values.put(game, 0);
            stats_values.put(cor, 0);
            stats_values.put(wrong, 0);
            stats_values.put(avg,0);
            db.insert(STATS, null, stats_values);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+MOVIE_TABLE_NAME);
        db.execSQL("UPDATE stats SET game = 0, cor = 0, wrong=0, avg=0 WHERE employee_id = 1;");
        onCreate(db);
    }

    public void insertRecords(int g, int c, int w, int a){

        ContentValues args = new ContentValues();
        args.put(game, g);
        args.put(cor, c);
        args.put(wrong, w);
        args.put(avg, a);

        mDb.update(STATS, args, id + "=" + 0, null);
    }

    public Cursor fetchAll() {
        return mDb.query(MOVIE_TABLE_NAME, new String[] {MOVIE_TITLE}, null, null, null, null, null);
    }

    public Cursor fetchStats() {
        return mDb.rawQuery("SELECT * FROM stats", null);
    }

    public Cursor firstQuery() {
        return mDb.rawQuery("SELECT DISTINCT title, director FROM movies where title is not null group by director order by random() limit 4", null);
    }

    public Cursor secondQuery() {
        return mDb.rawQuery("SELECT DISTINCT title, year FROM movies where title is not null group by year order by random() limit 4", null);
    }

    public Cursor thirdQuery(){
        return mDb.rawQuery("select title, last_name from stars " +
                            "left join stars_in_movies on stars_in_movies.star_id = stars.s_id " +
                            "left join movies on stars_in_movies.movie_id = movies.id " +
                            "where title is not null " +
                            "group by last_name order by random() limit 4", null);
    }

    public Cursor fourthQuery(){
        return mDb.rawQuery("select distinct title " +
                ", group_concat(last_name) " +
                "from stars " +
                "left join stars_in_movies on stars_in_movies.star_id = stars.s_id " +
                "left join movies on stars_in_movies.movie_id = movies.id " +
                "where title is not null " +
                "group by title " +
                "having count(*) = 3 " +
                "order by random() limit 4", null);
    }

    public Cursor fifthQuery(){
        return mDb.rawQuery("select title, group_concat(last_name) from stars s left join stars_in_movies sm on s.s_id = sm.star_id left join movies m on sm.movie_id = m.id group by title having count(*) = 2 order by random() limit 4", null);
    }

    public Cursor sixthQuery(){
        return mDb.rawQuery("select last_name, director from stars s left join stars_in_movies sm on s.s_id = sm.star_id left join movies m on sm.movie_id = m.id group by last_name order by random() limit 4", null);
    }

    public Cursor seventhQuery(){

        return mDb.rawQuery("select last_name, group_concat(director) from stars s left join stars_in_movies sm on s.s_id = sm.star_id left join movies m on sm.movie_id = m.id group by last_name having count(*) = 3 order by random() limit 4",null);
    }

    public Cursor eighthQuery(){
        return mDb.rawQuery("select last_name, group_concat(title) from stars s left join stars_in_movies sm on s.s_id = sm.star_id left join movies m on sm.movie_id = m.id group by last_name having count(*) = 2 order by random() limit 4", null);
    }

    public Cursor ninthQuery(){
        return mDb.rawQuery("select title, group_concat(last_name) from stars s left join stars_in_movies sm on s.s_id = sm.star_id left join movies m on sm.movie_id = m.id group by title having count(*) = 4 order by random() limit 4", null);
    }
    public Cursor tenthQuery(){
        return mDb.rawQuery("select last_name, director, year from stars s left join stars_in_movies sm on s.s_id = sm.star_id left join movies m on sm.movie_id = m.id group by last_name order by random() limit 4", null);
    }
}
