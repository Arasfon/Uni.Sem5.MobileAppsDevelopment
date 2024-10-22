package com.arasfon.uni.sem5.drivenext.domain.models.validation

typealias Validator<TValue, TError> = (TValue) -> ValidationResult<TError>
