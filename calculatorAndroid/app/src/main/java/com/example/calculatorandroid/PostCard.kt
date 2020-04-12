package com.example.calculatorandroid

import android.net.Uri
import android.widget.ImageView
import com.google.gson.annotations.SerializedName

class PostCard {
    @SerializedName("imageUri")
    var imageUri : String = ""
    @SerializedName("title")
    var title : String = "Title"
    @SerializedName("description")
    var description : String = "Description"
    constructor(imageUri : String, title : String, description : String)
    {
        this.imageUri = imageUri
        this.title = title
        this.description = description
    }
    override fun toString() : String
    {
        return "Title: " + title + "; Description: " + description + "; " + "Uri: " + imageUri;
    }

}