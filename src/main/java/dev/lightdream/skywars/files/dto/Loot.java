package dev.lightdream.skywars.files.dto;

import dev.lightdream.skywars.utils.XMaterial;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
public class Loot {

    public int numberOfGuaranteedItems = 3;

    public List<ChanceItem> islandLoot = Arrays.asList(
            new ChanceItem(new Item(XMaterial.IRON_HELMET, 1), 50),
            new ChanceItem(new Item(XMaterial.IRON_CHESTPLATE, 1), 50),
            new ChanceItem(new Item(XMaterial.IRON_LEGGINGS, 1), 50),
            new ChanceItem(new Item(XMaterial.IRON_BOOTS, 1), 50),
            new ChanceItem(new Item(XMaterial.IRON_SWORD, 1), 50)
    );


    public List<ChanceItem> midLoot = Arrays.asList(
            new ChanceItem(new Item(XMaterial.DIAMOND_HELMET, 1), 50),
            new ChanceItem(new Item(XMaterial.DIAMOND_CHESTPLATE, 1), 50),
            new ChanceItem(new Item(XMaterial.DIAMOND_LEGGINGS, 1), 50),
            new ChanceItem(new Item(XMaterial.DIAMOND_BOOTS, 1), 50),
            new ChanceItem(new Item(XMaterial.DIAMOND_SWORD, 1), 50)
    );

    public List<Item> guaranteedIslandLoot = Arrays.asList(
            new Item(XMaterial.GOLDEN_HELMET, 1),
            new Item(XMaterial.GOLDEN_CHESTPLATE, 1),
            new Item(XMaterial.GOLDEN_LEGGINGS, 1),
            new Item(XMaterial.GOLDEN_BOOTS, 1),
            new Item(XMaterial.GOLDEN_SWORD, 1)
    );

    public List<Item> guaranteedMidLoot = Arrays.asList(
            new Item(XMaterial.GOLDEN_HELMET, 1),
            new Item(XMaterial.GOLDEN_CHESTPLATE, 1),
            new Item(XMaterial.GOLDEN_LEGGINGS, 1),
            new Item(XMaterial.GOLDEN_BOOTS, 1),
            new Item(XMaterial.GOLDEN_SWORD, 1)
    );

    public List<?> getByName(String name) {
        switch (name) {
            case "islandLoot":
                return islandLoot;
            case "modLoot":
                return midLoot;
            case "guaranteedIslandLoot":
                return guaranteedIslandLoot;
            case "guaranteedMidLoot":
                return guaranteedMidLoot;
        }
        return new ArrayList<>();
    }
}
