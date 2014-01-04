package mcdelta.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import mcdelta.core.config.ConfigWrapper;
import mcdelta.core.config.IConfig;
import mcdelta.core.support.LimitedModSupport;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public abstract class ModDelta
{
     public static final List<ModDelta>    deltaMods      = new ArrayList<ModDelta>();
     protected ConfigWrapper               config;
     public static List<LimitedModSupport> limitedSupport = new ArrayList<LimitedModSupport>();
     
     
     
     
     public ModContainer mod ()
     {
          return Loader.instance().getIndexedModList().get(this.id());
     }
     
     
     
     
     public String id ()
     {
          return this.getClass().getAnnotation(Mod.class).modid();
     }
     
     
     
     
     public String name ()
     {
          return this.mod().getName();
     }
     
     
     
     
     public String version ()
     {
          return this.mod().getVersion();
     }
     
     
     
     
     public ConfigWrapper config ()
     {
          return this.config;
     }
     
     
     
     
     /**
      * @return IContent
      */
     public abstract IContent content ();
     
     
     
     
     public abstract void deltaInit (FMLPreInitializationEvent event);
     
     
     
     
     protected void initConfig (final FMLPreInitializationEvent evt)
     {
          // Get all the files
          final File configFolder = new File(evt.getModConfigurationDirectory().getAbsolutePath() + "/MCDelta/");
          configFolder.mkdirs();
          final File configFile = new File(configFolder.getAbsolutePath() + "/" + this.name() + ".cfg");
          
          // Create the config handler
          this.config = new ConfigWrapper();
          
          // Set the Configuration inside the Handler
          this.config.setConfiguration(new Configuration(configFile, true));
     }
     
     
     
     
     protected void init (final FMLPreInitializationEvent evt)
     {
          this.init(evt, null);
     }
     
     
     
     
     /**
      * THIS SHOULD ONLY BE CALLED BY DELTA CORE
      */
     protected static void loadDeltaMods (FMLPreInitializationEvent event)
     {
          for (final ModContainer mod : Loader.instance().getIndexedModList().values())
          {
               if (mod.getMod() != null)
               {
                    if (mod.getMod() instanceof ModDelta)
                    {
                         deltaMods.add((ModDelta) mod.getMod());
                         ((ModDelta) mod.getMod()).deltaInit(event);
                    }
               }
          }
     }
     
     
     
     
     protected void init (final FMLPreInitializationEvent evt, final IConfig config)
     {
          this.initConfig(evt);
          if (config != null)
          {
               config.init(this.config);
          }
          
          content().addContent();
          content().addRecipes();
     }
     
     
     
     
     /**
      * A simple if statement to check if a mod is loaded. Should NOT be used
      * when a API is required. Use the CompatibilityHandler (thanks Captain)
      * for that.
      */
     protected void doLimitedModSupport (final LimitedModSupport modSupport)
     {
          if (Loader.isModLoaded(modSupport.modid()))
          {
               limitedSupport.add(modSupport);
               modSupport.preInit();
          }
     }
}
