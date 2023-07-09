package com.fungiggle.lexilink.ui.screens.gameview

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fungiggle.lexilink.models.KeyPadButton
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class GameScreenViewModel @Inject constructor(): ViewModel() {
    val lettersList = mutableStateListOf<KeyPadButton>()

    //wordtopreview
    private var _wordtopreview:MutableLiveData<String> = MutableLiveData("")
    val wordtopreview:LiveData<String> = _wordtopreview

    fun updateWordToPreview(label:String){
        _wordtopreview.value += label
    }
    fun clearWordToPreview(){
        _wordtopreview.value = ""
    }
    init {
        lettersList.add(KeyPadButton(UUID.randomUUID().toString(),"A", Offset(0f,0f),0f))
        lettersList.add(KeyPadButton(UUID.randomUUID().toString(),"B", Offset(0f,0f),0f))
        lettersList.add(KeyPadButton(UUID.randomUUID().toString(),"C", Offset(0f,0f),0f))
        lettersList.add(KeyPadButton(UUID.randomUUID().toString(),"D", Offset(0f,0f),0f))
    }

}