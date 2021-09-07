package com.android.framework.mvvm.utilities

import android.util.Log

object DebugHelperUtility {
    /** The mode debug.  */
    private val MODE_DEBUG = true

    /** The mode debug.  */
    private val MODE_TRACK = true

    /** The Constant TAG.  */
    private val TAG = "DebugHelper"

    /**
     * Prints the and track exception.
     *
     * @param TAG
     * the tag
     * @param error
     * the exception
     */
    fun printError(TAG: String, error: Error) {

        if (MODE_DEBUG) {
            error(TAG, "Error = $error")
            error.printStackTrace()
            if (MODE_TRACK) {
                //BugSenseHandler.sendEvent("Error = " + error.toString());
            }
        }
    }

    fun printError(error: Error) {

        if (MODE_DEBUG) {
            error(TAG, "Error = $error")
            error.printStackTrace()
            if (MODE_TRACK) {
                //BugSenseHandler.sendEvent("Error = " + error.toString());
            }
        }
    }

    fun printError(tag: String, error: String) {
        if (MODE_DEBUG) {
            error(TAG, error)
        }
    }

    fun printInfo(message: String) {
        if (MODE_DEBUG) {
            info(TAG, "Info = $message")
            if (MODE_TRACK) {
                //BugSenseHandler.sendEvent("Error = " + error.toString());
            }
        }
    }

    fun printInfo(tag: String, message: String) {
        if (MODE_DEBUG) {
            info(tag, "Info = $message")
            if (MODE_TRACK) {
                //BugSenseHandler.sendEvent("Error = " + error.toString());
            }
        }
    }

    /**
     * Prints the and track exception.
     *
     * @param exception
     * the exception
     */
    fun trackException(exception: Exception) {

        print(TAG, exception, true)
    }

    /**
     * Prints the and track exception.
     *
     * @param TAG
     * the tag
     * @param exception
     * the exception
     */
    fun trackException(TAG: String, exception: Exception) {

        print(TAG, exception, true)
    }

    /**
     * Prints the and track exception.
     *
     * @param TAG
     * the tag
     * @param exception
     * the exception
     */
    fun print(TAG: String, exception: Exception, track: Boolean) {

        if (MODE_DEBUG) {
            error(TAG, "Exception = $exception")
            exception.printStackTrace()
            //			if (track) {
            //BugSenseHandler.sendException(exception);
            //			}
        }
    }

    /**
     * Prints the exception.
     *
     * @param exception
     * the exception
     */
    fun printException(exception: Exception) {

        print(TAG, exception, false)
    }

    /**
     * Prints the exception.
     *
     * @param TAG
     * the tag
     * @param exception
     * the exception
     */
    fun printException(TAG: String, exception: Exception) {

        print(TAG, exception, true)
    }

    /**
     * Prints the data.
     *
     * @param data
     * the data
     */
    fun printData(data: String) {

        error(TAG, data)
    }

    private fun info(TAG: String, data: String) {
        if (MODE_DEBUG) {
            Log.i("" + TAG, data)
        }
    }

    /**
     * Prints the data.
     *
     * @param TAG
     * the tag
     * @param data
     * the data
     */
    private fun error(TAG: String, data: String) {

        if (MODE_DEBUG) {
            Log.e("" + TAG, data)
        }
    }
}