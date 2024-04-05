package ru.practicum.android.diploma.di

import com.google.gson.Gson
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.app.App
import ru.practicum.android.diploma.data.network.HHApi
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient

val dataModule = module {

    single<HHApi> {
        Retrofit.Builder()
            .baseUrl("https://api.hh.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HHApi::class.java)
    }

    factory<NetworkClient> {
        RetrofitNetworkClient(get(), get())
    }

    factory {
        Gson()
    }

    single {
        App()
    }
}
