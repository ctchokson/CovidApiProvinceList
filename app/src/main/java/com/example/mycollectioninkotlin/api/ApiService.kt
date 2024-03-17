package com.example.mycollectioninkotlin.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val BASE_URL = "https://covid-19-statistics.p.rapidapi.com/"

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


/**
 * Interface that exposes the API method
 */
interface CovidApiService {
    /**

     * The @GET annotation indicates that the "neo/rest/v1/feed" endpoint will be requested with the GET
     * HTTP method
     */
    @Headers("X-RapidAPI-Key:1594292007msh2465fbc1922fddfp1fcacbjsn3ad354c84a4f","X-RapidAPI-Host:covid-19-statistics.p.rapidapi.com")
    @GET("provinces")
    suspend fun getProvinces( @Query("iso") iSo: String): Response<ResponseBody>

    @Headers("X-RapidAPI-Key:1594292007msh2465fbc1922fddfp1fcacbjsn3ad354c84a4f","X-RapidAPI-Host:covid-19-statistics.p.rapidapi.com")
    @GET("regions")
    suspend fun getRegions()

    @Headers("X-RapidAPI-Key:1594292007msh2465fbc1922fddfp1fcacbjsn3ad354c84a4f","X-RapidAPI-Host:covid-19-statistics.p.rapidapi.com")
    @GET("total")
    suspend fun getTotalReport(@Query("date") date: String = ""): Response<ResponseBody>

    @Headers("X-RapidAPI-Key:1594292007msh2465fbc1922fddfp1fcacbjsn3ad354c84a4f","X-RapidAPI-Host:covid-19-statistics.p.rapidapi.com")
    @GET("reports")
    suspend fun getReports(@Query("city_name") city: String="", @Query("region_province") province: String = "", @Query("iso") iSo: String = "", @Query("region_name") region: String = "", @Query("q") q: String = "", @Query("date") date: String = ""): Response<ResponseBody>
}

/**
 * A public Api object that exposes the lazy-initialization of Retrofit service
 */
object CovidApi {
    val retrofitService : CovidApiService by lazy { retrofit.create(CovidApiService::class.java) }
}
