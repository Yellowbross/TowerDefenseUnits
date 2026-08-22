package com.yellowbrossproductions.towerdefenseunits.entities.units;

import com.yellowbrossproductions.towerdefenseunits.entities.AbstractUnit;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.level.Level;

public class Angerer extends AbstractUnit {

    public Angerer(EntityType<? extends AbstractGolem> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


}
