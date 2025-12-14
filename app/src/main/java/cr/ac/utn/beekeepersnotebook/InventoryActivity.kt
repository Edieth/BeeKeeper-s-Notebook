package Controller

import android.content.Context
import Data.IDataManager
import Data.RestInventoryDataManager
import Entity.InventoryItem
import Entity.Person
import cr.ac.utn.beekeepersnotebook.R

// CORRECCIÓN 1: Constructor primario recibe Contexto y ID directamente
class InventoryController(private val context: Context, private val personId: String) {

    // CORRECCIÓN 2: Instanciación correcta del DataManager.
    // Asumimos que RestInventoryDataManager() tiene un constructor vacío.
    // Si realmente modificaste el Manager para recibir el ID, pon 'personId' dentro de los paréntesis.
    private val dataManager: IDataManager<InventoryItem> = RestInventoryDataManager()

    // CORRECCIÓN 3: Constructor secundario que delega al primario usando 'this'
    constructor(context: Context, person: Person) : this(context, person.ID)

    fun addItem(item: InventoryItem, onResult: (Boolean, String?) -> Unit) {
        // Aseguramos que el ítem pertenezca a la persona actual
        item.personId = this.personId

        dataManager.add(item) { ok, error ->
            if (!ok) onResult(false, context.getString(R.string.ErrorMsgAdd) + " " + (error ?: ""))
            else onResult(true, null)
        }
    }

    fun updateItem(item: InventoryItem, onResult: (Boolean, String?) -> Unit) {
        // Aseguramos que no se pierda la referencia al dueño al actualizar
        item.personId = this.personId

        dataManager.update(item) { ok, error ->
            if (!ok) onResult(false, context.getString(R.string.ErrorMsgUpdate) + " " + (error ?: ""))
            else onResult(true, null)
        }
    }

    fun deleteItem(id: String, onResult: (Boolean, String?) -> Unit) {
        dataManager.delete(id) { ok, error ->
            if (!ok) onResult(false, context.getString(R.string.ErrorMsgRemove) + " " + (error ?: ""))
            else onResult(true, null)
        }
    }

    fun getAll(onResult: (List<InventoryItem>) -> Unit) {
        // Nota: Este getAll trae 'todos' los items (posiblemente de todos los usuarios)
        // Si quieres solo los del usuario, deberías llamar a getAllByPerson
        dataManager.getAll(onResult)
    }

    fun getById(id: String, onResult: (InventoryItem?) -> Unit) {
        dataManager.getById(id, onResult)
    }

    // Este método usa el personId guardado en la clase para filtrar
    fun getAllByPerson(onResult: (List<InventoryItem>) -> Unit) {
        // Casteamos porque IDataManager genérico no suele tener 'getAllByPerson'
        (dataManager as RestInventoryDataManager).getAllByPerson(this.personId, onResult)
    }
}
