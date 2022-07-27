package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.Category
import biz.lermitage.sub.model.Logo
import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

/**
 * Intel Bluetooth Drivers checker.
 */
@Service
class TechPowerUpIntelBluetoothDriversChecker :
    TechPowerUpChecker(
        "Intel Bluetooth Drivers",
        "https://www.techpowerup.com/download/intel-wireless-bluetooth-drivers/",
        listOf(Category.DRIVERS.label, Category.MS_WINDOWS.label),
        Logo.INTEL
    ), Checker
