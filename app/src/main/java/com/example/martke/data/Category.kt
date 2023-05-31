package com.example.martke.data

sealed class Category(val category:String){
    object Laptop: Category("Laptop")
    object Phone: Category("Phone")
    object Wifi: Category("Wifi")
    object SoundSystem: Category("Sound System")
    object Accessory: Category("Accessory")


}
