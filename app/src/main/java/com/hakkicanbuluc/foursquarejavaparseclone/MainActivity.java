package com.hakkicanbuluc.foursquarejavaparseclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    EditText userNameText, passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameText = findViewById(R.id.user_name_signup_activity);
        passwordText = findViewById(R.id.password_text_signup_activity);

    }

    public void signUp(View view) {
        String userName = userNameText.getText().toString();
        String password = passwordText.getText().toString();
        if (!userName.isEmpty() && !password.isEmpty()) {
            ParseUser user = new ParseUser();
            user.setUsername(userName);
            user.setPassword(password);

            user.signUpInBackground(e -> {
                if (e != null) {
                    Toast.makeText(getApplicationContext(), e.
                            getLocalizedMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "User Signed " +
                            "Up!!!", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getApplicationContext(),
                            LocationsActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    public void signIn(View view) {
        String userName = userNameText.getText().toString();
        String password = passwordText.getText().toString();
        if (!userName.isEmpty() & !password.isEmpty()) {
            ParseUser.logInInBackground(userName, password, (user, e) -> {
                if (e != null) {
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(),
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Welcome: " + user.
                            getUsername(), Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getApplicationContext(),
                            LocationsActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}