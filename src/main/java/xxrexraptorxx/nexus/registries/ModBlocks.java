package xxrexraptorxx.nexus.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import xxrexraptorxx.nexus.blocks.NexusBlock;
import xxrexraptorxx.nexus.blocks.SecurityBarrier;
import xxrexraptorxx.nexus.blocks.SecurityWall;
import xxrexraptorxx.nexus.blocks.SupplyCrate;
import xxrexraptorxx.nexus.main.References;

import java.util.function.Function;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(References.MODID);


    public static void init(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }


    public static final DeferredBlock<NexusBlock> NEXUS_RED = registerBlock("nexus_red",
            properties -> new NexusBlock(properties.strength(100.0F, 5000.0F).sound(SoundType.METAL).mapColor(MapColor.DIAMOND).lightLevel(value -> 10).noOcclusion()
                    .requiresCorrectToolForDrops().instrument(NoteBlockInstrument.DRAGON).pushReaction(PushReaction.BLOCK)));

    public static final DeferredBlock<NexusBlock> NEXUS_BLUE = registerBlock("nexus_blue",
            properties -> new NexusBlock(properties.strength(100.0F, 5000.0F).sound(SoundType.METAL).mapColor(MapColor.DIAMOND).lightLevel(value -> 10).noOcclusion()
                    .requiresCorrectToolForDrops().instrument(NoteBlockInstrument.DRAGON).pushReaction(PushReaction.BLOCK)));

    public static final DeferredBlock<NexusBlock> NEXUS_GREEN = registerBlock("nexus_green",
            properties -> new NexusBlock(properties.strength(100.0F, 5000.0F).sound(SoundType.METAL).mapColor(MapColor.DIAMOND).lightLevel(value -> 10).noOcclusion()
                    .requiresCorrectToolForDrops().instrument(NoteBlockInstrument.DRAGON).pushReaction(PushReaction.BLOCK)));

    public static final DeferredBlock<NexusBlock> NEXUS_YELLOW = registerBlock("nexus_yellow",
            properties -> new NexusBlock(properties.strength(100.0F, 5000.0F).sound(SoundType.METAL).mapColor(MapColor.DIAMOND).lightLevel(value -> 10).noOcclusion()
                    .requiresCorrectToolForDrops().instrument(NoteBlockInstrument.DRAGON).pushReaction(PushReaction.BLOCK)));

    public static final DeferredBlock<NexusBlock> NEXUS_WHITE = registerBlock("nexus_white",
            properties -> new NexusBlock(properties.strength(100.0F, 5000.0F).sound(SoundType.METAL).mapColor(MapColor.DIAMOND).lightLevel(value -> 10).noOcclusion()
                    .requiresCorrectToolForDrops().instrument(NoteBlockInstrument.DRAGON).pushReaction(PushReaction.BLOCK)));

    public static final DeferredBlock<NexusBlock> NEXUS_BLACK = registerBlock("nexus_black",
            properties -> new NexusBlock(properties.strength(100.0F, 5000.0F).sound(SoundType.METAL).mapColor(MapColor.DIAMOND).lightLevel(value -> 10).noOcclusion()
                    .requiresCorrectToolForDrops().instrument(NoteBlockInstrument.DRAGON).pushReaction(PushReaction.BLOCK)));

    public static final DeferredBlock<SupplyCrate> SUPPLY_CRATE = registerBlock("supply_crate", properties -> new SupplyCrate(
            properties.strength(0.1F, 0.0F).sound(SoundType.WOOD).lightLevel(value -> 6).mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS)));

    public static final DeferredBlock<SecurityWall> SECURTIY_WALL = registerBlock("security_wall",
            properties -> new SecurityWall(properties.strength(-1.0F, 3600000.0F).sound(SoundType.METAL).mapColor(MapColor.COLOR_LIGHT_GRAY).requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BELL).pushReaction(PushReaction.BLOCK).noLootTable()));

    public static final DeferredBlock<SecurityBarrier> SECURTIY_BARRIER = registerBlock("security_barrier",
            properties -> new SecurityBarrier(properties.strength(-1.0F, 3600000.0F).mapColor(MapColor.COLOR_LIGHT_BLUE).requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASS).pushReaction(PushReaction.BLOCK).noLootTable().lightLevel(value -> 6).noOcclusion()));


    public static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> blockCreator) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, () -> blockCreator.apply(BlockBehaviour.Properties.of().setId(blockId(name))));
        registerBlockItems(name, toReturn);
        return toReturn;
    }


    public static <T extends Block> void registerBlockItems(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().setId(ModItems.itemId(name)).useBlockDescriptionPrefix()));
    }


    public static ResourceKey<Block> blockId(String name) {
        return ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(References.MODID, name));
    }

}
