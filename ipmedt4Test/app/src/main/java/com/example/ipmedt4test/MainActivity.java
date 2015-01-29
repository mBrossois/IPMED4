package com.example.ipmedt4test;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    String bNeus = "0", infotext, bKeel = "0", bOgen = "0", bSpray = "0", bPil = "0", bNiets =  "0", bAnders = "0", rate ="0", datum = "", opmerking = "";
    CheckBox neus;
    String pNaam = "", pGebDatum="", pEmail ="", pVrouw ="0", pMan="0";
    int neus1 = 0, ogen = 0, keel = 0, rating = 0;
    private GoogleMap mMap;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Add button listener

        //addListenerOnButtonClick();
        StrictMode.enableDefaults(); //STRICT MODE ENABLED
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            //mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    //.getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
               // setUpMap();
            }
        }
    }
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
    @Override
    public void onNavigationDrawerItemSelected(int position) {

    	Fragment objFragment = null;

    	switch (position){
    	case 0:
    		objFragment = new menu1_Fragment();
    		break;
    	case 1:
    		objFragment = new menu2_Fragment();
    		break;
    	case 2:
    		objFragment = new menu3_Fragment();
         //   setUpMapIfNeeded();
    		break;
    	case 3:
    		objFragment = new menu4_Fragment();
    		break;
    	case 4:
    		objFragment = new menu5_Fragment();
    		break;


    	}
//
//    	if (position == 2)
//        {
//            MapFragment nMapFragment = MapFragment.newInstance();
//            FragmentTransaction fragmentTransaction =
//                    getFragmentManager().beginTransaction();
//            fragmentTransaction.add(R.id.container, nMapFragment);
//            fragmentTransaction.commit();
//        }
//        else {
            // update the main content by replacing fragments
            onSectionAttached(position + 1);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, objFragment)
                    .commit();
