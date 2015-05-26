package com.example.xiaomiview;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;

import com.example.animation.AnimationFactory;
import com.example.animation.AnimatorValue;
import com.example.animation.AnimatorValueImplements;
import com.nineoldandroids.animation.TypeEvaluator;
import com.nineoldandroids.animation.ValueAnimator;

public class UnistallView  extends View implements ValueAnimator.AnimatorUpdateListener{
	 
	 private ArrayList<ShapeHolder> balls = new ArrayList<ShapeHolder>();
	 private ArrayList<AnimatorValue> animatorValues = new ArrayList<AnimatorValue>();
	 private AnimatorValue bounceAnim = null;
	 private BallXYHolder ballHolder = null;
     private int colors[];
	 
 	 public UnistallView(Context context) {
		super(context);
	 }
 	public UnistallView(Context context,AttributeSet set) {
		super(context,set);
	 }

     @SuppressWarnings("unchecked")
	private void createAnimation() {
    	 for(int i=0,j=0;i<balls.size();i++,j++){
    		 float X=balls.get(i).getX();
    		 float Y= balls.get(i).getY();
    		 float deptX,deptY,deptAlpha;
    		 if(i%2==0){
        		  deptX=-i*2; 
    		 }else{
    			  deptX=i*2; 
    		 }
			 int b=balls.size()/2;
    		 int a=j/2+5;
    		 deptY=Y/16;
    		 Object holder[]=new Object[10];
    		 deptAlpha=255/holder.length;
    		 //抛物线路径
    		 if(i>balls.size()/2-1){
    			 deptX=deptX/2;
    			 for(int k=0;k<holder.length;k++){
            			 holder[k]= new XYAHolder(X+deptX*k,(float) (Y-a*(Math.sin(b*(Math.PI/b/16*k)))*deptY),255-(k+1)*deptAlpha);
        		 }
    		 }else{
        		 for(int k=0;k<holder.length;k++){
        			 if(k<holder.length-1){
            			 holder[k]= new XYAHolder(X+deptX*k,(float) (Y-a*(Math.sin(a*(Math.PI/a/8*k)))*deptY),255-(k+1)*deptAlpha);
        			 }else{
            			 holder[k]= new XYAHolder(X+deptX*k,(float) (Y-a*(Math.sin(a*(Math.PI/a)+Math.PI/4))*deptY),255-(k+1)*deptAlpha);
        			 }
        		 }
    		 }
             ballHolder = new BallXYHolder(balls.get(i));
             bounceAnim = new AnimatorValueImplements(ballHolder, new XYEvaluator(),"XYA", holder);
             animatorValues.add(bounceAnim);
    	 }
     }

     public void startAnimation(int num,int colors[],float x,float y) {
    	 this.colors=colors;
         for(int i=0;i<num;i++){
        	double r=Math.random()-0.5; 
        	balls.add(createBall((float)(x+r*8), (float)(y+r*8),255f));
         }
         createAnimation();
//         for(int i=0;i<animatorValues.size();i++){
//             if(i>0&&i%4==0){
//            	 animatorValues.get(i).before(animatorValues.get(i-1));
//            	 animatorValues.get(i).getAnimator().addUpdateListener(this);
//             }
//         }
//         AnimatorValue[] values = new AnimatorValue[animatorValues.size()];
//         animatorValues.toArray(values);
    	 animatorValues.get(0).getAnimator().addUpdateListener(this);
         AnimationFactory.getInstance().createEngine().startTogether(800, null,animatorValues);
     }
     //后续增加的碎片球，抛物线方程应该和初始的碎片不一致
     public void addBall(int num,float x,float y){
    	 ArrayList<AnimatorValue> animatorValues = new ArrayList<AnimatorValue>();
         for(int i=0,j=0;i<num;i++,j++){
        	 double r=Math.random()-0.5;
        	 ShapeHolder ball=createBall((float)(x+r*16), (float)(y+r*16),255f);
        	 balls.add(ball);
			 float X=ball.getX();
			 float Y= ball.getY();
			 float deptX,deptY,deptAlpha;
    		 if(i%2==0){
        		  deptX=-i*2; 
    		 }else{
    			  deptX=i*2; 
    		 }
    		 int a=j/2+5;
    		 int b=balls.size()/2;
    		 deptY=Y/16;
    		 Object holder[]=new Object[10];
    		 deptAlpha=255/holder.length;
    		 if(i>balls.size()/2-1){
    			 deptX=deptX/2;
    			 for(int k=0;k<holder.length;k++){
            			 holder[k]= new XYAHolder(X+deptX*k,(float) (Y-a*(Math.sin(b*(Math.PI/b/16*k)))*deptY),255-(k+1)*deptAlpha);
        		 }
    		 }else{
        		 for(int k=0;k<holder.length;k++){
        			 if(k<holder.length-1){
            			 holder[k]= new XYAHolder(X+deptX*k,(float) (Y-a*(Math.sin(a*(Math.PI/a/8*k)))*deptY),255-(k+1)*deptAlpha);
        			 }else{
            			 holder[k]= new XYAHolder(X+deptX*k,(float) (Y-a*(Math.sin(a*(Math.PI/a)+Math.PI/4))*deptY),255-(k+1)*deptAlpha);
        			 }
        		 }
    		 }
             ballHolder = new BallXYHolder(ball);
             bounceAnim = new AnimatorValueImplements(ballHolder, new XYEvaluator(),"XYA",holder);
             animatorValues.add(bounceAnim);
         }
//         for(int i=0;i<animatorValues.size();i++){
//             if(i>0&&i%4==0){
//            	 animatorValues.get(i).before(animatorValues.get(i-1));
//            	 animatorValues.get(i).getAnimator().addUpdateListener(this);
//             }
//         }
//         AnimatorValue[] values = new AnimatorValue[animatorValues.size()];
//         animatorValues.toArray(values);
         animatorValues.get(0).getAnimator().addUpdateListener(this);
         AnimationFactory.getInstance().createEngine().startTogether(800, null,animatorValues);
     }
     
     

