package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.Category
import biz.lermitage.sub.model.Logo
import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

/**
 * ThrottleStop checker.
 */
@Service
class TechPowerUpIntelWiFiDriversChecker :
    TechPowerUpChecker(
        "Intel Wi-Fi Networking Drivers",
        "https://www.techpowerup.com/download/intel-wireless-networking-wifi-adapter-drivers/",
        listOf(Category.DRIVERS.label, Category.MS_WINDOWS.label),
        Logo.INTEL
    ), Checker
