package com.fungiggle.lexilink.data.chapter

import javax.inject.Inject

class ChapterRepo @Inject constructor(private val dao:ChapterDao) {
    suspend fun listChapters(completed: Int) = dao.listChapters(completed)
    suspend fun update(chapter: Chapter) = dao.update(chapter)
    suspend fun getChapter(chaptercompleted: Boolean) = dao.getChapter(chaptercompleted)
}