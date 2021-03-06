package fr.eurecom.mobserv.arianna.model;

import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class MapLevel extends Model implements BaseColumns {

	public final static String TABLE_NAME = "map_level";
	public final static String COLUMN_NAME_NAME = "name";
	public final static String COLUMN_NAME_MAP_IMAGE = "map_image";
	public final static String COLUMN_NAME_EVENT_URI = "event_uri";
	public static final String[] COLUMNS_NAME = { Model.COLUMN_NAME_URI,
			COLUMN_NAME_NAME, COLUMN_NAME_MAP_IMAGE, COLUMN_NAME_EVENT_URI };

	public final static String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
			+ " (" + _ID + Model.PRIMARY_KEY + COMMA_SEP + COLUMN_NAME_URI
			+ TEXT_TYPE + UNIQUE + COMMA_SEP + COLUMN_NAME_NAME + TEXT_TYPE
			+ COMMA_SEP + COLUMN_NAME_MAP_IMAGE + TEXT_TYPE + COMMA_SEP
			+ COLUMN_NAME_EVENT_URI + TEXT_TYPE + COMMA_SEP 
			+ "FOREIGN KEY(" + COLUMN_NAME_EVENT_URI+ ") REFERENCES " + Event.TABLE_NAME + "("+ Model.COLUMN_NAME_URI + ")"
			+" )";

	private String name;
	private String map_image;
	private Event event;
	private Map<String,NavigationNode> navigationNodes;
	private Map<String,NavigationLink> navigationLinks;
	
	/**
	 * @param context
	 * @param uri
	 * @param name
	 * @param map_image
	 * @param event
	 */
	public MapLevel(Context context, String uri, String name, String map_image,
			Event event) {
		super(context, uri);
		this.name = name;
		this.map_image = map_image;
		this.event = event;
	}

	/**
	 * @param context
	 * @param cursor
	 *            result from query to map DB entry and JAVA object
	 */
	public MapLevel(Context context, Cursor cursor) {
		super(context, cursor.getString(cursor
				.getColumnIndexOrThrow(COLUMN_NAME_URI)));
		this.name = cursor.getString(cursor
				.getColumnIndexOrThrow(COLUMN_NAME_NAME));
		this.map_image = cursor.getString(cursor
				.getColumnIndexOrThrow(COLUMN_NAME_MAP_IMAGE));
		this.event = (Event) Model.getByURI(Event.class,
				cursor.getString(cursor
						.getColumnIndexOrThrow(COLUMN_NAME_EVENT_URI)), this
						.getContext());
		;

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
	 * @return the map_image
	 */
	public String getMap_image() {
		return map_image;
	}

	/**
	 * @param map_image
	 *            the map_image to set
	 */
	public void setMap_image(String map_image) {
		this.map_image = map_image;
	}

	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public boolean save() {
		// Gets the data repository in write mode
		SQLiteDatabase db = DbHelper.getInstance(this.getContext())
				.getWritableDatabase();

		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(Model.COLUMN_NAME_URI, this.getUri());
		values.put(MapLevel.COLUMN_NAME_MAP_IMAGE, this.getMap_image());
		values.put(MapLevel.COLUMN_NAME_NAME, this.getName());
		values.put(MapLevel.COLUMN_NAME_EVENT_URI, this.getEvent().getUri());
		// Insert the new row, returning the primary key value of the new row
		long newRowId;
		newRowId = db.insert(MapLevel.TABLE_NAME, null, values);
		return newRowId >= 0;
	}

	/**
	 * @return the event
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * @param event
	 *            the event to set
	 */
	public void setEvent(Event event) {
		this.event = event;
	}

	/**
	 * @return the navigationNodes
	 */
	public Map<String,NavigationNode> getNavigationNodes() {
		if(this.navigationNodes == null || this.navigationNodes.isEmpty()){
			Map<String,NavigationNode> nodes=(Map) Model.getByParam(NavigationNode.class, NavigationNode.COLUMN_NAME_MAP_LEVEL, this.getUri(), this.getContext());
			this.navigationNodes=nodes;
		}
		return navigationNodes;
	}

	/**
	 * @param navigationNodes the navigationNodes to set
	 */
	public void setNavigationNodes(Map<String,NavigationNode> navigationNodes) {
		this.navigationNodes = navigationNodes;
	}

	/**
	 * @return the navigationLinks
	 */
	public Map<String,NavigationLink> getNavigationLinks() {
		if(this.navigationLinks == null || this.navigationLinks.isEmpty()){
			Map<String,NavigationLink> links=(Map) Model.getByParam(NavigationLink.class, NavigationLink.COLUMN_NAME_MAP_LEVEL, this.getUri(), this.getContext());
			this.navigationLinks=links;
		}
		return navigationLinks;
	}

	/**
	 * @param navigationLinks the navigationLinks to set
	 */
	public void setNavigationLinks(Map<String,NavigationLink> navigationLinks) {
		this.navigationLinks = navigationLinks;
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
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result
				+ ((map_image == null) ? 0 : map_image.hashCode());
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
		MapLevel other = (MapLevel) obj;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (map_image == null) {
			if (other.map_image != null)
				return false;
		} else if (!map_image.equals(other.map_image))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
