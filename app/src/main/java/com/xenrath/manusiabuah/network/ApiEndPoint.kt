package com.xenrath.manusiabuah.network

import com.xenrath.manusiabuah.data.model.user.ResponseUser
import com.xenrath.manusiabuah.data.model.address.ResponseAddressDetail
import com.xenrath.manusiabuah.data.model.address.ResponseAddressList
import com.xenrath.manusiabuah.data.model.address.ResponseAddressUpdate
import com.xenrath.manusiabuah.data.model.bargain.ResponseBargainDetail
import com.xenrath.manusiabuah.data.model.bargain.ResponseBargainList
import com.xenrath.manusiabuah.data.model.bargain.ResponseBargainUpdate
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionDetail
import com.xenrath.manusiabuah.data.model.product.ResponseProductDetail
import com.xenrath.manusiabuah.data.model.product.ResponseProductList
import com.xenrath.manusiabuah.data.model.product.ResponseProductUpdate
import com.xenrath.manusiabuah.data.model.rajaongkir.cost.ResponseRajaongkirCost
import com.xenrath.manusiabuah.data.model.rajaongkir.territory.ResponseRajaongkirTerritory
import com.xenrath.manusiabuah.data.model.user.ResponseUserUpdate
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
    ): Call<ResponseUser>

    @FormUrlEncoded
    @POST("login")
    fun loginSeller(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("level") level: String
    ): Call<ResponseUser>

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
    ): Call<ResponseProductList>

    @GET("province")
    fun getProvince(
        @Header("key") key: String
    ): Call<ResponseRajaongkirTerritory>

    @GET("city")
    fun getCity(
        @Header("key") key: String,
        @Query("province") id: String
    ): Call<ResponseRajaongkirTerritory>

    @Multipart
    @POST("product")
    fun insertProduct(
        @Query("user_id") user_id: String,
        @Query("name") name: String,
        @Query("category") category: String,
        @Query("price") price: String,
        @Query("description") description: String?,
        @Query("address") address: String,
        @Query("province_id") province_id: String,
        @Query("province_name") province_name: String,
        @Query("city_id") city_id: String,
        @Query("city_name") city_name: String,
        @Query("postal_code") postal_code: String,
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
        @Query("province_id") province_id: String,
        @Query("province_name") province_name: String,
        @Query("city_id") city_id: String,
        @Query("city_name") city_name: String,
        @Query("postal_code") postal_code: String,
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
    ): Call<ResponseBargainUpdate>

    @POST("myBargain")
    fun getMyBargain(
        @Query("user_id") user_id: String,
        @Query("status") status: String
    ): Call<ResponseBargainList>

    // For Seller

    @GET("bargainReject/{id}")
    fun bargainReject(
        @Path("id") id: Long
    ): Call<ResponseBargainUpdate>

    @GET("bargainAccept/{id}")
    fun bargainAccept(
        @Path("id") id: Long
    ): Call<ResponseBargainUpdate>

    @POST("bargainSellerWaiting")
    fun bargainSellerWaiting(
        @Query("user_id") user_id: String
    ): Call<ResponseBargainList>

    @POST("bargainSellerHistory")
    fun bargainHistoryWaiting(
        @Query("user_id") user_id: String
    ): Call<ResponseBargainList>

    @GET("offerhistory/{id}")
    fun offerHistory(
        @Path("id") id: String
    ): Call<ResponseBargainList>

    @POST("addAddress")
    fun insertAddress(
        @Query("user_id") user_id: String,
        @Query("name") name: String,
        @Query("phone") phone: String,
        @Query("address") address: String,
        @Query("place") place: String,
        @Query("province_id") province_id: String,
        @Query("province_name") province_name: String,
        @Query("city_id") city_id: String,
        @Query("city_name") city_name: String,
        @Query("postal_code") postal_code: String
    ): Call<ResponseAddressUpdate>

    @POST("myAddress")
    fun getAddress(
        @Query("user_id") user_id: String
    ): Call<ResponseAddressList>

    @POST("checkAddress")
    fun checkAddress(
        @Query("id") id: Long,
        @Query("user_id") user_id: String,
    ): Call<ResponseAddressUpdate>

    @POST("addressChecked")
    fun addressChecked(
        @Query("user_id") user_id: String,
    ): Call<ResponseAddressDetail>

    @POST("bargainAction/{id}")
    fun getBargainAction(
        @Path("id") id: Long,
        @Query("status") status: String,
        @Query("_method") _method: String
    ): Call<ResponseBargainUpdate>

    @GET("bargainDetail/{id}")
    fun getBargainDetail(
        @Path("id") id: Long
    ): Call<ResponseBargainDetail>

    @GET("bargainDetailDelivery/{id}")
    fun getBargainDetailDelivery(
        @Path("id") id: Long
    ): Call<ResponseBargainDetail>

    @FormUrlEncoded
    @POST("cost")
    fun calculateCost(
        @Header("key") key: String,
        @Field("origin") origin: String,
        @Field("destination") destination: String,
        @Field("weight") weight: Int,
        @Field("courier") courier: String
    ): Call<ResponseRajaongkirCost>

    @FormUrlEncoded
    @POST("checkout")
    fun checkout(
        @Field("bargain_id") bargain_id: String,
        @Field("address_id") address_id: String,
        @Field("courier") courier: String,
        @Field("delivery_service") delivery_service: String,
        @Field("total_transfer") total_transfer: Int,
        @Field("status") status: String
    ): Call<ResponseTransactionDetail>

    @GET("userDetail/{id}")
    fun getUserDetail(
        @Path("id") id: Long
    ): Call<ResponseUser>

    // User

    @Multipart
    @POST("profileupdate/{id}")
    fun updateProfile(
        @Path("id") id: Long,
        @Query("name") name: String,
        @Query("email") email: String,
        @Query("phone") phone: String,
        @Query("address") address: String?,
        @Part image: MultipartBody.Part?,
        @Query("_method") _method: String
    ): Call<ResponseUserUpdate>

    @POST("password/{id}")
    fun updatePassword(
        @Path("id") id: Long,
        @Query("password") password: String,
        @Query("password_confirmation") password_confirmation: String,
        @Query("_method") _method: String
    ): Call<ResponseUserUpdate>
}