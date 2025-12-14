package Entity
import com.google.firebase.firestore.Exclude
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


class Queen {

    private var id: String = ""
    private var hiveId: String = ""
    private var personId: String = ""     // ya estaba, ahora lo exponemos
    private var zoneID: String = ""
    private var typeInternal: String = ""
    var entryDateStr: String = ""

    constructor()

    constructor(id: String, entryDateStr: String, type: String, hiveId: String, zoneID: String) {
        this.id = id
        this.typeInternal = type
        this.hiveId = hiveId
        this.entryDateStr = entryDateStr
        this.zoneID = zoneID
    }

    @get:Exclude
    var EntryDate: LocalDate
        get() {
            return try {
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                LocalDate.parse(entryDateStr, formatter)
            } catch (e: Exception) {
                LocalDate.now()
            }
        }
        set(value) {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            this.entryDateStr = value.format(formatter)
        }

    var ID: String
        get() = id
        set(value) { id = value }

    var Type: String
        get() = typeInternal
        set(value) { typeInternal = value }

    var HiveID: String
        get() = hiveId
        set(value) { hiveId = value }

    var ZoneID: String
        get() = zoneID
        set(value) { zoneID = value }

    // ✅ NUEVO: para el API
    var PersonID: String
        get() = personId
        set(value) { personId = value }

    fun setEntryDateFromString(dateStr: String) {
        try {
            val parser = DateTimeFormatter.ofPattern("d/M/yyyy")
            val date = LocalDate.parse(dateStr, parser)
            val formatterOut = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            this.entryDateStr = date.format(formatterOut)
        } catch (e: Exception) {
            val today = LocalDate.now()
            val formatterOut = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            this.entryDateStr = today.format(formatterOut)
        }
    }

    fun getEntryDateAsString(): String = entryDateStr

    fun AgeText(): String {
        val today = LocalDate.now()
        val start = EntryDate
        if (start.isAfter(today)) return "0 días"

        val p: Period = Period.between(start, today)
        val years  = p.years
        val months = p.months
        val days   = p.days

        if (years == 0 && months == 0) {
            return when (days) {
                0 -> "0 días"
                1 -> "1 día"
                else -> "$days días"
            }
        }

        val partes = mutableListOf<String>()
        if (years > 0)  partes += if (years == 1) "1 año" else "$years años"
        if (months > 0) partes += if (months == 1) "1 mes" else "$months meses"
        if (days > 0)   partes += if (days == 1) "1 día" else "$days días"

        return when (partes.size) {
            1 -> partes[0]
            2 -> partes[0] + " y " + partes[1]
            else -> partes[0] + ", " + partes[1] + " y " + partes[2]
        }
    }

    fun AgeInMonths(): Int {
        val today = LocalDate.now()
        val start = EntryDate
        if (start.isAfter(today)) return 0

        val p = Period.between(start, today)
        return p.years * 12 + p.months
    }

    fun FullInfoQueen(): String =
        "${this.Type} ${this.ZoneID} ${this.getEntryDateAsString()} ${this.HiveID} ${this.ID} ${this.AgeInMonths()}"

    override fun toString(): String = FullInfoQueen()
}