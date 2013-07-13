/**
 * 
 */
package com.raven.rssreader.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.raven.rssreader.data.ReaderDataContract.Entry;
import com.raven.rssreader.data.ReaderDataContract.Feed;
import com.raven.rssreader.data.ReaderDataContract.Type;
import com.raven.rssreader.data.ReaderDataContract.TypeOrder;
import com.raven.rssreader.utils.Log;

/**
 * The data access to the persist data
 * 
 * @author rweinberger
 * 
 */
public class ReaderDBHelper extends SQLiteOpenHelper {

	private static final String TAG = "ReaderDBHelper";

	private static final String DATABASE_NAME = "RavenReader.db";
	private static final int DATABASE_VERSION = 1;

	private static final String TYPE_INTEGER = " INTEGER";
	private static final String TYPE_TEXT = " TEXT";
	private static final String COMMA_SEP = ", ";
	private static final String CREATE_STATEMENT = "CREATE TABLE ";
	private static final String PRIMARY_KEY = TYPE_INTEGER + " PRIMARY KEY";
	private static final String DROP_STATEMENT = "DROP TABLE IF EXISTS ";

	/*
	 * private static final String DEFAULT_CLUSTER_TITLE = "General";
	 * 
	 * private static final String SQL_CREATE_CLUSTER = CREATE_STATEMENT +
	 * Cluster.TABLE_NAME + " (" + BaseColumns._ID + PRIMARY_KEY + COMMA_SEP +
	 * Cluster.COLUMN_NAME_TITLE + TYPE_TEXT + COMMA_SEP +
	 * Cluster.COLUMN_NAME_PATH_PATTERN + TYPE_TEXT + COMMA_SEP +
	 * Cluster.COLUMN_NAME_ICON + TYPE_TEXT + ")";
	 * 
	 * private static final String SQL_DELETE_CLUSTER = DROP_STATEMENT +
	 * Cluster.TABLE_NAME;
	 */

	private static final String SQL_CREATE_FEED = CREATE_STATEMENT
			+ Feed.TABLE_NAME + " (" + BaseColumns._ID + PRIMARY_KEY
			+ COMMA_SEP + Feed.COLUMN_NAME_TITLE + TYPE_TEXT + COMMA_SEP
			+ Feed.COLUMN_NAME_DESCRIPTION + TYPE_TEXT + COMMA_SEP
			+ Feed.COLUMN_NAME_PATH
			+ TYPE_TEXT
			+ COMMA_SEP
			// + Feed.COLUMN_NAME_CLUSTER + TYPE_INTEGER + COMMA_SEP
			+ Feed.COLUMN_NAME_CREATED + TYPE_INTEGER + COMMA_SEP
			+ Feed.COLUMN_NAME_LAST_UPDATED + TYPE_INTEGER + COMMA_SEP
			+ Feed.COLUMN_NAME_DELETE_MIN_RECORDS + TYPE_INTEGER + COMMA_SEP
			+ Feed.COLUMN_NAME_DELETE_MIN_TIME + TYPE_INTEGER + COMMA_SEP
			+ Feed.COLUMN_NAME_REFRESH_TIME + TYPE_INTEGER + ")";

	private static final String SQL_DELETE_FEED = DROP_STATEMENT
			+ Feed.TABLE_NAME;

	private static final String SQL_CREATE_ENTRY = CREATE_STATEMENT
			+ Entry.TABLE_NAME + " (" + BaseColumns._ID + PRIMARY_KEY
			+ COMMA_SEP + Entry.COLUMN_NAME_TITLE + TYPE_TEXT + COMMA_SEP
			+ Entry.COLUMN_NAME_DESCRIPTION + TYPE_TEXT + COMMA_SEP
			+ Entry.COLUMN_NAME_IMAGE + TYPE_TEXT + COMMA_SEP
			+ Entry.COLUMN_NAME_TARGET + TYPE_TEXT + COMMA_SEP
			+ Entry.COLUMN_NAME_FEED + TYPE_INTEGER + COMMA_SEP
			+ Entry.COLUMN_NAME_TIMESTAMP + TYPE_INTEGER + ")";

	private static final String SQL_DELETE_ENTRY = DROP_STATEMENT
			+ Entry.TABLE_NAME;

	private static final String SQL_CREATE_TYPE = CREATE_STATEMENT
			+ Type.TABLE_NAME + " (" + BaseColumns._ID + PRIMARY_KEY
			+ COMMA_SEP + Type.COLUMN_NAME_TITLE + TYPE_TEXT + ")";

