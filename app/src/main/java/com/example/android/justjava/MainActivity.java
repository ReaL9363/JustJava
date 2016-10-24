package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    // Number of cups of coffee ordered
    int quantity = 1;
    EditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName = (EditText) findViewById(R.id.et_name);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 5) {
            Toast.makeText(this, "You can't order more than 100 cup coffee", Toast.LENGTH_SHORT).show();
            return;//a return statement can be used to branch out of a control flow block and exit the method
        } else {
            quantity = quantity + 1;
        }
        displayQunatity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You can't order less than 1 cup coffee", Toast.LENGTH_SHORT).show();
            return;
        } else {
            quantity = quantity - 1;
        }
        displayQunatity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.choclate_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        boolean hasChocolate = chocolateCheckBox.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate);
        //displayMessage(priceMessage);


        /*Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:47.6,-122.3"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }*/
        String subject="Just Java app Intent";
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        //This works to intent just the gmail app.
        intent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    private int calculatePrice(boolean addWhippedCreamPrice, boolean addChocolatePrice) {
        int priceForWhippedCreame = 1, priceForChocolate = 2;
        if (addWhippedCreamPrice && addChocolatePrice) {
            return quantity * (5 + priceForWhippedCreame + priceForChocolate);
        } else if (addWhippedCreamPrice) {
            return quantity * (5 + priceForWhippedCreame);
        } else if (addChocolatePrice) {
            return quantity * (5 + priceForChocolate);
        } else
            return quantity * 5;
    }

    //defining and calling a method
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = "Name:" + etName.getText().toString();
        priceMessage += "\nAdd Whipped Cream? " + addWhippedCream;
        priceMessage += "\nAdd Chocolate? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank you!";
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQunatity(int numberOfCoffee) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffee);
    }


    /**
     * This method displays the given text on the screen.
     *//*
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }*/
}
