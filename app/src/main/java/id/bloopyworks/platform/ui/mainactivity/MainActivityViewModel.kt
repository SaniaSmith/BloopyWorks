package id.bloopyworks.platform.ui.mainactivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.bloopyworks.platform.core.data.source.local_datastore.DataStoreRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class MainActivityViewModel(
    private val dataStore : DataStoreRepository
) : ViewModel() {

    val getToken = dataStore.getTokenKey.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "")
}