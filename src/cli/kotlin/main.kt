import java.io.File

@ExperimentalStdlibApi
fun convert(sl: List<String>): Array<Array<Int>> {
    val res = Array(128) { Array(5) { 0 } }
    sl.forEachIndexed { i, s ->
        if (s[0] != '#') {
            val word = s.replace("\\s+".toRegex(), "").split(";").toMutableList()
            word.remove("")
            if (word.size != 3) throw Exception("Format error at line ${i + 1}:\nRaw:\t$s\nParse:\t$word")

            val addr = word[0].toInt(16)
            val control = word[1]
            val next = word[2].toInt(16)

            val code =
                encode(control, next) ?: throw Exception("Encode error at line ${i + 1}:\nRaw:\t$s\nParse:\t$word")
            res[addr] = code
        }
    }
    return res;
}

@ExperimentalStdlibApi
fun fileio(input: File) {
    val path = input.parent
    val basename = input.nameWithoutExtension

    val res = convert(input.useLines { it.toList() })

    val buffer = Array(5) { ByteArray(128) }
    repeat(5) { i ->
        buffer[i] = res.map { it[i].toByte() }.toByteArray()
    }

    repeat(5) {
        File(File(path), "${basename}_${it}.bin").writeBytes(buffer[it])
    }
}

@ExperimentalStdlibApi
fun main(args: Array<String>) {
    val filename = try {
        args[0]
    } catch (e: ArrayIndexOutOfBoundsException) {
        print("Input file path: ")
        readLine()!!
    }
    fileio(File(filename))
}
