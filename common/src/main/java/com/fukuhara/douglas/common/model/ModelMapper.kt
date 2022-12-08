package com.fukuhara.douglas.common.model

/*
    This Mapper structure will be used to transform VO objects that were received from Rest Call
    into a Model object, that the system will consume
 */
interface ModelMapper<IN, OUT> {
    fun transform(voData: IN) : OUT
}