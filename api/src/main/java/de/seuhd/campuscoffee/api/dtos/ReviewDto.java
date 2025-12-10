package de.seuhd.campuscoffee.api.dtos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * DTO record for POS metadata.
 */
@Builder(toBuilder = true)
public record ReviewDto (
    @Nullable Long id,

    @Nullable LocalDateTime createdAt,
    @Nullable LocalDateTime updatedAt,

    @NotNull
    @NonNull Long posId,
    @NotNull
    @NonNull Long authorId,
    @NotBlank
    @NonNull String review,
    @Nullable Boolean approved
) implements Dto<Long> {
    @Override
    public @Nullable Long getId() {
        return id;
    }
}
