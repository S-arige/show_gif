package com.example.gifsurface;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.View.MeasureSpec;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements Callback {

	private SurfaceHolder holder;

	private Movie movie;//存储资源
	HandlerThread handlerThread=new HandlerThread("draw_handler");//生成新的线程
	private Handler handler;//生成handler对象，管理消息
	private Runnable runnable = new Runnable() {
		
		@Override
		public void run() {
			int startime=(int) System.currentTimeMillis();//开始的时候，也就是第一帧的时间

			while(IsRunning){
				int newtime=(int) System.currentTimeMillis();//每次绘制的时候的，当前时间
			
			
			Canvas canvas=holder.lockCanvas();//获得画布并绑定到SurfaceHolder上
			
			if(canvas!=null&&movie!=null){
				canvas.save();//保存之前绘制的画布的状态
				movie.setTime(( newtime - startime ) % movie.duration() );//通过现在的时间与开始的时间的时间差对每一帧的停留时间取模，获得是第几帧被显示
				movie.draw(canvas, 0, 0);//将内容绘制到画布上
				canvas.restore();//取出之前绘制的画布的状态。和save（）；一起使用时为了避免上次绘制对本次绘制的影响
				holder.unlockCanvasAndPost(canvas);//解除绑定并提交
			}else if(movie == null){
				return;
			}else if(canvas == null){
				return;
			}
			handler.removeCallbacks(runnable);
		handler.postDelayed(runnable, 20);//此处的20是在展示之前等待的毫秒值
			}
			
			
		}
	};

	private Boolean IsRunning=false;//是否在运行
//	private DrawGif drawGif;//绘制的线程
	private InputStream is;//输入流
//	private 
	public MySurfaceView(Context context,String path) {
		super(context);
		holder=this.getHolder();//获得SurfaceHolder对象
		holder.addCallback(this);//设置回掉监听
		
		handlerThread.start();	
		handler =  new Handler(handlerThread.getLooper());//handlerThread.start();必须在这个方法前面执行，否则将会报空指针
		
		
		
		
		try {
			is=context.getAssets().open(path);//打开图片资源
		} catch (IOException e) {
			e.printStackTrace();
		}
		movie=Movie.decodeStream(is);//转换为movie 类型
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		IsRunning=true;//可以运行

		handler.post(runnable);

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		IsRunning=false;//关掉绘制
		handler.removeCallbacks(runnable);

	}
	
	

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//没有重写，导致默认是填充父布局
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Log.i("tag", "onMeasure-----------------------------");
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightResult = 200;
		int widthResult = 200;
		if(heightMode == MeasureSpec.EXACTLY ){
			heightResult = heightSize;
			Log.i("tag", "heightMode-------EXACTLY"+ heightResult);
		}
		if(heightMode == MeasureSpec.AT_MOST){
			heightResult = movie.height();
			Log.i("tag", "heightMode-----AT_MOST"+ heightResult);
		}
		if(widthMode == MeasureSpec.EXACTLY ){
			widthResult = widthSize;
			Log.i("tag", "widthMode-----EXACTLY"+ widthResult);
		}
		
		if(widthMode == MeasureSpec.AT_MOST){
			if(movie.width() > widthSize){
				widthResult = widthSize;	
			}else{
				widthResult = movie.width();
			}
			
			Log.i("tag", "widthMode-----AT_MOST"+ widthResult);
		}
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(widthResult, heightResult);
	}

}
