
package pl.suzanowicz.android.gargoyleadmin;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {

    private static final int SERVER_PORT = 8080;

    private static final String DEB_TAG = "Json_Android";

    private String SERVER_HOST = "home.suzanowicz.pl";

    public static final String PREFS_NAME = "Gargoyle";

    private SharedPreferences mSettings;

    private ProgressDialog mProgress;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);

        // Restore preferences
        mSettings = getSharedPreferences(PREFS_NAME, 0);
        setContentView(R.layout.main);

        Button login = (Button) findViewById(R.id.login_button);

        Log.i(DEB_TAG, "onCreate");

        login.setOnClickListener(this);

        setUserNameText(mSettings.getString("Login", ""));

        setPasswordText(mSettings.getString("Password", ""));
    }

    public void setUserNameText(String $username) {
        EditText usernameEditText = (EditText) findViewById(R.id.txt_username);
        usernameEditText.setText($username);
    }

    public void setPasswordText(String $username) {
        EditText passwordEditText = (EditText) findViewById(R.id.txt_password);
        passwordEditText.setText($username);

    }

    /*
     * (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */

    public void onClick(View v) {

        // Handle based on which view was clicked.

        Log.i(DEB_TAG + " onClick ", "onClick");

        // this gets the resources in the xml file

        // and assigns it to a local variable of type EditText

        EditText usernameEditText = (EditText) findViewById(R.id.txt_username);

        EditText passwordEditText = (EditText) findViewById(R.id.txt_password);

        // the getText() gets the current value of the text box

        // the toString() converts the value to String data type

        // then assigns it to a variable of type String

        String sUserName = usernameEditText.getText().toString();

        String sPassword = passwordEditText.getText().toString();

        // call the backend using Get parameters (discouraged but works good for
        // this exampl <img
        // src="http://www.instropy.com/wp-includes/images/smilies/icon_wink.gif"
        // alt=";)" class="wp-smiley"> )

        String address = "http://" + SERVER_HOST + ":" + SERVER_PORT
                + "/jbackend.php?action=login&Login=" + sUserName + "&Password=" + sPassword + "";

        if (usernameEditText == null || passwordEditText == null) {
            // show some warning
        } else {
            // display the username and the password in string format
            showBusyCursor(true);

            mProgress = ProgressDialog.show(this,

            "Please wait...", "Login in process", true);

            Log.i(DEB_TAG, "Username: " + sUserName + "nPassword: " + sPassword);

            Log.i(DEB_TAG, "Requesting to " + address);

            JSONObject json = RestJsonClient.connect(address);

            mProgress.dismiss();

            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString("Login", sUserName);
            editor.putString("Password", sPassword);
            editor.commit();
            showBusyCursor(false);

            next();

        }// end else

    }// end OnClick

    /*

        *

        */

    private void showBusyCursor(Boolean $show) {
        setProgressBarIndeterminateVisibility($show);
    }

    private void next() {
        // you can call another activity by uncommenting the above lines
        // Intent myIntent = new Intent( this.getBaseContext() ,
        // LoggedActivity.class);
        // startActivityForResult(myIntent, 0);
    }

}
