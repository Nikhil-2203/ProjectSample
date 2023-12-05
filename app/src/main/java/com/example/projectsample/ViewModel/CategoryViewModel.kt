package com.example.projectsample.ViewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.projectsample.ApiCall
import com.example.projectsample.model.BaseClass
import com.example.projectsample.model.Product
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CategoryViewModel(application: Application) : BaseViewModel(application) {

    private val disposable = CompositeDisposable() //Low memory usages.
    var categoryList = MutableLiveData<ArrayList<String>>(arrayListOf())
    private val url = "https://dummyjson.com/products/"
    private val categoryBaseUrl = "https://dummyjson.com/products/category/"
    var categorizedProducts = MutableLiveData<ArrayList<Product>>()
    private val api = Retrofit
        .Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ApiCall::class.java)

    fun getCategoryFromAPI(): ArrayList<String>? {
        runBlocking {
            launch {
                disposable.add(
                    api.getCategories()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<String>>() {
                            override fun onSuccess(value: List<String>) {
                                categoryList.value = value as ArrayList<String>?
                            }

                            override fun onError(e: Throwable) {
                                e?.printStackTrace()
                            }

                        })
                )

            }
        }
        return categoryList.value
    }

    fun getCategorizedProductFromAPI(string: String) {
        //supportActionBar?.title = categoryType
        println(categoryBaseUrl + string)
        val retrofit = Retrofit.Builder()
            .baseUrl(categoryBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ApiCall::class.java)
        val call = service.getCategorizedProduct(categoryBaseUrl + string)
        call.enqueue(object : Callback<BaseClass> {
            override fun onFailure(call: Call<BaseClass>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<BaseClass>, response: Response<BaseClass>) {
                println(response.body())
                response.body().let {
                    categorizedProducts.value = it?.products as ArrayList<Product>?
                }
            }
        })
    }
    fun getFirstCategoryFromAPI(){
        val api2 = Retrofit
            .Builder()
            .baseUrl("https://dummyjson.com/products/category/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ApiCall::class.java)
        launch{
            disposable.add(
                api2.getSmartPhonesFromAPI()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<BaseClass>() {
                        override fun onSuccess(value: BaseClass) {
                            categorizedProducts.value = value?.products as ArrayList<Product>?
                            println("inside of onSuccess")
                        }

                        override fun onError(e: Throwable) {
                            e?.printStackTrace()
                        }

                    })
            )
        }
    }
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}