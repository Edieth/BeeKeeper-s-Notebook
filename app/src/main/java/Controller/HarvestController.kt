package Controller

import cr.ac.utn.beekeepersnotebook.R
import Data.IDataManager
import Data.MemoryDataManager
import Entity.HarvestRecord
import android.content.Context

class HarvestController {
    private var dataManager: IDataManager = MemoryDataManager
    private var context: Context

    constructor(context: Context){
        this.context=context
    }
    private var dataManager: IDataManager = MemoryDataManager

    fun addHarvest(record: HarvestRecord) {
        try {
            dataManager.add(record)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgAdd))
        }
    }

    fun updateHarvest(record: HarvestRecord) {
        try {
            dataManager.update(record)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgUpdate))
        }
    }

    fun getById(id: String): HarvestRecord? {
        try {
            return dataManager.getById(id)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgGetById))
        }
    }

    fun getByBeehiveId(beehiveId: String): HarvestRecord? {
        try {
            return dataManager.getByBeehiveId(beehiveId)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgGetById))
        }
    }

    fun removeHarvest(id: String) {
        try {
            val result = dataManager.getById(id)
            if (result == null) {
                throw Exception(context.getString(R.string.MsgDataNoFound))
            }
            dataManager.remove(id)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgRemove))
        }
    }
}