package Controller

import android.content.Context
import Data.IDataManager
import Data.RestInventoryDataManager
import Entity.InventoryItem
import Entity.Person
import cr.ac.utn.beekeepersnotebook.R

class InventoryController(private val context: Context, private val personId: String) {

    // Instanciamos el DataManager.
    // Usamos la implementación Rest directamente.
    private val dataManager: IDataManager<InventoryItem> = RestInventoryDataManager()

    // Constructor secundario para aceptar el objeto Person completo
    constructor(context: Context, person: Person) : this(context, person.ID)

    fun addItem(item: InventoryItem, onResult: (Boolean, String?) -> Unit) {
        // Aseguramos que el item se guarde asociado al usuario actual
        item.personId = this.personId

        dataManager.add(item) { ok, error ->
            if (!ok) {
                onResult(false, context.getString(R.string.ErrorMsgAdd) + " " + (error ?: ""))
            } else {
                onResult(true, null)
            }
        }
    }

    fun updateItem(item: InventoryItem, onResult: (Boolean, String?) -> Unit) {
        // Mantenemos la integridad del ID del usuario al actualizar
        item.personId = this.personId

        dataManager.update(item) { ok, error ->
            if (!ok) {
                onResult(false, context.getString(R.string.ErrorMsgUpdate) + " " + (error ?: ""))
            } else {
                onResult(true, null)
            }
        }
    }

    fun deleteItem(id: String, onResult: (Boolean, String?) -> Unit) {
        dataManager.delete(id) { ok, error ->
            if (!ok) {
                onResult(false, context.getString(R.string.ErrorMsgRemove) + " " + (error ?: ""))
            } else {
                onResult(true, null)
            }
        }
    }

    // Trae todos los items (Cuidado: esto podría traer items de otros usuarios si la API no filtra)
    fun getAll(onResult: (List<InventoryItem>) -> Unit) {
        dataManager.getAll(onResult)
    }

    // Trae solo los inventarios del usuario actual
    fun getAllByPerson(onResult: (List<InventoryItem>) -> Unit) {
        // Verificación de seguridad antes de castear
        if (dataManager is RestInventoryDataManager) {
            dataManager.getAllByPerson(this.personId, onResult)
        } else {
            // Si el DataManager no soporta filtro por persona, devolvemos lista vacía o error
            onResult(emptyList())
        }
    }

    fun getById(id: String, onResult: (InventoryItem?) -> Unit) {
        dataManager.getById(id, onResult)
    }
}
