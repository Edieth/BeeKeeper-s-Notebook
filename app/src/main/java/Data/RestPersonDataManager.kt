package Data

import Entity.DTOPerson
import Entity.Person
import Util.BeekeepersAPIService
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class RestPersonDataManager : IDataManager<Person> {

    private val scope = CoroutineScope(Dispatchers.IO)

    // ---------- helpers: Bitmap <-> Base64 ----------
    private fun bitmapToBase64(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos)
        return Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP)
    }

    private fun base64ToBitmap(b64: String): Bitmap? {
        return try {
            if (b64.isBlank()) return null
            val bytes = Base64.decode(b64, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        } catch (_: Exception) {
            null
        }
    }

    // ---------- mapper inline (Entity <-> DTO) ----------
    private fun toDTO(p: Person): DTOPerson {
        val b64 = when {
            p.photoBase64.isNotBlank() -> p.photoBase64
            p.Photo != null -> bitmapToBase64(p.Photo!!)
            else -> ""
        }

        return DTOPerson(
            id = p.ID,
            name = p.Name,
            fLastName = p.FLastName,
            sLastName = p.SLastName,
            email = p.Email,
            password = p.Password,
            phonePerson = p.PhonePerson,
            photoBase64 = b64
        )
    }

    private fun toEntity(dto: DTOPerson): Person {
        val person = Person()
        person.ID = dto.id
        person.Name = dto.name
        person.FLastName = dto.fLastName
        person.SLastName = dto.sLastName
        person.Email = dto.email
        person.Password = dto.password
        person.PhonePerson = dto.phonePerson
        person.photoBase64 = dto.photoBase64
        person.Photo = base64ToBitmap(dto.photoBase64)
        return person
    }

    // ---------- CRUD REST ----------
    override fun add(item: Person, onResult: (Boolean, String?) -> Unit) {
        scope.launch {
            try {
                val resp = BeekeepersAPIService.apiPerson.create(toDTO(item))
                val ok = resp.responseCode == 200
                withContext(Dispatchers.Main) {
                    onResult(ok, if (ok) null else resp.message)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { onResult(false, e.message) }
            }
        }
    }

    override fun update(item: Person, onResult: (Boolean, String?) -> Unit) {
        scope.launch {
            try {
                val id = item.ID
                if (id.isBlank()) {
                    withContext(Dispatchers.Main) {
                        onResult(false, "ID vacío, no se puede actualizar")
                    }
                    return@launch
                }

                val resp = BeekeepersAPIService.apiPerson.update(id, toDTO(item))
                val ok = resp.responseCode == 200
                withContext(Dispatchers.Main) {
                    onResult(ok, if (ok) null else resp.message)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { onResult(false, e.message) }
            }
        }
    }

    override fun delete(id: String, onResult: (Boolean, String?) -> Unit) {
        scope.launch {
            try {
                val resp = BeekeepersAPIService.apiPerson.delete(id)
                val ok = resp.responseCode == 200
                withContext(Dispatchers.Main) {
                    onResult(ok, if (ok) null else resp.message)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { onResult(false, e.message) }
            }
        }
    }

    override fun getAll(onResult: (List<Person>) -> Unit) {
        scope.launch {
            try {
                val resp = BeekeepersAPIService.apiPerson.getAll()
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

    override fun getById(id: String, onResult: (Person?) -> Unit) {
        scope.launch {
            try {
                val resp = BeekeepersAPIService.apiPerson.getById(id)
                val person =
                    if (resp.responseCode == 200)
                        resp.data.firstOrNull()?.let { toEntity(it) }
                    else null

                withContext(Dispatchers.Main) { onResult(person) }
            } catch (_: Exception) {
                withContext(Dispatchers.Main) { onResult(null) }
            }
        }
    }

    // 🔹 Estilo Census: filtrar en app
    override fun getByFullName(fullname: String, onResult: (Person?) -> Unit) {
        getAll { list ->
            val found = list.firstOrNull {
                it.FullName().equals(fullname, ignoreCase = true)
            }
            onResult(found)
        }
    }
}
