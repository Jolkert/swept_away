package dev.jolkert.sweptaway.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public class RemoveSweepMixin
{
	// this was the easiest way to not have to write separate mixins for each modloader. it's also, however, a very
	// frail implementation. if they ever add a call to `Player::getItemInHand` in this function we're probaly gonna have
	// to change the specifics of this operation. so yknow just keep that in mind when we bump mc version
	// -morgan 2026-02-03
	@WrapOperation(
			method = "attack",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/entity/player/Player;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"
			)
	)
	ItemStack removeSweep(Player instance, InteractionHand hand, Operation<ItemStack> original)
	{
		if (instance.getAttributeValue(Attributes.SWEEPING_DAMAGE_RATIO) == 0)
		{
			return ItemStack.EMPTY;
		}
		else
		{
			return original.call(instance, hand);
		}
	}
}
