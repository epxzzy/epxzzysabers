package com.epxzzy.epxzzysabers.utils;

public class Rotation {
    public float x, y, z;

    public Rotation(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Rotation lerp(Rotation a, Rotation b, float t) {
        float newX = a.x + (b.x - a.x) * t;
        float newY = a.y + (b.y - a.y) * t;
        float newZ = a.z + (b.z - a.z) * t;
        return new Rotation(newX, newY, newZ);
    }
}
