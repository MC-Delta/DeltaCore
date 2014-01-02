package mcdelta.core.item;

import mcdelta.core.ModDelta;
import mcdelta.core.material.ToolMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class ItemDeltaAxe extends ItemDeltaTool
{
    public ItemDeltaAxe(ModDelta mod, ToolMaterial mat)
    {
        super(mod, "axe", mat, ItemAxe.blocksEffectiveAgainst, 3.0F);
    }

    @Override
    public float getStrVsBlock(ItemStack stack, Block block)
    {
        return (block != null) && ((block.blockMaterial == Material.wood) || (block.blockMaterial == Material.plants) || (block.blockMaterial == Material.vine)) ? efficiencyOnProperMaterial
                : super.getStrVsBlock(stack, block);
    }
}