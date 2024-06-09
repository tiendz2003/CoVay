package com.example.covay_project.graphic

import java.io.Serializable

data class Circle(val c:Vector,  val r:Double):Serializable {
    private val  serialVersionUID = 1L;
    init{
        if(r<=0.0){
            throw IllegalArgumentException("Radius must be positive")
        }
    }

    override fun equals(other: Any?): Boolean {
        if(this === other)
            return true
        if(other == null || javaClass != other.javaClass)
            return false
        other as Circle
        if(c != other.c)
            return false
        if(r.compareTo(other.r)!= 0)
            return false
        return true
    }

    override fun hashCode(): Int {
        var result = c.hashCode()
        result = 31*result+r.hashCode()
        return result
    }

    override fun toString(): String {
        return "${javaClass.simpleName}(c:$c,r:$r)"
    }
}