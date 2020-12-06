package com.example.team_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity4 extends AppCompatActivity {
    dbHelper mydbhelper;
    SQLiteDatabase mysqlDB;

    EditText editaddress,edit;
    Button btn1, btn2, btn3;
    public class dbHelper extends SQLiteOpenHelper{
        public dbHelper(Context context){
            super(context, "address", null, 1);   //telDB -> 데이터베이스 이름
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL("CREATE TABLE contacts(Address CHAR(20) PRIMARY KEY)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {       // 테이블 초기화
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }

    }

    private static final String ERROR_MSG="Google play services are unavailable.";
    private TextView mTextView;
    private static final int LOCATION_PERMISSION_REQUEST=1;
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        // 데이터베이스
        setTitle("주소지");

        btn1=(Button) findViewById(R.id.button13);
        btn2=(Button) findViewById(R.id.button14);
        btn3=(Button) findViewById(R.id.button15);
        editaddress=(EditText)findViewById(R.id.editAddress);
        edit=(EditText)findViewById(R.id.editText1);

        mydbhelper=new dbHelper(this);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mysqlDB=mydbhelper.getWritableDatabase();       // 내용을 쓸 수 있는 데이터베이스
                mydbhelper.onUpgrade(mysqlDB,1,2);
                mysqlDB.close();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mysqlDB=mydbhelper.getWritableDatabase();
                mysqlDB.execSQL("INSERT INTO contacts VALUES('"+edit.getText().toString() +"');'");
                mysqlDB.close();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mysqlDB=mydbhelper.getReadableDatabase();
                Cursor cursor;  // rawQuery에서 나온 결과를 다 가지고 있는 변수
                cursor=mysqlDB.rawQuery("SELECT * FROM contacts;", null);
                String Addr="주소: \r\n";
                while(cursor.moveToNext()){
                    Addr+=cursor.getString(0)+"\r\n";
                }
                editaddress.setText(Addr);
                cursor.close();
                mysqlDB.close();
            }
        });


        // 텍스트뷰의 참조를 얻는다.
        mTextView=findViewById(R.id.myLocationText);

        // 현재 장치에서 구글 플레이 서비스를 사용할 수 있는지 확인한다.
        GoogleApiAvailability availability=GoogleApiAvailability.getInstance();
        int result=availability.isGooglePlayServicesAvailable(this);

        if(result!= ConnectionResult.SUCCESS){
            if (!availability.isUserResolvableError(result)) {
                Toast.makeText(this,ERROR_MSG,Toast.LENGTH_LONG).show();
            }
        }

        // 높은 정확도를 우선으로 하고 5초 간격으로 위치를 업데이트하도록 요청하는 데 사용됨
        mLocationRequest=new LocationRequest().setInterval(5000).setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);   // 새 위치 요청 객체



        Button btn=(Button)findViewById(R.id.button12);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }

    // 새 위치를 업데이트 받을 때마다 updateTextView 메소드를 호출하여 텍스트 뷰를 변경하는 새 LocationCallBack을 추가
    LocationCallback mLocationCallback=new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult){
            Location location=locationResult.getLastLocation();
            if (location!=null){
                updateTextView(location);
            }
        }
    };

    // 위치 요청(52번째 줄)과 위치 콜백(onLocationResult)을 사용해 위치 업데이트를 받는 요청을 시작하는 메소드
    private void requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED) {
            FusedLocationProviderClient fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this);

            fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback,null);
        }
    }


    // 시스템의 위치 설정과 위치 요청의 요건을 비교. 즉 위치 요청이 가능하도록 시스템 위치 설정이 되어 있다면  requestLocationUpdates메소드를 호출하도록 함
    public static final String TAG="MainActivity4";
    private static final int REQUEST_CHECK_SETTINGS =2;

    @Override
    protected void onStart(){
        super.onStart();
        // fine정확도에 접근할 권한이 있는지 확인한다.
        int permission= ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        // 권한이 승인되면 마지막 위치를 불러온다.
        if(permission==PERMISSION_GRANTED){
            getApplication();
            getLastLocation();
        }else{
            // 권한이 승인된 적이 없다면 요청한다.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST);
        }
        // 위치 설정이 위치 요청과 호환되는지 확인한다.
        LocationSettingsRequest.Builder builder=new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);

        SettingsClient client=LocationServices.getSettingsClient(this);

        Task<LocationSettingsResponse> task=client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // 위치 설정이 위치 요건의 요청을 충족하면 위치 업데이트를 요청한다.
                requestLocationUpdates();
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Exception에서 실패의 상태 코드를 추출한다.
                int statusCode=((ApiException)e).getStatusCode();
                switch (statusCode){
                    case CommonStatusCodes
                            .RESOLUTION_REQUIRED:
                        try{
                            // 위치 설정 문제를 해결할 수 있는 대화상자를 사용자에게 보여준다.
                            ResolvableApiException resolvable=(ResolvableApiException)e;
                            resolvable.startResolutionForResult(MainActivity4.this, REQUEST_CHECK_SETTINGS);
                        }catch (IntentSender.SendIntentException sendEx){
                            Log.e(TAG, "Location Settings resolution failed.", sendEx);
                        }
                        break;
                    case LocationSettingsStatusCodes
                            .SETTINGS_CHANGE_UNAVAILABLE:
                        // 위치 설정 문제를 사용자가 해결할 수 없다. 위치 업데이트를 요 청한다.
                        Log.d(TAG, "Location Settings can't be resolved.");
                        requestLocationUpdates();
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int ResultCode, Intent data) {
        super.onActivityResult(requestCode, ResultCode, data);      // 책에는 없는데 이거 안적으면 오류가 뜨네
        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);

        if (requestCode == REQUEST_CHECK_SETTINGS) {
            switch (requestCode) {
                case Activity.RESULT_OK:
                    // 요청된 변경이 적용됐다. 위치 업데이트를 요청한다.
                    requestLocationUpdates();
                    break;
                case Activity.RESULT_CANCELED:
                    // 요청된 변경이 적용되지 않았다.
                    Log.d(TAG, "Requested settings changes declined by user.");
                    // 사용할 수 있는 위치 서비스가 있는지 확인하고 있다면 위치 업데이트를 요청한다.
                    if (states.isLocationUsable())
                        requestLocationUpdates();
                    else
                        Log.d(TAG, "No location services available.");
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==LOCATION_PERMISSION_REQUEST){
            if(grantResults[0]!=PERMISSION_GRANTED){
                Toast.makeText(this, "Location Permission Denied", Toast.LENGTH_LONG).show();
            }else{
                getLastLocation();
            }
        }
    }

    // 위치를 받아 문자열을 반환하는 메소드
    private String geocodeLocation(Location location){
        String returnString="No";

        if(location==null){
            Log.d(TAG, "No Location to GeoCode");
            returnString="No Location to GeoCode";

            return returnString;
        }

        if(!Geocoder.isPresent()){
            Log.e(TAG,"No Geocoder Available");
            returnString="No Geocoder Available";
            return returnString;
        }
        else{
            Geocoder gc=new Geocoder(this, Locale.getDefault());
            try{
                List<Address> addresses=gc.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                StringBuilder sb=new StringBuilder();

                if(addresses.size() > 0) {
                    Address address = addresses.get(0);
                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
                        sb.append(address.getAddressLine(i)).append("\n");

                    sb.append(address.getCountryName()).append(", ");   // 나라 코드
                    sb.append(address.getAdminArea()).append(", ");  // 시
                    sb.append(address.getSubLocality()).append(", ");  // 구
                    sb.append(address.getSubThoroughfare()).append(", ");  // 번지
                }
                returnString=sb.toString();
            }catch (IOException e){
                Log.e(TAG, "I/O Error Geocoding.", e);
                returnString="I/O Error Geocoding";
            }

            return returnString;
        }
    }

    private void getLastLocation(){
        FusedLocationProviderClient fusedLocationProviderClient;
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);

        Boolean canAccessFineLocation=ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)==PERMISSION_GRANTED;

        Boolean canAccessCoarseLocation=ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION)==PERMISSION_GRANTED;

        if(canAccessCoarseLocation||canAccessFineLocation){
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    updateTextView(location);
                }
            });
        }
    }
    private void updateTextView(Location location){
        String latLongString ="No location found";

        if (location !=null){
            double lat=location.getLatitude();
            double lng=location.getLongitude();
            latLongString="lat:"+lat+"\nlong:"+lng;
        }

        String address=geocodeLocation(location);

        String outputText="당신의 현 위치는\n";
        if (!address.isEmpty()){
            outputText+=address;
        }
        outputText+="\n입니다.";

        mTextView.setText(outputText);
    }
}