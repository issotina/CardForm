package com.craftman.cardform;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by Shadaï ALI on 28/12/16.
 *
 * @Doc $doc
 */

public class CardForm extends LinearLayoutCompat {
    private static final char space = ' ';
    boolean isBackShowing = false;
    String amount = "$500.00";
    OnPayBtnClickListner onPayBtnClickListner;
    String cardNameError = "Correct Card Name is requierd";
    String cardNumberError = "Correct Card Number is requierd";
    String cvcError = "Correct  cvc is requierd";
    String expiryDateError = "Correct  expiry date is requierd";
    private EditText cardName;
    private EditText cardNumber;
    private TextView paymentAmountTextHolder;
    private TextView previewCardType;
    private EditText cvc;
    private EditText expiryDate;
    private TextView previewCardName;
    private TextView previewCardNumber;
    private TextView previewCvc;
    private TextView previewExpiry;
    private TextView paymentAmount;
    private ViewGroup cardBack;
    private ViewGroup cardFront;
    private Button btnPay;
    private char slash = '/';


    public CardForm(Context context) {
        super(context);
        init();
    }

    public CardForm(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CardForm(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setCardNameError(String cardNameError) {
        this.cardNameError = cardNameError;
    }

    public void setCardNumberError(String cardNumberError) {
        this.cardNumberError = cardNumberError;
    }

    public void setCvcError(String cvcError) {
        this.cvcError = cvcError;
    }

    public void setExpiryDateError(String expiryDateError) {
        this.expiryDateError = expiryDateError;
    }

    public Card getCard() {
        String expiry[] = getString(expiryDate).split(String.valueOf(slash));
        Integer month = 0, year = 0;
        if (expiry.length >= 2) {
            month = Integer.parseInt(expiry[0]);
            year = Integer.parseInt(parseDate(expiry[1]));
        }

        /*DateFormat sdfp = new SimpleDateFormat("yy");
        Date d = null;
        try {
            d = sdfp.parse(String.valueOf(year));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat sdff = new SimpleDateFormat("yyyy");
        year = Integer.parseInt(sdff.format(d));*/

        return new Card(getString(cardNumber).replaceAll(String.valueOf(space), "")
                , month, year, getString(cvc), getString(cardName), "", "", "", "", "", "", "");
    }

    private String parseDate(String str) {

        int year = Integer.valueOf(str);

        // Allow 5 years in the future for a 2 digit date
        if (year + 100 > new Date().getYear() + 5) {
            year = year + 1900;
        } else {
            year = year + 2000;
        }
        return String.valueOf(year);
    }


    private String getString(EditText ed) {
        return ed.getText().toString().trim();
    }

    private Integer getInt(EditText ed) {
        return Integer.parseInt(ed.getText().toString().trim());
    }


    private void init() {

        inflate(getContext(), R.layout.cardformlayout, this);

        /* inflate views */

        cardName = (EditText) findViewById(R.id.card_name);
        cardNumber = (EditText) findViewById(R.id.card_number);
        cvc = (EditText) findViewById(R.id.cvc);
        expiryDate = (EditText) findViewById(R.id.expiry_date);

        previewCardName = (TextView) findViewById(R.id.card_preview_name);
        previewCardNumber = (TextView) findViewById(R.id.card_preview_number);
        previewCvc = (TextView) findViewById(R.id.card_preview_cvc);
        previewExpiry = (TextView) findViewById(R.id.card_preview_expiry);
        paymentAmount = (TextView) findViewById(R.id.payment_amount);
        paymentAmountTextHolder = (TextView) findViewById(R.id.payment_amount_holder);
        previewCardType = (TextView) findViewById(R.id.card_preview_type);

        cardFront = (ViewGroup) findViewById(R.id.card_preview_front);
        cardBack = (ViewGroup) findViewById(R.id.card_preview_back);

        btnPay = (Button) findViewById(R.id.btn_pay);

        paymentAmount.setText(amount);


        btnPay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(getString(cardName))) {
                    cardName.setError(cardNameError);
                    return;
                }
                if (TextUtils.isEmpty(getString(cardNumber))) {
                    cardNumber.setError(cardNumberError);
                    return;
                }

                if (TextUtils.isEmpty(getString(cvc))) {
                    cvc.setError(cvcError);
                    return;
                }
                if (TextUtils.isEmpty(getString(expiryDate))) {
                    expiryDate.setError(expiryDateError);
                    return;
                }

                if (cardIsvalid()) {
                    onPayBtnClickListner.onClick(getCard());
                }
            }
        });


        cardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Remove spacing char
                if (editable.length() > 0 && (editable.length() % 5) == 0) {
                    final char c = editable.charAt(editable.length() - 1);
                    if (space == c) {
                        editable.delete(editable.length() - 1, editable.length());
                    }
                }
                // Insert char where needed.
                if (editable.length() > 0 && (editable.length() % 5) == 0) {
                    char c = editable.charAt(editable.length() - 1);
                    // Only if its a digit where there should be a space we insert a space
                    if (Character.isDigit(c) && TextUtils.split(editable.toString(), String.valueOf(space)).length <= 3) {
                        editable.insert(editable.length() - 1, String.valueOf(space));

                    }
                }

                if (editable.length() >= 16) {
                    previewCardType.setText(new Card(editable.toString(), 0, 0, "").getBrand());
                }
                previewCardNumber.setText(editable.toString());
            }
        });

        expiryDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                switch (editable.length()) {
                    case 1:
                        if (Integer.parseInt(editable.toString()) > 1) {
                            editable.clear();
                        }
                        break;

                    case 2:
                        if (((int) editable.charAt(0)) > 0) {
                            if (((int) editable.charAt(1)) > 2) {
                                editable.delete(1, 1);
                            }
                        }
                }

                if (editable.length() > 0 && (editable.length() % 3) == 0) {
                    char c = editable.charAt(editable.length() - 1);

                    if (Character.isDigit(c)) {
                        editable.insert(editable.length() - 1, String.valueOf(slash));

                    }
                }

                previewExpiry.setText(editable.toString());
            }
        });


        cardName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().trim().length() > 0) {
                    previewCardName.setText(editable.toString());
                }
            }
        });


        cvc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() > 0) {
                    previewCvc.setText(editable.toString());
                }
            }
        });


        cvc.setOnFocusChangeListener(
                new OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        if (b) showBack();
                    }
                });

        cardName.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) showFront();
            }
        });

        cardNumber.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) showFront();
            }
        });

        expiryDate.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) showFront();
            }
        });
    }

    private boolean cardIsvalid() {
        Card card = getCard();
        if (!card.validateNumber()) {
            cardNumber.setError(cardNumberError);
        }
        if (!card.validateExpiryDate()) {
            expiryDate.setError(expiryDateError);
        }
        if (!card.validateCVC()) {
            cvc.setError(expiryDateError);
        }

        return card.validateCard();
    }

    private void showBack() {
        if (!isBackShowing) {
            Animator cardFlipLeftIn = AnimatorInflater.loadAnimator(getContext(), R.animator.card_flip_left_in);
            cardFlipLeftIn.setTarget(cardFront);
            cardFlipLeftIn.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    cardFront.setVisibility(GONE);
                    cardBack.setVisibility(VISIBLE);
                    isBackShowing = true;
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            cardFlipLeftIn.start();
        }
    }


    private void showFront() {
        if (isBackShowing) {
            Animator cardFlipRightIn = AnimatorInflater.loadAnimator(getContext(), R.animator.card_flip_right_in);
            cardFlipRightIn.setTarget(cardBack);
            cardFlipRightIn.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    cardBack.setVisibility(GONE);
                    cardFront.setVisibility(VISIBLE);
                    isBackShowing = false;
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });

            cardFlipRightIn.start();

        }

    }

    public void setPayBtnClickListner(OnPayBtnClickListner onPayBtnClickListner) {
        this.onPayBtnClickListner = onPayBtnClickListner;
    }


}
