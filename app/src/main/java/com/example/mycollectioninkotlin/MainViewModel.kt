package com.example.mycollectioninkotlin



import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycollectioninkotlin.api.CovidApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class MainViewModel: ViewModel() {
    // Create a Flow of List<Province>
    private val _dataFlow = MutableStateFlow<List<Province>>(emptyList())
    val dataFlow: StateFlow<List<Province>> = _dataFlow

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
                try {
                    getProvince()

                }catch (ex: Exception){
                    return@launch
                }
        }
    }

    private suspend fun getProvince(){
        withContext(Dispatchers.IO){
            try {
                Log.d("getProvince", "Calling retrofit")
                val responseObject = CovidApi.retrofitService.getProvinces("CHN")
                if(responseObject.isSuccessful) {
                    responseObject.body()?.let {
                        Log.d("Response Body", "Forming jsonObj")
                        val jsonObj = JSONObject(it.string())
                       val responseList = parseProvinceJsonResult(jsonObj)
                        _dataFlow.emit(responseList)
                        Log.d("After Parsing", responseList.toString())
                    }
                }else{
                    Log.d("Call not Successful", "${responseObject.errorBody()}")
                }
            }
            catch (e: Exception){
                return@withContext
            }
        }
    }

    private fun parseProvinceJsonResult(result: JSONObject): ArrayList<Province>{
        Log.d("Parsing", "Parsing for Array")
        val provinceObject = result.getJSONArray("data")
        val provinceList = ArrayList<Province>()
        for(i in 0 until provinceObject.length()){
            val provinceJson = provinceObject.getJSONObject(i)
            provinceList.add(Province(provinceJson.getString("iso"),
                provinceJson.getString("name"),
                provinceJson.getString("province"),
                provinceJson.getString("lat"),
                provinceJson.getString("long")))

        }

        return provinceList
    }
}