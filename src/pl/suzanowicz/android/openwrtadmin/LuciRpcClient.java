package pl.suzanowicz.android.openwrtadmin;

import org.alexd.jsonrpc.JSONRPCClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
    
public class LuciRpcClient {
    private String mRouterAddress;
    private String mAuthToken;
    public void connect(String routerAddress, String user, String pass)
    {
        mRouterAddress = routerAddress;
        try
        {
            JSONRPCClient client = JSONRPCClient.create( "http://" + routerAddress + "/cgi-bin/luci/rpc/auth");
            client.setConnectionTimeout( 5000 );
            client.setSoTimeout( 5000 );
            mAuthToken = client.callString("login", user, pass );
            
        } catch (Exception e)
        {
            Log.wtf("garg", e);
        }
    }
    
    public String Sys(String command)
    {
            try
            {
                    JSONRPCClient client = JSONRPCClient.create( "http://" + mRouterAddress + "/cgi-bin/luci/rpc/sys?auth="+mAuthToken);
                    client.setConnectionTimeout( 2000 );
                    client.setSoTimeout( 2000 );
                    String returnValue = client.callString(command);
                    Log.v("returnValue", returnValue );
                    return returnValue;
            }catch( Exception e )
            {
                Log.wtf("garg", e);
            }
            return "Failed";
    }
    
    public String Net(String command)
    {
            try
            {
                    JSONRPCClient client = JSONRPCClient.create( "http://" + mRouterAddress + "/cgi-bin/luci/rpc/sys?auth="+mAuthToken);
                    client.setConnectionTimeout( 2000 );
                    client.setSoTimeout( 2000 );
                    //JSONArray returnValue = client.callJSONArray(command,"3g-wan");
                    JSONObject returnValue = client.callJSONObject(command);
                    String string = "";
                    JSONArray items = returnValue.names();
                    for (int i = 0; i < items.length(); i++) {
                        try {
                            JSONArray item = items.getJSONArray(i);
                            //string = string + item.getString("number") + ". " + item.getString("title") + " - " + item.getString("datetime") + "\n\n";
                        } catch (JSONException e) {
                            Log.i("JSON-RPC Client", e.toString());
                        }
                    }                   
                    Log.v("returnValue", string );
                    return string;
            }catch( Exception e )
            {
                Log.wtf("garg", e);
            }
            return "Failed";
    }     
}
