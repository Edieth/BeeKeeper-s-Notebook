package Data

import Entity.Beehive
import Entity.DTOBeehive
import Entity.Person
import Util.BeekeepersAPIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class RestBeehiveDataManager {

    private val scope = CoroutineScope(Dispatchers.IO)

    // =====================
    // Mapper Entity → DTO
    // =====================
    private fun toDTO(h: Beehive, personId: String): DTOBeehive {
        return DTOBeehive(
            id = h.ID,
            zoneId = h.ZoneID,
            name = h.Name,
            boxType = h.BoxType,
            queenId = h.QueenID,
            personId = personId
        )
    }

    // =====================
    // Mapper DTO → Entity
    // =====================
    private fun toEntity(dto: DTOBeehive): Beehive {
        val h = Beehive()
        h.ID = dto.id
        h.ZoneID = dto.zoneId
        h.Name = dto.name
        h.BoxType = dto.boxType
        h.QueenID = dto.queenId
        return h
    }

    // =====================
    // CRUD REST
    // =====================
    fun add(item: Beehive, personId: String, onResult: (Boolean, String?) -> Unit) {
        scope.launch {
            try {
                val resp = BeekeepersAPIService.apiHives.create(toDTO(item, personId))
                val ok = resp.responseCode == 200
                withContext(Dispatchers.Main) {
                    onResult(ok, if (ok) null else resp.message)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { onResult(false, e.message) }
            }
        }
    }

    fun update(item: Beehive, personId: String, onResult: (Boolean, String?) -> Unit) {
        scope.launch {
            try {
                val id = item.ID
                if (id.isBlank()) {
                    withContext(Dispatchers.Main) {
                        onResult(false, "ID vacío, no se puede actualizar")
                    }
                    return@launch
                }

                val resp = BeekeepersAPIService.apiHives.update(id, toDTO(item, personId))
                val ok = resp.responseCode == 200
                withContext(Dispatchers.Main) {
                    onResult(ok, if (ok) null else resp.message)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { onResult(false, e.message) }
            }
        }
    }

    fun delete(id: String, onResult: (Boolean, String?) -> Unit) {
        scope.launch {
            try {
                val resp = BeekeepersAPIService.apiHives.delete(id)
                val ok = resp.responseCode == 200
                withContext(Dispatchers.Main) {
                    onResult(ok, if (ok) null else resp.message)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { onResult(false, e.message) }
            }
        }
    }

    fun getById(id: String, onResult: (Beehive?) -> Unit) {
        scope.launch {
            try {
                val resp = BeekeepersAPIService.apiHives.getById(id)
                val hive =
                    if (resp.responseCode == 200)
                        resp.data.firstOrNull()?.let { toEntity(it) }
                    else null

                withContext(Dispatchers.Main) { onResult(hive) }
            } catch (_: Exception) {
                withContext(Dispatchers.Main) { onResult(null) }
            }
        }
    }

    fun getByPerson(personId: String, onResult: (List<Beehive>) -> Unit) {
        scope.launch {
            try {
                val resp = BeekeepersAPIService.apiHives.getByPerson(personId)
                val list =
                    if (resp.responseCode == 200)
                        resp.data.map { toEntity(it) }
                    else emptyList()

                withContext(Dispatchers.Main) { onResult(list) }
            } catch (_: Exception) {
                withContext(Dispatchers.Main) { onResult(emptyList()) }
            }
        }
    }

    fun getByZone(zoneId: String, onResult: (List<Beehive>) -> Unit) {
        scope.launch {
            try {
                val resp = BeekeepersAPIService.apiHives.getByZone(zoneId)
                val list =
                    if (resp.responseCode == 200)
                        resp.data.map { toEntity(it) }
                    else emptyList()

                withContext(Dispatchers.Main) { onResult(list) }
            } catch (_: Exception) {
                withContext(Dispatchers.Main) { onResult(emptyList()) }
            }
        }
    }
}