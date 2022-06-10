package danp.lab05.jetpack_room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

class CEM_Repository(private val cemDao: CEM_Dao) {

    val allCEMS: LiveData<List<CEM>> = cemDao.getAllProducts()
    val searchCEM = MutableLiveData<CEM>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertCEM(newCEM: CEM) {
        coroutineScope.launch(Dispatchers.IO) {
            cemDao.insertCEM(newCEM)
        }
    }

    fun updateCEM(updatecem: CEM) {
        coroutineScope.launch(Dispatchers.IO) {
            cemDao.updateCEM(updatecem)
        }
    }

    fun deleteCEM(id: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            cemDao.deleteCEM(id)
        }
    }

    fun findCEM(id: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            searchCEM.value = asyncFind(id)?.await()
        }
    }

    private fun asyncFind(id: Int): Deferred<CEM>? =
        coroutineScope.async(Dispatchers.IO) {
            return@async cemDao.findProduct(id)
        }
}
