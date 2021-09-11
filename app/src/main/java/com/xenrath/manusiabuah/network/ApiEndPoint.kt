package com.xenrath.manusiabuah.network

import com.xenrath.manusiabuah.data.model.account.ResponseAccountDetail
import com.xenrath.manusiabuah.data.model.account.ResponseAccountList
import com.xenrath.manusiabuah.data.model.account.ResponseAccountUpdate
import com.xenrath.manusiabuah.data.model.user.ResponseUser
import com.xenrath.manusiabuah.data.model.address.ResponseAddressDetail
import com.xenrath.manusiabuah.data.model.address.ResponseAddressList
import com.xenrath.manusiabuah.data.model.address.ResponseAddressUpdate
import com.xenrath.manusiabuah.data.model.bank.ResponseBankList
import com.xenrath.manusiabuah.data.model.offer.ResponseOfferDetail
import com.xenrath.manusiabuah.data.model.offer.ResponseOfferList
import com.xenrath.manusiabuah.data.model.offer.ResponseOfferUpdate
import com.xenrath.manusiabuah.data.model.product.ResponseProductDetail
import com.xenrath.manusiabuah.data.model.product.ResponseProductList
import com.xenrath.manusiabuah.data.model.product.ResponseProductUpdate
import com.xenrath.manusiabuah.data.model.rajaongkir.cost.ResponseRajaongkirCost
import com.xenrath.manusiabuah.data.model.rajaongkir.territory.ResponseRajaongkirTerritory
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionDetail
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionUpdate
import com.xenrath.manusiabuah.data.model.user.ResponseUserUpdate
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiEndPoint {

    // User

    @FormUrlEncoded
    @POST("userLogin")
    fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("level") level: String
    ): Call<ResponseUser>

    @FormUrlEncoded
    @POST("userRegister")
    fun userRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("password_confirmation") password_confirmation: String,
        @Field("phone") phone: String,
        @Field("level") level: String,
    ): Call<ResponseUser>

    @GET("userDetail/{id}")
    fun userDetail(
        @Path("id") id: Long
    ): Call<ResponseUser>

    @POST("userDetailSeller")
    fun userDetailSeller(
        @Query("user_id") user_id: String
    ): Call<ResponseUser>

    @Multipart
    @POST("profileUpdate/{id}")
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

    // Product

    @Multipart
    @POST("productCreate")
    fun productCreate(
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
        @Query("stock") stock: String
    ): Call<ResponseProductUpdate>

    @Multipart
    @POST("productUpdate/{id}")
    fun productUpdate(
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

    @DELETE("productDelete/{id}")
    fun productDelete(
        @Path("id") id: Long
    ): Call<ResponseProductUpdate>

    @GET("productList/{id}")
    fun productList(
        @Path("id") id: Long
    ): Call<ResponseProductList>

    @POST("productSeller")
    fun productSeller(
        @Query("user_id") user_id: String,
        @Query("category") category: String
    ): Call<ResponseProductList>

    @POST("productSearch")
    fun productSearch(
        @Query("keyword") keyword: String
    ): Call<ResponseProductList>

    @GET("productDetail/{id}")
    fun productDetail(
        @Path("id") id: Long
    ): Call<ResponseProductDetail>

    // Raja Ongkir

    @GET("province")
    fun getProvince(
        @Header("key") key: String
    ): Call<ResponseRajaongkirTerritory>

    @GET("city")
    fun getCity(
        @Header("key") key: String,
        @Query("province") id: String
    ): Call<ResponseRajaongkirTerritory>

    // Offer

    @POST("offerPrice")
    fun offerPrice(
        @Query("user_id") user_id: String,
        @Query("product_id") product_id: String,
        @Query("price") price: String,
        @Query("price_offer") price_offer: String,
        @Query("total_item") total_item: String,
        @Query("status") status: String
    ): Call<ResponseOfferUpdate>

    @GET("offerWaiting/{id}")
    fun offerWaiting(
        @Path("id") id: Long
    ): Call<ResponseOfferList>

    @GET("offerAccepted/{id}")
    fun offerAccepted(
        @Path("id") id: Long
    ): Call<ResponseOfferList>

    @GET("offerHistory/{id}")
    fun offerHistory(
        @Path("id") id: Long
    ): Call<ResponseOfferList>

    @POST("offerCanceled/{id}")
    fun offerCanceled(
        @Path("id") id: Long,
        @Query("_method") _method: String
    ): Call<ResponseOfferUpdate>

    // Offer Manage

    @GET("offerWaitingManage/{id}")
    fun offerWaitingManage(
        @Path("id") id: Long
    ): Call<ResponseOfferList>

    @GET("offerHistoryManage/{id}")
    fun offerHistoryManage(
        @Path("id") id: Long
    ): Call<ResponseOfferList>

    @POST("offerAccept/{id}")
    fun offerAccept(
        @Path("id") id: Long,
        @Query("_method") _method: String
    ): Call<ResponseOfferUpdate>

    @POST("offerReject/{id}")
    fun offerReject(
        @Path("id") id: Long,
        @Query("_method") _method: String
    ): Call<ResponseOfferUpdate>

    // Offer All

    @GET("offerDetail/{id}")
    fun offerDetail(
        @Path("id") id: Long
    ): Call<ResponseOfferDetail>

    // Address

    @POST("addressCreate")
    fun addressCreate(
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

    @POST("addressList")
    fun addressList(
        @Query("user_id") user_id: String
    ): Call<ResponseAddressList>

    @POST("addressActived")
    fun addressActived(
        @Query("id") id: Long,
        @Query("user_id") user_id: String,
    ): Call<ResponseAddressUpdate>

    @POST("addressChecked")
    fun addressChecked(
        @Query("user_id") user_id: String,
    ): Call<ResponseAddressDetail>

    //

    @GET("bargainDetail/{id}")
    fun getBargainDetail(
        @Path("id") id: Long
    ): Call<ResponseOfferDetail>

    @GET("bargainDetailDelivery/{id}")
    fun getBargainDetailDelivery(
        @Path("id") id: Long
    ): Call<ResponseOfferDetail>

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
    @POST("transactionOrder")
    fun transactionOrder(
        @Field("product_id") product_id: String,
        @Field("address_id") address_id: String,
        @Field("price") price: String,
        @Field("total_item") total_item: String,
        @Field("courier") courier: String,
        @Field("service_type") service_type: String,
        @Field("cost") cost: String,
        @Field("note") note: String,
        @Field("total_price") total_price: String
    ): Call<ResponseTransactionUpdate>

    @GET("transactionDetail/{id}")
    fun transactionDetail(
        @Path("id") id: Long
    ): Call<ResponseTransactionDetail>

    @GET("userDetail/{id}")
    fun getUserDetail(
        @Path("id") id: Long
    ): Call<ResponseUser>

    // Account

    @POST("accountCreate")
    fun accountCreate(
        @Query("user_id") user_id: String,
        @Query("bank_id") bank_id: String,
        @Query("bank_name") bank_name: String,
        @Query("name") name: String,
        @Query("number") number: String
    ): Call<ResponseAccountUpdate>

    @POST("accountUpdate/{id}")
    fun accountUpdate(
        @Path("id") id: Long,
        @Query("user_id") user_id: String,
        @Query("bank_id") bank_id: String,
        @Query("bank_name") bank_name: String,
        @Query("name") name: String,
        @Query("number") number: String,
        @Query("_method") _method: String
    ): Call<ResponseAccountUpdate>

    @DELETE("accountDelete/{id}")
    fun accountDelete(
        @Path("id") id: Long
    ): Call<ResponseAccountUpdate>

    @GET("accountList/{id}")
    fun accountList(
        @Path("id") id: Long
    ): Call<ResponseAccountList>

    @GET("accountDetail/{id}")
    fun accountDetail(
        @Path("id") id: Long
    ): Call<ResponseAccountDetail>

    // Bank

    @GET("bankList")
    fun bankList(): Call<ResponseBankList>
}