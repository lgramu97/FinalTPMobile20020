package com.example.tpfinalmoviles.io;

import com.example.tpfinalmoviles.io.Response.Rodeo;
import com.example.tpfinalmoviles.io.Response.RodeoAlerta;
import com.example.tpfinalmoviles.io.Response.RodeoAlertaFired;
import com.example.tpfinalmoviles.io.Response.Sesion;
import com.example.tpfinalmoviles.io.Response.Vaca;
import com.example.tpfinalmoviles.io.Response.VacaAlerta;
import com.example.tpfinalmoviles.io.Response.VacaAlertaFired;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CowApiService {


    @POST("herd")
    Call<Rodeo> agregarRodeo(@Body Rodeo rodeo);

    @GET("herd/{id}")
    Call<Rodeo> getRodeo(@Path("id") int idRodeo);

    @POST("herdAlert")
    Call<RodeoAlerta> agregarRodeoAlerta(@Body RodeoAlerta rodeoAlerta);

    @GET("cow/{id}")
    Call<Vaca> getCowID(@Path("id") int idVaca);

    @POST("cow")
    Call<Vaca> agregarVaca(@Body Vaca vaca);

    @POST("cowAlert")
    Call<VacaAlerta> agregarVacaAlerta(@Body VacaAlerta vacaAlerta);

    @POST("session")
    Call<Sesion> generarSesion(@Body Sesion sesion);

    @GET("herdFiredAlert")
    Call<List<RodeoAlertaFired>> getHerdFiredAlert();

    @GET("cowFiredAlert")
    Call<List<VacaAlertaFired>> getCowFiredAlert();
}
