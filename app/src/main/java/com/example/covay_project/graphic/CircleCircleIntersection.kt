package com.example.covay_project.graphic

import kotlin.math.sqrt

class CircleCircleIntersection(val c1: Circle, val c2: Circle) {
    val distanceC1cC2c: Double
    var distanceC1cRadicalLine: Double
    var distanceC2cRadicalLine: Double
    var distanceRadicalPointIntersectionPoints: Double
    var intersectionPoints: Vector?
    var intersectionPoint1: Vector?
    var intersectionPoint2: Vector?
    var radicalPoint: Vector?
    var type: Type
    var versorC1cC2c: Vector?
    var versorRadicalLine: Vector?

    init {
        val vector:Vector= c2.c.sub(c1.c)
        distanceC1cC2c = vector.mod()

        if (distanceC1cC2c == 0.0) {
            type = if (c1.r == c2.r) Type.COINCIDENT else Type.CONCENTRIC_CONTAINED
            radicalPoint = null
            distanceC1cRadicalLine = 0.0
            distanceC2cRadicalLine = 0.0
            versorC1cC2c = null
            versorRadicalLine = null
            intersectionPoints = null
            intersectionPoint1 = null
            intersectionPoint2 = null
            distanceRadicalPointIntersectionPoints = 0.0

        }

        versorC1cC2c = vector.scale(1.0 / distanceC1cC2c)
        distanceC1cRadicalLine =
            (sq(distanceC1cC2c) + sq(c1.r) - sq(c2.r)) / (2.0 * distanceC1cC2c)
        distanceC2cRadicalLine = distanceC1cC2c - distanceC1cRadicalLine
        radicalPoint = c1.c.add(versorC1cC2c!!.scale(distanceC1cRadicalLine))
        versorRadicalLine = versorC1cC2c!!.rotPlus90()

        val d = sq(c1.r) - sq(distanceC1cRadicalLine)

        if (d > 0.0) {
            type = Type.OVERLAPPING
            intersectionPoints = null
            distanceRadicalPointIntersectionPoints = sqrt(d)
            intersectionPoint1 = radicalPoint!!.add(versorRadicalLine!!.scale(distanceRadicalPointIntersectionPoints))
            intersectionPoint2 = radicalPoint!!.add(versorRadicalLine!!.scale(-distanceRadicalPointIntersectionPoints))
        } else if (distanceC1cC2c > maxOf(c1.r, c2.r)) {
            type = Type.SEPARATE
            intersectionPoints = null
            intersectionPoint1 = null
            intersectionPoint2 = null
            distanceRadicalPointIntersectionPoints = 0.0
        } else {
            val bool = d == 0.0
            type = if (bool) {
                if (distanceC1cC2c > maxOf(c1.r, c2.r)) Type.EXTERNALLY_TANGENT
                else Type.INTERNALLY_TANGENT
            } else {
                if (distanceC1cC2c > maxOf(c1.r, c2.r)) Type.SEPARATE
                else Type.ECCENTRIC_CONTAINED
            }
            intersectionPoints = if (bool) radicalPoint else null
            intersectionPoint1 = null
            intersectionPoint2 = null
            distanceRadicalPointIntersectionPoints = 0.0
        }
    }

    private fun sq(paramDouble: Double): Double {
        return paramDouble * paramDouble
    }

    fun getIntersectionPoints(): Array<Vector> {
        return when (type.getIntersectionPointCount()) {
            0 -> emptyArray()
            1 -> arrayOf(intersectionPoints!!)
            2 -> if (intersectionPoint1!!.x() < intersectionPoint2!!.x()) {
                arrayOf(Vector(intersectionPoint1!!.x().toDouble(), intersectionPoint1!!.y().toDouble()),
                    Vector(intersectionPoint2!!.x().toDouble(), intersectionPoint2!!.y().toDouble()))
            } else if (intersectionPoint1!!.x().toDouble() == intersectionPoint2!!.x().toDouble()) {
                if (intersectionPoint1!!.y().toDouble() < intersectionPoint2!!.y().toDouble()) {
                    arrayOf(Vector(intersectionPoint1!!.x().toDouble(), intersectionPoint1!!.y().toDouble()),
                        Vector(intersectionPoint2!!.x().toDouble(), intersectionPoint2!!.y().toDouble()))
                } else {
                    arrayOf(Vector(intersectionPoint2!!.x().toDouble(), intersectionPoint2!!.y().toDouble()),
                        Vector(intersectionPoint1!!.x().toDouble(), intersectionPoint1!!.y().toDouble()))
                }
            } else {
                arrayOf(Vector(intersectionPoint2!!.x().toDouble(), intersectionPoint2!!.y().toDouble()),
                    Vector(intersectionPoint1!!.x().toDouble(), intersectionPoint1!!.y().toDouble()))
            }
            else -> throw IllegalStateException("Coincident circles")
        }
    }

    override fun toString(): String {
        return "${javaClass.simpleName}(c1: $c1, c2: $c2, type: $type, " +
                "distanceC1cC2c: $distanceC1cC2c, radicalPoint: $radicalPoint, " +
                "distanceC1cRadicalLine: $distanceC1cRadicalLine, distanceC2cRadicalLine: $distanceC2cRadicalLine, " +
                "versorC1cC2c: $versorC1cC2c, versorRadicalLine: $versorRadicalLine, " +
                "intersectionPoint: $intersectionPoints, intersectionPoint1: $intersectionPoint1, " +
                "intersectionPoint2: $intersectionPoint2, " +
                "distanceRadicalPointIntersectionPoints: $distanceRadicalPointIntersectionPoints)"
    }

    enum class Type(val n: Int) {
        COINCIDENT(-1),
        CONCENTRIC_CONTAINED(0),
        ECCENTRIC_CONTAINED(0),
        EXTERNALLY_TANGENT(0),
        INTERNALLY_TANGENT(1),
        OVERLAPPING(2),
        SEPARATE(2);

        fun getIntersectionPointCount(): Int {
            return n
        }

        fun isConcentric(): Boolean {
            return this == COINCIDENT || this == CONCENTRIC_CONTAINED
        }

        fun isContained(): Boolean {
            return this == CONCENTRIC_CONTAINED || this == ECCENTRIC_CONTAINED
        }

        fun isDisjoint(): Boolean {
            return n == 0
        }

        fun isTangent(): Boolean {
            return n == 1
        }
    }
}
