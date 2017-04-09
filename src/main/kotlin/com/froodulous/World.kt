package com.froodulous

import java.util.*

/**
 * Class representing a world within which the cells exist.
 *
 * @param cells The 2-dimensional array of Cells to initialise the world.
 * @param wrapWorld Whether to allow the world to "wrap" in a toroidal fashion. If false, all cells outside of the range
 * of the provided cells is considered permanently dead.
 */
data class World(val cells: Array<Array<Cell>>, val wrapWorld: Boolean = true) {
    init {
        cells.forEachIndexed { index, row ->
            if (row.size != cells[0].size) throw IllegalArgumentException(
                    "Attempted to create world from cells: $cells, " +
                            "but row $index is not the same length as row 0")
        }
    }

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

/**
 * Evolve this World one step according to the provided Rules.
 *
 * @param rules The rules to determine how to evolve the World.
 * @return A new World representing the World after evolving.
 */
fun World.evolve(rules: Rules): World {
    val newCells = Array(size = width, init = { Array(height, { Cell() }) })
    for (x in 0..width - 1) {
        for (y in 0..height - 1) {
            newCells[x][y] = cells[x][y].evolve(getNumNeighbours(x, y), rules)
        }
    }
    return World(newCells, wrapWorld)
}

private fun World.getNumNeighbours(x: Int, y: Int): Int =
        if (this.wrapWorld) getNumNeighboursWrapped(x, y) else getNumNeighboursBounded(x, y)

private fun World.getNumNeighboursBounded(x: Int, y: Int): Int {
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

private fun World.getNumNeighboursWrapped(x: Int, y: Int): Int {
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

/**
 * Create a World from a String.
 *
 * @param input The String to parse into a World.
 * @param wrapWorld Whether the resulting World should Wrap toroidally.
 */
fun worldFromString(input: String, wrapWorld: Boolean = true): World {
    val cellArray = input.lines().map {
        it.map {
            Cell(alive = it != '.')
        }.toTypedArray()
    }.toTypedArray()
    return World(cellArray, wrapWorld)
}