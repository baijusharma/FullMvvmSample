package com.fullmvvmsample.baiju.ui.quotes

import androidx.lifecycle.ViewModel
import com.fullmvvmsample.baiju.data.repository.QuotesRepository
import com.fullmvvmsample.baiju.util.lazyDeferred

class QuotesViewModel(repository: QuotesRepository) : ViewModel() {

    val quotes by lazyDeferred { repository.getQuotes() }
}
