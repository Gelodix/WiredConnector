package com.gelo.wiredconnector;

import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue CHANNELS_NUMBER = BUILDER
            .comment("The number of channel the wired connector will have.\nPlease, avoid editing this value afterward")
            .defineInRange("channelsNumber", 20, 1, 100);

    static final ModConfigSpec SPEC = BUILDER.build();

}
