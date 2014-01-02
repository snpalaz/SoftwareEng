package core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MancalaDB {

	/* Columns of mancalaTable */
	public static final String KEY_ROWID = "_id";
	public static final String KEY_DIFFICULTY = "difficulty_level";
	public static final String KEY_STONE = "numberof_stone";
	public static final String KEY_WIN = "numberof_win";
	public static final String KEY_DRAW = "numberof_draw";
	public static final String KEY_LOSE = "numberof_lose";

	private static final String DATABASE_NAME = "Mancaladb";
	private static final String DATABASE_TABLE = "mancalaTable";
	private static final int DATABASE_VERSION = 1;

	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;

	private static class DbHelper extends SQLiteOpenHelper {
		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + "(" + KEY_ROWID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_DIFFICULTY
					+ " TEXT NOT NULL," + KEY_STONE + " TEXT NOT NULL,"
					+ KEY_WIN + " TEXT NOT NULL," + KEY_DRAW
					+ " TEXT NOT NULL," + KEY_LOSE + " TEXT NOT NULL);");

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE);
			onCreate(db);
		}

	}

	public MancalaDB(Context c) {
		ourContext = c;
	}

	public MancalaDB open() throws SQLException {
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	public void close() {

		ourHelper.close();
	}

	public long createEntry(String diff, String stone, String win, String draw,
			String lose) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_DIFFICULTY, diff);
		cv.put(KEY_STONE, stone);
		cv.put(KEY_WIN, win);
		cv.put(KEY_DRAW, draw);
		cv.put(KEY_LOSE, lose);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}

	public String getData() {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID, KEY_DIFFICULTY, KEY_STONE,
				KEY_WIN, KEY_DRAW, KEY_LOSE };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);
		String result = "";
		int rowId = c.getColumnIndex(KEY_ROWID);
		int rowDiff = c.getColumnIndex(KEY_DIFFICULTY);
		int rowStone = c.getColumnIndex(KEY_STONE);
		int rowWin = c.getColumnIndex(KEY_WIN);
		int rowDraw = c.getColumnIndex(KEY_DRAW);
		int rowLose = c.getColumnIndex(KEY_LOSE);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(rowId) + " " + c.getString(rowDiff)
					+ " " + c.getString(rowStone) + c.getString(rowWin)
					+ c.getString(rowDraw) + c.getString(rowLose) + "\n";
		}
		return result;
	}

	public String getDiffLevel(long l) throws SQLException {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID, KEY_DIFFICULTY, KEY_STONE,
				KEY_WIN, KEY_DRAW, KEY_LOSE };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "="
				+ l, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
			String name = c.getString(1);
			return name;
		}
		return null;
	}

	public String getStone(long l) throws SQLException {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID, KEY_DIFFICULTY, KEY_STONE,
				KEY_WIN, KEY_DRAW, KEY_LOSE };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "="
				+ l, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
			String stone = c.getString(2);
			return stone;
		}
		return null;
	}

	public String getWin(long l) throws SQLException {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID, KEY_DIFFICULTY, KEY_STONE,
				KEY_WIN, KEY_DRAW, KEY_LOSE };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "="
				+ l, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
			String win = c.getString(3);
			return win;
		}
		return null;
	}

	public String getDraw(long l) throws SQLException {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID, KEY_DIFFICULTY, KEY_STONE,
				KEY_WIN, KEY_DRAW, KEY_LOSE };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "="
				+ l, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
			String draw = c.getString(4);
			return draw;
		}
		return null;
	}

	public String getLose(long l) throws SQLException {
		String[] columns = new String[] { KEY_ROWID, KEY_DIFFICULTY, KEY_STONE,
				KEY_WIN, KEY_DRAW, KEY_LOSE };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "="
				+ l, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
			String lose = c.getString(5);
			return lose;
		}
		return null;
	}

	public void updateEntry(long lRow, String diff, String stone, String win,
			String draw, String lose) throws SQLException {
		ContentValues cv = new ContentValues();
		cv.put(KEY_DIFFICULTY, diff);
		cv.put(KEY_STONE, stone);
		cv.put(KEY_WIN, win);
		cv.put(KEY_DRAW, draw);
		cv.put(KEY_LOSE, lose);
		ourDatabase.update(DATABASE_TABLE, cv, KEY_ROWID + "=" + lRow, null);
	}

	public void updateSettings(long lRow, String diff, String stone) throws SQLException {
		ContentValues cv = new ContentValues();
		cv.put(KEY_DIFFICULTY, diff);
		cv.put(KEY_STONE, stone);
		ourDatabase.update(DATABASE_TABLE, cv, KEY_ROWID + "=" + lRow, null);
	}
	
	public void deleteEntry(long lRow1) throws SQLException {
		ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=" + lRow1, null);
	}
}
