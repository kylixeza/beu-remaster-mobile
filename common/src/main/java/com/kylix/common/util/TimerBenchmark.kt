package com.kylix.common.util

//could be used as a timer for benchmarking or for measuring the time taken for a task to complete
class TimerBenchmark {

    private var _startTime = 0L
    private var _endTime = 0L

    fun start() {
        _startTime = System.nanoTime()
    }

    fun stop() {
        _endTime = System.nanoTime()
    }

    fun elapsedTime(timeUnit: TimeUnit): Int {
        return when (timeUnit) {
            TimeUnit.NANOSECONDS -> (_endTime - _startTime).toInt()
            TimeUnit.MICROSECONDS -> ((_endTime - _startTime) / 1000).toInt()
            TimeUnit.MILLISECONDS -> ((_endTime - _startTime) / 1000000).toInt()
            TimeUnit.SECONDS -> ((_endTime - _startTime) / 1000000000).toInt()
        }
    }

    fun convertToMinutes(timeInSecond: Int): String {
        val minutes = timeInSecond / 60
        val seconds = timeInSecond % 60
        return "$minutes menit $seconds detik"

    }

    fun clear() {
        _startTime = 0L
        _endTime = 0L
    }

    enum class TimeUnit {
        NANOSECONDS,
        MICROSECONDS,
        MILLISECONDS,
        SECONDS
    }

}