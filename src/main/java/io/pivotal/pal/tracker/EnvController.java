package io.pivotal.pal.tracker;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnvController {
  private String port, memoryLimit, cfInstanceIndex, cfInstanceAddr;

  public EnvController(
      @Value("${port:NOT SET}") String port,
      @Value("${memory.limit:NOT SET}") String memoryLimit,
      @Value("${cf.instance.index:NOT SET}") String cfInstanceIndex,
      @Value("${cf.instance.addr:NOT SET}") String cfInstanceAddr
  ) {
    this.port = port;
    this.memoryLimit = memoryLimit;
    this.cfInstanceIndex = cfInstanceIndex;
    this.cfInstanceAddr = cfInstanceAddr;
  }

  @GetMapping("/env")
  public Map<String, String> getEnv(){
    Map<String, String> toReturn = new HashMap<>();
    toReturn.put("PORT", port);
    toReturn.put("MEMORY_LIMIT", memoryLimit);
    toReturn.put("CF_INSTANCE_INDEX", cfInstanceIndex);
    toReturn.put("CF_INSTANCE_ADDR", cfInstanceAddr);
    return toReturn;
  }
}
