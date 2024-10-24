package net.forixaim.efm_ex.capabilities.weapon_presets.types;

import net.forixaim.efm_ex.capabilities.CoreCapability;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

public class AxeType extends CoreCapability
{
	private static final AxeType instance = new AxeType();

	private AxeType()
	{
		init();
	}

	private void init()
	{
		provider.addDefaultConditional(COMBO_PROVIDER_REGISTRY.add("default", CapabilityItem.Styles.TWO_HAND, false, null));
		builder.initialSetup(
						CapabilityItem.WeaponCategories.LONGSWORD,
						EpicFightSounds.WHOOSH_BIG.get(),
						EpicFightSounds.BLADE_HIT.get()
				).collider(ColliderPreset.LONGSWORD)
				.passiveProvider(provider.exportWeaponPassiveSkill())
				.weaponCombinationPredicator(provider.exportCombination())
				.styleProvider(provider.exportStyle());
	}
}