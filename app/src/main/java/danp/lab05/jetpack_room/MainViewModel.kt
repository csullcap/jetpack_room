package danp.lab05.jetpack_room

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(application: Application) : ViewModel() {

    val allCEMS: LiveData<List<CEM>>
    private val repository: CEM_Repository
    val searchCEM: MutableLiveData<CEM>

    init {
        val CEMDb = CEM_RoomDatabase.getInstance(application)
        val CEM_Dao = CEMDb.CEMDao()
        repository = CEM_Repository(CEM_Dao)

        allCEMS = repository.allCEMS
        searchCEM = repository.searchCEM
    }

    fun insertCEM(cem: CEM) {
        repository.insertCEM(cem)
    }

    fun findCEM(id: Int) {
        repository.findCEM(id)
    }

    fun deleteCEM(id: Int) {
        repository.deleteCEM(id)
    }

}