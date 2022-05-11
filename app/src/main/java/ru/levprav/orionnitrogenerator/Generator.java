package ru.levprav.orionnitrogenerator;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.levprav.orionnitrogenerator.retrofit.RequestsMethods;
import ru.levprav.orionnitrogenerator.retrofit.models.CodeRequestModel;

public class Generator {
    private final String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static RequestsMethods requestsMethods;
    private static Retrofit retrofit;
    SharedPreferences sPref;
    Activity activity;
    private final MutableLiveData<ArrayList<CodeRequestModel>> array;

    public Generator(Activity activity, MutableLiveData<ArrayList<CodeRequestModel>> array){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://discordapp.com/api/v9/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        requestsMethods = retrofit.create(RequestsMethods.class);
        this.activity = activity;
        this.array = array;
    }

    public ArrayList<CodeRequestModel> startGeneration() throws IOException {
        String code = getCode();
        String token = loadToken(); // will be used to http headers
        Response response = Generator.getApi().checkCode(code).execute();
        Log.d("eee", response.toString());
        ArrayList<CodeRequestModel> arr = array.getValue();
        if(response.isSuccessful()){
            CodeRequestModel model = new CodeRequestModel();
            model.setRequestCode(code);
            model.setMessage("VALID");
            model.setCode("valid");
            arr.add(model);

        }else{

            CodeRequestModel model = new CodeRequestModel();
            model.setRequestCode(code);
            model.setMessage("Invalid");
            model.setCode("invalid");
            arr.add(model);
        }
        return arr;
    }



    private String getCode() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 16; i++) {
            builder.append(letters.charAt(ThreadLocalRandom.current().nextInt(letters.length())));
        }

        return builder.toString();
    }

    public static RequestsMethods getApi() {
        return requestsMethods;
    }

    private String loadToken() {
        sPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sPref.getString("token", "");
    }
}
