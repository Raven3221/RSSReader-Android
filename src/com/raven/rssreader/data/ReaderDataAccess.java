/**
 * 
 */
package com.raven.rssreader.data;

import com.raven.rssreader.data.ReaderDataContract.Feed;
import com.raven.rssreader.data.ReaderDataContract.TypeOrder;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * @author rweinberger
 * 
 */
public class ReaderDataAccess {

	private static final String SQL_SELECT = "SELECT ";
	private static final String SQL_FROM = " FROM ";
	private static final String SQL_WHERE = " WHERE ";
	private static final String SQL_ORDER_BY = " ORDER BY ";

	private static final String SQL_ORDER_ASC = " ASC";
	private static final String SQL_ORDER_DESC = " DESC";

	private SQLiteDatabase db = null;
	private ReaderDBHelper dbHelper = null;

	/*************************************************************
	 * Singleton pattern
	 *************************************************************/
	private static ReaderDataAccess instance = null;

	private ReaderDataAccess(Context context) {
		this.dbHelper = new ReaderDBHelper(context);
		open();
	}

	public static synchronized ReaderDataAccess initializeInstance(
			Context context) {
		if (null == instance) {
			instance = new ReaderDataAccess(context);
		}
		return instance;
	}

	public static ReaderDataAccess getInstance() {
		return instance;
	}

	/*************************************************************
	 * Opening / Closing the database
	 *************************************************************/
	public synchronized void open() {
		if (null == this.db) {
			this.db = this.dbHelper.getWritableDatabase();
		}
	}

	public synchronized void close() {
		this.db = null;
		this.dbHelper.close();
	}

	/*************************************************************
	 * Feeds management
	 *************************************************************/
	private static final String[] COLUMNS_ID = new String[] { BaseColumns._ID };

	public static final int ALL_FEEDS = -1;
	public static final int WITHOUT_TYPE = -2;

	public Cursor getFeedsIdForType(int type) {
		Cursor result = null;
		String where = null;
		String orderBy = null;
		switch (type) {
		case ALL_FEEDS:
			orderBy = Feed.COLUMN_NAME_CREATED + SQL_ORDER_DESC;
			result = this.db.query(Feed.TABLE_NAME, COLUMNS_ID, null, null,
					null, null, orderBy);
			break;
		case WITHOUT_TYPE:
			orderBy = Feed.COLUMN_NAME_CREATED + SQL_ORDER_DESC;
			where = new StringBuilder(SQL_SELECT).append(BaseColumns._ID)
					.append(SQL_FROM).append("?").append(SQL_WHERE)
					// .append(SQL_ORDER_BY).append(orderBy)
					.toString();

			String[] params = new String[] { Feed.TABLE_NAME };
			result = this.db.rawQuery(where, params);
			break;
		default:
			orderBy = TypeOrder.COLUMN_NAME_ORDER + SQL_ORDER_ASC;
			where = new StringBuilder(TypeOrder.COLUMN_NAME_TYPE_ID).append(
					" = ?").toString();
			result = this.db.query(TypeOrder.TABLE_NAME, COLUMNS_ID, where,
					new String[] { String.valueOf(type) }, null, null, orderBy);
		}
		return result;
	}
}
