package com.example.realmancala;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class StatsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_mancala_stats);
		
		final Button btnSettings = (Button)findViewById(R.id.btnGameOptionsInStats);
		final Button btnHome = (Button)findViewById(R.id.btnHomeInStats);
		
		btnSettings.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent gameOptions = new Intent(StatsActivity.this,
						GameOptionsActivity.class);
				startActivity(gameOptions);
				StatsActivity.this.finish();
			}
		});
		
		btnHome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Intent gameOptions = new Intent(StatsActivity.this,
//						GameOptionsActivity.class);
//				startActivity(gameOptions);
				StatsActivity.this.finish();
				
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)// donanýmsal olarak geri butonuna
		// basýlýrsa
		{

			
			return true;
		}
		return false;
	}
}
