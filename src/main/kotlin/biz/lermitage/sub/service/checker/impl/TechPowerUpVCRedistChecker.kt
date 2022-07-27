package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.Category
import biz.lermitage.sub.model.Logo
import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

/**
 * Visual C++ Redistributable Runtimes All-in-One checker.
 */
@Service
class TechPowerUpVCRedistChecker :
    TechPowerUpChecker(
        "Visual C++ Redistributable Runtimes All-in-One",
        "https://www.techpowerup.com/download/visual-c-redistributable-runtime-package-all-in-one/",
        listOf(Category.REDIST.label, Category.MS_WINDOWS.label),
        Logo.VC_REDIST
    ), Checker
