package com.fungiggle.lexilink.data.solution

import javax.inject.Inject

class SolutionRepo @Inject constructor(val dao:SolutionDao) {
    suspend fun list(levelid:String) = dao.list(levelid)
}