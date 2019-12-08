package com.fullmvvmsample.baiju.ui.quotes

import androidx.lifecycle.ViewModel
import com.fullmvvmsample.baiju.data.repository.QuotesRepository
import com.fullmvvmsample.baiju.util.lazyDeferred

class QuotesViewModel(repository: QuotesRepository) : ViewModel() {
        
    //lazyDeferred help us to avoid calling of quotes whenever QuotesViewModel is instantiated as it is now using lazy
    val vmQuotes by lazyDeferred {
        repository.getQuotes()
    }
}
