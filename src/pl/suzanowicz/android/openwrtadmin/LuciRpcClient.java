package pl.suzanowicz.android.openwrtadmin;

import org.alexd.jsonrpc.JSONRPCClient;

import android.util.Log;

public class LuciRpcClient {
    public String Auth()
    {

            try
            {
                    JSONRPCClient client = JSONRPCClient.create( "http://" + MainActivity.server() + "/cgi-bin/luci/rpc/auth");
                    client.setConnectionTimeout( 5000 );
                    client.setSoTimeout( 5000 );
                    String token = client.callString("login", MainActivity.user(), MainActivity.pass() );
                    Log.v(token, token );
                    return token;
            }catch( Exception e )
            {
                    Log.wtf("garg", e);
            }
            return "Fail";
    }
}
