package com.example.GPSSimulator;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends Activity implements LocationListener{

	private String mMockProviderName = LocationManager.GPS_PROVIDER;;
	private LocationManager locationManager;
	private boolean Permissionflag=true;
	private int track_choosen=1;
	private double speed=10.0;
	private String ServiceName="com.example.GPSSimulator.Daemon";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_track);
		check_location_permission();
		if(!Permissionflag)
			permissionerror();
		else if(checkdaemonservice())
		{
			setContentView(R.layout.activity_main);
			TextView textView;
			textView=(TextView) findViewById(R.id.textView2);
			//textView.setText("Running... Speed:"+Double.toString(speed)+"km/h");
			textView.setText("Running...");
		}
	}

	private boolean checkdaemonservice() 
	{
		ActivityManager mActivityManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);   
		List<ActivityManager.RunningServiceInfo> mServiceList = mActivityManager.getRunningServices(30);
		for(int i=0;i<mServiceList.size();i++)
		{
			if(ServiceName.equals(mServiceList.get(i).service.getClassName()))  
				return true;
		}
		return false;
	}
	
	public void onRadioButtonClicked(View view) {
		
	    boolean checked = ((RadioButton)view).isChecked();
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.Button_WML:
	            if (checked)
	            	track_choosen=1;
	            break;
	        case R.id.Button_WSS:
	            if (checked)
	            	track_choosen=2;
	            break;
	    }
	}
	
	public void onStopButtonClicked(View view)
	{
		Intent intent = new Intent(getApplicationContext(), Daemon.class);  
		stopService(intent);
		setContentView(R.layout.choose_track);
	}
	
	public void onStartButtonClicked(View view) 
	{
		
		EditText editText = (EditText) findViewById(R.id.edit_message);
		String message = editText.getText().toString();
		double getnumber=0.0;
		try{
			 getnumber = Double.parseDouble(message);
		} catch ( Exception e) {
			e.printStackTrace();
		}
		if(getnumber<36 && getnumber > 0.0)
			speed=getnumber;
		else if(message.isEmpty())
			speed=10;
		else return;
		Intent intent = new Intent(getApplicationContext(), Daemon.class);  
		intent.putExtra("track_choosen",track_choosen);
		intent.putExtra("speed", speed);
		startService(intent);
		
		setContentView(R.layout.activity_main);
		TextView textView;
		textView=(TextView) findViewById(R.id.textView2);
		textView.setText("Running... Speed:"+Double.toString(speed)+"km/h");
	}
	
	private void permissionerror()
	{
		String message="        权限错误，请检查手机“开发人员选项”中的“允许模拟位置”选项是否打开，并确认本应用已被允许访问位置信息。完成后请重启本应用。";
		TextView textView = new TextView(this);
	    textView.setTextSize(20);
	    textView.setText(message);
	    setContentView(textView);
	}
	
	
	private void check_location_permission() {
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		try{
			locationManager.addTestProvider(mMockProviderName, false, true, false, false, true, true,
					true, 0, 5);
		} catch (SecurityException e) {
			Permissionflag=false;
			return;
		}		
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
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void onLocationChanged(Location location) {
		double lat = location.getLatitude();
		double lng = location.getLongitude();
		Log.i("gps", String.format("location: x=%s y=%s", lat, lng));
	}
	
}
