package com.example.app;

public class Track {
	private Point keypoint[];
	private int pointnum;
	private Point curpoint;
	private int pre;//index of previous key point
	private double interval=1500.0;//time interval between two points (ms)
	private boolean random_track_flag=true;//Whether to set a random deviation on the track
	private double deviation=1.0/200000.0;
	private double EPSILON=0.00000001;
	Track(double [] lats_, double [] lons_, int pointnum_)
	{
		pointnum=pointnum_;
		keypoint=new Point [pointnum];
		for(int i=0;i<pointnum;i++)
			keypoint[i]=new Point(lats_[i],lons_[i]);
		pre=0;
		curpoint=keypoint[pre];
	}
	
	public Point gotonextpoint (double speed)
	{
		int next=pre+1;
		if(next>=pointnum)next-=pointnum;
		double dis_interval=((speed+(Math.random()-0.5)*speed*0.4) / 3.6) * (interval / 1000);
		double dis_keypoint=curpoint.getDistance( keypoint[next] );
		if( dis_interval - dis_keypoint > - EPSILON )
		{
			int time=(int)(dis_keypoint/(speed /3.6) * 1000);
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			pre=next;
			curpoint=keypoint[pre];
		}
		else
		{
			double latdiff= keypoint[next].lat -curpoint.lat;
			double londiff= keypoint[next].lon -curpoint.lon;
			double rate=dis_interval/dis_keypoint;
			try {
				Thread.sleep((int)interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			curpoint=new Point(curpoint.lat+latdiff*rate,curpoint.lon+londiff*rate);;
		}
		if(random_track_flag)curpoint.footprintrandom(deviation);
		return curpoint.GPSrandom();
	}
}
