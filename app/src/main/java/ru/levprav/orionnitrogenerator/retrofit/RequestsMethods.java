package ru.levprav.orionnitrogenerator.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.levprav.orionnitrogenerator.retrofit.models.CodeRequestModel;

public interface RequestsMethods {
    @GET("entitlements/gift-codes/{code}?with_application=false&with_subscription_plan=true")
    Call<CodeRequestModel> checkCode(@Path(value = "code", encoded = true) String code);
}
