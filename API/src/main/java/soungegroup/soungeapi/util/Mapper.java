package soungegroup.soungeapi.util;

import org.modelmapper.ModelMapper;

public final class Mapper {
    private Mapper() {
        throw new IllegalStateException();
    }

    public static final ModelMapper INSTANCE = new ModelMapper();
}
