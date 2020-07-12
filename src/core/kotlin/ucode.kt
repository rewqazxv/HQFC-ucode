private val con_format = { s: String -> s.toUpperCase().replace("[ _#]".toRegex(), "") }
private val control_all = arrayListOf(
    "NC1", "NC0", "TJ", "CN#", "M", "S3", "S2", "S1", "S0",
    "M1", "LDDR1", "WRD", "LRW", "CEL", "ALU_BUS", "RS_BUS",
    "SW_BUS", "IAR_BUS", "LDER", "M3", "AR1_INC", "LDAR1",
    "LDIAR", "M4", "PC_INC", "PC_ADD", "LDPC", "LDIR", "INTC",
    "INTS", "P3", "P2", "P1", "P0"
).map(con_format)

@ExperimentalStdlibApi
fun encode(controls: String, next: Int): Array<Int>? {
    if (next > 0x3f) return null

    val conset = controls.split(",").map { con_format(it) }.toMutableSet()
    conset.removeAll(listOf(""))
    var code = 0L
    for (i in control_all) {
        if (conset.contains(i)) {
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

fun decode(hex: Long): Pair<Array<String>, Int> {
    var code = hex
    val next = (code and 0x3f).toInt()

    code = code ushr 6
    val control = ArrayList<String>()
    for (i in control_all.reversed()) {
        if ((code and 1) == 1L) {
            control.add(i)
        }
        code = code ushr 1
    }

    return Pair(control.reversed().toTypedArray(), next)
}
