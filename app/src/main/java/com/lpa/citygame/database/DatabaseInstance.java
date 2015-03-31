package com.lpa.citygame.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseInstance {
	// The Android's default system path of your application database.
	private static String DB_PATH = "/data/data/com.lpa.citygame/databases";
	private static String DB_NAME = "cities.db";
	private File dbFile;
	private final Context myContext;
	
	private static DatabaseInstance databaseInstance = null;

	/**
	 * Constructor Takes and keeps a reference of the passed context in order to
	 * access to the application assets and resources.
	 * 
	 * @param context
	 * @throws IOException 
	 */
	private DatabaseInstance(Context context) throws IOException {
		this.myContext = context;
		dbFile = context.getDatabasePath(DB_NAME);
		createDataBase ();
	}
	
	public SQLiteDatabase getWritableDatabase (){
		return myContext.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
	}
	
	public void createDataBase() throws IOException {
		boolean dbFileExist = dbFile.exists() && dbFile.isFile();
		if (!dbFileExist) {
			copyDataBase();
		}
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException {
		InputStream myInput = myContext.getAssets().open(DB_NAME);
		
		// Make directory if not exist
		File dbDir = new File (DB_PATH);
		if (!dbDir.exists()){
			dbDir.mkdir();
		}
		
		OutputStream myOutput = new FileOutputStream(dbFile);
		
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}
		
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}
	
	public static void createInstance (Context context){
		if (databaseInstance == null){
			try {
				databaseInstance = new DatabaseInstance (context);
			} catch (IOException e) {
				// TODO: Создать активити с ошибкой
				e.printStackTrace();
			}
		}
	}
	
	public static DatabaseInstance getInstance (){
		return databaseInstance;
	}

	
}
