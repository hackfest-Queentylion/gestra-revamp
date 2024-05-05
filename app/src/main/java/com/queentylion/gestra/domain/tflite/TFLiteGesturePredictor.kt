package com.queentylion.gestra.domain.tflite

interface TFLiteGesturePredictor {
    fun predict(gloveKeypoint: List<List<Int>>): String
}