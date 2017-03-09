package com.farazromani.android.kithna;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.common.base.Strings;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    EditText mOriginalPriceET;
    EditText mTotalDiscountET;
    EditText mTotalTaxET;
    TextView mResultTV;
    TextView mDiscountTypeTV;
    boolean isPctDiscount = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOriginalPriceET = (EditText) findViewById(R.id.original_price_edittext);
        mTotalDiscountET = (EditText) findViewById(R.id.total_discount_edittext);
        mTotalTaxET = (EditText) findViewById(R.id.total_tax_edittext);
        mResultTV = (TextView) findViewById(R.id.result_textview);
        mDiscountTypeTV = (TextView) findViewById(R.id.discount_type_tv);
        isPctDiscount = getDiscountType().equals("%");

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateResult();
            }
        };

        mOriginalPriceET.addTextChangedListener(textWatcher);
        mTotalDiscountET.addTextChangedListener(textWatcher);
        mTotalTaxET.addTextChangedListener(textWatcher);
        mDiscountTypeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPctDiscount) {
                    mDiscountTypeTV.setText("$");
                } else {
                    mDiscountTypeTV.setText("%");
                }
                isPctDiscount = getDiscountType().equals("%");
                updateResult();
            }
        });

        updateResult();
    }

    /**
     * Calculate and update the final price displayed on the result text view.
     */
    void updateResult() {
        double originalPrice = getOriginalPrice();
        double discountAmount = getDiscountAmount();
        double totalTax = getTotalTax();
        double total;

        if (isPctDiscount) {
            total = (originalPrice - (originalPrice * (discountAmount / 100))) * ((totalTax / 100) + 1);
        } else {
            total = (originalPrice - discountAmount) * ((totalTax / 100) + 1);
        }

        if (total < 0) {
            total = 0;
        }

        // Two decimal places
        NumberFormat formatter = new DecimalFormat("#0.00");
        String formatVal = formatter.format(total);

        mResultTV.setText("$" + formatVal);
    }

    double getOriginalPrice() {
        String tmp = mOriginalPriceET.getText().toString();

        if (!Strings.isNullOrEmpty(tmp)) {
            return Double.parseDouble(tmp);
        }

        return 0;
    }

    double getDiscountAmount() {
        String tmp = mTotalDiscountET.getText().toString();

        if (!Strings.isNullOrEmpty(tmp)) {
            return Double.parseDouble(tmp);
        }

        return 0;
    }

    double getTotalTax() {
        String tmp = mTotalTaxET.getText().toString();

        if (!Strings.isNullOrEmpty(tmp)) {
            return Double.parseDouble(tmp);
        }

        return 0;
    }

    String getDiscountType() {
        return mDiscountTypeTV.getText().toString();
    }
}
