package com.hekapoo.badgekeeper.modules.network_module;


import android.content.Context;

import androidx.lifecycle.LifecycleOwner;

/*
 * Main class handling incoming/outgoing network related requests.
 */
public class NetworkCore {
    private static NetworkCore instance = null;

    private NetworkCore() { }

    public static NetworkCore getInstance()
    {
        if (instance == null)
            instance = new NetworkCore();

        return instance;
    }

    public interface connectionStatusInterface{
        void connectionStatus(Boolean status);
    }

    //Method used to continuously check if the the place in which the method is called has internet access
    public void hasInternet(LifecycleOwner owner, Context ctx, connectionStatusInterface status){
        NetworkConnection networkConnection = new NetworkConnection(ctx.getApplicationContext());
        networkConnection.observe(owner, status::connectionStatus);
    }


}
