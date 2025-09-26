package io.github.bohdanzhuvak.onlinestore.legacy.infrastructurelegacy.config;

import io.github.bohdanzhuvak.onlinestore.domain.policy.BalanceChecker;
import io.github.bohdanzhuvak.onlinestore.domain.policy.CancelOrderPolicy;
import io.github.bohdanzhuvak.onlinestore.domain.policy.InitialOrderPolicy;
import io.github.bohdanzhuvak.onlinestore.domain.policy.OrderPolicy;
import io.github.bohdanzhuvak.onlinestore.domain.policy.OrderPolicyRegistry;
import io.github.bohdanzhuvak.onlinestore.domain.policy.PayOrderPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PolicyConfig {

  @Bean
  public PayOrderPolicy getPayOrderPolicy(BalanceChecker balanceChecker) {
    return new PayOrderPolicy(balanceChecker);
  }

  @Bean
  public InitialOrderPolicy getInitialOrderPolicy() {
    return new InitialOrderPolicy();
  }

  @Bean
  public CancelOrderPolicy getCancelOrderPolicy() {
    return new CancelOrderPolicy();
  }

  @Bean
  public OrderPolicyRegistry orderPolicyRegistry(List<OrderPolicy> policies) {
    return new OrderPolicyRegistry(policies);
  }
}
