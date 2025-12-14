package Data

import Entity.HarvestDTO
import Entity.HarvestRecord
import Util.BeekeepersAPIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RestHarvestDataManager(private var personId: String) {

    private val scope = CoroutineScope(Dispatchers.IO)

    // ==========================================
    // MAPPERS (Traductor App <-> API)
    // ==========================================

    // De App (Record) a API (DTO)
    private fun toDTO(h: HarvestRecord): HarvestDTO {
        return HarvestDTO(
            id = h.ID,
            beehiveId = h.BeehiveID,
            zoneId = h.ZoneID,
            // Mapeamos los nombres de la App a los del DTO
            dateHarvest = h.DateHarvestHoney,
            honeyFrames = h.HoneyFramesHarvest,
            honeyAmountKg = h.HoneyAmountKgHarvest,
            honeyAmountKgNeta = h.HoneyAmountKgNetaHarvest,
            personId = personId
        )
    }

    // De API (DTO) a App (Record)
    private fun toEntity(dto: HarvestDTO): HarvestRecord {
        val h = HarvestRecord()
        h.ID = dto.id ?: ""
        h.BeehiveID = dto.beehiveId ?: ""
        h.ZoneID = dto.zoneId ?: ""
        h.DateHarvestHoney = dto.dateHarvest ?: ""
        h.HoneyFramesHarvest = dto.honeyFrames ?: 0
        h.HoneyAmountKgHarvest = dto.honeyAmountKg ?: 0.0
        h.HoneyAmountKgNetaHarvest = dto.honeyAmountKgNeta ?: 0.0
        return h
    }

    // ==========================================
    // CRUD
    // ==========================================

    fun add(item: HarvestRecord, onResult: (Boolean, String?) -> Unit) {
        scope.launch {
            try {
                // Usamos apiHarvest (creada en paso 3)
                val resp = BeekeepersAPIService.apiHarvest.create(toDTO(item))
                val ok = resp.responseCode == 200
                withContext(Dispatchers.Main) { onResult(ok, if (ok) null else resp.message) }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { onResult(false, e.message) }
            }
        }
    }

    fun update(item: HarvestRecord, onResult: (Boolean, String?) -> Unit) {
        scope.launch {
            try {
                if (item.ID.isEmpty()) {
                    withContext(Dispatchers.Main) { onResult(false, "ID vacío") }
                    return@launch
                }
                val resp = BeekeepersAPIService.apiHarvest.update(item.ID, toDTO(item))
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
                val resp = BeekeepersAPIService.apiHarvest.delete(id)
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

    fun getByPerson(onResult: (List<HarvestRecord>) -> Unit) {
        scope.launch {
            try {
                val resp = BeekeepersAPIService.apiHarvest.getByPerson(personId)
                val list = if (resp.responseCode == 200)
                    resp.data.map { toEntity(it) }
                else emptyList()
                withContext(Dispatchers.Main) { onResult(list) }
            } catch (_: Exception) {
                withContext(Dispatchers.Main) { onResult(emptyList()) }
            }
        }
    }

    fun getById(id: String, onResult: (HarvestRecord?) -> Unit) {
        scope.launch {
            try {
                val resp = BeekeepersAPIService.apiHarvest.getById(id)
                val item = if (resp.responseCode == 200)
                    resp.data.firstOrNull()?.let { toEntity(it) }
                else null
                withContext(Dispatchers.Main) { onResult(item) }
            } catch (_: Exception) {
                withContext(Dispatchers.Main) { onResult(null) }
            }
        }
    }
}