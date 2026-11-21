package com.kampus.praktikumpemob

class AdviceRepository {
    suspend fun getAdvice() : AdviceRespon {
        return RetrofitInstance.api.getAdvice()
    }

}