package com.example.GPSSimulator;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.view.View;

public class Daemon extends Service implements LocationListener{

	
	private String mMockProviderName = LocationManager.GPS_PROVIDER;;
	private LocationManager locationManager;
	private double lats_WML[]={39.99341872600990,39.99316329840075,39.99276789061891,39.99262718775220,39.99258245790271,39.99259355182861,39.99269042069986,39.99272779820316,39.99269668243249,39.99262226256699,39.99260336589914,39.99263482666007,39.99264816863253,39.99280298589017,39.99287079458549,39.99309679669885,39.99345947008601,39.99358996464883,39.99367729554752,39.99414815213886,39.99426944139763,39.99436418910675,39.99446505584430,39.99434004524900,39.99432372598913,39.99430643035513,39.99429247607107,39.99427607188360,39.99426517265692,39.99422953361762,39.99416810646498,39.99413838874796,39.99410043310149,39.99401107724110,39.99386422158884,39.99384166746929,39.99361518662289,39.99350797628836,39.99341872600990};
	private double lons_WML[]={116.30158174237638,116.30196723586447,116.30246253077404,116.30306487876791,116.30338076725442,116.30350262711767,116.30393227023815,116.30429764357316,116.30448367861456,116.30473129568217,116.30502171879571,116.30521852117099,116.30525195252990,116.30541251111158,116.30540581603718,116.30539910114153,116.30540574228090,116.30540572593073,116.30538565524689,116.30522501817734,116.30517011288441,116.30508177770862,116.30492378066329,116.30475782722279,116.30471102246740,116.30459186166830,116.30433088134542,116.30368575648694,116.30344882281094,116.30329492852456,116.30313434991672,116.30308614767850,116.30306070205638,116.30303666013153,116.30301392286464,116.30178779504800,116.30172155033900,116.30167006232317,116.30158174237638};
	private double lats_WSS[]={39.9867674,39.9858179,39.9857542,39.9857069,39.9856617,39.985633,39.9856165,39.9856042,39.9856042,39.9856083,39.9856309,39.9856761,39.9857234,39.9857665,39.9858056,39.9858734,39.9859124,39.9867653,39.986827,39.9868722,39.9869215,39.9869667,39.9869975,39.9870201,39.9870366,39.9870407,39.9870366,39.9870222,39.9870058,39.986979,39.9869503,39.9869112,39.9868598,39.9868146,39.9867674};
	private double lons_WSS[]={116.306642,116.3067144,116.3067493,116.3068056,116.3068834,116.3069478,116.3070095,116.3070819,116.3071623,116.3072535,116.3073528,116.3074332,116.3075003,116.3075352,116.3075566,116.3075754,116.3075754,116.3075244,116.307503,116.3074735,116.3074225,116.3073742,116.3073125,116.3072348,116.307157,116.3070792,116.3070014,116.306937,116.3068753,116.3068217,116.3067707,116.3067251,116.3066795,116.30665,116.306642};

	private int track_choosen;
	private Track track;
	
	private double speed=10.0;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		inilocation();
		Bundle bundle = intent.getExtras(); 
		track_choosen=bundle.getInt("track_choosen");
		speed=bundle.getDouble("speed");
		startmocklocate();	
		return 0;
	}

	private void startmocklocate()
	{
		if(track_choosen==1)
			track=new Track(lats_WML, lons_WML, lats_WML.length);
		else if(track_choosen==2)
			track=new Track(lats_WSS, lons_WSS, lats_WSS.length);
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					Point p=track.gotonextpoint(speed);
					setLocation(p);
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
			return;
		}//check permission
		locationManager.setTestProviderEnabled(mMockProviderName, true);
		locationManager.requestLocationUpdates(mMockProviderName, 0, 0, this);
		
	}
	
	@SuppressLint("NewApi")
	private void setLocation(Point p) {
		Location location = new Location(mMockProviderName);
		location.setTime(System.currentTimeMillis());
		location.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
		location.setLatitude(p.lat);
		location.setLongitude(p.lon);
		location.setAltitude(53.0f+(float)Math.random()*5.0);
		location.setAccuracy(5.0f+(float)(Math.random()*2.0));
		location.setSpeed((float) ((speed+Math.random()-0.5)/3.6));
		locationManager.setTestProviderLocation(mMockProviderName, location);
	}
	
	public void setspeed(View view) {
		
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}
