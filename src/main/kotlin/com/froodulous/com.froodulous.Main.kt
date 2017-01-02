package com.froodulous

/**
 * Simple entry point for playing around.
 * Created by luke on 02/01/2017.
 */
fun main(args: Array<String>) {

    val cells = Array(2, { Array(2, { Cell() }) })
    cells[0][0] = Cell(alive = true)

    var world = World(cells)
    val rules = Rules(setOf(1, 3), emptySet())


    for (i in 0..100) {
        printState(world, i)
        world = world.evolve(rules)
    }
}

private fun printState(world: World, step: Int) {
    println("Step $step, Current World:")
    println(world)
}

