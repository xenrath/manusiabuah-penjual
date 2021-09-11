package com.xenrath.manusiabuah.ui.transaction.detail

class TransactionDetailPresenter(val view: TransactionDetailContract.View): TransactionDetailContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
    }

    override fun transactionDetail(id: Long) {
//        view.onLoading(true, "Menampilkan daftar pembelian...")
//        ApiService.endPoint.getBargainDetail(id).enqueue(object : Callback<ResponseBargainDetail> {
//            override fun onResponse(
//                call: Call<ResponseBargainDetail>,
//                response: Response<ResponseBargainDetail>
//            ) {
//                view.onLoading(false)
//                val responseBargainDetail: ResponseBargainDetail? = response.body()
//                view.onResult(responseBargainDetail!!)
//            }
//
//            override fun onFailure(call: Call<ResponseBargainDetail>, t: Throwable) {
//                view.onLoadingGet(false)
//            }
//
//        })
    }

    override fun updateBargainStatus(id: Long, status: String) {
//        view.onLoadingAction(true)
//        ApiService.endPoint.getBargainAction(
//            id,
//            status,
//            "PUT"
//        ).enqueue(object: Callback<ResponseBargainUpdate> {
//            override fun onResponse(
//                call: Call<ResponseBargainUpdate>,
//                response: Response<ResponseBargainUpdate>
//            ) {
//                view.onLoadingAction(false)
//                if (response.isSuccessful){
//                    val responseBargainUpdate: ResponseBargainUpdate? = response.body()
//                    view.onResultUpdate(responseBargainUpdate!!)
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseBargainUpdate>, t: Throwable) {
//                view.onLoadingAction(false)
//            }
//
//        })
    }
}