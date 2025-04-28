package com.epxzzy.createsaburs.misc;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

public class MoveableSlot extends Slot {
    public int x;
    public int y;
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
