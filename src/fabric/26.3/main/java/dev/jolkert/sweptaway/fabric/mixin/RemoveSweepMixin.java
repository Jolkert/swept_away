package dev.jolkert.sweptaway.fabric.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
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
	@ModifyReturnValue(method = "isSweepAttack", at = @At("RETURN"))
	boolean removeSweep(boolean original)
	{

		Player self = (Player) (Object) this;
		return original && self.getAttributeValue(Attributes.SWEEPING_DAMAGE_RATIO) != 0;
	}

//	@WrapOperation(
//		method = "attack",
//		at = @At(
//			value = "INVOKE",
//			target = "Lnet/minecraft/world/entity/player/Player;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"
//		)
//	)
//	ItemStack removeSweep(Player instance, InteractionHand hand, Operation<ItemStack> original)
//	{
//		if (instance.getAttributeValue(Attributes.SWEEPING_DAMAGE_RATIO) == 0)
//		{
//			return ItemStack.EMPTY;
//		}
//		else
//		{
//			return original.call(instance, hand);
//		}
//	}
}
