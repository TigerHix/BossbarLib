package me.tigerhix.lib.bossbar.common;

public final class Maths {

    private Maths() {
    }

    public static <T extends Comparable<T>> T clamp(T value, T min, T max) {
        if (value.compareTo(min) < 0) return min;
        else if (value.compareTo(max) > 0) return max;
        else return value;
    }

}
