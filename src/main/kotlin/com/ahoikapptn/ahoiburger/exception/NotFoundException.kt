package com.ahoikapptn.ahoiburger.exception

class NotFoundException(type: String, id: String) :
    RuntimeException("$type not found with id: $id")
