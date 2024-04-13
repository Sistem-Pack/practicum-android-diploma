package ru.practicum.android.diploma.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.app.App
import ru.practicum.android.diploma.app.PRACTICUM_DIPLOMA_PREFERENCES
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.converters.FavoriteVacancyDbConverter
import ru.practicum.android.diploma.data.dto.areas.AreasRequest
import ru.practicum.android.diploma.data.dto.industry.IndustriesRequest
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

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    factory {
        FavoriteVacancyDbConverter()
    }

    factory {
        IndustriesRequest()
    }

    factory {
        AreasRequest()
    }
    
    single<SharedPreferences> {
        androidContext()
            .getSharedPreferences(PRACTICUM_DIPLOMA_PREFERENCES, Context.MODE_PRIVATE)
    }
    
}
