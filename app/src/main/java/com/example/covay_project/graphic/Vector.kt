package com.example.covay_project.graphic

import java.io.Serializable
import java.lang.Math.cos
import java.lang.Math.sin

data class Vector (val x :Double,val y:Double ):Serializable {
    companion object {
        val NULL = Vector(0.0, 0.0)
        val X = Vector(1.0, 0.0)
        val Y = Vector(0.0, 1.0)
        fun fromAngle(angle: Double):Vector{
            return Vector(kotlin.math.cos(angle), kotlin.math.sin(angle))
        }
        fun fromPolar(angle: Double,magnitude:Double):Vector{
            return Vector(kotlin.math.cos(angle)*magnitude,kotlin.math.sin(angle)*magnitude)
        }
    }
    private val  serialVersionUID = 1L;
    fun add(other:Vector):Vector{
        return Vector(this.x+other.x,this.y+other.y)
    }
    fun angle():Double{
        return kotlin.math.atan2(this.y,this.x)
    }
    fun dot(other:Vector):Double{
        return this.x*other.x+this.y*other.y
    }

    override fun equals(other: Any?): Boolean {
        if(this === other)
            return true
        if(other == null ||javaClass != other.javaClass)
            return false
        other as Vector
        if(x.compareTo(other.x)!= 0)
            return false
        return y.compareTo(other.y) == 0
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31*result+y.hashCode()
        return result
    }

    fun mod(): Double {
        return kotlin.math.sqrt(modSquared())
    }

    fun modSquared(): Double {
        return dot(this)
    }

    fun neg(): Vector {
        return Vector(-this.x, -this.y)
    }

    fun normalize(): Vector {
        return scale(1.0 / mod())
    }

    fun rotMinus90(): Vector {
        return Vector(this.y, -this.x)
    }

    fun rotPlus90(): Vector {
        return Vector(-this.y, this.x)
    }

    fun scale(factor: Double): Vector {
        return Vector(this.x * factor, this.y * factor)
    }

    fun sub(other: Vector): Vector {
        return Vector(this.x - other.x, this.y - other.y)
    }

    override fun toString(): String {
        return "${javaClass.simpleName}($x, $y)"
    }

    fun x(): Float {
        return x.toFloat()
    }

    fun y(): Float {
        return y.toFloat()
    }

    operator fun get(index: Int): Double {
        return if (index == 0) x else if (index == 1) y else throw IndexOutOfBoundsException("Vector index out of bounds: $index")
    }
}
