package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import twilightforest.entity.EntityTFCharmEffect;

public class RenderTFCharm extends RenderSnowball<EntityTFCharmEffect> {

	public RenderTFCharm(EntityRendererManager manager, RenderItem itemRenderer) {
		super(manager, Item.getItemFromBlock(Blocks.BARRIER), itemRenderer);
	}

	@Override
	public ItemStack getStackToRender(EntityTFCharmEffect charm) {
		if (charm.getItemID() > -1) {
			return new ItemStack(Item.getItemById(charm.getItemID()));
		} else {
			return ItemStack.EMPTY;
		}
	}
}
