package com.yellowbrossproductions.towerdefenseunits.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class TowerDefenseConfig {
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> mobAngererList;
    public static ForgeConfigSpec.BooleanValue mobAngererlist_whiteorblack;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> unitTargetList;
    public static ForgeConfigSpec.BooleanValue unitTargetList_whiteorblack;

    public static ForgeConfigSpec.BooleanValue cameraShakesAllowed;
    public static ForgeConfigSpec.BooleanValue mend_allowHeartEffect;
    public static ForgeConfigSpec.IntValue plaguingTurret_splatLimit;

    public static void init(ForgeConfigSpec.Builder common, ForgeConfigSpec.Builder client) {
        client.push("Client Settings");

        cameraShakesAllowed = client
                .comment("Setting this to false will disable camera shakes.")
                .define("cameraShakesAllowed", true);

        mend_allowHeartEffect = client
                .comment("Controls if the heart visual effect floating above Mend should be shown. Set this to false if you are facing lag issues with Mends.")
                .define("mend_allowHeartEffect", true);

        plaguingTurret_splatLimit = common
                .comment("The amount of Plaguing Turrets that can be near each other before their splat textures will stop rendering.",
                        "Depending on your computer's strength, you may want to lower or increase this number.")
                .defineInRange("plaguingTurret_splatLimit", 150, 0, Integer.MAX_VALUE);

        client.pop();

        common.push("Units");
        unitTargetList = common
                .comment("Hostile Mobs that Units are not allowed to target whatsoever are put here.",
                        "Putting mobs that grief such as Creepers here is recommended",
                        "Format must be like 'examplemod:entity'. Example: \"minecraft:zombie\" if you want a specific entity, or just \"minecraft\" for a whole mod",
                        "You can use the /summon command to scroll through and find the IDs for mobs you want!",
                        "Requires game restart")
                .defineList("unitTargetList", List.of(
                        "minecraft:creeper"
                ), String.class::isInstance);

        unitTargetList_whiteorblack = common
                .comment("Should the game treat the Unit targeting list as a Whitelist or a Blacklist?",
                        "'True' for Whitelist, 'False' for Blacklist",
                        "Requires game restart")
                .define("unitTargetList_whiteorblack", false);

        common.push("Angerer");
        mobAngererList = common
                .comment("Hostile Mobs put here will not become automatically-aggro towards Angerers.",
                        "Format must be like 'examplemod:entity'. Example: \"minecraft:zombie\" if you want a specific entity, or just \"minecraft\" for a whole mod",
                        "You can use the /summon command to scroll through and find the IDs for mobs you want!",
                        "Requires game restart")
                .defineList("mobAngererList", List.of(
                        "minecraft:creeper"
                ), String.class::isInstance);

        mobAngererlist_whiteorblack = common
                .comment("Should the game treat the Angerer mob list as a Whitelist or a Blacklist?",
                        "'True' for Whitelist, 'False' for Blacklist",
                        "Requires game restart")
                .define("mobAngererlist_whiteorblack", false);
        common.pop();
        common.pop();
    }
}
