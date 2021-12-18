package com.redgrapefruit.justenoughgems.util

import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.ArmorMaterial
import net.minecraft.item.Items
import net.minecraft.item.ToolMaterial
import net.minecraft.recipe.Ingredient
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents

// Kotlin DSLs for Armor and Tool Materials. Copied from the tutorials in my private vault

@DslMarker
annotation class ToolMaterialDsl

@ToolMaterialDsl
inline fun toolMaterial(action: ToolMaterialScope.() -> Unit): ToolMaterial {
    val scope = ToolMaterialScope()
    scope.action()
    return scope.create()
}

class ToolMaterialScope(
    var durability: Int? = null,
    var miningSpeedMultiplier: Float? = null,
    var attackDamage: Float? = null,
    var miningLevel: Int? = null,
    var enchantability: Int? = null,
    var repairIngredient: Ingredient? = null
) {
    fun placeholderRepairIngredient() {
        repairIngredient = Ingredient.ofItems(Items.BEDROCK)
    }

    fun create(): ToolMaterial {
        assert(durability)
        assert(miningSpeedMultiplier)
        assert(attackDamage)
        assert(miningLevel)
        assert(enchantability)
        assert(repairIngredient)

        return Impl(this)
    }

    private fun <T> assert(value: T?) {
        if (value == null) throw RuntimeException("Assertion failed: null reference")
    }

    private class Impl(private val scope: ToolMaterialScope) : ToolMaterial {
        override fun getDurability(): Int {
            return scope.durability!!
        }

        override fun getMiningSpeedMultiplier(): Float {
            return scope.miningSpeedMultiplier!!
        }

        override fun getAttackDamage(): Float {
            return scope.attackDamage!!
        }

        override fun getMiningLevel(): Int {
            return scope.miningLevel!!
        }

        override fun getEnchantability(): Int {
            return scope.enchantability!!
        }

        override fun getRepairIngredient(): Ingredient {
            return scope.repairIngredient!!
        }
    }
}

@DslMarker
annotation class ArmorMaterialDsl

@ArmorMaterialDsl
inline fun armorMaterial(build: ArmorMaterialScope.() -> Unit): ArmorMaterial {
    val scope = ArmorMaterialScope()
    scope.build()
    return scope.create()
}

class ArmorMaterialScope(
    private var durabilityValues: MutableMap<EquipmentSlot, Int> = mutableMapOf(),
    private var protectionValues: MutableMap<EquipmentSlot, Int> = mutableMapOf(),
    var enchantability: Int? = null,
    var equipSound: SoundEvent? = null,
    var repairIngredient: Ingredient? = null,
    var name: String? = null,
    var toughness: Float? = null,
    var knockbackResistance: Float? = null
) {
    fun durability(feet: Int, legs: Int, chest: Int, head: Int) {
        durabilityValues.init(feet, legs, chest, head)
    }

    fun protection(feet: Int, legs: Int, chest: Int, head: Int) {
        protectionValues.init(feet, legs, chest, head)
    }

    fun defaultEquipSound() {
        equipSound = SoundEvents.ITEM_ARMOR_EQUIP_GENERIC
    }

    fun noKnockbackResistance() {
        knockbackResistance = 0.0f
    }

    fun create(): ArmorMaterial {
        assertSlotMap(durabilityValues)
        assertSlotMap(protectionValues)
        assert(enchantability)
        assert(equipSound) { defaultEquipSound() }
        assert(repairIngredient)
        assert(name)
        assert(toughness)
        assert(knockbackResistance) { noKnockbackResistance() }

        return Impl(this)
    }

    private fun <T> assert(value: T?) {
        if (value == null) throw RuntimeException("Assertion failed. Null")
    }

    private fun <T> assert(value: T?, fallback: () -> Unit) {
        if (value == null) fallback()
    }

    private fun assertSlotMap(map: MutableMap<EquipmentSlot, Int>) {
        if (map.size != 4) throw RuntimeException("Assertion failed. Not enough data")
    }

    private class Impl(private val scope: ArmorMaterialScope) : ArmorMaterial {
        override fun getDurability(slot: EquipmentSlot?): Int {
            return scope.durabilityValues[slot]!!
        }

        override fun getProtectionAmount(slot: EquipmentSlot?): Int {
            return scope.protectionValues[slot]!!
        }

        override fun getEnchantability(): Int {
            return scope.enchantability!!
        }

        override fun getEquipSound(): SoundEvent {
            return scope.equipSound!!
        }

        override fun getRepairIngredient(): Ingredient {
            return scope.repairIngredient!!
        }

        override fun getName(): String {
            return scope.name!!
        }

        override fun getToughness(): Float {
            return scope.toughness!!
        }

        override fun getKnockbackResistance(): Float {
            return scope.knockbackResistance!!
        }
    }
}

private fun MutableMap<EquipmentSlot, Int>.init(a: Int, b: Int, c: Int, d: Int) {
    put(EquipmentSlot.FEET, a)
    put(EquipmentSlot.LEGS, d)
    put(EquipmentSlot.CHEST, c)
    put(EquipmentSlot.HEAD, d)
}
