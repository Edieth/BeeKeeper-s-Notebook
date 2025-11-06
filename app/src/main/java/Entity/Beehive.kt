package Entity
import android.graphics.Bitmap
import Entity.Province
import java.time.LocalDate


class Beehive {

    private var id: String = ""
    private var name: String = ""
    private var boxType: String = ""
    private var queenId: String = ""
    private var zoneId: String = ""
    private var state: String = ""
    private var district: String = ""
    private var address: String = ""
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var photo: Bitmap? = null

    constructor()

    constructor(
        id: String, name: String, boxType: String,
        queenId: String, zoneId: String, state: String,
        district: String, address: String, latitude: Double,
        longitude: Double, photo: Bitmap?
    ) {
        this.id = id
        this.name = name
        this.boxType = boxType
        this.queenId = queenId
        this.zoneId = zoneId
        this.state = state
        this.district = district
        this.address = address
        this.latitude = latitude
        this.longitude = longitude
        this.photo = photo
    }

    var ID: String
        get() = this.id
        set(value) {
            this.id = value
        }

    var Name: String
        get() = this.name
        set(value) {
            this.name = value
        }

    var BoxType: String
        get() = this.boxType
        set(value) {
            this.boxType = value
        }

    var QueenID: String
        get() = this.queenId
        set(value) {
            this.queenId = value
        }

    var ZoneID: String
        get() = this.zoneId
        set(value) {
            this.zoneId = value
        }

    var State: String
        get() = this.state
        set(value) {
            this.state = value
        }

    var District: String
        get() = this.district
        set(value) {
            this.district = value
        }

    var Address: String
        get() = this.address
        set(value) {
            this.address = value
        }

    var Latitude: Double
        get() = this.latitude
        set(value) {
            this.latitude = value
        }

    var Longitude: Double
        get() = this.longitude
        set(value) {
            this.longitude = value
        }

    var Photo: Bitmap?
        get() = this.photo
        set(value) {
            this.photo = value
        }

    fun FullInfo(): String =
        "$this.name $this.boxType $this.queenId $this.zoneId"
}

