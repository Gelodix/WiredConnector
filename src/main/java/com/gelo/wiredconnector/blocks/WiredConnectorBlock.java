package com.gelo.wiredconnector.blocks;

import com.gelo.wiredconnector.Config;
import edn.stratodonut.drivebywire.wire.MultiChannelWireSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class WiredConnectorBlock extends Block implements MultiChannelWireSource, EntityBlock{
    private List<String> channels_list = new ArrayList<>();
    public WiredConnectorBlock(Properties props) {
        super(props);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new WiredConnectorBlockEntity(blockPos, blockState);
    }

    @Override
    public List<String> wire$getChannels() {
        return getChannels();
    }

    @Override
    public String wire$nextChannel(String currentChannel, boolean forward) {
        List<String> channels = getChannels();
        int currentChannelIndex = channels.indexOf(currentChannel);
        if (currentChannelIndex == -1) return channels.getFirst();

        if (forward) {
            if (currentChannelIndex == channels.size() - 1) return channels.getFirst();
            else return channels.get(currentChannelIndex + 1);
        } else {
            if (currentChannelIndex == 0) return channels.getLast();
            else return channels.get(currentChannelIndex - 1);
        }
    }

    private List<String> getChannels(){
        if (channels_list.isEmpty()){
            int max_channels = Config.CHANNELS_NUMBER.getAsInt();
            for (int count = 1; count < max_channels + 1; count++){
                channels_list.add("channel_" + count);
            }
        }
        return channels_list;
    }
}
