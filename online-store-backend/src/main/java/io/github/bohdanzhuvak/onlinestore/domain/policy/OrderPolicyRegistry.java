package io.github.bohdanzhuvak.onlinestore.domain.policy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderPolicyRegistry {

  private final Map<String, OrderPolicy> policies = new HashMap<>();

  public OrderPolicyRegistry(List<OrderPolicy> policyList) {
    for (OrderPolicy policy : policyList) {
      policies.put(policy.getType(), policy);
    }
  }

  public OrderPolicy getPolicy(String type) {
    if (!policies.containsKey(type)) {
      throw new IllegalArgumentException("No policy for type: " + type);
    }
    return policies.get(type);
  }
}
