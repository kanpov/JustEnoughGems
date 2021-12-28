package com.redgrapefruit.justenoughgems.init

import com.redgrapefruit.justenoughgems.item.*
import com.redgrapefruit.justenoughgems.util.IRegistry
import com.redgrapefruit.justenoughgems.util.toId
import com.redgrapefruit.justenoughgems.util.toLocalId
import net.fabricmc.fabric.api.`object`.builder.v1.client.model.FabricModelPredicateProviderRegistry
import net.minecraft.item.Item
import net.minecraft.util.registry.Registry

object JEGItems : IRegistry {
    // Gems
    val QUARTZ_GEM = GemItem(GemItemConfig.QUARTZ, 1)
    val ANDALUSITE_GEM = GemItem(GemItemConfig.ANDALUSITE, 2)
    val MORGANITE_GEM = GemItem(GemItemConfig.MORGANITE, 3)
    val TAAFFEITE_GEM = GemItem(GemItemConfig.TAAFFEITE, 4)
    val PINK_QUARTZ_GEM = GemItem(GemItemConfig.PINK_QUARTZ, 5)
    val EUCLASE_GEM = GemItem(GemItemConfig.EUCLASE, 6)
    val CHRYSOLITE_GEM = GemItem(GemItemConfig.CHRYSOLITE, 7)
    val SAPPHIRE_GEM = GemItem(GemItemConfig.SAPPHIRE, 8)
    val HELIODORE_GEM = GemItem(GemItemConfig.HELIODORE, 9)
    val RUBY_GEM = GemItem(GemItemConfig.RUBY, 10)
    val AQUAMARINE_GEM = GemItem(GemItemConfig.AQUAMARINE, 11)
    val TANZANITE_GEM = GemItem(GemItemConfig.TANZANITE, 12)
    val TSAVORITE_GEM = GemItem(GemItemConfig.TSAVORITE, 13)
    val VERDELITE_GEM = GemItem(GemItemConfig.VERDELITE, 14)
    val INDIGOLITE_GEM = GemItem(GemItemConfig.INDIGOLITE, 15)
    val OPAL_GEM = GemItem(GemItemConfig.OPAL, 16)

    // Amulets
    val QUARTZ_AMULET = GemAmulet(GemAmuletConfig.QUARTZ, 1)
    val ANDALUSITE_AMULET = GemAmulet(GemAmuletConfig.ANDALUSITE, 2)
    val MORGANITE_AMULET = GemAmulet(GemAmuletConfig.MORGANITE, 3)
    val TAAFFEITE_AMULET = GemAmulet(GemAmuletConfig.TAAFFEITE, 4)
    val PINK_QUARTZ_AMULET = GemAmulet(GemAmuletConfig.PINK_QUARTZ, 5)
    val EUCLASE_AMULET = GemAmulet(GemAmuletConfig.EUCLASE, 6)
    val CHRYSOLITE_AMULET = GemAmulet(GemAmuletConfig.CHRYSOLITE, 7)
    val SAPPHIRE_AMULET = GemAmulet(GemAmuletConfig.SAPPHIRE, 8)
    val HELIODORE_AMULET = GemAmulet(GemAmuletConfig.HELIODORE, 9)
    val RUBY_AMULET = GemAmulet(GemAmuletConfig.RUBY, 10)
    val AQUAMARINE_AMULET = GemAmulet(GemAmuletConfig.AQUAMARINE, 11)
    val TANZANITE_AMULET = GemAmulet(GemAmuletConfig.TANZANITE, 12)
    val TSAVORITE_AMULET = GemAmulet(GemAmuletConfig.TSAVORITE, 13)
    val VERDELITE_AMULET = GemAmulet(GemAmuletConfig.VERDELITE, 14)
    val INDIGOLITE_AMULET = GemAmulet(GemAmuletConfig.INDIGOLITE, 15)
    val OPAL_AMULET = GemAmulet(GemAmuletConfig.OPAL, 16)

