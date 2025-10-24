package Data

import Entity.Beehive

interface IDataManager {
    fun add(person: Beehive)
    fun update(person: Beehive)
    fun remove(id: String)
    fun getAll(): List<Beehive>
    fun getById(id: String): Beehive?
    fun getByFullName(FullInfoBehive: String): Beehive?
}