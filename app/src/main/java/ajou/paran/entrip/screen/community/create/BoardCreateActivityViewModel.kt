package ajou.paran.entrip.screen.community.create

import ajou.paran.entrip.repository.Impl.CommunityRepositoryImpl
import ajou.paran.entrip.repository.network.dto.community.RequestPost
import ajou.paran.entrip.repository.network.dto.community.ResponseFindByIdPhoto
import ajou.paran.entrip.util.buildImageBodyPart
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.*
import javax.inject.Inject

@HiltViewModel
class BoardCreateActivityViewModel
@Inject
constructor(
    private val sharedPreferences: SharedPreferences,
    private val communityRepositoryImpl: CommunityRepositoryImpl
) : ViewModel() {
    companion object {
        private const val TAG = "[BCActVM]"
    }

    //region private variable

    private val _photoIdList: MutableLiveData<List<Long>> = MutableLiveData()
    private val _photoList: MutableLiveData<List<ResponseFindByIdPhoto>> = MutableLiveData()
    private val _postId: MutableLiveData<Long> = MutableLiveData()
    private val photoMap: HashMap<Long, Long> = hashMapOf()

    //endregion

    //region public variable

    val userId: String
        get() = sharedPreferences.getString("user_id", null) ?: ""
    val photoIdList: LiveData<List<Long>>
        get() = _photoIdList
    val photoList: LiveData<List<ResponseFindByIdPhoto>>
        get() = _photoList
    val postId: LiveData<Long>
        get() = _postId

    //endregion

    fun postPhoto(file: File, priority: Long = 1L) = CoroutineScope(Dispatchers.IO).launch {
        val photoId = communityRepositoryImpl.savePhoto(priority, file.buildImageBodyPart())
        photoMap[priority] = photoId
        _photoIdList.postValue(photoMap.values.toList())
        Log.d(TAG, "photoID: $photoId, Priority: $priority")
    }

    fun postPost(title: String, context: String) = CoroutineScope(Dispatchers.IO).launch {
        val postId = communityRepositoryImpl.savePost(
            RequestPost(
                title = title,
                content = context,
                author = userId,
                photoIdList = photoMap.values.toList()
            )
        )
        _postId.postValue(postId)
    }

    fun findPhotos(list: List<Long>) = CoroutineScope(Dispatchers.IO).launch {
        val mutableList: MutableList<ResponseFindByIdPhoto> = mutableListOf()
        for (photoId in list.sorted()){
            val photo = communityRepositoryImpl.findByIdPhoto(photoId = photoId)
            mutableList.add(photo)
        }
        _photoList.postValue(mutableList.toList())
    }

    fun getFileName() = "${System.currentTimeMillis()}_${userId}_image.png"
//    fun getFileName() = "${System.currentTimeMillis()}_image.png"

    fun mapClear() = photoMap.clear()
}