    // Fragments
    val QUARTZ_FRAGMENT = BaseItem(1)
    val ANDALUSITE_FRAGMENT = BaseItem(2)
    val MORGANITE_FRAGMENT = BaseItem(3)
    val TAAFFEITE_FRAGMENT = BaseItem(4)
    val PINK_QUARTZ_FRAGMENT = BaseItem(5)
    val EUCLASE_FRAGMENT = BaseItem(6)
    val CHRYSOLITE_FRAGMENT = BaseItem(7)
    val SAPPHIRE_FRAGMENT = BaseItem(8)
    val HELIODORE_FRAGMENT = BaseItem(9)
    val RUBY_FRAGMENT = BaseItem(10)
    val AQUAMARINE_FRAGMENT = BaseItem(11)
    val TANZANITE_FRAGMENT = BaseItem(12)
    val TSAVORITE_FRAGMENT = BaseItem(13)
    val VERDELITE_FRAGMENT = BaseItem(14)
    val INDIGOLITE_FRAGMENT = BaseItem(15)
    val OPAL_FRAGMENT = BaseItem(16)

    override fun register() {
        register("quartz_gem", QUARTZ_GEM)
        register("andalusite_gem", ANDALUSITE_GEM)
        register("morganite_gem", MORGANITE_GEM)
        register("taaffeite_gem", TAAFFEITE_GEM)
        register("pink_quartz_gem", PINK_QUARTZ_GEM)
        register("euclase_gem", EUCLASE_GEM)
        register("chrysolite_gem", CHRYSOLITE_GEM)
        register("sapphire_gem", SAPPHIRE_GEM)
        register("heliodore_gem", HELIODORE_GEM)
        register("ruby_gem", RUBY_GEM)
        register("aquamarine_gem", AQUAMARINE_GEM)
        register("tanzanite_gem", TANZANITE_GEM)
        register("tsavorite_gem", TSAVORITE_GEM)
        register("verdelite_gem", VERDELITE_GEM)
        register("indigolite_gem", INDIGOLITE_GEM)
        register("opal_gem", OPAL_GEM)

        register("quartz_amulet", QUARTZ_AMULET)
        register("andalusite_amulet", ANDALUSITE_AMULET)
        register("morganite_amulet", MORGANITE_AMULET)
        register("taaffeite_amulet", TAAFFEITE_AMULET)
        register("pink_quartz_amulet", PINK_QUARTZ_AMULET)
        register("euclase_amulet", EUCLASE_AMULET)
        register("chrysolite_amulet", CHRYSOLITE_AMULET)
        register("sapphire_amulet", SAPPHIRE_AMULET)
        register("heliodore_amulet", HELIODORE_AMULET)
        register("ruby_amulet", RUBY_AMULET)
        register("aquamarine_amulet", AQUAMARINE_AMULET)
        register("tanzanite_amulet", TANZANITE_AMULET)
        register("tsavorite_amulet", TSAVORITE_AMULET)
        register("verdelite_amulet", VERDELITE_AMULET)
        register("indigolite_amulet", INDIGOLITE_AMULET)
        register("opal_amulet", OPAL_AMULET)

        register("quartz_fragment", QUARTZ_FRAGMENT)
        register("andalusite_fragment", ANDALUSITE_FRAGMENT)
        register("morganite_fragment", MORGANITE_FRAGMENT)
        register("taaffeite_fragment", TAAFFEITE_FRAGMENT)
        register("pink_quartz_fragment", PINK_QUARTZ_FRAGMENT)
        register("euclase_fragment", EUCLASE_FRAGMENT)
        register("chrysolite_fragment", CHRYSOLITE_FRAGMENT)
        register("sapphire_fragment", SAPPHIRE_FRAGMENT)
        register("heliodore_fragment", HELIODORE_FRAGMENT)
        register("ruby_fragment", RUBY_FRAGMENT)
        register("aquamarine_fragment", AQUAMARINE_FRAGMENT)
        register("tanzanite_fragment", TANZANITE_FRAGMENT)
        register("tsavorite_fragment", TSAVORITE_FRAGMENT)
        register("verdelite_fragment", VERDELITE_FRAGMENT)
        register("indigolite_fragment", INDIGOLITE_FRAGMENT)
        register("opal_fragment", OPAL_FRAGMENT)
    }

    private fun register(name: String, item: Item) {
        Registry.register(Registry.ITEM, name.toId(), item)

        if (item is GemItem) {
            FabricModelPredicateProviderRegistry.register(item, "usage".toLocalId(), GemItem::modelPredicateProvider)
        }
    }
}
