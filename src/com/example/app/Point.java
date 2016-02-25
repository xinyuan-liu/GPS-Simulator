package com.example.app;

import java.util.Random;

public class Point {
	public double lat,lon;
	
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
	
	public Point random(double deviation)
	{
		Random random = new Random();
		return new Point(lat+random.nextGaussian()*deviation,lon+random.nextGaussian()*deviation);
	}
}
