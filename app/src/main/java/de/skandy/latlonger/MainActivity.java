package de.skandy.latlonger;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.Locale;

import de.skandy.latlonger.util.SystemUiHider;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class MainActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        findViewById(R.id.fab).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(!checkInput()){
            return;
        }
        switch (view.getId()){
            case R.id.fab:

                float latitude = getLat();
                float longitude = getLong();

                Log.d("coord", "" + latitude + " " + longitude);

                String uri = String.format(Locale.ENGLISH, "geo:%f,%f?q=%f,%f",latitude,longitude,latitude,longitude);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                break;

            default:break;

        }
    }
    private boolean checkInput(){

        int[] ids = new int[] {R.id.tip_lat1,R.id.tip_lat2,R.id.tip_lat3, R.id.tip_lon1,R.id.tip_lon2,R.id.tip_lon3};
        boolean validInput = true;


        for(int i = 0; i<ids.length;i++){
            String text = ((TextInputLayout)findViewById(ids[i])).getEditText().getText().toString();
            if(text == null || text.isEmpty()){
                ((TextInputLayout)findViewById(ids[i])).setError("Please check your input");
                validInput = false;
            } else {
                ((TextInputLayout)findViewById(ids[i])).setError(null);

            }
        }

        return validInput;
    }

    private float getLong(){
        return calcCoordinate(R.id.longitude1,R.id.longitude2,R.id.longitude3);
    }

    private float getLat(){
        return calcCoordinate(R.id.latitude1,R.id.latitude2,R.id.latitude3);
    }

    private float calcCoordinate(int id1, int id2, int id3){
        float part1 = Float.parseFloat(((EditText)findViewById(id1)).getText().toString());
        float part2 = Float.parseFloat(((EditText)findViewById(id2)).getText().toString());
        float part3 = Float.parseFloat(((EditText) findViewById(id3)).getText().toString());
        return Math.signum(part1) * (Math.abs(part1) + (part2 / 60.0f) + (part3 / 3600.0f));
    }
}
