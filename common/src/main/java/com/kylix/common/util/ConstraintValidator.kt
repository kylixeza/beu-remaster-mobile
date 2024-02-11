package com.kylix.common.util

import androidx.viewbinding.ViewBinding

interface ConstraintValidator<VB: ViewBinding> {

    fun VB.validate()

}