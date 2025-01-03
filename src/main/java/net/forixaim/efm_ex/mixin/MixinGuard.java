package net.forixaim.efm_ex.mixin;

import net.forixaim.efm_ex.capabilities.weaponcaps.EXWeaponCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.client.events.engine.ControllEngine;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.UUID;

@Mixin(value = GuardSkill.class, remap = false)
public class MixinGuard
{
	@Unique
	private GuardSkill epicFight_EXCapability$self = (GuardSkill) (Object) this;

	@Unique
	private static final UUID EXCAP_UUID = UUID.fromString("057e27b3-b742-40f8-bcc2-c6ac70ec215b");


	@Inject(method = "onInitiate", at = @At("HEAD"), remap = false)
	private void onInitiate(SkillContainer container, CallbackInfo ci)
	{
		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.MOVEMENT_INPUT_EVENT, EXCAP_UUID, movementInputEvent ->
		{
			if (container.getExecuter().getOriginal().isUsingItem() && container.getExecuter().getHoldingItemCapability(InteractionHand.MAIN_HAND) instanceof EXWeaponCapability weaponCapability)
			{
				if (weaponCapability.getGuardMotion(epicFight_EXCapability$self, GuardSkill.BlockType.GUARD, movementInputEvent.getPlayerPatch()) != null)
				{
					LocalPlayer clientPlayer = movementInputEvent.getPlayerPatch().getOriginal();
					clientPlayer.setSprinting(false);
					clientPlayer.sprintTriggerTime = -1;
					Minecraft mc = Minecraft.getInstance();
					ControllEngine.setKeyBind(mc.options.keySprint, false);
				}
			}
		});
	}

	@Inject(method = "onRemoved", at = @At("HEAD"), remap = false)
	private void onRemoved(SkillContainer container, CallbackInfo ci)
	{
		container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.MOVEMENT_INPUT_EVENT, EXCAP_UUID);
	}
}
