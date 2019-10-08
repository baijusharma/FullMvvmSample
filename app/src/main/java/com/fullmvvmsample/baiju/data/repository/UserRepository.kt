package com.fullmvvmsample.baiju.data.repository


import com.fullmvvmsample.baiju.data.db.AppDatabase
import com.fullmvvmsample.baiju.data.db.entities.User
import com.fullmvvmsample.baiju.data.network.MyApi
import com.fullmvvmsample.baiju.data.network.SafeApiRequest
import com.fullmvvmsample.baiju.data.network.responses.AuthResponse

// Primary constructor for passing dependency
class UserRepository(private val myApi: MyApi, private val db: AppDatabase) : SafeApiRequest() {

    //.enqueue help us to perform asynchronous call (OLD Way)
/*
      fun userLogin(email: String, password: String): LiveData<String> {

          val loginResponse: MutableLiveData<String> = MutableLiveData()

          MyApi().userLogin(email, password).enqueue(object : Callback<ResponseBody> {
              override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                  loginResponse.value = t.message
              }

              override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                  if (response.isSuccessful) {
                      loginResponse.value = response.body()?.string()
                  } else {
                      loginResponse.value = response.errorBody()?.string()
                  }
              }
          })
          return loginResponse
      }*/
    //Above function using Coroutines way-- video-6 Simplified Coding

    suspend fun userLogin(email: String, password: String): AuthResponse {
        return apiRequest { myApi.userLogin(email, password) }
    }

    suspend fun userSignUp(name: String, email: String, password: String): AuthResponse {
        return apiRequest { myApi.userSignUp(name, email, password) }
    }

    suspend fun saveUser(user: User): Long {
        return db.getUserDao().upsertUser(user)
    }

    fun getUser() = db.getUserDao().getUser()
}



