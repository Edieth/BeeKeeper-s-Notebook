package Data

import Entity.DTOQueen
import Entity.Queen
import Util.BeekeepersAPIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RestQueenDataManager(private var personId: String) {

    private val scope = CoroutineScope(Dispatchers.IO)

    private fun toDTO(q: Queen): DTOQueen {
        return DTOQueen(
            id = q.ID,
            // Aquí corregimos los nombres para que coincidan con DTOQueen.kt
            hiveId = q.HiveID,
            personId = personId, // Usamos el usuario logueado
            zoneID = q.ZoneID,   // DTO usa "zoneID"
            typeInternal = q.Type, // DTO usa "typeInternal", Entity usa "Type"
            entryDateInternal = q.getEntryDateAsString() // DTO usa "entryDateInternal"
        )
    }

    // Convierte de DTO (Api) a Entidad (App)
    private fun toEntity(dto: DTOQueen): Queen {
        val q = Queen()
        // Corregimos los nombres aquí también
        q.ID = dto.id
        q.Type = dto.typeInternal // Mapeamos typeInternal -> Type
        q.HiveID = dto.hiveId
        q.ZoneID = dto.zoneID
        q.PersonID = dto.personId
        // Usamos tu función para setear la fecha desde el string del DTO
        q.setEntryDateFromString(dto.entryDateInternal)
        return q
    }

    // ==========================================
    // CRUD (Igual que antes, pero ahora toDTO/toEntity funcionan)
    // ==========================================

    fun add(item: Queen, onResult: (Boolean, String?) -> Unit) {
        scope.launch {
            try {
                val resp = BeekeepersAPIService.apiQueen.create(toDTO(item))
                val ok = resp.responseCode == 200
                withContext(Dispatchers.Main) { onResult(ok, if (ok) null else resp.message) }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { onResult(false, e.message) }
            }
        }
    }

    fun update(item: Queen, onResult: (Boolean, String?) -> Unit) {
        scope.launch {
            try {
                val id = item.ID
                if (id.isEmpty()) {
                    withContext(Dispatchers.Main) { onResult(false, "ID vacío") }
                    return@launch
                }
                val resp = BeekeepersAPIService.apiQueen.update(id, toDTO(item))
                val ok = resp.responseCode == 200
                withContext(Dispatchers.Main) { onResult(ok, if (ok) null else resp.message) }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { onResult(false, e.message) }
            }
        }
    }

    fun delete(id: String, onResult: (Boolean, String?) -> Unit) {
        scope.launch {
            try {
                val resp = BeekeepersAPIService.apiQueen.delete(id)
                val ok = resp.responseCode == 200
                withContext(Dispatchers.Main) { onResult(ok, if (ok) null else resp.message) }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { onResult(false, e.message) }
            }
        }
    }

    // ==========================================
    // CONSULTAS
    // ==========================================

    fun getByPerson(onResult: (List<Queen>) -> Unit) {
        scope.launch {
            try {
                val resp = BeekeepersAPIService.apiQueen.getByPerson(personId)
                val list = if (resp.responseCode == 200)
                    resp.data.map { toEntity(it) }
                else emptyList()
                withContext(Dispatchers.Main) { onResult(list) }
            } catch (_: Exception) {
                withContext(Dispatchers.Main) { onResult(emptyList()) }
            }
        }
    }

    fun getById(id: String, onResult: (Queen?) -> Unit) {
        scope.launch {
            try {
                val resp = BeekeepersAPIService.apiQueen.getById(id)
                val queen = if (resp.responseCode == 200)
                    resp.data.firstOrNull()?.let { toEntity(it) }
                else null
                withContext(Dispatchers.Main) { onResult(queen) }
            } catch (_: Exception) {
                withContext(Dispatchers.Main) { onResult(null) }
            }
        }
    }

    fun getByHive(hiveId: String, onResult: (List<Queen>) -> Unit) {
        scope.launch {
            try {
                val resp = BeekeepersAPIService.apiQueen.getByHive(hiveId)
                val list = if (resp.responseCode == 200)
                    resp.data.map { toEntity(it) }
                else emptyList()
                withContext(Dispatchers.Main) { onResult(list) }
            } catch (_: Exception) {
                withContext(Dispatchers.Main) { onResult(emptyList()) }
            }
        }
    }
}