//        }
    }
    public void goPollenkaart(View view)
    {
        Fragment objFragment = new menu3_Fragment();
       // setUpMapIfNeeded();
        switchFragment(objFragment);
        mTitle ="Pollenkaart";
        restoreActionBar();
    }
    public void setMedicijnenLayout()
    {
        if(bPil == "1")
        {
            CheckBox pil = (CheckBox) findViewById(R.id.checkBox4);
            pil.setChecked(true);
        }
        if(bSpray == "1")
        {
            CheckBox spray=(CheckBox)findViewById(R.id.checkBox);
            spray.setChecked(true);
        }
        if(bAnders == "1")
        {
            CheckBox anders=(CheckBox)findViewById(R.id.checkBox6);
            anders.setChecked(true);
        }
        if(bNiets == "1")
        {
            CheckBox niets = (CheckBox)findViewById(R.id.checkBox5);
            niets.setChecked(true);
        }
    }
    public void setKlachtenLayout()
    {
       if(bNeus == "1")
        {
            CheckBox neus = (CheckBox) findViewById(R.id.checkBox3);
            neus.setChecked(true);
       }
        if(bOgen == "1")
        {
            CheckBox ogen=(CheckBox)findViewById(R.id.checkBox2);
            ogen.setChecked(true);
        }
        if(bKeel == "1")
        {
            CheckBox keel=(CheckBox)findViewById(R.id.checkBox1);
            keel.setChecked(true);
        }
        if(rate != "0")
        {
            RatingBar gevoel = (RatingBar)findViewById(R.id.ratingBar);
            Float rating = Float.parseFloat(rate);
            rating /= 2;
            gevoel.setRating(rating);
        }
        EditText opm = (EditText) findViewById(R.id.editTekst);
        opm.setText(opmerking);

    }
    public void PersGegevensToDatabase()
    {
        String url = "http://149.210.186.51/setPersGegevens.php";
        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
        HttpPost httppost = new HttpPost(url);
        try {
            // Add your data
            List<NameValuePair> nameValuePairs2 = new ArrayList<NameValuePair>(4);
            nameValuePairs2.add(new BasicNameValuePair("Naam", pNaam));
            nameValuePairs2.add((new BasicNameValuePair("Geboortedatum", pGebDatum)));
            nameValuePairs2.add((new BasicNameValuePair("Email", pEmail)));
            nameValuePairs2.add((new BasicNameValuePair("Vrouw", pVrouw)));
            nameValuePairs2.add((new BasicNameValuePair("Man", pMan)));


            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs2));

            HttpResponse response = client.execute(httppost);



        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public void verzendenGegevens(View view)
    {
        /*Calendar c = Calendar.getInstance();
        int dag = c.get(Calendar.DATE);
        int maand = c.get(Calendar.MONTH);
        maand += 1;
        int jaar = c.get(Calendar.YEAR);
        datum = String.valueOf(dag + "-" + maand + "-" + jaar);*/
        EditText naam=(EditText)findViewById(R.id.Naam);
        EditText gebdatum=(EditText)findViewById(R.id.GebDatum);
        EditText email=(EditText)findViewById(R.id.Email);
        pNaam = naam.getText().toString();
        pGebDatum = gebdatum.getText().toString();
        pEmail = email.getText().toString();
        RadioButton vrouw=(RadioButton)findViewById(R.id.Vrouw);
        RadioButton man=(RadioButton)findViewById(R.id.Man);



        if(vrouw.isChecked())
        {
            pVrouw = "1";
        }
        else
        {
            pVrouw = "0";
        }
        if(man.isChecked())
        {
            pMan = "1";
        }
        else
        {
            pMan = "0";
        }

        PersGegevensToDatabase();

        Fragment objFragment = new menu1_Fragment();
        switchFragment(objFragment);
        mTitle = "Home";
        restoreActionBar();
        getData();
        // Toast.makeText(getApplicationContext(), datum, Toast.LENGTH_LONG).show();
    }

    public void goKlachtmelden (View view)
    {
        Fragment objFragment = new menu2_Fragment();
        switchFragment(objFragment);
        mTitle ="Klachten melden";
        restoreActionBar();
        //setKlachtenLayout();
    }
    public void klachtToDatabase()
    {
        String url = "http://149.210.186.51/setKlachten.php";
        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
        HttpPost httppost = new HttpPost(url);
        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
            nameValuePairs.add(new BasicNameValuePair("Id", "12132124"));
            nameValuePairs.add((new BasicNameValuePair("Neus", bNeus)));
            nameValuePairs.add((new BasicNameValuePair("Ogen", bOgen)));
            nameValuePairs.add(new BasicNameValuePair("Keel", bKeel));
            nameValuePairs.add(new BasicNameValuePair("Rating", rate));
            nameValuePairs.add(new BasicNameValuePair("Opmerkingen", opmerking));
            nameValuePairs.add(new BasicNameValuePair("Spray", bSpray));
            nameValuePairs.add((new BasicNameValuePair("Pil", bPil)));
            nameValuePairs.add((new BasicNameValuePair("Anders", bAnders)));
            nameValuePairs.add(new BasicNameValuePair("Niets", bNiets));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = client.execute(httppost);



        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public void sendMessage(View view){
        //Do something in response to the button


        neus=(CheckBox)findViewById(R.id.checkBox3);
        CheckBox ogen=(CheckBox)findViewById(R.id.checkBox2);
        CheckBox keel=(CheckBox)findViewById(R.id.checkBox1);
        RatingBar gevoel = (RatingBar)findViewById(R.id.ratingBar);

        StringBuilder result=new StringBuilder();
        result.append("Selected Items:");
        setRating();
        setMessage();
        if(keel.isChecked()){
            bKeel = "1";
        }
        else
        {
            bKeel = "0";
        }
        if(ogen.isChecked()){
            bOgen = "1";
        }
        else
        {
            bOgen = "0";
        }
        if(neus.isChecked()){
            bNeus = "1";
        }
        else
        {
            bNeus="0";
        }
        //Displaying the message on the toast
        //Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
        Fragment objFragment = new menu2B_Fragment();
        switchFragment(objFragment);
        //setContentView(R.layout.menu2b_layout);
        mTitle = "Medicijn melden";
        restoreActionBar();
        setMedicijnenLayout();
    }
    public void verzendenKlachten(View view)
    {
        CheckBox spray=(CheckBox)findViewById(R.id.checkBox);
        CheckBox pil=(CheckBox)findViewById(R.id.checkBox4);
        CheckBox anders=(CheckBox)findViewById(R.id.checkBox6);
        CheckBox niets = (CheckBox)findViewById(R.id.checkBox5);

        if(spray.isChecked())
        {
            bSpray = "1";
        }
        else
        {
            bSpray = "0";
        }
        if(pil.isChecked())
        {
            bPil = "1";
        }
        else
        {
            bPil = "0";
        }
        if(anders.isChecked())
        {
            bAnders = "1";
        }
        else
        {
            bAnders = "0";
        }
        if(niets.isChecked())
        {
            bNiets = "1";
        }
        else
        {
            bNiets = "0";
        }
        klachtToDatabase();
        Fragment objFragment = new menu1_Fragment();
        switchFragment(objFragment);
        mTitle = "Home";
        restoreActionBar();
        getData();
        Toast.makeText(getApplicationContext(), "Verzonden!", Toast.LENGTH_LONG).show();
        bNiets = "0";
        bAnders= "0";
        bPil = "0";
        bSpray = "0";
    }
    public void switchKlachten(View view)
    {
        CheckBox spray=(CheckBox)findViewById(R.id.checkBox);
        CheckBox pil=(CheckBox)findViewById(R.id.checkBox4);
        CheckBox anders=(CheckBox)findViewById(R.id.checkBox6);
        CheckBox niets = (CheckBox)findViewById(R.id.checkBox5);

        if(spray.isChecked())
        {
            bSpray = "1";
        }
        else
        {
            bSpray = "0";
        }
        if(pil.isChecked())
        {
            bPil = "1";
        }
        else
        {
            bPil = "0";
        }
        if(anders.isChecked())
        {
            bAnders = "1";
        }
        else
        {
            bAnders = "0";
        }
        if(niets.isChecked())
        {
            bNiets = "1";
        }
        else
        {
            bNiets = "0";
        }
        Fragment objFragment = new menu2_Fragment();
        switchFragment(objFragment);
       // setContentView(R.layout.menu2_layout);
       // View rootview = (View) findViewById(R.id.layout2);
        mTitle = "Klachten melden";
        restoreActionBar();
        setKlachtenLayout();
    }
    public void switchFragment(Fragment objFragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, objFragment)
                .commit();
        fragmentManager.executePendingTransactions();
    }
    public void setRating()
    {
        //Puts the value off the rating bar in a string
        RatingBar rating = (RatingBar) findViewById(R.id.ratingBar);
        String message = "";
        float theRating = rating.getRating();
        theRating *= 2;
        rate = Float.toString(theRating);

    }

    public void setMessage()
    {
        //Puts the message from the input field in the string
        EditText editText = (EditText) findViewById(R.id.editTekst);
        opmerking = editText.getText().toString();
    }


    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;


        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            getData();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
    public void getData(){
        String result = "";
        int teller = 0;
        InputStream isr = null;
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://149.210.186.51/getKlachten.php"); //YOUR PHP SCRIPT ADDRESS
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            isr = entity.getContent();
        }
        catch(Exception e){
            Log.e("log_tag", "Error in http connection " + e.toString());
            infotext =("Couldnt connect to database");
        }
        //convert response to string
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            isr.close();

            result=sb.toString();
        }
        catch(Exception e){
            Log.e("log_tag", "Error  converting result "+e.toString());
        }

        //parse json data
        try {
            String s = "";
            neus1 = 0;
            ogen = 0;
            keel = 0;
            JSONArray jArray = new JSONArray(result);
            Boolean ho = false;
            Calendar c = Calendar.getInstance();
            int dag = c.get(Calendar.DATE);
            int maand = c.get(Calendar.MONTH);
            maand += 1;
            int jaar = c.get(Calendar.YEAR);
            if(maand < 10) {
                datum = String.valueOf(jaar + "-0" + maand + "-" + dag);
            }
            else
            {
                datum = String.valueOf(jaar + "-" + maand + "-" + dag);
            }
            for(int i=0; i<jArray.length();i++) {
                JSONObject json = jArray.getJSONObject(i);
                ho = json.getString("Datum").equals(datum);
                if(json.getString("Datum").equals(datum) ) {
                    if (json.getString("Ogen").equals( "1")) {
                        ogen += 1;
                    }
                    if (json.getString("Neus").equals( "1")) {
                        neus1 += 1;
                    }
                    if (json.getString("Keel").equals( "1")) {
                        keel += 1;
                    }
                    rating += Integer.parseInt(json.getString("Rating"));
                    teller++;
                }

            }

            rating = rating / teller;
            String infoBegin = "";
            if(teller <= 5)
            {
                infoBegin = "Er zijn weinig pollen";
            }
            else if(teller > 10 && teller <=20)
            {
                infoBegin = "Er zijn midel matig veel ";
            }
            else
            {
                infoBegin = "Er zijn veel Pollen";
            }

            if (keel > neus1 && keel > ogen)
            {
                infotext =("last van hun keel");
            }
            else if(neus1 > keel && neus1 > ogen)
            {
                infotext = "last van hun neus";
            }
            else if(ogen > keel && ogen > neus1)
            {
                infotext = "last van hun ogen.";
            }
            else if(ogen == keel && ogen == neus1)
            {
                infotext = "overal even veel last van";
            }
            else if(ogen == neus1)
            {
                infotext = "last van hun ogen en neus";
            }
            else if(ogen == keel)
            {
                infotext = "last van hun ogen en keel";
            }
            else if(keel == neus1)
            {
                infotext = "last van hun keel en neus";
            }

            TextView hometext = (TextView)findViewById(R.id.textSwag);
            hometext.setText(infoBegin + " in de lucht."+ System.getProperty("line.separator")+"Mensen hebben " + infotext + ".");


        } catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag", "Error Parsing Data "+e.toString());
        }

    }
}
