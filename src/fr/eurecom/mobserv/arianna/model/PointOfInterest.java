package fr.eurecom.mobserv.arianna.model;

import java.util.Map;

import com.google.gson.annotations.Expose;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class PointOfInterest extends Model implements BaseColumns,
		Comparable<PointOfInterest> {
	public final static String TABLE_NAME = "point_of_interest";
	public final static String COLUMN_NAME_TITLE = "title";
	public final static String COLUMN_NAME_SUBTITLE = "subtitle";
	public final static String COLUMN_NAME_DESCRIPTION = "description";
	public final static String COLUMN_NAME_IMAGE = "image";
	public final static String COLUMN_NAME_CATEGORY = "category";
	public final static String COLUMN_NAME_NAV_NODE = "nav_node";
	public static final String COLUMN_NAME_X = "x";
	public static final String COLUMN_NAME_Y = "y";

	public static final String[] COLUMNS_NAME = { Model.COLUMN_NAME_URI,
			COLUMN_NAME_TITLE, COLUMN_NAME_SUBTITLE, COLUMN_NAME_DESCRIPTION,
			COLUMN_NAME_IMAGE, COLUMN_NAME_CATEGORY, COLUMN_NAME_NAV_NODE,
			COLUMN_NAME_X, COLUMN_NAME_Y };

	public final static String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
			+ " (" + _ID + PRIMARY_KEY + COMMA_SEP + COLUMN_NAME_URI
			+ TEXT_TYPE + UNIQUE + COMMA_SEP + COLUMN_NAME_TITLE + TEXT_TYPE
			+ COMMA_SEP + COLUMN_NAME_SUBTITLE + TEXT_TYPE + COMMA_SEP
			+ COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP
			+ COLUMN_NAME_IMAGE + TEXT_TYPE + COMMA_SEP + COLUMN_NAME_CATEGORY
			+ TEXT_TYPE + COMMA_SEP + COLUMN_NAME_NAV_NODE + TEXT_TYPE
			+ COMMA_SEP + COLUMN_NAME_X + Model.COORDINATE_TYPE
			+ Model.COMMA_SEP + COLUMN_NAME_Y + Model.COORDINATE_TYPE
			+ Model.COMMA_SEP + "FOREIGN KEY(" + COLUMN_NAME_CATEGORY
			+ ") REFERENCES " + Category.TABLE_NAME + "("
			+ Model.COLUMN_NAME_URI + ")" + COMMA_SEP + "FOREIGN KEY("
			+ COLUMN_NAME_NAV_NODE + ") REFERENCES "
			+ NavigationNode.TABLE_NAME + "(" + Model.COLUMN_NAME_URI + ")"
			+ " )";
	@Expose
	private String title;
	private String subtitle;
	private String description;
	private String image;
	private Category category;
	private NavigationNode navNode;
	@Expose
	private int x;
	@Expose
	private int y;

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/** TODO properties **/
	private Map<String, String> properties;

	/**
	 * @param context
	 * @param uri
	 * @param title
	 * @param subtitle
	 * @param description
	 * @param image
	 * @param category
	 * @param navNode
	 * @param x
	 * @param y
	 */
	public PointOfInterest(Context context, String uri, String title,
			String subtitle, String description, String image,
			Category category, NavigationNode navNode, int x, int y) {
		super(context, uri);
		this.title = title;
		this.subtitle = subtitle;
		this.description = description;
		this.image = image;
		this.category = category;
		this.navNode = navNode;
		this.setX(x);
		this.setY(y);
	}

	/**
	 * @param context
	 * @param cursor
	 *            result from query to map DB entry and JAVA object
	 */
	public PointOfInterest(Context context, Cursor cursor) {
		super(context, cursor.getString(cursor.getColumnIndex(COLUMN_NAME_URI)));
		this.title = cursor.getString(cursor
				.getColumnIndexOrThrow(COLUMN_NAME_TITLE));
		this.subtitle = cursor.getString(cursor
				.getColumnIndexOrThrow(COLUMN_NAME_SUBTITLE));
		;
		this.description = cursor.getString(cursor
				.getColumnIndexOrThrow(COLUMN_NAME_DESCRIPTION));
		this.image = cursor.getString(cursor
				.getColumnIndexOrThrow(COLUMN_NAME_IMAGE));
		this.setX(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_X)));
		this.setY(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_Y)));
		this.category = (Category) Model.getByURI(Category.class, cursor
				.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_CATEGORY)),
				this.getContext());
		this.navNode = (NavigationNode) Model.getByURI(NavigationNode.class,
				cursor.getString(cursor
						.getColumnIndexOrThrow(COLUMN_NAME_NAV_NODE)), this
						.getContext());
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the subtitle
	 */
	public String getSubtitle() {
		return subtitle;
	}

	/**
	 * @param subtitle
	 *            the subtitle to set
	 */
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the properties
	 */
	public Map<String, String> getProperties() {
		return properties;
	}

	/**
	 * @param properties
	 *            the properties to set
	 */
	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public boolean save() {
		SQLiteDatabase db = DbHelper.getInstance(this.getContext())
				.getWritableDatabase();

		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(Model.COLUMN_NAME_URI, this.getUri());
		values.put(COLUMN_NAME_TITLE, this.getTitle());
		values.put(COLUMN_NAME_SUBTITLE, this.getSubtitle());
		values.put(COLUMN_NAME_DESCRIPTION, this.getDescription());
		values.put(COLUMN_NAME_IMAGE, this.getImage());
		values.put(COLUMN_NAME_CATEGORY, this.getCategory().getUri());
		values.put(COLUMN_NAME_NAV_NODE, this.getNavNode().getUri());
		values.put(COLUMN_NAME_X, this.getX());
		values.put(COLUMN_NAME_Y, this.getY());
		// Insert the new row, returning the primary key value of the new row
		long newRowId;
		newRowId = db.insert(PointOfInterest.TABLE_NAME, null, values);
		return newRowId >= 0;
	}

	@Override
	protected String getTableName() {
		return TABLE_NAME;
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
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((navNode == null) ? 0 : navNode.hashCode());
		result = prime * result
				+ ((properties == null) ? 0 : properties.hashCode());
		result = prime * result
				+ ((subtitle == null) ? 0 : subtitle.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + x;
		result = prime * result + y;
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
		PointOfInterest other = (PointOfInterest) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (navNode == null) {
			if (other.navNode != null)
				return false;
		} else if (!navNode.equals(other.navNode))
			return false;
		if (properties == null) {
			if (other.properties != null)
				return false;
		} else if (!properties.equals(other.properties))
			return false;
		if (subtitle == null) {
			if (other.subtitle != null)
				return false;
		} else if (!subtitle.equals(other.subtitle))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	/**
	 * @return the navNode
	 */
	public NavigationNode getNavNode() {
		return this.navNode;
	}

	/**
	 * @param navNode
	 *            the navNode to set
	 */
	public void setNavNode(NavigationNode navNode) {
		this.navNode = navNode;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public int compareTo(PointOfInterest another) {
		int i;
		if (this.getCategory().isFacility())
			if (another.getCategory().isFacility())
				return ( ( i=this.getCategory().getUri().compareTo(another.getCategory().getUri())) != 0) ? 
						i : this.getTitle().compareTo(another.getTitle());
			else
				return +1;
		else if (another.getCategory().isFacility())
			return -1;
		else
			return this.getTitle().compareTo(another.getTitle());
	}
}