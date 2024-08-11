package com.example.dnschanger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.ContentViewCallback;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static TextView dns1Text;
    TextView dns2Text;
    Spinner dnsSpinner;

    private NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();
    DnsServer dnsServer = new DnsServer(this);
    List<String> dnsServerList ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       dns1Text = findViewById(R.id.dns1TextLine);
       dns2Text = findViewById(R.id.dns2TextLine);
       dnsSpinner = findViewById(R.id.snipper);

       getCurrentDnsServers();


        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver,filter);

    }


    public void getCurrentDnsServers(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager != null) {
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            builder.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
            NetworkRequest networkRequest = builder.build();

            connectivityManager.registerNetworkCallback(networkRequest, new ConnectivityManager.NetworkCallback(){

                @Override
                public void onAvailable(@NonNull Network network) {
                    super.onAvailable(network);
                    LinkProperties linkProperties = connectivityManager.getLinkProperties(network);
                    if(linkProperties != null){
                        List<String> dnsServers = new ArrayList<>();
                        for(java.net.InetAddress dns : linkProperties.getDnsServers()){
                               dnsServers.add(dns.getHostAddress());
                        }


                        String dns1 = dnsServers.size() > 0 ? dnsServers.get(0) : "N/A";
                        String dns2 = dnsServers.size() > 1 ? dnsServers.get(1) : "Bulunamadı";

                        runOnUiThread(() -> {
                            dnsServer.setDnsMap("Mevcut DNS", dns1, dns2);
                            dnsServerList = new ArrayList<>(dnsServer.getDnsList().keySet());
                            setDnsSpinnerAdapter();

                            dns1Text.setText(dns1);
                            dns2Text.setText(dns2);
                        });



                    }
                }
            });
        }
    }

    public void setDnsSpinnerAdapter() {

        dnsServerList = new ArrayList<>(dnsServer.getDnsList().keySet());


        Collections.reverse(dnsServerList);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dnsServerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dnsSpinner.setAdapter(adapter);

        dnsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedDNS = dnsServerList.get(position);

                String[] dnsValues = dnsServer.getDnsList().get(selectedDNS);

                if (dnsValues != null && dnsValues.length >= 2) {
                    dns1Text.setText(dnsValues[0]);
                    dns2Text.setText(dnsValues[1]);
                } else {
                    dns1Text.setText("N/A");
                    dns2Text.setText("N/A");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dns1Text.setText("");
                dns2Text.setText("");
            }
        });
    }


    public void changeDns(View v) {
        Process process = null;
        DataOutputStream os = null;

        try {
            if (!isRooted()) {
                Toast.makeText(this, "Root izni gerekli.", Toast.LENGTH_SHORT).show();
                return;
            }

            String dns1 = dns1Text.getText().toString().trim();
            String dns2 = dns2Text.getText().toString().trim();

            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());


            os.writeBytes("setprop net.dns1 " + dns1 + "\n");
            os.writeBytes("setprop net.dns2 " + dns2 + "\n");
            os.writeBytes("exit\n");

            os.flush();
            process.waitFor();


            InputStream errorStream = process.getErrorStream();
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
            String line;
            while ((line = errorReader.readLine()) != null) {
                Log.e("DNSChange", line);
            }

            Toast.makeText(this, "DNS Başarıyla Değiştirildi.", Toast.LENGTH_SHORT).show();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(this, "DNS değiştirilemedi: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isRooted() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");
            process.getOutputStream().close();
            process.destroy();
            return true;
        } catch (IOException e) {
            return false;
        }
    }







}