package xxrexraptorxx.nexus.utils;

import net.minecraft.util.StringRepresentable;

public enum NexusColors implements StringRepresentable {

    NONE("none"),
    RED("red"),
    BLUE("blue"),
    GREEN("green"),
    YELLOW("yellow"),
    WHITE("white"),
    BLACK("black");

    private final String name;

    private NexusColors(String pName) {
        this.name = pName;
    }


    public String toString() {
        return this.name;
    }


    public String getSerializedName() {
        return this.name;
    }

}
