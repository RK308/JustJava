package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    String drinkOfTheDay = "Mocha";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        if (quantity==100){
     /*       Context context = getApplicationContext();
              CharSequence text = "Quantity should be between 1 - 99";
              int duration = Toast.LENGTH_SHORT;

              Toast toast = Toast.makeText(context, text, duration);
              toast.show();  */

            // show an error message as a toast
            Toast.makeText(this,"Quantity cannot be more than 100",Toast.LENGTH_SHORT ).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        if(quantity==1){
            Context context = getApplicationContext();
            CharSequence text = "Quantity cannot be less than 1";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText name = (EditText) findViewById(R.id.name_view);
        String userName = name.getText().toString();
     /* Log.v("MainActivity","Name : " + userName);  */

        CheckBox topping = (CheckBox) findViewById(R.id.toppings_checkbox);
        boolean hasWhippedCream = topping.isChecked();
    /*  Log.v("MainActivity","Has Whipped Cream : " + hasWhippedCream);  */

        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean  hasChocolate = chocolate.isChecked();
   /*   Log.v("MainActivity","Has Chocolate : " + hasChocolate);   */

        int price=calculatePrice(hasWhippedCream,hasChocolate);
        String message= createOrderSummary(price, hasWhippedCream, hasChocolate, userName);

        /**drinkOfTheDay = drinkOfTheDay + " Cappuccino";*/

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, userName));
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
//     n   displayMessage(message);
//    }
    /*   displayMessage("Total : $" + price + "\nThank You!");*/
}


    /**

     * Calculates the price of the order.
     *  * @Param addWhippedCream is whether or not the user wants whipped cream topping
     * @Param addChocolate is whether or not the user wants chocolate topping
     * @return total price

     */

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;

        if (addWhippedCream) {
            basePrice = basePrice + 1;

        }
        if (addChocolate){
            basePrice = basePrice + 2;

        }

        int totalPrice = quantity * basePrice;
        return totalPrice;
    }

   /*  Creates summary of the order
   *
   * @Param name of the customer
   * @Param addWhippedCream is whether or not the user wants whipped cream topping
   * @Param addChocolate is whether or not the user wants chocolate topping
   * @param price of the order
   * @return text summary
   */

    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name){
     /* String name = "Roopa Koduri";    */
        String message = getString(R.string.order_summary_name, name) + "\n" + getString(R.string.order_summary_whipped_cream,addWhippedCream) + "\n" + getString(R.string.order_summary_chocolate, addChocolate) + "\n" + getString(R.string.order_summary_quantity, quantity);
        message += "\n" + getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price)) + "\n" + getString(R.string.thank_you);
        return message;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int quantity) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }


    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }


}

