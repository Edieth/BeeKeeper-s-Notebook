package Controller

import Data.RestBeehiveDataManager
import Entity.Beehive
import android.content.Context
import cr.ac.utn.beekeepersnotebook.R

// Usamos el constructor primario de Kotlin para recibir context y personId de una vez
class BeehiveController(private val context: Context, private val personId: String) {


    private val dataManager = RestBeehiveDataManager()

    fun addBeehive(hive: Beehive, onResult: (Boolean, String?) -> Unit) {
        dataManager.add(hive, personId) { ok, error ->
            if (!ok) onResult(false, context.getString(R.string.ErrorMsgAdd) + " ${error ?: ""}")
            else onResult(true, null)
        }
    }

    fun updateBeehive(hive: Beehive, onResult: (Boolean, String?) -> Unit) {
        dataManager.update(hive, personId) { ok, error ->
            if (!ok) onResult(false, context.getString(R.string.ErrorMsgUpdate) + " ${error ?: ""}")
            else onResult(true, null)
        }
    }

    fun deleteBeehive(id: String, onResult: (Boolean, String?) -> Unit) {
        dataManager.delete(id) { ok, error ->
            if (!ok) onResult(false, context.getString(R.string.ErrorMsgRemove) + " ${error ?: ""}")
            else onResult(true, null)
        }
    }

    fun getByPerson(onResult: (List<Beehive>) -> Unit) {
        dataManager.getByPerson(personId, onResult)
    }

    fun getById(id: String, onResult: (Beehive?) -> Unit) {
        dataManager.getById(id, onResult)
    }

    fun getByZone(zoneId: String, onResult: (List<Beehive>) -> Unit) {
        dataManager.getByZone(zoneId, onResult)
    }
}