package com.nivedhithav.androiddatastorage;

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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pref extends AppCompatActivity {

    public final static String STORE_PREFERENCES="displayStatus.txt";
    public final static String PREFERENCE_VALUES="PreferenceValues.txt";
    public int counter=0;
    private SimpleDateFormat s=new SimpleDateFormat("MM/dd/yyyy-hh:mm a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        counter=sharedPrefs.getInt("COUNTER", 0);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        counter=sharedPrefs.getInt("COUNTER", 0);
    }

    public void preferences(View view)
    {
        //save the preferences (if not null) in a file.
        EditText editText1=(EditText)findViewById(R.id.anamevalue);
        EditText editText2=(EditText)findViewById(R.id.aauthorvalue);
        EditText editText3=(EditText)findViewById(R.id.descvalue);

        String name=editText1.getText().toString();
        String author=editText2.getText().toString();
        String description=editText3.getText().toString();

        if(name!=null && author!=null && description!=null)
        {
            try
            {
                counter+=1;

                SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("name",name);
                editor.putString("author",author);
                editor.putString("desc",description);
                editor.putInt("COUNTER", counter);
                editor.commit();

                OutputStreamWriter out=new OutputStreamWriter(openFileOutput(STORE_PREFERENCES,MODE_APPEND));
                String message="\nSaved Preference "+counter+", "+s.format(new Date());
                out.write(message);
                out.close();
                OutputStreamWriter out2=new OutputStreamWriter(openFileOutput(PREFERENCE_VALUES,MODE_APPEND));
                String value="\nCount "+counter+": Title: "+name+", Author: "+author+" and Description: "+description;
                out2.write(value);
                out2.close();
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }


        }

        //Retrieve the contents of the file in the OnResume() in MainActivity

        //Go back to the main screen
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }

    public void cancel(View v){
        Pref.this.finish();
    }

}
