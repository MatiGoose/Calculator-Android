package com.example.calculatorandroid

import android.net.Uri
import android.widget.ImageView

class PostCard {
    var imageUri : Uri? = null
    var title : String = "Title"
    var description : String = "Description"
    constructor(imageUri : Uri, title : String, description : String)
    {
        this.imageUri = imageUri
        this.title = title
        this.description = description
    }
}