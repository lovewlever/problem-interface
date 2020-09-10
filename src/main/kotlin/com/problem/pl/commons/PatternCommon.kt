package com.problem.pl.commons

import java.util.regex.Pattern

object PatternCommon {
    val PATTERN_PHONE = Pattern.compile("^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$")
    val PATTERN_EMAIL = Pattern.compile("^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})\$")
}