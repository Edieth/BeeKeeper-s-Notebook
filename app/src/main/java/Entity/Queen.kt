package Entity
import java.time.LocalDate

class Queen {

    private var id: String = ""
    private var type: String = ""
    private lateinit var entryDate: LocalDate

    constructor()

    constructor(id: String, type: String, entryDate: LocalDate) {
        this.id = id
        this.type = type
        this.entryDate = entryDate
    }

    var ID: String
        get() = this.id
        set(value) { this.id = value }

    var Type: String
        get() = this.type
        set(value) { this.type = value }

    var EntryDate: LocalDate
        get() = this.entryDate
        set(value) { this.entryDate = value }

    fun FullInfoQueen(): String = "$this.type $this.entryDate"
}
