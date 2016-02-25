package com.example.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements LocationListener{

	private String mMockProviderName = LocationManager.GPS_PROVIDER;;
    private LocationManager locationManager;
    private double lats1[]={39.99342953085937,39.99275308945312,39.99257865839843,39.992701348046864,39.99273424804687,39.992609116992185,39.99263320664062,39.99282220664061,39.99360310664062,39.994293506640616,39.99445031699217,39.99433643769531,39.99428713769531,39.994239058398435,39.994117589453126,39.993870989453114,39.993870989453114,39.99373128945312,39.99353459980469,39.99352823085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937};
    private double lons1[]={116.30156555351564,116.30248581210938,116.30346150175781,116.30398537070312,116.30437157070313,116.30487403964842,116.30514162929688,116.30539912929689,116.30536692929687,116.30514162929688,116.30491693964844,116.30476673964843,116.30457486035156,116.30340780175781,116.30307591210939,116.30304371210937,116.30266821210938,116.30255011210937,116.30200484316406,116.30168355351563,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564};

    private double speed=10.0;
    private Track WeimingLake=new Track(lats1, lons1, 21);
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		inilocation();
		startmocklocate();	
	}
	
	private void startmocklocate()
	{
		Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Point p=WeimingLake.gotonextpoint(speed);
                    setLocation(p.lon, p.lat);
                }
            }
        });
		thread.start();
	}
	
	private void inilocation() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.addTestProvider(mMockProviderName, false, true, false, false, true, true,
                true, 0, 5);
        locationManager.setTestProviderEnabled(mMockProviderName, true);
        locationManager.requestLocationUpdates(mMockProviderName, 0, 0, this);
        
    }
	
	@SuppressLint("NewApi")
	private void setLocation(double longitude, double latitude) {
        Location location = new Location(mMockProviderName);
        location.setTime(System.currentTimeMillis());
        location.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setAltitude(20.0f);
        location.setAccuracy(3.0f);
        location.setSpeed((float) (speed/3.6));
        locationManager.setTestProviderLocation(mMockProviderName, location);
    }
	
	public void setspeed(View view) {
	    EditText editText = (EditText) findViewById(R.id.edit_message);
	    String message = editText.getText().toString();
	    double getnumber=0.0;
	    try{
	    	 getnumber = Double.parseDouble(message);
	    } catch ( Exception e) {
            e.printStackTrace();
        }
	    TextView textView;
	    textView=(TextView)findViewById(R.id.textView2);
	    if(getnumber<36 && getnumber > 0.0)
	    {
	    	speed=getnumber;
		    textView.setText("Running... Speed:"+Double.toString(speed)+"km/h");
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
