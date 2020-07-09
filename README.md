# 微指令计算器

适用于 HQFC-BY 计算机组成原理仿真系统和 TEC-9 计算机组成原理实验系统的微指令翻译器，实现十六进制微指令代码表示和控制标志+下址表示的转换。

支持的标志有：NC1, NC0, TJ, CN#, M, S3, S2, S1, S0, M1, LDDR1, WRD, LRW, CEL, ALU_BUS,RS_BUS, SW_BUS, IAR_BUS, LDER, M3, AR1_INC, LDAR1, LDIAR, M4, PC_INC, PC_ADD, LDPC,LDIR, INTC, INTS, P3, P2, P1, P0。逗号分隔，不区分大小写，下划线可忽略；字段存在表示该位为 1。十六进制微指令代码中可以有任意数量的空格分隔。

预编译的版本在 `docs` 文件夹下，可通过 https://rewqazxv.github.io/HQFC-ucode/ 访问。
