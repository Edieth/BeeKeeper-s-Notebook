package Controller
import Data.IDataManager
import Data.MemoryDataManager
import Entity.Zone
import android.content.Context
import cr.ac.utn.beekeepersnotebook.R

class ZoneController {
    private var dataManager: IDataManager = MemoryDataManager
    private var context: Context

    constructor(context: Context){
        this.context=context
    }
        fun addZone(zone: Zone) {
            try {
                dataManager.add(zone)
            } catch (e: Exception) {
                throw Exception(context.getString(R.string.ErrorMsgAdd))
            }
        }

        fun updateZone(zone: Zone) {
            try {
                dataManager.update(zone)
            } catch (e: Exception) {
                throw Exception(context.getString(R.string.ErrorMsgUpdate))
            }
        }

        fun getById(id: String): Zone? {
            try {
                return dataManager.getById(id)
            } catch (e: Exception) {
                throw Exception(context.getString(R.string.ErrorMsgGetById))
            }
        }

        fun getAll(): List<Zone> = dataManager.getAll()

        fun removeZone(id: String) {
            try {
                val r = dataManager.getById(id)
                if (r == null) throw Exception(context.getString(R.string.MsgDataNoFound))
                dataManager.remove(id)
            } catch (e: Exception) {
                throw Exception(context.getString(R.string.ErrorMsgRemove))
            }
        }
    }




}