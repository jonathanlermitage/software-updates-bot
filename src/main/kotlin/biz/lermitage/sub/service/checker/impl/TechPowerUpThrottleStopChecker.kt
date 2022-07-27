package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.Category
import biz.lermitage.sub.model.Logo
import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

/**
 * ThrottleStop checker.
 */
@Service
class TechPowerUpThrottleStopChecker :
    TechPowerUpChecker(
        "ThrottleStop",
        "https://www.techpowerup.com/download/techpowerup-throttlestop/",
        listOf(Category.MS_WINDOWS.label),
        Logo.THROTTLESTOP
    ), Checker
