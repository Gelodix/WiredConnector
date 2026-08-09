package com.gelo.wiredconnector.peripherals;

import com.gelo.wiredconnector.Config;
import com.gelo.wiredconnector.blocks.WiredConnectorBlockEntity;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import org.jetbrains.annotations.NotNull;

public class WiredConntectorPeripherals implements IPeripheral {
    private final WiredConnectorBlockEntity entity;

    public WiredConntectorPeripherals(WiredConnectorBlockEntity entity) {
        this.entity = entity;
    }

    // Defining device type name
    @Override
    public @NotNull String getType() {
        return "wired_connector";
    }

    @Override
    public boolean equals(@javax.annotation.Nullable IPeripheral iPeripheral) {
        return iPeripheral instanceof WiredConntectorPeripherals && this.entity == ((WiredConntectorPeripherals)iPeripheral).entity;
    }

    // utility function to format channels name
    private String createChannelName(int channel) {
        return "channel_" + channel;
    }

    private void checkChannel(int channel) throws LuaException {
        int max_channel = Config.CHANNELS_NUMBER.getAsInt();
        if (channel > max_channel || channel < 1) throw new LuaException("Channel id must be between 1 and " + max_channel);
    }

    // function used to set a channel value to 0 or 15
    // false for 0, true for 15
    @LuaFunction(mainThread = true)
    public void setOutput(int channel, boolean value) throws LuaException {
        checkChannel(channel);

        entity.setChannelValue(createChannelName(channel), value ? 15 : 0);
    }

    // function used to get a channel value
    // return true if the value is between 1 and 15 or false if the value equal 0
    @LuaFunction(mainThread = false)
    public boolean getOutput(int channel) throws LuaException {
        checkChannel(channel);

        return !(entity.getChannelValue(createChannelName(channel)) == 0);
    }

    // function used to set a channel to any value between 0 and 15
    @LuaFunction(mainThread = true)
    public void setAnalogOutput(int channel, int value) throws LuaException {
        checkChannel(channel);

        if (value < 16 && value >= 0) {
            entity.setChannelValue(createChannelName(channel), value);
        } else throw new LuaException("Analog output value must be between 0 and 15");
    }

    // function used to get a channel analog value
    // return the exact value of the channel
    @LuaFunction(mainThread = false)
    public int getAnalogOutput(int channel) throws LuaException {
        checkChannel(channel);

        return entity.getChannelValue(createChannelName(channel));
    }
}
