package org.youdian.android_demos.animation;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.youdian.android_demos.R;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.util.Xml;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;

public class CubeAnimation extends Animation {
    
    private float mFromDegrees;
    private float mToDegrees;
    
    private int mFromXType = ABSOLUTE;;
    private float mFromX = 0;
    private int mFromXdata = 0;
    
    private int mToXType = ABSOLUTE;
    private float mToX = 0;
    private int mToXData = 0;
    
    private Camera mCamera;
    private Resources mResources;
    private float mAxisY = 0;
    private int mAxisYType = ABSOLUTE;
    
    public CubeAnimation(float fromX,float toX,float fromDegree,float toDegree,float axisY) {
        this.mFromX = fromX;
        this.mToX = toX;
        this.mFromDegrees = fromDegree;
        this.mToDegrees = toDegree;
        this.mAxisY = axisY;
        
        mFromXType = TypedValue.TYPE_FLOAT;
        mToXType = TypedValue.TYPE_FLOAT;
        mAxisYType = ABSOLUTE;
        
    }
    public CubeAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.CubeAnimation);
        mResources = context.getResources();
        TypedValue value = a.peekValue(R.styleable.CubeAnimation_fromX);
        if(value.type==TypedValue.TYPE_FLOAT){
            this.mFromX = value.getFloat();
            this.mFromXType = value.type;
        }else{
            this.mFromXType = value.type;
            this.mFromXdata = value.data;
        }
        
        value = a.peekValue(R.styleable.CubeAnimation_toX);
        if(value.type==TypedValue.TYPE_FLOAT){//FLOAT ���͵ģ���������������ˣ���Ϊ�±ߵ�resolveData �����ò���TypedValue��û������
            this.mToX = value.getFloat();
            this.mToXType = value.type;
        }else{
            this.mToXType = value.type;
            this.mToXData = value.data;
        }
        boolean t = a.getBoolean(R.styleable.CubeAnimation_positive, true);
        if (!(t)) {
            this.mToDegrees = 0.0F;
            this.mFromDegrees = 90.0F;
        }
        this.mFromDegrees = a.getFloat(R.styleable.CubeAnimation_fromDegree, 0);
        this.mToDegrees = a.getFloat(R.styleable.CubeAnimation_toDegree, 90);
        
        value = a.peekValue(R.styleable.CubeAnimation_axisY);
        this.mAxisYType = value.type;
        //������ͬ����������ʲô�ã����Լ��������趨�ͽ����������������ļ�����Ҫ�������� <attr name="axisY" format="float|integer"/>
        //�����float���ͣ���������������ı���   �����int�ͣ���Ϊ������ֵ
        if(this.mAxisYType==TypedValue.TYPE_FLOAT){
            this.mAxisY = value.getFloat();
            this.mAxisYType = RELATIVE_TO_SELF;
        }else{
            this.mAxisY = value.data;
        }
        a.recycle();
    }
    
    public void initialize(int width, int height, int parentWidth,
            int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        if(this.mFromXType!=TypedValue.TYPE_FLOAT){//����Float���ʹ���̶�ֵ�����Ѿ������������ٽ��� ��ͬ
            this.mFromX = resolveData(this.mFromXType,this.mFromXdata, width,
                    parentWidth);
        }
        if(mToXType!=TypedValue.TYPE_FLOAT){
            this.mToX = resolveData(this.mToXType,this.mToXData,width,parentWidth);
        }
        this.mCamera = new Camera();
        
        if(mAxisYType==RELATIVE_TO_SELF) {//������������Ĵ�С�������򰴱��������ȡ��Ӧֵ��������Ϊ�̶�����ֵ
            mAxisY = mAxisY*height;
        }
        System.out.println("mFromX="+mFromX+",mToX=="+mToX);
    }
    float resolveData( int type, int data, int size, int psize) {
        float value = 0;
        if (type == TypedValue.TYPE_FRACTION) {
            value = TypedValue.complexToFraction(data, size, psize);
        } else if (type == TypedValue.TYPE_DIMENSION) {
            value = TypedValue.complexToDimension(data, mResources.getDisplayMetrics());
        } else{//������ɴ������óɵ�ABSOLUTE���ͻ��� �����ļ��������int�Ĺ̶�ֵ
            value= data;
        }
        return value;
    }
    
    
    // �Զ��嶯����ҪҪʵ�ֵķ���
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float fromDegrees = this.mFromDegrees;
        float degrees = fromDegrees + (this.mToDegrees - fromDegrees)
                * interpolatedTime;

        Camera camera = this.mCamera;

        Matrix matrix = t.getMatrix();

        camera.save();
        camera.rotateX(degrees);
        
        camera.getMatrix(matrix);
        camera.restore();
        
        
        matrix.postTranslate(mFromX+(mToX-mFromX)*interpolatedTime, this.mAxisY);
 
    }

    // ��Ϊ��AnimationUtils�޷�������������������ԣ���������CubeAnimation�������ļ����߰������������set�����ļ��������������������
    public static Animation loadAnimation(Context context, int id)
            throws NotFoundException {

        XmlResourceParser parser = null;
        try {
            parser = context.getResources().getAnimation(id);
            return createAnimationFromXml(context, parser, null,
                    Xml.asAttributeSet(parser));
        } catch (XmlPullParserException ex) {
            NotFoundException rnf = new NotFoundException(
                    "Can't load animation resource ID #0x"
                            + Integer.toHexString(id));
            rnf.initCause(ex);
            throw rnf;
        } catch (IOException ex) {
            NotFoundException rnf = new NotFoundException(
                    "Can't load animation resource ID #0x"
                            + Integer.toHexString(id));
            rnf.initCause(ex);
            throw rnf;
        } finally {
            if (parser != null)
                parser.close();
        }
    }

    private static Animation createAnimationFromXml(Context c,
            XmlPullParser parser, AnimationSet parent, AttributeSet attrs)
            throws XmlPullParserException, IOException {

        Animation anim = null;
        // Make sure we are on a start tag.
        int type;
        int depth = parser.getDepth();
        while (((type = parser.next()) != XmlPullParser.END_TAG || parser
                .getDepth() > depth) && type != XmlPullParser.END_DOCUMENT) {
            if (type != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("set")) {
                anim = new AnimationSet(c, attrs);
                createAnimationFromXml(c, parser, (AnimationSet) anim, attrs);
            } else if (name.equals("alpha")) {
                anim = new AlphaAnimation(c, attrs);
            } else if (name.equals("scale")) {
                anim = new ScaleAnimation(c, attrs);
            } else if (name.equals("rotate")) {
                anim = new RotateAnimation(c, attrs);
            } else if (name.equals("translate")) {
                anim = new TranslateAnimation(c, attrs);
            } else if (name.equals("cube")) {
                anim = new CubeAnimation(c, attrs);
            } else {
                throw new RuntimeException(
                        "not a cubeanimation animation name: "
                                + parser.getName());
            }
        }
        if (parent != null) {
            parent.addAnimation(anim);
        }

        return anim;

    }
}