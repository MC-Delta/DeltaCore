package mcdelta.core.item;

import mcdelta.core.ModDelta;
import mcdelta.core.material.ToolMaterial;
import net.minecraft.block.Block;
import net.minecraft.item.ItemSpade;

public class ItemDeltaShovel extends ItemDeltaTool
{

    public ItemDeltaShovel(ModDelta m, ToolMaterial mat)
    {
        super(m, "shovel", mat, ItemSpade.blocksEffectiveAgainst, 1.0F);
    }

    @Override
    public boolean canHarvestBlock(Block block)
    {
        return block == Block.snow ? true : block == Block.blockSnow;
    }
}
