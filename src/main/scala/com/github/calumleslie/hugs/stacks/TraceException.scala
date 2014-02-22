package com.github.calumleslie.hugs.stacks

class TraceException(val trace: List[State], e: Exception) extends Exception("Encountered error during trace", e)