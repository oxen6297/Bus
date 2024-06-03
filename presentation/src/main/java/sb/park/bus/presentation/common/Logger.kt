package sb.park.bus.presentation.common

import timber.log.Timber

inline fun <reified T> T.debug(message: String) = Timber.d(message)
inline fun <reified T> T.error(message: String) = Timber.e(message)
inline fun <reified T> T.warn(message: String) = Timber.w(message)
inline fun <reified T> T.info(message: String) = Timber.i(message)
inline fun <reified T> T.error(exception: Exception) = Timber.e(exception)