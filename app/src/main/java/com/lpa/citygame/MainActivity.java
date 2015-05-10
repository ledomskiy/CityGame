package com.lpa.citygame;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.lpa.citygame.database.DatabaseInstance;

public class MainActivity extends ActionBarActivity implements OnClickListener{

	// Инициализация приложения
    /*
    public void init (){
		DatabaseInstance.createInstance(this);
	}
	*/

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button newGame = (Button)findViewById(R.id.new_game);
		newGame.setOnClickListener(this);

        /*
		DatabaseInstance db = DatabaseInstance.getInstance();

		if (db != null) {
			Cursor cursor = db.getWritableDatabase().rawQuery("SELECT * FROM City;", null);
			int indexLoginColumn;
			indexLoginColumn = cursor.getColumnIndex("CityName");
			cursor.moveToFirst();
			if (cursor != null) {
				Integer count = cursor.getCount();
				Log.v("Count rows in cursor", count.toString());
				String login = "";
				do {
					login = cursor.getString(indexLoginColumn);
					Log.v("CityName", login);
				} while (cursor.moveToNext());
			}
		}
		*/

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.new_game:
				Intent intent = new Intent (this, PreferencesActivity.class);
				startActivity (intent);
				break;
		}
		
	}
}
