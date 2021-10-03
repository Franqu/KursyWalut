package com.example.kursw.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kursw.R;
import com.example.kursw.databinding.KursFragmentBinding;
import com.example.kursw.utility.FlagGraphicProvider;
import com.example.kursw.model.Kurs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class KursFragment extends Fragment {

    private KursFragmentBinding binding;
    private KursAdapter adapter;
    private ArrayList<Kurs> dataSet = null;
    private boolean isBuy = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataset();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = KursFragmentBinding.inflate(inflater, container, false);
        adapter = new KursAdapter(dataSet,isBuy);
        binding.rvKurs.setAdapter(adapter);
        binding.rvKurs.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateValueLabel(isBuy);
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isBuy = !isBuy;
                adapter = new KursAdapter(dataSet,isBuy);
                binding.rvKurs.setAdapter(adapter);
                updateValueLabel(isBuy);

            }
        });
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initDataset() {
        dataSet = new ArrayList<>();
            OkHttpClient client = new OkHttpClient();
            try {
                String url = "https://api.nbp.pl/api/exchangerates/tables/c/";
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                TGetKursCallBack getKursCallBack = new TGetKursCallBack(dataSet);
                client.newCall(request).enqueue(getKursCallBack);
                while (!getKursCallBack.isOdczytZakonczony) {
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                Log.println(Log.ERROR, "okHttp", "Error during getting data from API");
            }
        }

    private class TGetKursCallBack implements Callback {

        private volatile boolean isOdczytZakonczony = false;
        private volatile boolean isSendSuccesful = false;
        private ArrayList<Kurs> dataSet = new ArrayList<>();

        public TGetKursCallBack(ArrayList<Kurs> dataSet) {
            this.dataSet = dataSet;
        }
        @Override
        public void onFailure(Call call, IOException e) {
            isOdczytZakonczony = true;
            isSendSuccesful = false;
            Log.println(Log.ERROR, "okHttp", "Error during getting data from API: " + e.toString());
        }

        @Override
        public void onResponse(Call call, Response response) {
            InputStream is = response.body().byteStream();
            InputStreamReader isr = null;
            try {
                isr = new InputStreamReader(is, "utf-8");
                BufferedReader br = new BufferedReader(isr);

                int b;
                StringBuilder buf = new StringBuilder(999999);
                while ((b = br.read()) != -1) {
                    buf.append((char) b);
                }
                br.close();
                JSONArray jsonArray = new JSONArray(buf.toString());
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONArray ratesArray = jsonArray.getJSONObject(i).getJSONArray("rates");
                    for (int j = 0; j < ratesArray.length(); j++){
                        JSONObject json = ratesArray.getJSONObject(j);
                        String name = json.getString("currency");
                        String symbol = json.getString("code");
                        double valueBuy = json.getDouble("bid");
                        double valueSell = json.getDouble("ask");
                        Kurs kurs = new Kurs(name,symbol,valueSell,valueBuy, FlagGraphicProvider.drawableWithFlag(getContext(),symbol));
                        dataSet.add(kurs);
                    }


                }

            } catch (Throwable e) {
                Log.println(Log.ERROR, "okHttp", "Error during getting data from API: " + e.toString() );
            }
            isOdczytZakonczony = true;
            isSendSuccesful = true;
        }
    }


    private void updateValueLabel(boolean isBuy) {
        if (isBuy)
            binding.tvValue.setText(R.string.kurs_value_buy);
        else
            binding.tvValue.setText(R.string.kurs_value_sell);
    }

}