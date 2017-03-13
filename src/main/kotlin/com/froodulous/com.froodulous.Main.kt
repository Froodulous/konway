package com.froodulous

/**
 * Simple entry point for playing around.
 * Created by luke on 02/01/2017.
 */
fun main(args: Array<String>) {

    val cells = Array(5, { Array(5, { Cell() }) })
    cells[1][2] = Cell(alive = true)
    cells[2][2] = Cell(alive = true)
    cells[3][2] = Cell(alive = true)

    var world = worldFromString("............\n" +
            "..#.........\n" +
            "...#........\n" +
            ".###........\n" +
            "............\n" +
            "............\n" +
            "............\n" +
            "............\n" +
            "............\n" +
            "............\n" +
            "............\n" +
            "............")
    val rules = Rules(bornNumbers = setOf(3), surviveNumbers = setOf(2))

    for (i in 0..100) {
        printState(world, step = i)
        world = world.evolve(rules)
    }
}

private fun printState(world: World, step: Int) {
    println("Step $step, Current World:")
    println(world)
}
