package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

/**
 * Spring Boot update checker.
 */
@Service
class SpringBootChecker : GenericJsonPathChecker(
    "https://start.spring.io/metadata/client",
    "\$.bootVersion.default",
    "Spring Boot",
    "https://start.spring.io"), Checker
