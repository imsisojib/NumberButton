package com.imsisojib.number_button;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.cardview.widget.CardView;

public class NumberButton extends RelativeLayout {
    private Context mContext;
    private AttributeSet attrs;
    private int styleAttr;
    private View view;

    /** Core Components*/


    CardView cardView;
    EditText etNumber;
    ImageButton btnDec,btnInc;

    /**Drawables*/
    Drawable numberDrawable,incIcon,decIcon;
    float cardElevation,cardRadius;
    float number = 1;
    float incrementValue,numberTextSize;
    int incIconHeight, decIconHeight;
    int textColor,backGroundColor;
    String numberTextStyle="";
    boolean isIntegerIncrement = true;

    public NumberButton(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public NumberButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.attrs = attrs;
        initView();
    }

    public NumberButton(Context context, AttributeSet attrs, int defStyleAttr, Context mContext, int styleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = mContext;
        this.attrs = attrs;
        this.styleAttr = styleAttr;
        initView();
    }

    private void initView() {
        this.view=this;
        //Inflating the XML view
        inflate(mContext, R.layout.number_button,this);

        TypedArray arr = mContext.obtainStyledAttributes(attrs,R.styleable.NumberButtonBasic,
                styleAttr,0);

        numberDrawable = arr.getDrawable(R.styleable.NumberButtonBasic_numberDrawable);
        incIcon = arr.getDrawable(R.styleable.NumberButtonBasic_incIconSrc);
        decIcon = arr.getDrawable(R.styleable.NumberButtonBasic_decIconSrc);
        cardElevation = arr.getFloat(R.styleable.NumberButtonBasic_btnElevation,6);

        textColor = arr.getColor(R.styleable.NumberButtonBasic_numberTextColor,getResources().getColor(R.color.black));
        numberTextStyle = arr.getString(R.styleable.NumberButtonBasic_numberTextStyle);

        backGroundColor = arr.getColor(R.styleable.NumberButtonBasic_btnBackgroundColor,getResources().getColor(R.color.design_default_color_background));
        incrementValue = arr.getFloat(R.styleable.NumberButtonBasic_incrementValue,1);
        cardRadius = arr.getFloat(R.styleable.NumberButtonBasic_btnRadius,8);

        numberTextSize = arr.getFloat(R.styleable.NumberButtonBasic_numberTextSize,16);

        incIconHeight = arr.getInteger(R.styleable.NumberButtonBasic_incIconHeight,24);
        decIconHeight = arr.getInteger(R.styleable.NumberButtonBasic_decIconHeight,24);

        checkIsIntegerOrFloat();

        //components
        cardView = findViewById(R.id.cardview_container);

        etNumber = findViewById(R.id.editext_number);
        //etNumber.setText(String.valueOf(number));
        setNumber(number);
        etNumber.addTextChangedListener(numberChangedListener);

        btnDec = findViewById(R.id.button_remove);
        btnDec.setOnClickListener(decrementClickListener);

        btnInc = findViewById(R.id.button_add);
        btnInc.setOnClickListener(incrementClickListener);

        /**SETTING ATTRIBUTES in onInit()*/
        /*
        <attr name="elevation" format="reference"/>
        <attr name="radius" format="reference"/>
        <attr name="cardElevation" format="reference"/>
        <attr name="backgroundColor" format="reference"/>

        <attr name="incIconSrc" format="reference"/>
        <attr name="incIconHeight" format="reference"/>

        <attr name="decIconSrc" format="reference"/>
        <attr name="decIconHeight" format="reference"/>



        <attr name="textColor" format="reference"/>
        <attr name="textSize" format="reference"/>
        <attr name="textStyle" format="reference"/>
        <attr name="fontFamily" format="reference"/>
        <attr name="incrementValue" format="reference"/>
        */


        if (numberDrawable!=null){
            setNumberDrawable(numberDrawable);
        }
        if (incIcon!=null){
            setIncrementIcon(incIcon);
        }
        if (decIcon!=null){
            setDecrementIcon(decIcon);
        }

        setIncrementIconMaxHeight(incIconHeight);
        setDecrementIconMaxHeight(decIconHeight);

        setButtonElevation(cardElevation);
        setButtonBackgroundColor(backGroundColor);
        setButtonRadius(cardRadius);

        setNumberTextColor(textColor);
        setNumberTextStyle(numberTextStyle);
        setNumberTextSize(numberTextSize);


        arr.recycle();
    }

    private void checkIsIntegerOrFloat() {
        if (incrementValue * 100- (int)incrementValue * 100 == 0){
            isIntegerIncrement = true;
        }else isIntegerIncrement = false;

    }

    /**SETTERS*/
    public void setNumberDrawable(Drawable drawable){
        etNumber.setBackground(drawable);
    }
    public void setIncrementIcon(Drawable drawable){
        btnInc.setImageDrawable(drawable);
    }
    public void setDecrementIcon(Drawable drawable){
        btnDec.setImageDrawable(drawable);
    }
    public void setIncrementIconMaxHeight(int height){
        btnInc.setMaxHeight(height);

    }
    public void setDecrementIconMaxHeight(int height){
        btnDec.setMaxHeight(height);

    }
    public void setButtonRadius(float cardRadius) {
        cardView.setRadius(cardRadius);
    }
    public void setButtonBackgroundColor(int backGroundColor) {
        cardView.setCardBackgroundColor(backGroundColor);

    }
    public void setNumberTextColor(int textColor) {
        etNumber.setTextColor(textColor);

    }
    public void setButtonElevation(float cardElevation) {
        cardView.setCardElevation(cardElevation);

    }
    public void setNumberTextStyle(String numberTextStyle) {
        if (numberTextStyle==null) return;
        switch (numberTextStyle){
            case "bold":{
                etNumber.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                break;
            }
            case "italic:":{
                etNumber.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                break;
            }
            case "bold|italic":{
                etNumber.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
                break;
            }
            default: etNumber.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }
    }

    public void setNumberTextSize(float numberTextSize) {
        etNumber.setTextSize(numberTextSize);
    }


    /**GETTERS*/
    public float getNumber(){
        String strNumber = ""+etNumber.getText().toString().trim();
        if (strNumber.isEmpty()) return 0;

        return Float.parseFloat(strNumber);
    }

    //OnClickListeners
    private View.OnClickListener incrementClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            number += incrementValue;
            setNumber(number);
            //etNumber.setText(String.valueOf(number));
        }
    };
    private View.OnClickListener decrementClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            number -= incrementValue;
            if (number <= 0){
                setNumber(0);
                number = 0;
                return;
            }
           // etNumber.setText(String.valueOf(number));
            setNumber(number);
        }
    };

    private TextWatcher numberChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().isEmpty()){
                setNumber(0);
                return;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void setNumber(float value){
        if (isIntegerIncrement){
            etNumber.setText(""+(int)value);
        }else etNumber.setText(String.valueOf(value));
    }



}
