package fre.shown.tryhook.module.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Shaman
 * @date 2019/12/6 14:18
 */


@Configuration
@MapperScan("fre.shown.tryhook.module.*.dao")
public class ModuleConfig {
}
