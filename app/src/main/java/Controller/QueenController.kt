package Controller

import Data.RestQueenDataManager
import Entity.Queen
import android.content.Context
import cr.ac.utn.beekeepersnotebook.R

class QueenController(private val context: Context, private val personId: String) {


    private val dataManager = RestQueenDataManager(personId)

    fun addQueen(queen: Queen, onResult: (Boolean, String?) -> Unit) {
        queen.PersonID = personId

        dataManager.add(queen) { ok, error ->
            if (!ok) {
                onResult(false, context.getString(R.string.ErrorMsgAdd) + " ${error ?: ""}")
            } else {
                onResult(true, null)
            }
        }
    }

    fun updateQueen(queen: Queen, onResult: (Boolean, String?) -> Unit) {

        queen.PersonID = personId

        dataManager.update(queen) { ok, error ->
            if (!ok) {
                onResult(false, context.getString(R.string.ErrorMsgUpdate) + " ${error ?: ""}")
            } else {
                onResult(true, null)
            }
        }
    }

    fun deleteQueen(id: String, onResult: (Boolean, String?) -> Unit) {
        dataManager.delete(id) { ok, error ->
            if (!ok) {
                onResult(false, context.getString(R.string.ErrorMsgRemove) + " ${error ?: ""}")
            } else {
                onResult(true, null)
            }
        }
    }

    fun getByPerson(onResult: (List<Queen>) -> Unit) {
        dataManager.getByPerson(onResult)
    }

    fun getById(id: String, onResult: (Queen?) -> Unit) {
        dataManager.getById(id, onResult)
    }

    fun getByHive(hiveId: String, onResult: (List<Queen>) -> Unit) {
        dataManager.getByHive(hiveId, onResult)
    }
}