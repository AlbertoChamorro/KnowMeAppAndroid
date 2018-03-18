package com.knowme.knowme.auth.view;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.knowme.knowme.R;
import com.knowme.knowme.auth.presenter.CreateAccountPresenter;
import com.knowme.knowme.model.User;
import com.knowme.knowme.util.Helper;

public class CreateAccountActivity extends AppCompatActivity implements ICreateAccountView{

    private static final String TAG = "Create Account Activity";
    private Toolbar toolbar;

    private TextInputEditText emailTextInputEditText, nameTextInputEditText, user_nameTextInputEditText,
            passwordTextInputEditText, confirm_passwordTextInputEditText;
    private Button joinUsButton;
    private ProgressBar mProgressBar;

    private CreateAccountPresenter createAccountPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        this.createAccountPresenter = new CreateAccountPresenter(this);
        this.showToolbar(getResources().getString(R.string.text_create_account), true);

        this.emailTextInputEditText = (TextInputEditText) findViewById(R.id.email);
        this.nameTextInputEditText = (TextInputEditText) findViewById(R.id.name);
        this.user_nameTextInputEditText = (TextInputEditText) findViewById(R.id.user_name);
        this.passwordTextInputEditText = (TextInputEditText) findViewById(R.id.password);
        this.confirm_passwordTextInputEditText = (TextInputEditText) findViewById(R.id.confirm_password);

        this.joinUsButton = (Button) findViewById(R.id.joinUsButton);
        this.mProgressBar = (ProgressBar) findViewById(R.id.progress_bar_create_account);
        this.toogleProgressBar(false);

        this.joinUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewAccount();
            }
        });
    }

    @Override
    public void createNewAccount() {

        String email = emailTextInputEditText.getText().toString();
        String name = nameTextInputEditText.getText().toString();
        String userName = user_nameTextInputEditText.getText().toString();
        String password = passwordTextInputEditText.getText().toString();
        String confirmPassword = confirm_passwordTextInputEditText.getText().toString();

        if(email.equals("") || name.equals("") || userName.equals("") || password.equals("")){
            Helper.showToast(this, "Debe llenar los campos." , Toast.LENGTH_LONG);
            return;
        }

        if(confirmPassword.equals("")){
            Helper.showToast(this, "Debe confirmar su contraseña." , Toast.LENGTH_LONG);
            return;
        }

        if (!password.equals(confirmPassword)){
            Helper.showToast(this, "Las contraseñas deben coincidir." , Toast.LENGTH_LONG);
            return;
        }

        User user = new User(email, name, userName, password);
        createAccountPresenter.createAccount(user, CreateAccountActivity.this);
    }

    @Override
    public void createAcccountError(String error) {
        Helper.showToast(this, error , Toast.LENGTH_LONG);
    }

    @Override
    public void toogleEnabledComponents(Boolean state) {
        this.emailTextInputEditText.setEnabled(state);
        this.nameTextInputEditText.setEnabled(state);
        this.user_nameTextInputEditText.setEnabled(state);
        this.passwordTextInputEditText.setEnabled(state);
        this.confirm_passwordTextInputEditText.setEnabled(state);
        this.joinUsButton.setEnabled(state);
    }

    @Override
    public void toogleProgressBar(Boolean state) {

        int stateVisibility = state == true ? View.VISIBLE : View.GONE;
        this.mProgressBar.setVisibility(stateVisibility);
    }

    @Override
    public void goToLogin(String message) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        Helper.showToast(this, message, Toast.LENGTH_LONG);
    }

    public void showToolbar(String title, Boolean showBackButton) {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(showBackButton);
    }
}
