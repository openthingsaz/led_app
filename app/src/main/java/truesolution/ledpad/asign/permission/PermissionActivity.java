package truesolution.ledpad.asign.permission;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import truesolution.ledpad.asign.IntroActivity;
import truesolution.ledpad.asign.MDEBUG;
import truesolution.ledpad.asign.R;

import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * The type Permission activity.
 */
@SuppressLint("NewApi")
public class PermissionActivity extends AppCompatActivity {
	private int REQUEST_PERMISSION_CODE = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_permission);
		
		MDEBUG.debug("onCreate!");
		requestPermission();
	}
	
	/**
	 * Request permission.
	 */
	public void requestPermission() {
		int bluetoothPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH);
		int bluetoothAdminPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN);
		int readExternalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
		int writeExternalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
		int accessFineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
		int accessCoarseLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
		int wifiChangePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE);
		int wifiAccessPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE);
		
		if(bluetoothPermission == PackageManager.PERMISSION_GRANTED
				&& bluetoothAdminPermission == PackageManager.PERMISSION_GRANTED
				&& readExternalStoragePermission == PackageManager.PERMISSION_GRANTED
				&& writeExternalStoragePermission == PackageManager.PERMISSION_GRANTED
				&& accessFineLocationPermission == PackageManager.PERMISSION_GRANTED
				&& accessCoarseLocationPermission == PackageManager.PERMISSION_GRANTED
				&& wifiChangePermission == PackageManager.PERMISSION_GRANTED
				&& wifiAccessPermission == PackageManager.PERMISSION_GRANTED
		) { // 모두 동의함
			startLoginActivity();
		} else {
			String[] _permissions = new String[]{
					Manifest.permission.BLUETOOTH,
					Manifest.permission.BLUETOOTH_ADMIN,
					Manifest.permission.READ_EXTERNAL_STORAGE,
					Manifest.permission.WRITE_EXTERNAL_STORAGE,
					Manifest.permission.ACCESS_FINE_LOCATION,
					Manifest.permission.ACCESS_COARSE_LOCATION,
					Manifest.permission.CHANGE_WIFI_STATE,
					Manifest.permission.ACCESS_WIFI_STATE
			};
			
			ActivityCompat.requestPermissions(this, _permissions, REQUEST_PERMISSION_CODE);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		if(grantResults.length == 0) {
			requestPermission();
		} else {
			if(requestCode == REQUEST_PERMISSION_CODE) {
				boolean isGranted = true;

				for(int i = 0; i < grantResults.length; i++) {
					MDEBUG.debug("permissions[" + i + "] : " + permissions[i] + ", grantResults[" + i + "] : " + grantResults[i]);
					if(grantResults[i] == PackageManager.PERMISSION_GRANTED) { //동의
					} else { //거부
						isGranted = false;
						break;
					}
				} //for

				if(isGranted) {
					startLoginActivity();
				} else {
					Toast.makeText(getApplicationContext(), "[설정] > [권한] 에서 권한을 허용해야 사용할 수 있습니다.", Toast.LENGTH_SHORT).show();

					new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
						public void run() {
							finishAffinity();
							System.runFinalizersOnExit(true);
							System.exit(0);
						}
					}, 1000);
				}
			}
		}
	}
	
	/**
	 * Start login activity.
	 */
	public void startLoginActivity() {
		Intent intent = new Intent(PermissionActivity.this, IntroActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
		startActivity(intent);
		finish();
	}
}