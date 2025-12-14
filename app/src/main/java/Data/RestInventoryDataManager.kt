package Data

import Entity.DTOInventoryItem
import Entity.InventoryItem
import Util.BeekeepersAPIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestInventoryDataManager : IDataManager<InventoryItem> {
    private val scope = CoroutineScope(Dispatchers.IO)

    private fun toDTO(item: InventoryItem): DTOInventoryItem {
        return DTOInventoryItem(
            id = item.ID,
            personId = item.PersonID,
            nameInventoryItem = item.Name,
            inventoryItemTotalQuantity = item.InventoryTotalQuantity,
            inventoryDataType = item.InventoryDataType
        )
    }

    private fun toEntity(dto: DTOInventoryItem): InventoryItem {
        val item = InventoryItem()
        item.ID = dto.id ?: ""
        item.PersonID = dto.personId ?: ""
        item.Name = dto.nameInventoryItem  ?: ""
        item.InventoryTotalQuantity = dto.inventoryItemTotalQuantity
        item.InventoryDataType = dto.inventoryDataType
        return item
    }

    override fun add(item: InventoryItem, onResult: (Boolean, String?) -> Unit) {
        scope.launch {
            try {
                val resp = BeekeepersAPIService.apiInventory.create(toDTO(item))
                val ok = resp.responseCode == 200
                withContext(Dispatchers.Main) { onResult(ok, if (ok) null else resp.message) }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) { onResult(false, e.message) }
            }
        }
    }

    override fun update(item: InventoryItem, onResult: (Boolean, String?) -> Unit) {
        scope.launch {
            try {
                val id = item.ID
                if (id.isBlank()) {
                    withContext(Dispatchers.Main) { onResult(false, "ID vacío, no se puede actualizar") }
                    return@launch
                }

                val resp = BeekeepersAPIService.apiInventory.update(id, toDTO(item))
                val ok = resp.responseCode == 200
                withContext(Dispatchers.Main) { onResult(ok, if (ok) null else resp.message) }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) { onResult(false, e.message) }
            }
        }
    }

    override fun delete(id: String, onResult: (Boolean, String?) -> Unit) {
        scope.launch {
            try {
                if (id.isBlank()) {
                    withContext(Dispatchers.Main) { onResult(false, "ID vacío, no se puede eliminar") }
                    return@launch
                }

                val resp = BeekeepersAPIService.apiInventory.delete(id)
                val ok = resp.responseCode == 200
                withContext(Dispatchers.Main) { onResult(ok, if (ok) null else resp.message) }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) { onResult(false, e.message) }
            }
        }
    }

    override fun getAll(onResult: (List<InventoryItem>) -> Unit) {
        // opcional: si lo ocupas, puedes consumir /inventory
        scope.launch {
            try {
                val resp = BeekeepersAPIService.apiInventory.getAll() // si existe en la interfaz
                val list = if (resp.responseCode == 200) resp.data.map { toEntity(it) } else emptyList()
                withContext(Dispatchers.Main) { onResult(list) }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) { onResult(emptyList()) }
            }
        }
    }

    override fun getById(id: String, onResult: (InventoryItem?) -> Unit) {
        scope.launch {
            try {
                val resp = BeekeepersAPIService.apiInventory.getById(id) // si existe en la interfaz
                val item = if (resp.responseCode == 200) resp.data.firstOrNull()?.let { toEntity(it) } else null
                withContext(Dispatchers.Main) { onResult(item) }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) { onResult(null) }
            }
        }
    }

    // ✅ ESTE ES EL QUE NECESITAS PARA TU PANTALLA
    fun getAllByPerson(personId: String, onResult: (List<InventoryItem>) -> Unit) {
        scope.launch {
            try {
                val resp = BeekeepersAPIService.apiInventory.getByPerson(personId)
                val list = if (resp.responseCode == 200) resp.data.map { toEntity(it) } else emptyList()
                withContext(Dispatchers.Main) { onResult(list) }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) { onResult(emptyList()) }
            }
        }
    }
    }

