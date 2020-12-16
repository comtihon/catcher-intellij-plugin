package com.github.comtihon.catcherintellijplugin.exceptions


open class BusinessException(msg: String) : RuntimeException(msg)

class ExecutionFailedException(msg: String) : BusinessException(msg)