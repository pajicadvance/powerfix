package me.pajic.powerfix.mixin;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin {

    @ModifyArg(
            method = "onHitEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;modifyDamage(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;F)F"
            ),
            index = 4
    )
    private float fixPowerDamage(float baseDamage) {
        AbstractArrow arrow = (AbstractArrow) (Object) this;
        if (EnchantmentHelper.getItemEnchantmentLevel(arrow.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.POWER), arrow.getWeaponItem()) > 0) {
            return baseDamage + 0.5F;
        }
        return baseDamage;
    }
}
