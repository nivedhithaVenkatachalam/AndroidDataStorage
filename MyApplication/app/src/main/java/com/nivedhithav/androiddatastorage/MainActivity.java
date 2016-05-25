package com.nivedhithav.androiddatastorage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        try
        {
            InputStream in=openFileInput(Pref.STORE_PREFERENCES);
            if(in!=null)
            {
                InputStreamReader tmp=new InputStreamReader(in);
                BufferedReader reader=new BufferedReader(tmp);
                String str;
                StringBuilder buf=new StringBuilder();
                while((str=reader.readLine())!=null)
                {
                    buf.append(str +"\n");
                }
                in.close();
                TextView savedPref=(TextView)findViewById(R.id.info);
                savedPref.setText(buf.toString());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void preferences(View v){
        Intent intent=new Intent(MainActivity.this,Pref.class);
        startActivity(intent);
    }

    public void sql(View v){
        Intent intent=new Intent(MainActivity.this,Sqldb.class);
        startActivity(intent);
    }

    public void close(View v){
        MainActivity.this.finish();
    }

}
