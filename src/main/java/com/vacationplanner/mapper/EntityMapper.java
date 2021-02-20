package com.vacationplanner.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface EntityMapper<E, M> {
    E toEntity(M model);

    M toModel(E entity);

    default List<E> toEntities(List<M> models) {
        return models.stream().map(this::toEntity).collect(Collectors.toCollection(ArrayList::new));
    }

    default List<M> toModels(List<E> entities) {
        return entities.stream().map(this::toModel).collect(Collectors.toCollection(ArrayList::new));
    }
}
