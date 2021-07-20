package com.xenrath.manusiabuah.network

import com.xenrath.manusiabuah.data.ResponseProduct
import com.xenrath.manusiabuah.data.ResponseLogin
import com.xenrath.manusiabuah.data.database.model.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiEndPoint {

    @FormUrlEncoded
    @POST("register")
    fun registerSeller(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("password_confirmation") password_confirmation: String,
        @Field("phone") phone: String,
        @Field("level") level: String,
    ): Call<ResponseLogin>

    @FormUrlEncoded
    @POST("login")
    fun loginSeller(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("level") level: String
    ): Call<ResponseLogin>

    @GET("product")
    fun getProduct(): Call<ResponseProduct>

    @POST("myproduct")
    fun myProduct(
        @Query("user_id") user_id: String
    ): Call<ResponseProductList>

    @POST("productsforsale")
    fun getProductForSale(
        @Query("user_id") user_id: String,
        @Query("category") category: String
    ): Call<ResponseProductList>

    @GET("searchproduct")
    fun searchProduct(
        @Query("keyword") keyword: String
    ): Call<ResponseProduct>

    @Multipart
    @POST("product")
    fun insertProduct(
        @Query("user_id") user_id: String,
        @Query("name") name: String,
        @Query("category") category: String,
        @Query("price") price: String,
        @Query("description") description: String?,
        @Query("address") address: String,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Part image: MultipartBody.Part,
        @Query("stock") stock: String,
    ): Call<ResponseProductUpdate>

    @GET("product/{id}")
    fun getProductDetail(
        @Path("id") id: Long
    ): Call<ResponseProductDetail>

    @Multipart
    @POST("product/{id}")
    fun updateProduct(
        @Path("id") id: Long,
        @Query("name") name: String,
        @Query("price") price: String,
        @Query("description") description: String,
        @Query("address") address: String,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Part image: MultipartBody.Part?,
        @Query("stock") stock: String,
        @Query("_method") _method: String
    ): Call<ResponseProductUpdate>

    @DELETE("product/{id}")
    fun deleteProduct(
        @Path("id") id: Long
    ): Call<ResponseProductUpdate>

    @POST("offerprice")
    fun offerPrice(
        @Query("user_id") user_id: String,
        @Query("product_id") product_id: String,
        @Query("price") price: String,
        @Query("price_offer") price_offer: String,
        @Query("total_item") total_item: String,
        @Query("status") status: String
    ): Call<ResponseBargain>

    @GET("offerwaiting/{id}")
    fun getOfferWaiting(
        @Path("id") id: String
    ): Call<ResponseBargainList>

    @GET("offerreject/{id}")
    fun offerReject(
        @Path("id") id: Long
    ): Call<ResponseBargainUpdate>

    @GET("offeraccept/{id}")
    fun offerAccept(
        @Path("id") id: Long
    ): Call<ResponseBargainUpdate>

    @GET("offerhistory/{id}")
    fun offerHistory(
        @Path("id") id: String
    ): Call<ResponseBargainList>
}