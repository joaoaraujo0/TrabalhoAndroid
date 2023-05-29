package com.unaerp.trabalhoandroid

import com.google.firebase.firestore.FirebaseFirestore

object  FirestoreSingleton {
    private var instance: FirebaseFirestore? = null

    fun getInstance(): FirebaseFirestore {
        if (instance == null) {
            instance = FirebaseFirestore.getInstance()
        }
        return instance!!
    }
}