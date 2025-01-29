package dev.jolkert.sweptaway.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public class SweepRemovalMixin
{
	@ModifyExpressionValue(
			method = "attack",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isOnGround()Z", ordinal = 1)
	)
	private boolean sweptAway$removeSweep(boolean originalValue)
	{
		return originalValue && ((PlayerEntity)(Object)this).getAttributeValue(EntityAttributes.SWEEPING_DAMAGE_RATIO) != 0;
	}
}
