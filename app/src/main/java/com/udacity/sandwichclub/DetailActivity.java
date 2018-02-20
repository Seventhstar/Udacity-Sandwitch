package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView placeOfOriginTextView;
    private TextView descriptionTextView;
    private TextView alsoKnownTextView;
    private TextView ingridientsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        placeOfOriginTextView = findViewById(R.id.detail_place_of_origin_tv);
        descriptionTextView = findViewById(R.id.description_tv);
        alsoKnownTextView = findViewById(R.id.also_known_tv);
        ingridientsTextView = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        setEmptyTexts();
        setTitle(sandwich.getMainName());

        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if (placeOfOrigin != null && !placeOfOrigin.isEmpty()) {
            placeOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        } else {
            placeOfOriginTextView.setText(getString(R.string.detail_origin_not_set));
        }

        descriptionTextView.setText("    " + sandwich.getDescription());
        alsoKnownTextView.setText(listToString(sandwich.getAlsoKnownAs()));
        ingridientsTextView.setText(listToString(sandwich.getIngredients()));
    }

    private String listToString(List<String> list) {
        if (list != null && !list.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String str : list) {
                stringBuilder.append(str + ", ");
            }
            stringBuilder.setLength(stringBuilder.length() - 2);
            return stringBuilder.toString();
        }
        return "";
    }

    private void setEmptyTexts() {
        setTitle("");
        placeOfOriginTextView.setText("");
        descriptionTextView.setText("");
    }
}
