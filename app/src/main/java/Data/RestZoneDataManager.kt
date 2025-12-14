package Data

import Entity.DTOZone
import Entity.Person
import Entity.Zone
import Util.BeekeepersAPIService
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RestZoneDataManager {

    // Eliminamos la variable 'context' porque no se usa para nada aquí.
    private var personId: String = ""

    // Constructor principal que usa tu Controller
    constructor(context: Context, person: Person) {
        // Ignoramos el context, solo guardamos el ID
        this.personId = person.ID
    }

    // Constructor vacío por si lo necesitas instanciar sin datos al inicio
    constructor() {
        this.personId = ""
    }

    private val scope = CoroutineScope(Dispatchers.IO)

    // Convertir de Entidad (App) a DTO (Backend)
    private fun toDTO(zone: Zone): DTOZone {
        return DTOZone(
            id = zone.ID,
            // Si la zona ya tiene personID úsalo, si no, usa el del Manager
            personID = if (zone.PersonID.isNotEmpty()) zone.PersonID else personId,
            name = zone.Name,
            state = zone.State,
            district = zone.District,
            address = zone.Address,
            latitude = zone.Latitude,
            longitude = zone.Longitude
        )
    }

    // Convertir de DTO (Backend) a Entidad (App)
    private fun toEntity(dto: DTOZone): Zone {
        val zone = Zone()
        zone.ID = dto.id
        zone.PersonID = dto.personID
        zone.Name = dto.name
        zone.State = dto.state
        zone.District = dto.district
        zone.Address = dto.address
        zone.Latitude = dto.latitude
        zone.Longitude = dto.longitude
        return zone
    }

    fun add(zone: Zone, onResult: (Boolean, String?) -> Unit) {
        scope.launch {
            try {
                // Asegurar que el ID viaje
                if (zone.PersonID.isEmpty()) zone.PersonID = personId

                val resp = BeekeepersAPIService.apiZone.create(toDTO(zone))
                val ok = resp.responseCode == 200
                withContext(Dispatchers.Main) { onResult(ok, if (ok) null else resp.message) }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { onResult(false, "Error: ${e.message}") }
            }
        }
    }

    fun update(zone: Zone, onResult: (Boolean, String?) -> Unit) {
        scope.launch {
            try {
                val resp = BeekeepersAPIService.apiZone.update(zone.ID, toDTO(zone))
                val ok = resp.responseCode == 200
                withContext(Dispatchers.Main) { onResult(ok, if (ok) null else resp.message) }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { onResult(false, "Error: ${e.message}") }
            }
        }
    }

    fun delete(id: String, onResult: (Boolean, String?) -> Unit) {
        scope.launch {
            try {
                val resp = BeekeepersAPIService.apiZone.delete(id)
                val ok = resp.responseCode == 200
                withContext(Dispatchers.Main) { onResult(ok, if (ok) null else resp.message) }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { onResult(false, "Error: ${e.message}") }
            }
        }
    }

    fun getByPerson(personId: String, onResult: (List<Zone>) -> Unit) {
        scope.launch {
            try {
                val resp = BeekeepersAPIService.apiZone.getByPerson(personId)
                val list = if (resp.responseCode == 200) resp.data.map { toEntity(it) } else emptyList()
                withContext(Dispatchers.Main) { onResult(list) }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { onResult(emptyList()) }
            }
        }
    }
}