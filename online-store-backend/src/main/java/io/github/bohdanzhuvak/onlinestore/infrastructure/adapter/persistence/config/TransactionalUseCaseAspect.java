package io.github.bohdanzhuvak.onlinestore.infrastructure.adapter.persistence.config;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@RequiredArgsConstructor
public class TransactionalUseCaseAspect {

  private final TransactionalUseCaseExecutor transactionalUseCaseExecutor;

  @SneakyThrows
  private static Object proceed(ProceedingJoinPoint joinPoint) {
    return joinPoint.proceed();
  }

  @Pointcut("@within(useCase)")
  void inUseCase(UseCase useCase) {

  }

  @Around("inUseCase(useCase)")
  Object useCase(ProceedingJoinPoint proceedingJoinPoint, UseCase useCase) {
    return transactionalUseCaseExecutor.executeInTransaction(() -> proceed(proceedingJoinPoint));
  }
}
