package com.epxzzy.epxzzysabers.screen.components;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

public class MoveableSlot extends Slot {
    public MoveableSlot(Container pContainer, int pSlot, int pX, int pY) {
        super(pContainer, pSlot, pX, pY);
    }
    public int[] getPos(){
        return new int[]{this.x,this.y};
    }
    public void setPos(int newx,int newy){
       this.x = newx;
       this.y = newy;
    }
}
