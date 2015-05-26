package com.example.xiaomiunistallappanimation;

import com.example.animation.AnimationEngine;
import com.example.animation.AnimationFactory;
import com.example.animation.AnimatorValue;
import com.example.animation.AnimatorValueImplements;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import com.example.xiaomiview.UnistallView;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {
	private ImageView im_unistall;
	private Button btn_xiaomi;
	private Button btn_reset;
	private UnistallView view;
	private int colors[]=new int[10];
	private boolean start_0=true;
	private boolean start_1=true;
	private boolean start_2=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
		initEvents();
		initColors();
	}

	private void initColors(){
		Bitmap bitmap=convertViewToBitmap(im_unistall);
		int width=im_unistall.getWidth();
		int height=im_unistall.getHeight();
		for(int i=0;i<colors.length/2;i++){
			if(i==colors.length/2-1){
				colors[i]=bitmap.getPixel((int)(width*i*0.25f-1), (int)(height*i*0.25f-1));
			}else{
				colors[i]=bitmap.getPixel((int)(width*i*0.25f), (int)(height*i*0.25f));
			}
		}
		for(int i=5;i<colors.length;i++){
			if(i==colors.length-1){
				colors[i]=bitmap.getPixel((int)(width*(i-colors.length/2)*0.25f-1), (int)(height-height*(i-colors.length/2)*0.25f));
			}else{
				colors[i]=bitmap.getPixel((int)(width*(i-colors.length/2)*0.25f), (int)(height-height*(i-colors.length/2)*0.25f)-1);
			}
		}
		
	}
	
	
	private void initViews(){
		im_unistall=(ImageView) findViewById(R.id.im_unistall);
		btn_xiaomi=(Button) findViewById(R.id.btn_xiaomi);
		btn_reset=(Button) findViewById(R.id.btn_reset);
		view=(UnistallView) findViewById(R.id.view_add);
	}
	
	private void initEvents(){
		btn_xiaomi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			   AnimatorValue a1=new AnimatorValueImplements(im_unistall, "TranslationX", 0f,6f,6f,-6f,-6f,0f);
			   a1.getAnimator().setRepeatCount(2);
			   AnimatorValue a2=new AnimatorValueImplements(im_unistall, "TranslationY", 0f,-6f,6f,6f,-6f,0f);
			   a2.getAnimator().setRepeatCount(2);
			   AnimatorValue a3=new AnimatorValueImplements(im_unistall, "ScaleX", 1f,0.6f,0.2f);
			   AnimatorValue a4=new AnimatorValueImplements(im_unistall, "ScaleY", 1f,0.6f,0.2f);
			   a3.before(a2);						 
			   a3.getAnimator().addUpdateListener(new AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator arg0) {
					if(start_0){
							 view.startAnimation(20,colors,im_unistall.getX()+ im_unistall.getWidth()/2,im_unistall.getY()+im_unistall.getHeight()/2);
							 start_0=false;
					}else if((Float)(arg0.getAnimatedValue("ScaleX"))>0.3f&&(Float)(arg0.getAnimatedValue("ScaleX"))<0.4f){
							 if(start_1){
							    view.addBall(30, im_unistall.getX()+im_unistall.getWidth()/2,im_unistall.getY()+im_unistall.getHeight()/2);
							     start_1=false; 
							 }
					}else if((Float)(arg0.getAnimatedValue("ScaleX"))>0.2f&&(Float)(arg0.getAnimatedValue("ScaleX"))<0.3f){
							 if(start_2){
	    					    view.addBall(40, im_unistall.getX()+im_unistall.getWidth()/2,im_unistall.getY()+im_unistall.getHeight()/2);
								start_2=false; 
							  }
					}
				}
			});
			   AnimationEngine engine= AnimationFactory.getInstance().createEngine();
			   engine.startTogetherByLink(1000,new AnimatorListener() {
				
				@Override
				public void onAnimationStart(Animator arg0) {
					
				}
				
				@Override
				public void onAnimationRepeat(Animator arg0) {
					
				}
				
				@Override
				public void onAnimationEnd(Animator arg0) {
					im_unistall.setVisibility(View.INVISIBLE);
				}
				
				@Override
				public void onAnimationCancel(Animator arg0) {
					
				}
			},a1,a2,a3,a4);
			}
		});
		btn_reset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				 start_0=true;
				 start_1=true;
				 start_2=true;
				view.clearBalls();
				im_unistall.setVisibility(View.VISIBLE);
				AnimatorValue a1=new AnimatorValueImplements(im_unistall, "ScaleX", 0.2f,0.6f,1f);
			    AnimatorValue a2=new AnimatorValueImplements(im_unistall, "ScaleY", 0.2f,0.6f,1f);
			    AnimationFactory.getInstance().createEngine().startTogether(1000, null, a1,a2);
			}
		});
	}
	
	private  Bitmap convertViewToBitmap(View view){
	        view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
	        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
	        view.buildDrawingCache();
	        Bitmap bitmap = view.getDrawingCache();
     	    return bitmap;
	}
	


}
