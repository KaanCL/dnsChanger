package com.example.dnschanger;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class DnsServer {

    String dns1;
    String dns2;

    private HashMap<String, String[]> dnsMap = new HashMap<>();
    private SharedPreferences sharedPreferences;



    public DnsServer(Context context) {
      // sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        dnsMap.put("Google DNS", new String[]{"8.8.8.8", "8.8.4.4"});
        dnsMap.put("Cloudflare DNS", new String[]{"1.1.1.1", "1.0.0.1"});
        dnsMap.put("OpenDNS", new String[]{"208.67.222.222", "208.67.220.220"});

        //loadSavedDns();
    }

    private void loadSavedDns(){
        String savedDns1 = sharedPreferences.getString("dns1",null);
        String savedDns2 = sharedPreferences.getString("dns2", null);

        if (savedDns1 != null && savedDns2 != null) {
            dnsMap.put("Saved DNS", new String[]{savedDns1, savedDns2});
        }


    }
    public HashMap<String, String[]> getDnsList() {
        return dnsMap;
    }

    public void saveDns(String dns1, String dns2){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("saved_dns1",dns1);
        editor.putString("saved_dns2",dns2);

        dnsMap.put("Saved DNS",new String[]{dns1,dns2});

    }




}