     private ShapeHolder createBall(float x, float y,float alpha) {
         OvalShape circle = new OvalShape();
         double random=Math.random()-0.5; 
         float radio=(float)(20+random*16);
         circle.resize(radio,radio);
         ShapeDrawable drawable = new ShapeDrawable(circle);
         ShapeHolder shapeHolder = new ShapeHolder(drawable);
         shapeHolder.setX(x - radio/2);
         shapeHolder.setY(y - radio/2);
         shapeHolder.setAlpha(alpha);
         Paint paint = drawable.getPaint(); 
         int  color_random=(int) (Math.random()*colors.length);
         paint.setColor(colors[color_random]);
         shapeHolder.setPaint(paint);
         return shapeHolder;
     }

     @Override
     protected void onDraw(Canvas canvas) {
    	 for (int i = 0; i < balls.size(); ++i) {
             ShapeHolder shapeHolder = balls.get(i);
             canvas.save();
             canvas.translate(shapeHolder.getX(), shapeHolder.getY());
             shapeHolder.getShape().draw(canvas);
             canvas.restore();
         }
     }

     public void onAnimationUpdate(ValueAnimator animation) {
         invalidate();
     }
     
     public void clearBalls(){
    	 balls.clear();
    	 animatorValues.clear();
     }
     
     //匀速插值
     public class XYEvaluator implements TypeEvaluator {
         public Object evaluate(float fraction, Object startValue, Object endValue) {
        	 XYAHolder startXYA = (XYAHolder) startValue;
        	 XYAHolder endXYA = (XYAHolder) endValue;
             return new XYAHolder(startXYA.getX() + fraction * (endXYA.getX() - startXYA.getX()),
            		 startXYA.getY() + fraction * (endXYA.getY() - startXYA.getY()),  startXYA.getAlpha() + fraction * (endXYA.getAlpha() - startXYA.getAlpha()));
         }
     }
     
     public class XYAHolder {
         private float mX;
         private float mY;
         private float  mAlpha;
         public XYAHolder(float x, float y,float alpha) {
             mX = x;
             mY = y;
             mAlpha=alpha;
         }

         public float getX() {
             return mX;
         }

         public void setX(float x) {
             mX = x;
         }

         public float getY() {
             return mY;
         }

         public void setY(float y) {
             mY = y;
         }
         
         public float getAlpha() {
             return mAlpha;
         }

         public void setAlpha(int alpha) {
             mAlpha=alpha;
         }
     }
     
     public class BallXYHolder {

         private ShapeHolder mBall;

         public BallXYHolder(ShapeHolder ball) {
             mBall = ball;
         }

         public void setXYA(XYAHolder xyaHolder) {
             mBall.setX(xyaHolder.getX());
             mBall.setY(xyaHolder.getY());
             mBall.setAlpha(xyaHolder.getAlpha());
         }

         public XYAHolder getXYA() {
             return new XYAHolder(mBall.getX(), mBall.getY(),mBall.getAlpha());
         }
         
     }

}
