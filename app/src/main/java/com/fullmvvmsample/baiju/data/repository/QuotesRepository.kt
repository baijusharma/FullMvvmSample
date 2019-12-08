package com.fullmvvmsample.baiju.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fullmvvmsample.baiju.data.db.AppDatabase
import com.fullmvvmsample.baiju.data.db.entities.Quote
import com.fullmvvmsample.baiju.data.network.MyApi
import com.fullmvvmsample.baiju.data.network.SafeApiRequest
import com.fullmvvmsample.baiju.data.prefrences.PrefrenceProvider
import com.fullmvvmsample.baiju.util.MyCoroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit


private val MINIMUM_INTERVAL = 6

// Repository can communicate with Repository
class QuotesRepository(
    private val api: MyApi,
    private val db: AppDatabase,
    private val prefs: PrefrenceProvider
) : SafeApiRequest() {
    private val quotes = MutableLiveData<List<Quote>>()

    //The code inside the init block is the first to be executed when the class/object is instantiated.

    init {
        quotes.observeForever {
            // This fun will be called, only if there is a change in the LiveData
            saveQuotes(it)
        }
    }

    private fun saveQuotes(quotes: List<Quote>  ?) {
        MyCoroutines.io {
          //  prefs.saveLastSavedAt(LocalDateTime.now().toString())
            db.getQuoteDao().saveAllQuotes(quotes!!) // Save Quotes In local DB
        }
    }

    suspend fun getQuotes(): LiveData<List<Quote>> {
        return withContext(Dispatchers.IO) {
            fetchQuotes()
            db.getQuoteDao().getQuotes()
        }

    }

    private suspend fun fetchQuotes() {
        val lastSavedAt = prefs.getLastSavedAt()
        if (lastSavedAt == null || isFetchNeeded(LocalDateTime.parse(lastSavedAt))) {
            val response = apiRequest { api.getQuotes() }
            quotes.postValue(response.quotes)
        }
    }

    private fun isFetchNeeded(savedAt: LocalDateTime): Boolean {
        return ChronoUnit.HOURS.between(savedAt, LocalDateTime.now()) > MINIMUM_INTERVAL
        //return true
    }
}