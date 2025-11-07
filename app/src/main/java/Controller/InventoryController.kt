package Controller
import android.content.Context
import Data.IDataManager
import Data.MemoryDataManager
import Entity.InventoryItem
import java.lang.Exception
import cr.ac.utn.beekeepersnotebook.R



class InventoryController {
    private var dataManager: IDataManager = MemoryDataManager
    private  var context: Context

    constructor(context: Context){
        this.context=context
    }
    fun addItem(item: InventoryItem) {
        try {
            dataManager.add(item)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgAdd))
        }
    }

    fun updateItem(item: InventoryItem) {
        try {
            dataManager.update(item)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgUpdate))
        }
    }

    fun getById(id: String): InventoryItem? {
        try {
            return dataManager.getById(id)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgGetById))
        }
    }

    fun getByName(name: String): InventoryItem? {
        try {
            return dataManager.getByName(name)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgGetById))
        }
    }

    fun removeItem(id: String) {
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