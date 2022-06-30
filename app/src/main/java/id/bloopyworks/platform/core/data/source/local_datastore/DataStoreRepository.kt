package id.bloopyworks.platform.core.data.source.local_datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStoreRepository (
    private val token_key: DataStore<Preferences>,
) {

        companion object {
            private val KEY_TOKEN = stringPreferencesKey("token")
        }

        //Get Token
        val getTokenKey: Flow<String> = token_key.data
            .catch { exception ->
                // dataStore.data throws an IOException when an error is encountered when reading data
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[KEY_TOKEN] ?: ""
            }

        //Save Token
        suspend fun saveTokenKey(token: String) {
            token_key.edit { preferences ->
                preferences[KEY_TOKEN] = token
            }
        }

        //Delete Token
        suspend fun deleteTokenKey() {
            token_key.edit { preferences ->
                preferences[KEY_TOKEN] = ""
            }
        }
}