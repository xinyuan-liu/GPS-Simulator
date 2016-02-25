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
    //private double lats1[]={39.99342953085937,39.99275308945312,39.99257865839843,39.992701348046864,39.99273424804687,39.992609116992185,39.99263320664062,39.99282220664061,39.99360310664062,39.994293506640616,39.99445031699217,39.99433643769531,39.99428713769531,39.994239058398435,39.994117589453126,39.993870989453114,39.993870989453114,39.99373128945312,39.99353459980469,39.99352823085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937,39.99342953085937};
    //private double lons1[]={116.30156555351564,116.30248581210938,116.30346150175781,116.30398537070312,116.30437157070313,116.30487403964842,116.30514162929688,116.30539912929689,116.30536692929687,116.30514162929688,116.30491693964844,116.30476673964843,116.30457486035156,116.30340780175781,116.30307591210939,116.30304371210937,116.30266821210938,116.30255011210937,116.30200484316406,116.30168355351563,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564,116.30156555351564};
    
    private double lats2[]={39.99341872600990,39.99316329840075,39.99276789061891,39.99262718775220,39.99258245790271,39.99259355182861,39.99269042069986,39.99272779820316,39.99269668243249,39.99262226256699,39.99260336589914,39.99263482666007,39.99264816863253,39.99280298589017,39.99287079458549,39.99309679669885,39.99345947008601,39.99358996464883,39.99367729554752,39.99414815213886,39.99426944139763,39.99436418910675,39.99446505584430,39.99434004524900,39.99432372598913,39.99430643035513,39.99429247607107,39.99427607188360,39.99426517265692,39.99422953361762,39.99416810646498,39.99413838874796,39.99410043310149,39.99401107724110,39.99386422158884,39.99384166746929,39.99361518662289,39.99350797628836,39.99341872600990};
    private double lons2[]={116.30158174237638,116.30196723586447,116.30246253077404,116.30306487876791,116.30338076725442,116.30350262711767,116.30393227023815,116.30429764357316,116.30448367861456,116.30473129568217,116.30502171879571,116.30521852117099,116.30525195252990,116.30541251111158,116.30540581603718,116.30539910114153,116.30540574228090,116.30540572593073,116.30538565524689,116.30522501817734,116.30517011288441,116.30508177770862,116.30492378066329,116.30475782722279,116.30471102246740,116.30459186166830,116.30433088134542,116.30368575648694,116.30344882281094,116.30329492852456,116.30313434991672,116.30308614767850,116.30306070205638,116.30303666013153,116.30301392286464,116.30178779504800,116.30172155033900,116.30167006232317,116.30158174237638};


    
    private double speed=10.0;
    //private Track WeimingLake_inaccurate=new Track(lats1, lons1, lats1.length);
    private Track WeimingLake=new Track(lats2, lons2, lats2.length);
    private boolean Permissionflag=true;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		inilocation();
		if(!Permissionflag)
		{
			TextView textView;
		    textView=(TextView)findViewById(R.id.textView2);
			textView.setText("Permission denied, please check your settings and restart the application.");
		}
		else startmocklocate();	
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
        try{
        	locationManager.addTestProvider(mMockProviderName, false, true, false, false, true, true,
                    true, 0, 5);
        } catch (SecurityException e) {
        	Permissionflag=false;
        	return;
        }
        
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
		if(!Permissionflag)return;
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
