package com.xenrath.manusiabuah.ui.manage.purchase.tabs.waiting

class ManagePurchaseWaitingPresenter(val view: ManagePurchaseWaitingContract.View): ManagePurchaseWaitingContract.Presenter {

    override fun getBargainWaiting(
        user_id: String,
        status: String
    ) {
        view.onLoading(true, "Menampilkan tawaran...")
//        ApiService.endPoint.getMyBargain(
//            user_id,
//            status
//        ).enqueue(object: Callback<ResponseBargainList> {
//            override fun onResponse(
//                call: Call<ResponseBargainList>,
//                response: Response<ResponseBargainList>
//            ) {
//                view.onLoading(false)
//                if (response.isSuccessful) {
//                    val responseBargainList: ResponseBargainList? = response.body()
//                    view.onResult(responseBargainList!!)
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseBargainList>, t: Throwable) {
//                view.onLoading(false)
//            }
//
//        })
    }
}