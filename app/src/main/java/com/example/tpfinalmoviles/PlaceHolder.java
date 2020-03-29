package com.example.tpfinalmoviles;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PlaceHolder {

    @GET("cow/{id}")
    Call<Vaca> getCowID(@Path("id") int idVaca);

    @POST("cow")
    Call<Vaca> agregarVaca(@Body Vaca vaca);

    @POST("cowAlert")
    Call<VacaAlerta> agregarVacaAlerta(@Body VacaAlerta vacaAlerta);
}
