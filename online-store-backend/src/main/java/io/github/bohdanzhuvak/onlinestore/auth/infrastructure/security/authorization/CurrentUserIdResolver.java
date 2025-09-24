package io.github.bohdanzhuvak.onlinestore.auth.infrastructure.security.authorization;

import io.github.bohdanzhuvak.onlinestore.auth.infrastructure.security.UserPrincipal;
import io.github.bohdanzhuvak.onlinestore.shared.security.CurrentUserId;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CurrentUserIdResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(CurrentUserId.class)
        && parameter.getParameterType().equals(Long.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter,
                                ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest,
                                WebDataBinderFactory binderFactory) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !(auth.getPrincipal() instanceof UserPrincipal userPrincipal)) {
      throw new IllegalStateException("No authenticated user");
    }
    return userPrincipal.id();
  }
}
