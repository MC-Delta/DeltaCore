package mcdelta.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mcdelta.core.config.CoreConfig;
import mcdelta.core.event.ExtraTooltipInfo;
import mcdelta.core.logging.Logger;
import mcdelta.core.material.MaterialRegistry;
import mcdelta.core.network.PacketHandler;
import mcdelta.core.proxy.CommonProxy;
import mcdelta.core.support.LimitedModSupport;
import mcdelta.core.support.SupportEssentialAlloys;
import mcdelta.core.support.SupportThaumcraft;
import mcdelta.core.support.compatibility.CompatibilityHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod (modid = DeltaCore.MOD_ID, useMetadata = true)
@NetworkMod (clientSideRequired = true, serverSideRequired = false, channels =
{ DeltaCore.MOD_ID }, packetHandler = PacketHandler.class)
public class DeltaCore extends ModDelta
{
     // TODO TOP PRIORITY
     //
     // TODO (possible ideas)
     // - camo creeper
     // - timed potion to prevent potion effects
     
     public static final String            MOD_ID               = "deltacore";
     
     @Instance (MOD_ID)
     public static DeltaCore               instance;
     
     @SidedProxy (clientSide = "mcdelta.core.proxy.ClientProxy", serverSide = "mcdelta.core.proxy.CommonProxy")
     public static CommonProxy             proxy;
     
     public static StringBuilder           localizationWarnings = new StringBuilder();
     
     public static Random                  rand                 = new Random();
     
     public static List<LimitedModSupport> limitedSupport       = new ArrayList<LimitedModSupport>();
     
     
     
     
     @Override
     public void deltaInit (FMLPreInitializationEvent event)
     {
          this.init(event, new CoreConfig());
     }
     
     
     
     
     @EventHandler
     public void preInit (final FMLPreInitializationEvent event)
     {
          Logger.logger = event.getModLog();
          localizationWarnings.append("The following are missing localized names: \n");
          
          loadDeltaMods(event);
          
          OreDictionary.registerOre("ingotIron", new ItemStack(Item.ingotIron));
          OreDictionary.registerOre("ingotGold", new ItemStack(Item.ingotGold));
          OreDictionary.registerOre("gemDiamond", new ItemStack(Item.diamond));
          
          MaterialRegistry.addVanillaMaterials();
          this.doLimitedModSupport(new SupportEssentialAlloys());
          this.doLimitedModSupport(new SupportThaumcraft());
     }
     
     
     
     
     @EventHandler
     public void load (final FMLInitializationEvent event)
     {
          MinecraftForge.setBlockHarvestLevel(Block.oreNetherQuartz, "pickaxe", 2);
          
          MinecraftForge.EVENT_BUS.register(new ExtraTooltipInfo());
     }
     
     
     
     
     @EventHandler
     public void postInit (final FMLPostInitializationEvent event)
     {
          if (localizationWarnings.length() > 45)
          {
               Logger.debug(localizationWarnings);
          }
          
          proxy.registerRenderers();
          
          for(LimitedModSupport support : limitedSupport)
          {
               support.postInit();
          }
          
          CompatibilityHandler.init();
     }
     
     
     
     
     /**
      * A simple if statement to check if a mod is loaded. Should NOT be used
      * when a API is required. Use the CompatibilityHandler (thanks Captain) for that.
      */
     private void doLimitedModSupport (final LimitedModSupport modSupport)
     {
          if (Loader.isModLoaded(modSupport.modid()))
          {
               limitedSupport.add(modSupport);
               modSupport.preInit();
          }
     }
     
     private final IContent content = new DeltaContent();
     
     
     
     
     @Override
     public IContent content ()
     {
          return this.content;
     }
     
     
}
