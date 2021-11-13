package com.galvanize.prodman.util;

import com.google.common.base.Strings;

import java.util.Optional;

/**
 * @author Krishna Sadasivam
 */
public final class EnumMapper {

    public static <T extends Enum<T>> Optional<T> getEnumFromString(Class<T> c, String s) {
        if (!Strings.isNullOrEmpty(s)) {
            try {
                return Optional.of(Enum.valueOf(c, s.trim().toUpperCase()));
            } catch (IllegalArgumentException e) {
            }
        }

        return Optional.empty();
    }
}
