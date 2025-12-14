package Controller

import Data.RestHarvestDataManager
import Entity.HarvestRecord
import android.content.Context
import cr.ac.utn.beekeepersnotebook.R

class HarvestController(private val context: Context, personId: String) {

    // Inicializamos el nuevo DataManager REST pasándole el usuario
    private val dataManager = RestHarvestDataManager(personId)

    fun addHarvest(h: HarvestRecord, onResult: (Boolean, String?) -> Unit) {
        dataManager.add(h) { ok, error ->
            if (!ok) onResult(false, context.getString(R.string.ErrorMsgAdd) + " " + (error ?: ""))
            else onResult(true, null)
        }
    }

    fun updateHarvest(h: HarvestRecord, onResult: (Boolean, String?) -> Unit) {
        dataManager.update(h) { ok, error ->
            if (!ok) onResult(false, context.getString(R.string.ErrorMsgUpdate) + " " + (error ?: ""))
            else onResult(true, null)
        }
    }

    fun deleteHarvest(id: String, onResult: (Boolean, String?) -> Unit) {
        dataManager.delete(id) { ok, error ->
            if (!ok) onResult(false, context.getString(R.string.ErrorMsgRemove) + " " + (error ?: ""))
            else onResult(true, null)
        }
    }

    // CORRECCIÓN CLAVE: Usamos getByPerson en lugar de getAll
    // Esto es lo que HarvestActivity está buscando y no encontraba
    fun getByPerson(onResult: (List<HarvestRecord>) -> Unit) {
        dataManager.getByPerson(onResult)
    }

    fun getById(id: String, onResult: (HarvestRecord?) -> Unit) {
        dataManager.getById(id, onResult)
    }
}