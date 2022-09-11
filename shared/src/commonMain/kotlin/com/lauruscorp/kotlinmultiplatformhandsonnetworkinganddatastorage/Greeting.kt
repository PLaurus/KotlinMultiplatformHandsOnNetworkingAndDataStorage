package com.lauruscorp.kotlinmultiplatformhandsonnetworkinganddatastorage

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}