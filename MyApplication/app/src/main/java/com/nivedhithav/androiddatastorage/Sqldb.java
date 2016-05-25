package com.nivedhithav.androiddatastorage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sqldb extends AppCompatActivity {

    public int counter=0;
    private SimpleDateFormat s=new SimpleDateFormat("MM/dd/yyyy-hh:mm a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqldb);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        counter=sharedPrefs.getInt("SQL_COUNTER", 0);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        counter=sharedPrefs.getInt("SQL_COUNTER", 0);
    }

    public void saveData(View view)
    {
        EditText editText1=(EditText)findViewById(R.id.anamevalue);
        EditText editText2=(EditText)findViewById(R.id.aauthorvalue);
        EditText editText3=(EditText)findViewById(R.id.descvalue);
        String name=editText1.getText().toString();
        String author=editText2.getText().toString();
        String desc=editText3.getText().toString();
        DataController dataController=new DataController(getBaseContext());
        dataController.open();
        long retValue= dataController.insert(name,author,desc);
        dataController.close();
        if(retValue!=-1)
        {
            Context context = getApplicationContext();
            CharSequence text=getString(R.string.save_success);
            int duration= Toast.LENGTH_LONG;
            Toast.makeText(context, text, duration).show();

            try
            {
                counter+=1;

                SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("SQL_COUNTER", counter);
                editor.commit();

                OutputStreamWriter out=new OutputStreamWriter(openFileOutput(Pref.STORE_PREFERENCES,MODE_APPEND));
                out.write("\nSQLite "+counter+", "+s.format(new Date()));
                out.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);

    }

    public void cancel(View v){
        Sqldb.this.finish();
    }

}
