package io.github.bohdanzhuvak.onlinestore.user.infrastructure.adapter.in.rest.resource;

public record UpdateProfileRequest(
    String firstName,
    String lastName
) {
}
