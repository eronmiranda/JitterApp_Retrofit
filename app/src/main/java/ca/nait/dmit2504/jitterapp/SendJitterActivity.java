package ca.nait.dmit2504.jitterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SendJitterActivity extends AppCompatActivity {

    private EditText mLoginText;
    private EditText mMessageText;
    private Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_jitter);

        mLoginText = findViewById(R.id.name_text);
        mMessageText = findViewById(R.id.message_text);
        mSubmitButton = findViewById(R.id.send_button);

        mSubmitButton.setOnClickListener((View view) ->{
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://www.youcode.ca")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
            YoucodeService youcodeService = retrofit.create(YoucodeService.class);

            String name = mLoginText.getText().toString();
            String message = mMessageText.getText().toString();

            Call<String> postCall = youcodeService.postJitter(name, message);
            postCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    //Log.i(TAG, "Post was successful");
                    mLoginText.setText("");
                    mMessageText.setText("");
                    Toast.makeText(SendJitterActivity.this, "success fetching data", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    //Log.i(TAG, "Post was not successful");
                    Toast.makeText(SendJitterActivity.this,"failure to post data", Toast.LENGTH_SHORT).show();
                }
            });
        });


    }
}
