package Entity

import java.time.LocalDate

class HarvestRecord {

    private var id: String = ""
    private lateinit var date: LocalDate
    private var beehiveId: String = ""
    private var honeyFrames: Int = 0
    private var honeyAmountKg: Double = 0.0

    constructor()

    constructor(
        id: String,
        date: LocalDate,
        beehiveId: String,
        honeyFrames: Int,
        honeyAmountKg: Double
    ) {
        this.id = id
        this.date = date
        this.beehiveId = beehiveId
        this.honeyFrames = honeyFrames
        this.honeyAmountKg = honeyAmountKg
    }

    var ID: String
        get() = this.id
        set(value) { this.id = value }

    var DateHarvest: LocalDate
        get() = this.date
        set(value) { this.date = value }

    var BeehiveID: String
        get() = this.beehiveId
        set(value) { this.beehiveId = value }

    var HoneyFramesHarvest: Int
        get() = this.honeyFrames
        set(value) { this.honeyFrames = value }

    var HoneyAmountKgHarvest: Double
        get() = this.honeyAmountKg
        set(value) { this.honeyAmountKg = value }

    fun FullInfoHarvest(): String =
        "$this.date $this.beehiveId $this.honeyFrames $this.honeyAmountKg"
}