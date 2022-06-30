package id.bloopyworks.platform.core.data.source.local_datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import id.bloopyworks.platform.core.utlis.Constant.TOKEN_KEY

object DataStorePref {
        val Context.token_key: DataStore<Preferences> by preferencesDataStore(TOKEN_KEY)
}