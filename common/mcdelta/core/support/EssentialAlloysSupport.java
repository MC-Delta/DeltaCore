package mcdelta.core.support;

import mcdelta.core.material.ToolMaterial;

public class EssentialAlloysSupport implements LimitedModSupport
{
     public static ToolMaterial BRONZE;
     public static ToolMaterial MAGIC;
     public static ToolMaterial STEEL;
     
     
     
     
     @Override
     public String modid ()
     {
          return "essentialalloys";
     }
     
     
     
     
     @Override
     public void modLoaded ()
     {
          BRONZE = new ToolMaterial(new Object[]
          { "bronze", 0xd3b838, "ingotBronze", true, true, false }, new Object[]
          { 2, 418, 9.0F, 2.0F, 22 }, null);
          MAGIC = new ToolMaterial(new Object[]
          { "magic", 0x7340ad, "ingotMagic", true, true, true }, new Object[]
          { 3, 205, 12.0F, 1.0F, 44 }, null);
          STEEL = new ToolMaterial(new Object[]
          { "steel", 0x637080, "ingotSteel", true, true, false }, new Object[]
          { 4, 1111, 4.0F, 3.0F, 12 }, new Object[]
          { 33, new int[]
          { 3, 8, 6, 3 }, 10 });
     }
     
}