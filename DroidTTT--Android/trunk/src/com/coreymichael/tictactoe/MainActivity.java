package com.coreymichael.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
    public final static String EXTRA_MESSAGE = "com.coreymichael.tictactoe.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/** Called when the user clicks the Start Game button */
	public void startGame(View view) {
	    // Do something in response to button
		Intent intent = new Intent(this, GameActivity.class);
		
		String message = "test message";
		intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);
	}


}
