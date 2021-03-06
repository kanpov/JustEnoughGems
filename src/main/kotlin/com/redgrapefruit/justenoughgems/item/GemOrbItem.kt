package com.redgrapefruit.justenoughgems.item

import com.redgrapefruit.itemnbt3.CustomData
import com.redgrapefruit.itemnbt3.DataClient
import com.redgrapefruit.justenoughgems.util.*
import net.minecraft.client.item.TooltipContext
import net.minecraft.client.world.ClientWorld
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.Text.literal
import net.minecraft.text.Text
import net.minecraft.text.Text.translatable
import net.minecraft.util.Formatting
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

class GemOrbItem(private val config: GemOrbConfig, tier: Int) : ModItem(tier, true) {
    private fun readyForUsage(stack: ItemStack): Boolean {
        var out = false

        DataClient.use(::GemItemState, stack) { state ->
            out = state.reload >= config.reloadTime
        }

        return out
    }

    override fun inventoryTick(stack: ItemStack, world: World, entity: Entity, slot: Int, selected: Boolean) {
        super.inventoryTick(stack, world, entity, slot, selected)

        DataClient.use(::GemItemState, stack) { state ->
            ++state.reload

            if (state.uses == 0) {
                stack.decrement()
            }
        }
    }

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val stack = user.getStackInHand(hand)

        // Validation

        if (!readyForUsage(stack)) return super.use(world, user, hand)

        var outOfUses = false

        DataClient.use(::GemItemState, stack) { state ->
            println(state.uses)
            if (state.uses <= 0) outOfUses = true
        }

        if (outOfUses) {
            stack.decrement()
            return TypedActionResult.consume(stack)
        }

        // Count in usage

        DataClient.use(::GemItemState, stack) { state ->
            state.reload = 0
            --state.uses
        }

        // Application

        config.effects.forEach { effect ->
            effect.chance.ifValid {
                val duration = effect.duration.pick()
                val amplifier = effect.amplifier.pick()
                val instance = StatusEffectInstance(effect.statusEffect, duration, amplifier)
                user.addStatusEffect(instance)
            }
        }

        return super.use(world, user, hand)
    }

    override fun appendTooltip(
        stack: ItemStack,
        world: World?,
        tooltip: MutableList<Text>,
        context: TooltipContext
    ) {
        super.appendTooltip(stack, world, tooltip, context)

        if (readyForUsage(stack)) {
            tooltip += Text.translatable("misc.jeg.ready_to_use")
                .formatted(Formatting.GREEN)
        } else {
            DataClient.use(::GemItemState, stack) { state ->
                tooltip += Text.translatable("misc.jeg.cooldown")
                    .append(Text.literal("${state.reload}/${config.reloadTime}"))
                    .formatted(Formatting.RED)
            }
        }

        DataClient.use(::GemItemState, stack) { state ->
            tooltip += Text.translatable("misc.jeg.uses_left")
                .append(Text.literal("${state.uses}/4"))
                .formatted(Formatting.AQUA)
        }

        tooltip += Text.literal("")
        tooltip += Text.translatable("misc.jeg.effects")
            .formatted(Formatting.GRAY)

        config.effects.forEach { effect ->
            tooltip += Text.literal("- ")
                .append(Text.translatable(effect.statusEffect.translationKey))
                .append(Text.literal(" (${effect.chance.format()}% chance)"))
        }
    }

    companion object {
        fun modelPredicateProvider(stack: ItemStack, world: ClientWorld?, entity: LivingEntity?, i: Int): Float {
            var uses = 0

            DataClient.use(::GemItemState, stack) { state ->
                uses = state.uses
            }

            return when (uses) {
                0 -> 0.4f // hack
                1 -> 0.4f
                2 -> 0.3f
                3 -> 0.2f
                4 -> 0.1f
                else -> throw RuntimeException("Out of bounds: $uses")
            }
        }
    }
}

private data class GemItemState(var reload: Int = 0, var uses: Int = 4) : CustomData {
    override fun getNbtCategory(): String = "GemItemState"

    override fun readNbt(nbt: NbtCompound) {
        reload = nbt.getInt("Reload")
        uses = nbt.getInt("Uses")
    }

    override fun writeNbt(nbt: NbtCompound) {
        nbt.putInt("Reload", reload)
        nbt.putInt("Uses", uses)
    }
}
