package id.bloopyworks.platform.ui.mainscreen.homepage.attendance

import androidx.lifecycle.ViewModel
import id.bloopyworks.platform.core.data.bloopyRepository
import id.bloopyworks.platform.core.data.source.local_datastore.DataStoreRepository

class ReqAttendaceViewModel(
    private val repo: bloopyRepository,
    private val dataStore: DataStoreRepository
) : ViewModel()  {


}