package com.froodulous

import java.util.*

class Rules(val bornNumbers: Set<Int>, val surviveNumbers: Set<Int>)

data class Cell(val alive: Boolean = false, val age: Int = 0) {
    override fun equals(other: Any?): Boolean {
        return other is Cell && alive == other.alive
    }

    override fun hashCode(): Int {
        return alive.hashCode()
    }

    override fun toString(): String {
        return if (alive) "#" else "."
    }
}

data class World(val cells: Array<Array<Cell>>) {
    val width = cells.size
    val height = cells[0].size

    override fun equals(other: Any?): Boolean {
        if (other is World) {
            return Arrays.deepEquals(cells, other.cells)
        }
        return false
    }

    override fun hashCode(): Int {
        return Arrays.deepHashCode(cells)
    }

    override fun toString(): String {
        val sb = StringBuilder()

        for (arrayOfCells in cells) {
            for (cell in arrayOfCells) {
                sb.append(cell.toString())
            }
            sb.appendln()
        }

        return sb.toString()
    }
}

fun Rules.survives(numNeighbours: Int) = bornNumbers.contains(numNeighbours) || surviveNumbers.contains(numNeighbours)

fun Rules.born(numNeighbours: Int) = bornNumbers.contains(numNeighbours)

fun World.evolve(rules: Rules): World {
    val newCells = Array(size = width, init = { Array(height, { Cell() }) })
    for (x in 0..width - 1) {
        for (y in 0..height - 1) {
            newCells[x][y] = cells[x][y].evolve(getNumNeighboursWrapped(x, y), rules)
        }
    }
    return World(newCells)
}

fun World.getNumNeighboursBounded(x: Int, y: Int): Int {
    val xRange = x - 1..x + 1
    val yRange = y - 1..y + 1
    return xRange.flatMap { x -> yRange.map { y -> Pair(x, y) } }
            .filter { it.first != x || it.second != y }
            .filter { it.first >= 0 }
            .filter { it.first < width }
            .filter { it.second >= 0 }
            .filter { it.second < height }
            .map { cells[it.first][it.second] }
            .filter(Cell::alive)
            .count()
}

fun World.getNumNeighboursWrapped(x: Int, y: Int): Int {
    val xRange = x - 1..x + 1
    val yRange = y - 1..y + 1
    return xRange.flatMap { x -> yRange.map { y -> Pair(x, y) } }
            .map { Pair(wrapCoordinate(it.first, width), wrapCoordinate(it.second, height)) }
            .filter { it.first != x || it.second != y }
            .distinct()
            .map { cells[it.first][it.second] }
            .filter(Cell::alive)
            .count()
}

fun wrapCoordinate(coordinate: Int, limit: Int) = Math.floorMod(coordinate, limit)

fun Cell.evolve(numNeighbours: Int, rules: Rules): Cell {
    if (alive) {
        if (rules.survives(numNeighbours)) {
            return Cell(true, age + 1)
        }
    } else {
        if (rules.born(numNeighbours)) {
            return Cell(true)
        }
    }
    return Cell()
}