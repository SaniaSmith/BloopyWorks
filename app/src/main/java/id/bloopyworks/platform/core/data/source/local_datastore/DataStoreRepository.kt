package id.bloopyworks.platform.core.data.source.local_datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStoreRepository (
    private val token_key: DataStore<Preferences>,
//    private val token_verif: DataStore<Preferences>,
//    private val user_id: DataStore<Preferences>
) {

        companion object {
            private val KEY_TOKEN = stringPreferencesKey("token")
//            private val KEY_VERIF = stringPreferencesKey("verification_token")
//            private val KEY_USERID = intPreferencesKey("user_id")
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

        //Save Token Verification
//        suspend fun saveTokenVerif(tokenVerif: String) {
//            token_verif.edit { preferences ->
//                preferences[KEY_VERIF] = tokenVerif
//            }
//        }
//
//        //Save User ID
//        suspend fun saveUserId(userId: Int) {
//            user_id.edit { preferences ->
//                preferences[KEY_USERID] = userId
//            }
//        }
}