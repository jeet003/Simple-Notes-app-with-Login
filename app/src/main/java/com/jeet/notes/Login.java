package com.jeet.notes;



import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends Activity {
    EditText username,password;
    Button button;
    SharedPreferences sp;
    public static String un;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username=(EditText)findViewById(R.id.username);
        button=(Button)findViewById(R.id.button);

        sp=getSharedPreferences("login",MODE_PRIVATE);

        //if SharedPreferences contains username then directly redirect to Home activity
        if(sp.contains("username")){

            Intent in=new Intent(Login.this,MainActivity.class);
            in.putExtra("UN",un);
            startActivity(in);
            finish();   //finish current activity
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginCheck();
            }
        });
    }

    void loginCheck(){
        //check username and password are correct and then add them to SharedPreferences
        un=username.getText().toString();
        if(!(username.getText().toString().equals(""))){
            SharedPreferences.Editor e=sp.edit();
                e.putString("username", username.getText().toString());

                e.commit();


            Toast.makeText(Login.this,"Login Successful",Toast.LENGTH_LONG).show();

            Intent in=new Intent(Login.this,MainActivity.class);
            in.putExtra("UN",un);
            startActivity(in);

            finish();
        }
        else{
            Toast.makeText(Login.this,"Incorrect Login Details",Toast.LENGTH_LONG).show();
        }
    }
}
