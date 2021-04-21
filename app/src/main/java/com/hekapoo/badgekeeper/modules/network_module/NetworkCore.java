package com.hekapoo.badgekeeper.modules.network_module;


import android.content.Context;

import androidx.lifecycle.LifecycleOwner;

/*
 * Main class handling incoming/outgoing network related requests.
 */
public class NetworkCore {
    private static NetworkCore instance = null;
    private static Boolean isConnected = false;

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
    public void hasInternetListenerRegister(LifecycleOwner owner, Context ctx){
        NetworkConnection networkConnection = new NetworkConnection(ctx.getApplicationContext());
        networkConnection.observe(owner, status->{
            isConnected = status;
        });
    }

    //Method used to continuously check if the the place in which the method is called has internet access (callback)
    public void hasInternetCallback(LifecycleOwner owner, Context ctx,connectionStatusInterface status){
        NetworkConnection networkConnection = new NetworkConnection(ctx.getApplicationContext());
        networkConnection.observe(owner, status::connectionStatus);
    }

    public boolean isConnected(){return isConnected;}

}
