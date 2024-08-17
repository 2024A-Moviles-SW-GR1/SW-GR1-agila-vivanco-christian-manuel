package in.thbd.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.thbd.tools.Tools;

import in.thbd.instagram.Home.HomeActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Tools.setSystemBarColor(android.R.color.transparent, this);
        findViewById(R.id.login_btn).setOnClickListener(view->{
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        });

    }
}