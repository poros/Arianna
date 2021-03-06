package fr.eurecom.mobserv.arianna.model;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * @author uccio
 * 
 */
public class Category extends Model implements BaseColumns {
	public static Map<String, Category> categories = new HashMap<String, Category>();
	public static final String TABLE_NAME = "category";
	public static final String COLUMN_NAME_NAME = "name";
	public static final String COLUMN_NAME_ICON = "icon";
	public static final String COLUMN_NAME_FACILITY = "facility";
	public static final String[] COLUMNS_NAME = { Model.COLUMN_NAME_URI,
			COLUMN_NAME_NAME, COLUMN_NAME_ICON, COLUMN_NAME_FACILITY };

	public static final String SQL_CREATE_TABLE = "CREATE TABLE "
			+ Category.TABLE_NAME + " (" + Category._ID + Model.PRIMARY_KEY
			+ Model.COMMA_SEP + Model.COLUMN_NAME_URI + Model.TEXT_TYPE
			+ Model.UNIQUE + Model.COMMA_SEP + Category.COLUMN_NAME_NAME
			+ Model.TEXT_TYPE + Model.COMMA_SEP + Category.COLUMN_NAME_ICON
			+ Model.TEXT_TYPE + Model.COMMA_SEP + Category.COLUMN_NAME_FACILITY
			+ Model.TEXT_TYPE + ")";

	private String name;
	private String icon;
	private boolean facility;
	private Map<String,PointOfInterest> poiS;

	/**
	 * 
	 * @param context
	 * @param URI
	 * @param name
	 *            name displayed for the category
	 * @param icon
	 *            absolute path of the icon on disk
	 * @param facility
	 *            true if it is a facility, false otherwise
	 */
	public Category(Context context, String URI, String name, String icon,
			boolean facility) {
		super(context, URI);
		this.name = name;
		this.icon = icon;
		this.facility = facility;
	}

	/**
	 * @param context
	 * @param cursor
	 *            result from query to map DB entry and JAVA object
	 */
	public Category(Context context, Cursor cursor) {
		super(context, cursor.getString(cursor
				.getColumnIndexOrThrow(COLUMN_NAME_URI)));
		this.name = cursor.getString(cursor
				.getColumnIndexOrThrow(COLUMN_NAME_NAME));
		this.icon = cursor.getString(cursor
				.getColumnIndexOrThrow(COLUMN_NAME_ICON));
		this.facility = (cursor.getInt(cursor
				.getColumnIndexOrThrow(COLUMN_NAME_FACILITY)) > 0 ? true
				: false);
	}

	/**
	 * @author dani
	 */
	@Override
	public boolean save() {
		// Gets the data repository in write mode
		SQLiteDatabase db = DbHelper.getInstance(this.getContext())
				.getWritableDatabase();

		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(Model.COLUMN_NAME_URI, this.getUri());
		values.put(Category.COLUMN_NAME_NAME, this.getName());
		values.put(Category.COLUMN_NAME_FACILITY, this.isFacility());
		values.put(Category.COLUMN_NAME_ICON, this.getIcon());
		/**
		 * TODO colonne nullabili: icon con valori di default se non presente
		 **/
		// Insert the new row, returning the primary key value of the new row
		long newRowId;
		newRowId = db.insert(Category.TABLE_NAME, null, values);
		return newRowId >= 0;
	}

	@Override
	protected String getTableName() {
		return Category.TABLE_NAME;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon
	 *            the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the facility
	 */
	public boolean isFacility() {
		return facility;
	}

	/**
	 * @param facility
	 *            the facility to set
	 */
	public void setFacility(boolean facility) {
		this.facility = facility;
	}

	/**
	 * @return the poiS
	 */
	public Map<String,PointOfInterest> getPoiS() {
		if(this.poiS == null || this.poiS.isEmpty()){
			Map<String,PointOfInterest> pois=(Map)Model.getByParam(PointOfInterest.class, PointOfInterest.COLUMN_NAME_CATEGORY, this.getUri(), this.getContext());
			this.poiS=pois;
		}
		return poiS;
	}

	/**
	 * @param poiS the poiS to set
	 */
	public void setPoiS(Map<String,PointOfInterest> poiS) {
		this.poiS = poiS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (facility ? 1231 : 1237);
		result = prime * result + ((icon == null) ? 0 : icon.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (facility != other.facility)
			return false;
		if (icon == null) {
			if (other.icon != null)
				return false;
		} else if (!icon.equals(other.icon))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
