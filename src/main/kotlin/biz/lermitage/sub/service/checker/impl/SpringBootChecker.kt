package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.Category
import biz.lermitage.sub.model.Logo
import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

/**
 * Spring Boot update checker.
 */
@Service
class SpringBootChecker : GenericJsonPathChecker(
    listOf(Category.JAVA.label, Category.LIBRARY.label, Category.SPRING_FRAMEWORK.label),
    "https://start.spring.io/metadata/client",
    "\$.bootVersion.default",
    "Spring Boot",
    "https://start.spring.io",
    Logo.SPRING_BOOT), Checker
