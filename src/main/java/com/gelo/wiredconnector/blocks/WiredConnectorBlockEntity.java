package com.gelo.wiredconnector.blocks;

import com.gelo.wiredconnector.CCWiredConnector;
import edn.stratodonut.drivebywire.wire.WireNetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;

public class WiredConnectorBlockEntity extends BlockEntity {

    private final Map<String, Integer> channels = new HashMap<String, Integer>();

    public WiredConnectorBlockEntity(BlockPos pos, BlockState state) {
        super(CCWiredConnector.WIRED_CONNECTOR_BE.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        CompoundTag channelsTag = new CompoundTag();
        for (Map.Entry<String, Integer> entry : channels.entrySet()) {
            channelsTag.putInt(entry.getKey(), entry.getValue());
        }

        tag.put("Channels", channelsTag);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        if (tag.contains("Channels")) {
            CompoundTag channelsTag = tag.getCompound("Channels");
            channels.clear();
            for(String key : channelsTag.getAllKeys()) {
                channels.put(key, channelsTag.getInt(key));
            }
        }
    }

    public void setChannelValue(String channel, int value) {
        channels.put(channel, value);
        setChanged();

        if (this.level != null && !this.level.isClientSide()) {
            WireNetworkManager.trySetSignalAt(this.level, this.getBlockPos(), channel, value);
        }
    }

    public int getChannelValue(String channel) {
        return channels.getOrDefault(channel, 0);
    }
}
