package org.fhi360.ddd.webservice;


import org.fhi360.ddd.domain.ARV;
import org.fhi360.ddd.domain.Drug;
import org.fhi360.ddd.domain.IssuedDrug;
import org.fhi360.ddd.dto.Data;
import org.fhi360.ddd.dto.PatientDto;
import org.fhi360.ddd.dto.PharmacyDto;
import org.fhi360.ddd.dto.Response;
import org.fhi360.ddd.domain.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface ClientAPI {

    @Headers("Content-Type: application/json")
    @POST("api/ddd/mobile/pharmacy")
    Call<org.fhi360.ddd.dto.Response> saveAccount(@Body PharmacyDto pharmacy);

    @Headers("Content-Type: application/json")
    @POST("api/ddd/mobile/sync/patient")
    Call<Response> syncPatient(@Body List<PatientDto> patientDto);

    @Headers("Content-Type: application/json")
    @POST("api/ddd/mobile/save/patient")
    Call<Response> savePatient(@Body PatientDto patient);

    @Headers("Content-Type: application/json")
    @POST("api/ddd/mobile/save/drug")
    Call<Response> saveDrug(@Body Drug drug);

    @Headers("Content-Type: application/json")
    @POST("api/ddd/mobile/save/inventory")
    Call<Response> saveInventory(@Body IssuedDrug issuedDrug);

    @Headers("Content-Type: application/json")
    @POST("api/ddd/mobile/update/patient")
    Call<Response> updatePatient(@Body PatientDto patient);

    @Headers("Content-Type: application/json")
    @POST("api/ddd/mobile/save/arv")
    Call<Response> saveARVRefill(@Body ARV arvs);

    @Headers("Content-Type: application/json")
    @POST("api/ddd/mobile/sync/arv")
    Call<Response> syncARVRefill(@Body List<ARV> arvDtos);

    @Headers("Content-Type: application/json")
    @GET("api/ddd/mobile/pharmacy/{pin}")
    Call<Data> activatePharmacy(@Path("pin") String pin);


    @Headers("Content-Type: application/json")
    @GET("api/ddd/mobile/login/{username}/{password}")
    Call<Response> login(@Path("username") String username, @Path("password") String password);

    //mobile/patient/{deviceId}/{pin}/{accountUserName}/{accountPassword}
    @Headers("Content-Type: application/json")
    @GET("api/ddd/mobile/patient/{deviceId}/{pin}/{accountUserName}/{accountPassword}")
    Call<Data> downLoad(@Path("deviceId") String deviceId,
                        @Path("pin") String pin,
                        @Path("accountUserName") String accountUserName,
                        @Path("accountPassword") String accountPassword);


    @Headers("Content-Type: application/json")
    @GET("api/ddd/mobile/facility/{deviceId}/{facilityId}/{accountUserName}/{accountPassword}")
    Call<Data> getFacilityCode(@Path("deviceId") String deviceId,
                               @Path("facilityId") Long facilityId,
                               @Path("accountUserName") String accountUserName,
                               @Path("accountPassword") String accountPassword);


    @Headers("Content-Type: application/json")
    @POST("api/ddd/mobile/discontinue/{dateDiscontinue}/{reasonDiscontinued}/{id}")
    Call<Response> discontinued(@Path("dateDiscontinue") String dateDiscontinue,
                                @Path("reasonDiscontinued") String reasonDiscontinued,
                                @Path("id") Long id);


    @Headers("Content-Type: application/json")
    @GET("api/ddd/mobile-patient/all")
    Call<Data> getAllPatients();

    @Headers("Content-Type: application/json")
    @GET("api/ddd/mobile-patient/facility/{facilityId}")
    Call<Data> getAllPatientsFacilityId(@Path("facilityId") Long facilityId);


    @Headers("Content-Type: application/json")
    @GET("api/ddd/mobile-patient/arv/date-rage/{start}/{end}")
    Call<Data> getAllPatientsByDateRange(@Path("start") String start,
                                         @Path("end") String end);


    @Headers("Content-Type: application/json")
    @GET("api/ddd/mobile-patient/arv/date-rage/{start}/{end}/{facilityId}")
    Call<Data> getAllPatientsByDateRange(@Path("start") String start,
                                         @Path("end") String end,@Path("facilityId") Long facilityId);


    @Headers("Content-Type: application/json")
    @DELETE("/api/ddd/mobile-patient/delete")
    Call<Void> deletePatient(@Query(value = "hospitalNum") String hospitalNum,
                             @Query(value = "facilityId") Long facilityId);

}
