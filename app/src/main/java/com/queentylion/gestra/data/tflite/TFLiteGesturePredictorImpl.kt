package com.queentylion.gestra.data.tflite

import android.content.Context
import android.os.SystemClock
import android.util.Log
import com.queentylion.gestra.domain.tflite.TFLiteGesturePredictor
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.nnapi.NnApiDelegate
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class TFLiteGesturePredictorImpl(
    private var numThreads: Int = 2, // Change to 4 maybe
    private var currentDelegate: Delegate = Delegate.CPU, /// Try use NNApi
    private var currentModel: Int = MODEL_INT8,
    private val context: Context,
) : TFLiteGesturePredictor {
    enum class Delegate {
        CPU, GPU, NNAPI
    }

    companion object {
        const val MODEL_INT8 = 0
        private const val TAG = "Sign Lang Classifier"
    }

    private var interpreterPredict: Interpreter? = null
    private var inputPredictTargetWidth = 0
    private var inputPredictTargetHeight = 0
    private var inputTransformTargetWidth = 0
    private var inputTransformTargetHeight = 0
    private var outputPredictShape = intArrayOf()

    private val predictedLabels =
        arrayOf(
            "halo",
            "saudara",
            "aku",
            "apa",
            "beli",
            "jadi",
            "kabar",
            "kita",
            "makan",
            "sehat",
            "NONE"
        )

    init {
        setupStyleTransfer()
        interpreterPredict!!.let { interpreter ->
            inputPredictTargetHeight = interpreter.getInputTensor(0).shape()[1]
            inputPredictTargetWidth = interpreter.getInputTensor(0).shape()[2]
            outputPredictShape = interpreter.getOutputTensor(0).shape()
        }

    }

    private fun setupStyleTransfer() {
        val tfliteOption = Interpreter.Options()
        tfliteOption.setNumThreads(numThreads)

        when (currentDelegate) {
            Delegate.CPU -> Unit        // Default
            Delegate.GPU -> {
//                if (CompatibilityList().isDelegateSupportedOnThisDevice) {
//                    tfliteOption.addDelegate(GpuDelegate())
//                } else {
//                    error("GPU is not supported on this device")
//                }
            }

            Delegate.NNAPI -> tfliteOption.addDelegate(NnApiDelegate())
        }
        val modelPredict: String = if (currentModel == MODEL_INT8) {
            "model_sibi.tflite"
        } else {
            "model_sibi.tflite"
        }

        try {
            interpreterPredict = Interpreter(
                FileUtil.loadMappedFile(context, modelPredict), tfliteOption
            )

        } catch (e: Exception) {
            Log.e(TAG, "TFLite failed to load model with error: " + e.message)
            error("Style transfer failed to initialize. See error logs for details")
        }
    }

    override fun predict(gloveKeypoint: List<List<Int>>): String {
        val startTime = System.currentTimeMillis()

        // Flatten the glove keypoints and convert them to float
        val flatInputArray = gloveKeypoint.flatten().map { it.toFloat() }.toFloatArray()

        // Ensure the size of the flatInputArray matches the expected shape
        val expectedInputSize = inputPredictTargetHeight * inputPredictTargetWidth
        if (flatInputArray.size != expectedInputSize) {
            error("Size of input array (${flatInputArray.size}) does not match the expected shape ($inputTransformTargetHeight x $inputTransformTargetWidth)")
        }

        // Reshape the input array to match the expected input shape
        val reshapedInputArray = flatInputArray.copyOf()

        // Create TensorBuffer from the reshaped input array
        val inputDataType = DataType.FLOAT32
        val inputTensorBuffer = TensorBuffer.createFixedSize(
            intArrayOf(
                inputPredictTargetHeight,
                inputPredictTargetWidth
            ), inputDataType
        )
        inputTensorBuffer.loadArray(reshapedInputArray)

        // Create TensorBuffer for the output
        val predictOutput = TensorBuffer.createFixedSize(outputPredictShape, DataType.FLOAT32)

        // Run inference
        interpreterPredict?.run(inputTensorBuffer.buffer, predictOutput.buffer)

        val endTime = System.currentTimeMillis()
        val inferenceTime = endTime - startTime

        Log.d("TIME ML", inferenceTime.toString())

        // Return the predicted label
        return getOutputString(predictOutput)
    }


    fun clearStyleTransferUtil() {
        interpreterPredict = null
    }

    private fun getOutputString(
        output: TensorBuffer,
    ): String {
        val maxIndexFeature0 = indexOfMaxValue(output.floatArray)
        return predictedLabels[maxIndexFeature0]
    }

    private fun indexOfMaxValue(arr: FloatArray): Int {
        var maxIndex = -1
        var maxValue = Float.MIN_VALUE

        for (i in arr.indices) {
            if (arr[i] > maxValue) {
                maxValue = arr[i]
                maxIndex = i
            }
        }

        Log.d("SIBIML", maxValue.toString())
        return maxIndex
    }

}