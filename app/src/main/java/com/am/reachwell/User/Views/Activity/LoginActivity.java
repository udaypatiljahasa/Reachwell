package com.am.reachwell.User.Views.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.am.reachwell.Global.Depedencies.module.ActivityModule;
import com.am.reachwell.R;
import com.am.reachwell.ReachwellApplication;
import com.am.reachwell.User.Dependencies.component.DaggerUserComponent;
import com.am.reachwell.User.Dependencies.component.UserComponent;
import com.am.reachwell.User.ViewModel.LoginViewModel;

import javax.inject.Inject;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{

    @Inject
    LoginViewModel loginViewModel;
    private EditText usernameET,passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getComponent().inject(this);
        usernameET = (EditText) findViewById(R.id.tv_username);
        passwordET = (EditText) findViewById(R.id.txtPassword);
        // Set up the login form.
        Button signIn = (Button) findViewById(R.id.sign_in_button);
        signIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                loginViewModel.authenticateUser(usernameET.getText().toString().trim(),passwordET.getText().toString().trim());
                Intent intent = new Intent(LoginActivity.this,DashboardActivity.class);
                startActivity(intent);
            }
        });
    }


    private UserComponent getComponent(){
        return DaggerUserComponent.builder().activityModule(new ActivityModule(this))
                .applicationComponent(ReachwellApplication.get(this).getComponent()).build();
    }
}

