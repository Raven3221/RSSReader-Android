/**
 * 
 */
package com.raven.rssreader.data;

import android.provider.BaseColumns;

/**
 * Encapsulate all the data structure, containing the fields names on the DB.
 * 
 * @author rweinberger
 * 
 */
public class ReaderDataContract {

	/**
	 * Scheme for clusters
	 * 
	 * @author rweinberger
	 * 
	 
	public class Cluster implements BaseColumns {
		// private constructor to prevent instance
		private Cluster() {
		}

		public static final String TABLE_NAME = "cluster";
		public static final String COLUMN_NAME_TITLE = "title";
		public static final String COLUMN_NAME_PATH_PATTERN = "pattern";
		public static final String COLUMN_NAME_ICON = "icon";
		public static final int DEFAULT_COLUMN = 1;
	}
	*/

	/**
	 * Scheme for feed channel
	 * 
	 * @author rweinberger
	 * 
	 */
	public class Feed implements BaseColumns {
		// private constructor to prevent instance
		protected Feed() {
		}

		public static final String TABLE_NAME = "feed";
		public static final String COLUMN_NAME_TITLE = "title";
		public static final String COLUMN_NAME_DESCRIPTION = "description";
		public static final String COLUMN_NAME_PATH = "path";
//		public static final String COLUMN_NAME_CLUSTER = "cluster_id";
		public static final String COLUMN_NAME_CREATED = "created";
		public static final String COLUMN_NAME_LAST_UPDATED = "last_updated";
		public static final String COLUMN_NAME_DELETE_MIN_RECORDS = "min_store_records";
		public static final String COLUMN_NAME_DELETE_MIN_TIME = "min_store_date";
		public static final String COLUMN_NAME_REFRESH_TIME = "refresh_time";
	}

	/**
	 * Scheme for feed entry
	 * 
	 * @author rweinberger
	 * 
	 */
	public class Entry implements BaseColumns {
		// private constructor to prevent instance
		private Entry() {
		}

		public static final String TABLE_NAME = "entry";
		public static final String COLUMN_NAME_FEED = "feed_id";
		public static final String COLUMN_NAME_TITLE = "title";
		public static final String COLUMN_NAME_DESCRIPTION = "description";
		public static final String COLUMN_NAME_IMAGE = "image";
		public static final String COLUMN_NAME_TARGET = "target";
		public static final String COLUMN_NAME_TIMESTAMP = "date";
		public static final String COLUMN_NAME_STATUS = "status";// NEED to be
																	// added -
																	// Read/favorite
																	// - bitwise
	}

	/**
	 * Scheme for types
	 * 
	 * @author rweinberger
	 * 
	 */
	public class Type implements BaseColumns {
		// private constructor to prevent instance
		private Type() {
		}

		public static final String TABLE_NAME = "type";
		public static final String COLUMN_NAME_TITLE = "title";
	}

	/**
	 * Scheme for types
	 * 
	 * @author rweinberger
	 * 
	 */
	public class TypeOrder implements BaseColumns {
		// private constructor to prevent instance
		private TypeOrder() {
		}

		public static final String TABLE_NAME = "typeOrder";
		public static final String COLUMN_NAME_TYPE_ID = "type_id";
		public static final String COLUMN_NAME_FEED_ID = "feed_id";
		public static final String COLUMN_NAME_ORDER = "order";
	}
}
