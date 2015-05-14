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

	private Movie movie;//�洢��Դ
	HandlerThread handlerThread=new HandlerThread("draw_handler");//�����µ��߳�
	private Handler handler;//����handler���󣬹�����Ϣ
	private Runnable runnable = new Runnable() {
		
		@Override
		public void run() {
			int startime=(int) System.currentTimeMillis();//��ʼ��ʱ��Ҳ���ǵ�һ֡��ʱ��

			while(IsRunning){
				int newtime=(int) System.currentTimeMillis();//ÿ�λ��Ƶ�ʱ��ģ���ǰʱ��
			
			
			Canvas canvas=holder.lockCanvas();//��û������󶨵�SurfaceHolder��
			
			if(canvas!=null&&movie!=null){
				canvas.save();//����֮ǰ���ƵĻ�����״̬
				movie.setTime(( newtime - startime ) % movie.duration() );//ͨ�����ڵ�ʱ���뿪ʼ��ʱ���ʱ����ÿһ֡��ͣ��ʱ��ȡģ������ǵڼ�֡����ʾ
				movie.draw(canvas, 0, 0);//�����ݻ��Ƶ�������
				canvas.restore();//ȡ��֮ǰ���ƵĻ�����״̬����save������һ��ʹ��ʱΪ�˱����ϴλ��ƶԱ��λ��Ƶ�Ӱ��
				holder.unlockCanvasAndPost(canvas);//����󶨲��ύ
			}else if(movie == null){
				return;
			}else if(canvas == null){
				return;
			}
			handler.removeCallbacks(runnable);
		handler.postDelayed(runnable, 20);//�˴���20����չʾ֮ǰ�ȴ��ĺ���ֵ
			}
			
			
		}
	};

	private Boolean IsRunning=false;//�Ƿ�������
//	private DrawGif drawGif;//���Ƶ��߳�
	private InputStream is;//������
//	private 
	public MySurfaceView(Context context,String path) {
		super(context);
		holder=this.getHolder();//���SurfaceHolder����
		holder.addCallback(this);//���ûص�����
		
		handlerThread.start();	
		handler =  new Handler(handlerThread.getLooper());//handlerThread.start();�������������ǰ��ִ�У����򽫻ᱨ��ָ��
		
		
		
		
		try {
			is=context.getAssets().open(path);//��ͼƬ��Դ
		} catch (IOException e) {
			e.printStackTrace();
		}
		movie=Movie.decodeStream(is);//ת��Ϊmovie ����
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		IsRunning=true;//��������

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
		IsRunning=false;//�ص�����
		handler.removeCallbacks(runnable);

	}
	
	

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//û����д������Ĭ������丸����
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
