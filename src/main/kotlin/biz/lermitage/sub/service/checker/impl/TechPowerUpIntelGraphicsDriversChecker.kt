package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.Category
import biz.lermitage.sub.model.Logo
import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

/**
 * ThrottleStop checker.
 */
@Service
class TechPowerUpIntelGraphicsDriversChecker :
    TechPowerUpChecker(
        "Intel Graphics Drivers",
        "https://www.techpowerup.com/download/intel-integrated-graphics-drivers/",
        listOf(Category.DRIVERS.label, Category.MS_WINDOWS.label),
        Logo.INTEL
    ), Checker
