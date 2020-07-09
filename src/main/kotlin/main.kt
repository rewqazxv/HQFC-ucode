val bits_all = arrayListOf(
    "NC1", "NC0", "TJ", "CN#", "M", "S3", "S2", "S1", "S0",
    "M1", "LDDR1", "WRD", "LRW", "CEL", "ALU_BUS", "RS_BUS",
    "SW_BUS", "IAR_BUS", "LDER", "M3", "AR1_INC", "LDAR1",
    "LDIAR", "M4", "PC_INC", "PC_ADD", "LDPC", "LDIR", "INTC",
    "INTS", "P3", "P2", "P1", "P0"
)

@ExperimentalStdlibApi
@JsName("encode")
fun encode(control: String, next: Int): Array<Int>? {
    if (next > 0x3f) return null

    val conset = control.split(",").map { it.trim().toUpperCase() }.toSet()
    var code = 0L
    for (i in bits_all) {
        if (conset.contains(i) || conset.contains(i.replace("_", ""))) {
            code = code or 1
        }
        code = code shl 1
    }
    if (code.countOneBits() != conset.size) return null
    code = (code shl (6 - 1)) or ((next and 0x3f).toLong())

    val res = ArrayList<Int>()
    repeat(5) {
        res.add((code and 0xff).toInt())
        code = code ushr 8
    }
    return res.reversed().toTypedArray()
}

@JsName("decode")
fun decode(hex: String): Pair<Array<String>, Int>? {
    var bits = try {
        hex.toLong(16)
    } catch (e: NumberFormatException) {
        return null
    }
    val next = (bits and 0x3f).toInt()

    bits = bits ushr 6
    val control = ArrayList<String>()
    for (i in bits_all.reversed()) {
        if ((bits and 1) == 1L) {
            control.add(i)
        }
        bits = bits ushr 1
    }

    return Pair(control.reversed().toTypedArray(), next)
}