	private static final String SQL_DELETE_TYPE = DROP_STATEMENT
			+ Type.TABLE_NAME;

	private static final String SQL_CREATE_TYPE_ORDER = CREATE_STATEMENT
			+ TypeOrder.TABLE_NAME + " (" + BaseColumns._ID + PRIMARY_KEY
			+ COMMA_SEP + TypeOrder.COLUMN_NAME_TYPE_ID + TYPE_INTEGER
			+ COMMA_SEP + TypeOrder.COLUMN_NAME_FEED_ID + TYPE_INTEGER
			+ COMMA_SEP + TypeOrder.COLUMN_NAME_ORDER + TYPE_INTEGER + ")";

	private static final String SQL_DELETE_TYPE_ORDER = DROP_STATEMENT
			+ TypeOrder.TABLE_NAME;

	/**********************************
	 * Triggers
	 **********************************/

	private static final String CREATE_TRIGGER_STATEMENT = "CREATE TRIGGER IF NOT EXSITS ";
	private static final String DROP_TRIGGER_STATEMENT = "DROP TRIGGER IF EXISTS ";
	private static final String FOR_EACH_BEGIN_TRIGGER = " FOR EACH ROW BEGIN ";
	private static final String END_TRIGGER = "; END";
	private static final String OPERATION_INSERT = " INSERT ";
	private static final String OPERATION_DELETE = " DELETE ";
	// private static final String OPERATION_UPDATE = " UPDATE ";
	private static final String OPERATION_BEFORE = " BEFORE ";
	private static final String OPERATION_AFTER = " AFTER ";

	private static final String TRIGGER_NAME_DELETE_ENTRY_ON_DELETE_FEED = "trg_delete_entry_upon_delete_feed";
	// private static final String TRIGGER_NAME_DELETE_FEED_ON_DELETE_CLUSTER =
	// "trg_delete_feed_upon_delete_cluster";
	private static final String TRIGGER_NAME_INSERT_TYPE_ORDER = "trg_insert_type_order";
	private static final String TRIGGER_NAME_DELETE_TYPE_ORDER_ON_DELETE_TYPE = "trg_delete_type_order_upon_delete_type";
	private static final String TRIGGER_NAME_DELETE_TYPE_ORDER_ON_DELETE_FEED = "trg_delete_type_order_upon_delete_feed";

	private static final String SQL_CREATE_TRIGGER_DELETE_ENTRY_ON_DELETE_FEED = CREATE_TRIGGER_STATEMENT
			+ TRIGGER_NAME_DELETE_ENTRY_ON_DELETE_FEED
			+ OPERATION_BEFORE
			+ OPERATION_DELETE
			+ "ON "
			+ Feed.TABLE_NAME
			+ FOR_EACH_BEGIN_TRIGGER
			+ "DELETE FROM "
			+ Entry.TABLE_NAME
			+ " WHERE "
			+ Entry.COLUMN_NAME_FEED
			+ " = old."
			+ BaseColumns._ID
			+ END_TRIGGER;

	/*
	 * private static final String
	 * SQL_CREATE_TRIGGER_DELETE_FEED_ON_DELETE_CLUSTER =
	 * CREATE_TRIGGER_STATEMENT + TRIGGER_NAME_DELETE_FEED_ON_DELETE_CLUSTER +
	 * OPERATION_BEFORE + OPERATION_DELETE + "ON " + Cluster.TABLE_NAME +
	 * FOR_EACH_BEGIN_TRIGGER + "DELETE FROM " + Feed.TABLE_NAME + " WHERE " +
	 * Feed.COLUMN_NAME_CLUSTER + " = old." + BaseColumns._ID + END_TRIGGER;
	 */
	private static final String SQL_CREATE_TRIGGER_INSERT_TYPE_ORDER = CREATE_TRIGGER_STATEMENT
			+ TRIGGER_NAME_INSERT_TYPE_ORDER
			+ OPERATION_AFTER
			+ OPERATION_INSERT
			+ "ON "
			+ TypeOrder.TABLE_NAME
			+ FOR_EACH_BEGIN_TRIGGER
			+ "UPDATE "
			+ TypeOrder.TABLE_NAME
			+ " SET "
			+ TypeOrder.COLUMN_NAME_ORDER
			+ " = "
			+ "SELECT MAX("
			+ TypeOrder.COLUMN_NAME_ORDER
			+ ") FROM "
			+ TypeOrder.TABLE_NAME
			+ " WHERE "
			+ TypeOrder.COLUMN_NAME_TYPE_ID
			+ " = old."
			+ TypeOrder.COLUMN_NAME_TYPE_ID
			+ " WHERE "
			+ BaseColumns._ID
			+ " = new." + BaseColumns._ID + END_TRIGGER;

