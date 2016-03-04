package com.example.daniel_li.prog2b;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by joleary and noon on 2/19/16 at very late in the night. (early in the morning?)
 */
//extends service
public class WatchToPhoneService implements GoogleApiClient.ConnectionCallbacks {

    private static GoogleApiClient mWatchApiClient;
    private static String path;
    private static String text;

    public static void sendMessage(final String path, final String text, Context context ) {
        WatchToPhoneService.path = path;
        WatchToPhoneService.text = text;
        mWatchApiClient = new GoogleApiClient.Builder(context)
                .addApi( Wearable.API )
                .addConnectionCallbacks(new WatchToPhoneService())
                .build();
        //and actually connect it
        mWatchApiClient.connect();
    }


    @Override //alternate method to connecting: no longer create this in a new thread, but as a callback
    public void onConnected(Bundle bundle) {
        Log.d("T", "in onconnected");
        Wearable.NodeApi.getConnectedNodes(mWatchApiClient)
                .setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
                    @Override
                    public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
                        List<Node> nodes = getConnectedNodesResult.getNodes();
                        Log.d("Potato", "found nodes");
                        //when we find a connected node, we populate the list declared above
                        //finally, we can send a message
                        for (Node node : nodes) {
                            Wearable.MessageApi.sendMessage(
                                    mWatchApiClient, node.getId(), path, text.getBytes());
                        }
                        Log.d("Potato", "sent");
                        mWatchApiClient.disconnect();
                    }
                });
    }


    @Override //we need this to implement GoogleApiClient.ConnectionsCallback
    public void onConnectionSuspended(int i) {}

}
