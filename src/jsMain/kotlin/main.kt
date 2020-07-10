@JsName("encode")
@ExperimentalStdlibApi
fun js_encode(control: String, nexthex: String): Array<Int>? {
    val next = if (nexthex.isEmpty()) 0 else try {
        nexthex.toInt(16)
    } catch (e: NumberFormatException) {
        return null
    }
    return encode(control, next)
}

@JsName("decode")
fun js_decode(hex: String): Pair<Array<String>, Int>? {
    val code = try {
        hex.toLong(16)
    } catch (e: NumberFormatException) {
        return null
    }
    return decode(code)
}