	private static final String SQL_CREATE_TRIGGER_DELETE_TYPE_ORDER_ON_DELETE_TYPE = CREATE_TRIGGER_STATEMENT
			+ TRIGGER_NAME_DELETE_TYPE_ORDER_ON_DELETE_TYPE
			+ OPERATION_BEFORE
			+ OPERATION_DELETE
			+ "ON "
			+ Type.TABLE_NAME
			+ FOR_EACH_BEGIN_TRIGGER
			+ "DELETE FROM "
			+ TypeOrder.TABLE_NAME
			+ " WHERE "
			+ TypeOrder.COLUMN_NAME_TYPE_ID
			+ " = old."
			+ BaseColumns._ID + END_TRIGGER;

	private static final String SQL_CREATE_TRIGGER_DELETE_TYPE_ORDER_ON_DELETE_FEED = CREATE_TRIGGER_STATEMENT
			+ TRIGGER_NAME_DELETE_TYPE_ORDER_ON_DELETE_FEED
			+ OPERATION_BEFORE
			+ OPERATION_DELETE
			+ "ON "
			+ Feed.TABLE_NAME
			+ FOR_EACH_BEGIN_TRIGGER
			+ "DELETE FROM "
			+ TypeOrder.TABLE_NAME
			+ " WHERE "
			+ TypeOrder.COLUMN_NAME_FEED_ID
			+ " = old."
			+ BaseColumns._ID + END_TRIGGER;

	/**
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public ReaderDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
	 * .SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase database) {
		// logAndPerform(database, SQL_CREATE_CLUSTER);
		logAndPerform(database, SQL_CREATE_FEED);
		logAndPerform(database, SQL_CREATE_ENTRY);
		logAndPerform(database, SQL_CREATE_TYPE);
		logAndPerform(database, SQL_CREATE_TYPE_ORDER);

		createTriggers(database);

		// ContentValues cv = new ContentValues(1);
		// cv.put(Cluster.COLUMN_NAME_TITLE, DEFAULT_CLUSTER_TITLE);
		// database.insert(Cluster.TABLE_NAME, null, cv);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
	 * .SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		dropTriggers(database);
		logAndPerform(database, SQL_DELETE_TYPE_ORDER);
		logAndPerform(database, SQL_DELETE_TYPE);
		logAndPerform(database, SQL_DELETE_ENTRY);
		logAndPerform(database, SQL_DELETE_FEED);
		// logAndPerform(database, SQL_DELETE_CLUSTER);
		onCreate(database);
	}

	private static void createTriggers(SQLiteDatabase database) {
		logAndPerform(database, SQL_CREATE_TRIGGER_DELETE_ENTRY_ON_DELETE_FEED);
		// logAndPerform(database,
		// SQL_CREATE_TRIGGER_DELETE_FEED_ON_DELETE_CLUSTER);
		logAndPerform(database, SQL_CREATE_TRIGGER_INSERT_TYPE_ORDER);
		logAndPerform(database,
				SQL_CREATE_TRIGGER_DELETE_TYPE_ORDER_ON_DELETE_TYPE);
		logAndPerform(database,
				SQL_CREATE_TRIGGER_DELETE_TYPE_ORDER_ON_DELETE_FEED);
	}

	private static void dropTriggers(SQLiteDatabase database) {
		logAndPerform(database, DROP_TRIGGER_STATEMENT
				+ TRIGGER_NAME_DELETE_ENTRY_ON_DELETE_FEED);
		// logAndPerform(database, DROP_TRIGGER_STATEMENT
		// + TRIGGER_NAME_DELETE_FEED_ON_DELETE_CLUSTER);
		logAndPerform(database, DROP_TRIGGER_STATEMENT
				+ TRIGGER_NAME_INSERT_TYPE_ORDER);
		logAndPerform(database, DROP_TRIGGER_STATEMENT
				+ TRIGGER_NAME_DELETE_TYPE_ORDER_ON_DELETE_TYPE);
		logAndPerform(database, DROP_TRIGGER_STATEMENT
				+ TRIGGER_NAME_DELETE_TYPE_ORDER_ON_DELETE_FEED);
	}

	private static void logAndPerform(SQLiteDatabase db, String action) {
		Log.i(TAG, action);
		db.execSQL(action);
	}

}
