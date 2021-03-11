package com.example.movieappcompose.data.persistence

import io.reactivex.rxjava3.core.Observable
import timber.log.Timber


abstract class PersistenceWithParam<IN, MODEL, API_MODEL> {
    open fun get(param: IN): Observable<MODEL> = getLocal(param)
            .doOnSubscribe {
                Timber.d("subscribed to local")
            }
            .concatWith(getRemoteAndSave(param).doOnSubscribe {
                Timber.d("subscribed to remote")
            })

    private fun getRemoteAndSave(param: IN): Observable<MODEL> = getRemote(param).saveInDb(param)

    protected abstract fun getLocal(param: IN): Observable<MODEL>
    protected abstract fun getRemote(param: IN): Observable<API_MODEL>
    abstract fun Observable<API_MODEL>.saveInDb(param: IN): Observable<MODEL>
}


abstract class Persistence<MODEL, API_MODEL> {
    open fun get(): Observable<MODEL> = getLocal().concatWith(getRemoteAndSave())
    private fun getRemoteAndSave(): Observable<MODEL> = getRemote().saveInDb()

    protected abstract fun getLocal(): Observable<MODEL>
    protected abstract fun getRemote(): Observable<API_MODEL>
    abstract fun Observable<API_MODEL>.saveInDb(): Observable<MODEL>
}