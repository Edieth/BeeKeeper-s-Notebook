package Data

import Entity.Beehive

interface IDataManager {
    fun add(behive: Beehive)
    fun update(behive: Beehive)
    fun remove(id: String)
    fun getAll(): List<Beehive>
    fun getById(id: String): Beehive?
    fun getByFullName(FullInfoBehive: String): Beehive?
}