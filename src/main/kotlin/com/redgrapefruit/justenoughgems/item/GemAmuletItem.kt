package com.redgrapefruit.justenoughgems.item

import com.redgrapefruit.itemnbt3.CustomData
import com.redgrapefruit.itemnbt3.DataClient
import com.redgrapefruit.justenoughgems.util.RomanNumber
import com.redgrapefruit.justenoughgems.util.decrement
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.Entity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.Text.literal
import net.minecraft.text.Text
import net.minecraft.text.Text.translatable
import net.minecraft.util.Formatting
import net.minecraft.world.World

class GemAmuletItem(val config: GemAmuletConfig, tier: Int) : ModItem(tier, true) {
    override fun inventoryTick(stack: ItemStack, world: World, entity: Entity, slot: Int, selected: Boolean) {
        if (entity !is PlayerEntity) return

        DataClient.use(::GemAmuletState, stack) { state ->
            state.decreaseInDurability++

            if (!state.initialized) {
                config.onInsertEffects.forEach { (effect, amplifier) ->
                    val instance = StatusEffectInstance(effect, Int.MAX_VALUE, amplifier)

                    if (entity.hasStatusEffect(effect)) {
                        entity.removeStatusEffect(effect)
                    }

                    entity.addStatusEffect(instance)
                }

                state.initialized = true
            }

            if (state.decreaseInDurability >= config.initialDurability) {
                stack.decrement()
                return@use
            }

            config.effects.forEach { (effect, amplifier) ->
                val instance = StatusEffectInstance(effect, config.expiryTime, amplifier)
                entity.addStatusEffect(instance)
            }
        }
    }

    override fun appendTooltip(
        stack: ItemStack,
        world: World?,
        tooltip: MutableList<Text>,
        context: TooltipContext
    ) {
        super.appendTooltip(stack, world, tooltip, context)

        DataClient.use(::GemAmuletState, stack) { state ->
            tooltip += Text.translatable("misc.jeg.durability")
                .append(Text.literal("${config.initialDurability - state.decreaseInDurability}/${config.initialDurability}"))
                .formatted(getDurabilityColoring(stack))
        }

        tooltip += Text.literal("")
        tooltip += Text.translatable("misc.jeg.effects").formatted(Formatting.GRAY)

        config.effects.forEach { (effect, amplifier) ->
            tooltip += Text.literal("- ")
                .append(Text.translatable(effect.translationKey))
                .append(Text.literal(" ${RomanNumber.toRoman(amplifier + 1)}"))
        }
    }

    private fun getCurrentDurability(stack: ItemStack): Int {
        var output = 0

        DataClient.use(::GemAmuletState, stack) { state ->
            output = config.initialDurability - state.decreaseInDurability
        }

        return output
    }

    private fun getDurabilityColoring(stack: ItemStack): Formatting {
        val durability = getCurrentDurability(stack)

        // States:
        // x>3/4 => Dark Green
        // x>2/4 => Green
        // x>1/4 => Yellow
        // x<1/4 => Red

        val quarter = config.initialDurability / 4

        return when {
            durability > quarter * 3 -> Formatting.DARK_GREEN
            durability > quarter * 2 -> Formatting.GREEN
            durability > quarter -> Formatting.YELLOW
            else -> Formatting.RED
        }
    }
}

data class GemAmuletState(
    var decreaseInDurability: Int = 0,
    var initialized: Boolean = false /* https://github.com/RedGrapefruit09/JustEnoughGems/issues/1#issuecomment-995539643 */ )
: CustomData {
    override fun getNbtCategory(): String = "GemAmuletState"

    override fun readNbt(nbt: NbtCompound) {
        decreaseInDurability = nbt.getInt("Durability Decrease")
        initialized = nbt.getBoolean("Initialized")
    }

    override fun writeNbt(nbt: NbtCompound) {
        nbt.putInt("Durability Decrease", decreaseInDurability)
        nbt.putBoolean("Initialized", initialized)
    }
}
