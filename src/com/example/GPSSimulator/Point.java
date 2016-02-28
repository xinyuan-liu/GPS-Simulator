package com.example.GPSSimulator;

import java.util.Random;

public class Point {
	public double lat,lon;
	private static double lat_dev=0.0,lon_dev=0.0;
	private static double GPSaccuracy=1.0/30000.0;
	Point(double lat_,double lon_)
	{
		lat=lat_;
		lon=lon_;
	}
	Point(){}
	public double rad(double degree)
	{
		return degree * Math.PI /180.0;
	}
	//get distance (m)
	public double getDistance(Point p) {  
		double a,b,R;
		R = 6378137;
		a = rad(lat) - rad(p.lat);
		b = rad(lon) - rad(p.lon);
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2  
				* R  
				* Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(rad(lat))  
						* Math.cos(rad(p.lat)) * sb2 * sb2));
		return d;
	}
	
	public void footprintrandom(double deviation)
	{
		Random random = new Random();
		lat=lat+random.nextGaussian()*deviation;
		lon=lon+random.nextGaussian()*deviation;
	}
	public Point GPSrandom()
	{
		if(Math.random()>0.6)
		{
			do
			{
				lat_dev=lat_dev+GPSaccuracy*(Math.random()*2-1)*2;
			} while(Math.abs(lat_dev)>GPSaccuracy*2);
			do
			{
				lon_dev=lon_dev+GPSaccuracy*(Math.random()*2-1)*2;
			}
			while(Math.abs(lon_dev)>GPSaccuracy*2);
		}
		return new Point(lat+lat_dev,lon+lon_dev);
	}
}
