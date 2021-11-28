package com.redgrapefruit.justenoughgems.util

import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import kotlin.random.Random

abstract class ModItem : Item(Settings().group(ItemGroup.MISC))

open class Range(
    val min: Int,
    val max: Int
) {
    open fun pick() = Random.nextInt(min, max + 1)

    companion object {
        fun nonRandom(value: Int) = NonRandomRange(value)
    }
}

class NonRandomRange(private val i: Int) : Range(Int.MIN_VALUE, Int.MAX_VALUE) {
    override fun pick(): Int = i
}

@JvmInline
value class Chance(private val chance: Int) {
    fun randomize() = Random.nextInt(101) <= chance

    inline fun ifValid(action: () -> Unit) {
        if (randomize()) action()
    }

    fun format(): String = chance.toString()
}

fun ItemStack.decrement() = decrement(1)

interface IRegistry {
    fun register()
}

fun String.toId() = Identifier("jeg", this)
fun String.toLocalId() = Identifier(this)

fun avg(vararg numbers: Int): Int {
    return numbers.sum() / numbers.size
}

fun avg(r: Range) = avg(r.min, r.max)

fun ticksToSeconds(t: Int) = t / 20
