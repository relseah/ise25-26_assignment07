package de.seuhd.campuscoffee.api.controller;

import de.seuhd.campuscoffee.api.dtos.ReviewDto;
import de.seuhd.campuscoffee.api.mapper.DtoMapper;
import de.seuhd.campuscoffee.api.mapper.ReviewDtoMapper;
import de.seuhd.campuscoffee.api.openapi.CrudOperation;
import de.seuhd.campuscoffee.domain.model.objects.Review;
import de.seuhd.campuscoffee.domain.ports.api.CrudService;
import de.seuhd.campuscoffee.domain.ports.api.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static de.seuhd.campuscoffee.api.openapi.Operation.*;
import static de.seuhd.campuscoffee.api.openapi.Resource.REVIEW;

/**
 * Controller for handling reviews for POS, authored by users.
 */
@Tag(name = "Reviews", description = "Operations for managing reviews for points of sale.")
@Controller
@RequestMapping("/api/reviews")
@Slf4j
@RequiredArgsConstructor
public class ReviewController extends CrudController<Review, ReviewDto, Long> {
    private final ReviewService reviewService;
    private final ReviewDtoMapper reviewDtoMapper;

    @Override
    protected @NonNull CrudService<Review, Long> service() {
        return reviewService;
    }

    @Override
    protected @NonNull DtoMapper<Review, ReviewDto> mapper() {
        return reviewDtoMapper;
    }

    @Operation
    @CrudOperation(operation = GET_ALL, resource = REVIEW)
    @GetMapping("")
    public @NonNull ResponseEntity<List<ReviewDto>> getAll() {
        return super.getAll();
    }

    // TODO: Implement the missing methods/endpoints.
    @CrudOperation(operation = GET_BY_ID, resource = REVIEW)
    @GetMapping("/{id}")
    public @NonNull ResponseEntity<ReviewDto> getById(@Parameter(description = "Unique identifier of the review to retrieve.", required = true)
                                                      @PathVariable Long id) {
        return super.getById(id);
    }

    @CrudOperation(operation = CREATE, resource = REVIEW)
    @PostMapping("")
    public @NonNull ResponseEntity<ReviewDto> create(@Parameter(description = "Data of the review to create.", required = true)
                                                     @RequestBody @Valid ReviewDto dto) {
        return super.create(dto);
    }

    @CrudOperation(operation = UPDATE, resource = REVIEW)
    @PutMapping("/{id}")
    public @NonNull ResponseEntity<ReviewDto> update(@Parameter(description = "Unique identifier of the review to update.", required = true) @PathVariable long id, @Parameter(description = "Data of the review to update.", required = true) @RequestBody @Valid ReviewDto dto) {
        return super.update(id, dto);
    }

    @CrudOperation(operation = DELETE, resource = REVIEW)
    @DeleteMapping("/{id}")
    public @NonNull ResponseEntity<Void> delete(@Parameter(description = "Unique identifier of the review to delete.", required = true) @PathVariable long id) {
        return super.delete(id);
    }

    @CrudOperation(operation = FILTER, resource = REVIEW)
    @GetMapping("/filter")
    public @NonNull ResponseEntity<List<ReviewDto>> filter(
            @RequestParam("pos_id") Long posId,
            @RequestParam("approved") Boolean approved
    ) {
        return ResponseEntity.ok(reviewService.filter(posId, approved).stream().map(reviewDtoMapper::fromDomain).toList());
    }

    @PutMapping("/{id}/approve")
    public @NonNull ResponseEntity<ReviewDto> approve(
            @Parameter(description = "Unique identifier of the review to be approved.", required = true) @PathVariable Long id,
            @Parameter(description = "Unique identifier of the user approving the review.", required = true) @RequestParam("user_id") Long userId
    ) {
        return ResponseEntity.ok(reviewDtoMapper.fromDomain(reviewService.approve(reviewService.getById(id), userId)));
    }

}
