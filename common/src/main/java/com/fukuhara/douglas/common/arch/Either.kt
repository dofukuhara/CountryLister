package com.fukuhara.douglas.common.arch

/*
    Either structure will be used as a coroutine response body:
    - If Either is from type "Left", it means that the response from the coroutine call presented and error,
       and the error can be obtained via `exception` attribute
    - If Either is from type "Right", it means that the response from the coroutine call was successful,
       and the value can be obtained via `data` attribute
 */
sealed class Either<out L, out R> {
    data class Left<out L, Nothing>(val exception: L) : Either<L, Nothing>()
    data class Right<Nothing, out R>(val data: R) : Either<Nothing, R>()
}

fun <L> L.left() = Either.Left<L, Nothing>(this)
fun <R> R.right() = Either.Right<Nothing, R>(this)
